package conexion;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Host implements Runnable {
    static int cuenta = 0;
    ServerSocket socket;
    PrintStream envio;
    BufferedReader recibe;
    ArrayList<Socket> clientesConectados = new ArrayList<>();
    Thread thread;

    public Host(int puerto) {
        try {
            socket = new ServerSocket(puerto);
        } catch (IOException e) {
            System.out.println("No se pudo crear el socket " + e);
        }
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        try {
            String str = "";
            Socket cliente = null;
            String finEspera = "";
            // Acepta la conexión de los clientes
            while (!finEspera.equals("\n")) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Esperando conexión...");
                cliente = socket.accept();

                // Cuenta la cantidad de clientes conectados
                cuenta++;
                System.out.println("Ha entrado el Cliente nº " + cuenta);
                clientesConectados.add(cliente);
                // Da capacidad de parar el bucle
                finEspera = sc.nextLine();
            }

            while (!str.equalsIgnoreCase("salir")) {

                // Crea los streams de entrada y salida
                if (cliente == null) {
                  throw new NullPointerException("Hay un cliente nulo");
                }
                recibe = new BufferedReader(new InputStreamReader((cliente.getInputStream())));
                envio = new PrintStream(cliente.getOutputStream());

                // Lee el mensaje del cliente
                str = recibe.readLine();
                if (str.equalsIgnoreCase("salir")) envio.println("Adiós");

                // Respuesta
                else {
                    JOptionPane.showMessageDialog(null, str);
                    envio.println(
                            JOptionPane.showInputDialog("Introduce tu mensaje al cliente:")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Ha ocurrido una excepción: " + e);
        } finally {
            try {
                for (Socket cliente: clientesConectados) {
                    cliente.close();
                }
            } catch (Exception e) {
                System.out.println("No se pudo cerrar la conexión " + e);
            }
        }
    }
}

package conexion;

import utilidades.Conectividad;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Cliente {
    PrintStream envio;
    BufferedReader recibe;
    Socket host;

    public Cliente(String ip, int puerto) throws Exception {
        if (Conectividad.comprobarIP(ip)) connectToServer(ip, puerto);
        else throw new Exception("IP no válida");
    }

    private void connectToServer(String ip, int puerto) {
        try {
            // Se crea la conexión con el servidor
            String str = "";
            host = new Socket(ip, puerto);

            // Se crean los Streams de entrada y salida
            recibe = new BufferedReader(new InputStreamReader((host.getInputStream())));
            envio = new PrintStream(host.getOutputStream());

            while (!str.equals("Adiós")) {
                str = JOptionPane.showInputDialog("Introduce tu mensaje al host:");
                if (str == null) envio.println();
                // Se envía el string al servidor
                else envio.println(str);

                // Se lee el mensaje del servidor
                str = recibe.readLine();

                // Mensaje del servidor:
                JOptionPane.showMessageDialog(null, str);
            }
        } catch (Exception e) {
            System.out.println("Exception " + e);
        }
    }

    public static void main(String[] args) throws Exception {
        new Cliente("192.168.12.239", 8080);
    }
}


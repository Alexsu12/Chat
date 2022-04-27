package utilidades;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Recepcion {
    private String recepcionHost(Socket cliente) {
        BufferedReader recibe;
        String mensaje = "";
        try {
            recibe = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            mensaje = recibe.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensaje;
    }

    private String recepcionCliente(Socket cliente) {
        BufferedReader recibe;
        String mensaje = "";
        try {
            recibe = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            mensaje = recibe.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensaje;
    }
    public static void recepcionClientes() {

    }
}

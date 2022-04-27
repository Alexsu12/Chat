package utilidades;

import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class Envio {
    public static void enviar(String mensaje, ArrayList<Socket> clientes) {
        PrintStream envio;
        for (Socket cliente : clientes) {
            try {
                envio = new PrintStream(cliente.getOutputStream());
                envio.println(mensaje);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

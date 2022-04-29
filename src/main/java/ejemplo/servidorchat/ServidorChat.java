package ejemplo.servidorchat;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServidorChat {
    public static void main(String[] args) {

        // Carga el archivo de configuracion de log4J
        PropertyConfigurator.configure("src/main/java/ejemplo/servidorchat/log4j.properties");
        Logger log = Logger.getLogger(ServidorChat.class);

        int puerto = 1234;
        int maximoConexiones = 10; // Maximo de conexiones simultaneas
        ServerSocket servidor = null;
        Socket socket = null;
        MensajesChat mensajes = new MensajesChat();

        try {
            // Se crea el serverSocket
            servidor = new ServerSocket(puerto, maximoConexiones);

            // Bucle infinito para esperar conexiones
            while (true) {
                log.info("Servidor a la espera de conexiones.");
                socket = servidor.accept();
                log.info("Cliente con la IP " + socket.getInetAddress().getHostName() + " conectado.");

                ConexionCliente cc = new ConexionCliente(socket, mensajes);
                cc.start();

            }
        } catch (IOException ex) {
            log.error("Error: " + ex.getMessage());
        } finally{
            try {
                socket.close();
                servidor.close();
            } catch (IOException ex) {
                log.error("Error al cerrar el servidor: " + ex.getMessage());
            }
        }
    }
}

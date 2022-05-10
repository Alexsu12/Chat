package ejemplo.servidorchat

import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator
import java.io.IOException
import java.net.ServerSocket
import java.net.Socket

object ServidorChat {
    @JvmStatic
    fun main(args: Array<String>) {

        // Carga el archivo de configuración de log4J
        PropertyConfigurator.configure("src/main/resources/log4j.properties")
        val log = Logger.getLogger(ServidorChat::class.java)
        val puerto = 1234
        val maximoConexiones = 10 // Máximo de conexiones simultaneas
        var servidor: ServerSocket? = null
        var socket: Socket? = null
        val mensajes = MensajesChat()
        try {
            // Se crea el serverSocket
            servidor = ServerSocket(puerto, maximoConexiones)

            // Bucle infinito para esperar conexiones
            while (true) {
                log.info("Servidor a la espera de conexiones.")
                socket = servidor.accept()
                log.info("Cliente con la IP " + socket.inetAddress.hostName + " conectado.")
                val cc = ConexionCliente(socket, mensajes)
                cc.start()
            }
        } catch (ex: IOException) {
            log.error("Error: " + ex.message)
        } finally {
            try {
                assert(socket != null)
                socket!!.close()
                servidor!!.close()
            } catch (ex: IOException) {
                log.error("Error al cerrar el servidor: " + ex.message)
            }
        }
    }
}
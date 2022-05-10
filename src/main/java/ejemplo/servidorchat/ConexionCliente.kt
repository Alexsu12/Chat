package ejemplo.servidorchat

import org.apache.log4j.Logger
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket
import java.util.*

class ConexionCliente(private val socket: Socket, private val mensajes: MensajesChat) : Thread(), Observer {
    private val log = Logger.getLogger(ConexionCliente::class.java)
    private var entradaDatos: DataInputStream? = null
    private var salidaDatos: DataOutputStream? = null

    init {
        try {
            entradaDatos = DataInputStream(socket.getInputStream())
            salidaDatos = DataOutputStream(socket.getOutputStream())
        } catch (ex: IOException) {
            log.error("Error al crear los stream de entrada y salida : " + ex.message)
        }
    }

    override fun run() {
        var mensajeRecibido: String?
        var conectado = true
        // Se apunta a la lista de observadores de mensajes
        mensajes.addObserver(this)
        while (conectado) {
            try {
                // Lee un mensaje enviado por el cliente
                mensajeRecibido = entradaDatos!!.readUTF()
                // Pone el mensaje recibido en mensajes para que se notifique
                // a sus observadores que hay un nuevo mensaje.
                mensajes.mensaje = mensajeRecibido
            } catch (ex: IOException) {
                log.info("Cliente con la IP " + socket.inetAddress.hostName + " desconectado.")
                conectado = false
                // Si se ha producido un error al recibir datos del cliente se cierra la conexión con él.
                try {
                    entradaDatos!!.close()
                    salidaDatos!!.close()
                } catch (ex2: IOException) {
                    log.error("Error al cerrar los stream de entrada y salida :" + ex2.message)
                }
            }
        }
    }

    override fun update(o: Observable, arg: Any) {
        try {
            // Envía el mensaje al cliente
            salidaDatos!!.writeUTF(arg.toString())
        } catch (ex: IOException) {
            log.error("Error al enviar mensaje al cliente (" + ex.message + ").")
        }
    }
}
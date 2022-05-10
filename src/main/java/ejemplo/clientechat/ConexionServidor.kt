package ejemplo.clientechat

import org.apache.log4j.Logger
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket
import javax.swing.JTextField

class ConexionServidor(socket: Socket, private val tfMensaje: JTextField, private val usuario: String) :
    ActionListener {
    private val log = Logger.getLogger(ConexionServidor::class.java)
    private var salidaDatos: DataOutputStream? = null

    init {
        try {
            salidaDatos = DataOutputStream(socket.getOutputStream())
        } catch (ex: IOException) {
            log.error("Error al crear el stream de salida : " + ex.message)
        } catch (ex: NullPointerException) {
            log.error("El socket no se creo correctamente. ")
        }
    }

    override fun actionPerformed(e: ActionEvent) {
        try {
            salidaDatos!!.writeUTF(usuario + ": " + tfMensaje.text)
            tfMensaje.text = ""
        } catch (ex: Exception) {
            log.error("Error al intentar enviar un mensaje: " + ex.message)
        }
    }
}
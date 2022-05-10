package ejemplo.clientechat

import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator
import java.awt.*
import java.io.DataInputStream
import java.io.IOException
import java.net.Socket
import javax.swing.*

class ClienteChat : JFrame("Cliente Chat") {
    private val log = Logger.getLogger(ClienteChat::class.java)
    private val mensajesChat: JTextArea
    private var socket: Socket? = null

    init {

        // Elementos de la ventana
        mensajesChat = JTextArea()
        mensajesChat.isEnabled = false // El área de mensajes del chat no se debe de poder editar
        mensajesChat.lineWrap = true // Las líneas se parten al llegar al ancho del textArea
        mensajesChat.wrapStyleWord = true // Las líneas se parten entre palabras (por los espacios blancos)
        val font = Font("Verdana", Font.BOLD, 12)
        mensajesChat.font = font
        mensajesChat.foreground = Color.BLUE
        val scrollMensajesChat = JScrollPane(mensajesChat)
        val tfMensaje = JTextField("")
        val btEnviar = JButton("Enviar")


        // Posicionamiento de los elementos en la ventana
        val contenedor = this.contentPane
        contenedor.layout = GridBagLayout()
        val restriccionesGrid = GridBagConstraints() // Restricciones al colocar elementos
        restriccionesGrid.insets = Insets(20, 20, 20, 20)
        restriccionesGrid.gridx = 0
        restriccionesGrid.gridy = 0
        restriccionesGrid.gridwidth = 2
        restriccionesGrid.weightx = 1.0
        restriccionesGrid.weighty = 1.0
        restriccionesGrid.fill = GridBagConstraints.BOTH
        contenedor.add(scrollMensajesChat, restriccionesGrid)

        // Restaura valores por defecto
        restriccionesGrid.gridwidth = 1
        restriccionesGrid.weighty = 0.0
        restriccionesGrid.fill = GridBagConstraints.HORIZONTAL
        restriccionesGrid.insets = Insets(0, 20, 20, 20)
        restriccionesGrid.gridx = 0
        restriccionesGrid.gridy = 1
        contenedor.add(tfMensaje, restriccionesGrid)

        // Restaura valores por defecto
        restriccionesGrid.weightx = 0.0
        restriccionesGrid.gridx = 1
        restriccionesGrid.gridy = 1
        contenedor.add(btEnviar, restriccionesGrid)
        this.setBounds(400, 100, 400, 500)
        this.isVisible = true
        defaultCloseOperation = EXIT_ON_CLOSE

        // Ventana de configuración inicial
        val vc = VentanaConfiguracion(this)
        val host = vc.host
        val puerto = vc.puerto
        val usuario = vc.usuario
        log.info(
            "Quieres conectarte a " + host + " en el puerto " + puerto + " con el nombre de usuario: "
                    + usuario + "."
        )

        // Se crea el socket para conectar con el Servidor del Chat
        try {
            socket = Socket(host, puerto)
        } catch (ex: IOException) {
            log.error("No se ha podido conectar con el servidor (" + ex.message + ").")
        }

        // Acción para el botón enviar
        btEnviar.addActionListener(socket?.let { ConexionServidor(it, tfMensaje, usuario) })
    }

    fun recibirMensajesServidor() {
        // Obtiene el flujo de entrada del socket
        var entradaDatos: DataInputStream? = null
        var mensaje: String
        try {
            entradaDatos = DataInputStream(socket!!.getInputStream())
        } catch (ex: IOException) {
            log.error("Error al crear el stream de entrada: " + ex.message)
        } catch (ex: NullPointerException) {
            log.error("El socket no se creo correctamente. ")
        }

        // Bucle infinito que recibe mensajes del servidor
        var conectado = true
        while (conectado) {
            try {
                assert(entradaDatos != null)
                mensaje = entradaDatos!!.readUTF()
                mensajesChat.append(mensaje + System.lineSeparator())
            } catch (ex: IOException) {
                log.error("Error al leer del stream de entrada: " + ex.message)
                conectado = false
            } catch (ex: NullPointerException) {
                log.error("El socket no se creo correctamente. ")
                conectado = false
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // Carga el archivo de configuración de log4J
            PropertyConfigurator.configure("src/main/resources/log4j.properties")
            val c = ClienteChat()
            c.recibirMensajesServidor()
        }
    }
}
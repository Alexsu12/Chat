package ejemplo.clientechat

import org.apache.log4j.Logger
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.awt.event.ActionEvent
import javax.swing.*

class VentanaConfiguracion(padre: JFrame?) : JDialog(padre, "Configuración inicial", true) {
    private val log = Logger.getLogger(VentanaConfiguracion::class.java)
    private val tfUsuario: JTextField
    private val tfHost: JTextField
    private val tfPuerto: JTextField

    init {
        val lbUsuario = JLabel("Usuario:")
        val lbHost = JLabel("Host:")
        val lbPuerto = JLabel("Puerto:")
        tfUsuario = JTextField()
        tfHost = JTextField("localhost")
        tfPuerto = JTextField("1234")
        val btAceptar = JButton("Aceptar")
        btAceptar.addActionListener { e: ActionEvent? -> isVisible = false }
        val c = this.contentPane
        c.layout = GridBagLayout()
        val gbc = GridBagConstraints()
        gbc.insets = Insets(20, 20, 0, 20)
        gbc.gridx = 0
        gbc.gridy = 0
        c.add(lbUsuario, gbc)
        gbc.gridx = 0
        gbc.gridy = 1
        c.add(lbHost, gbc)
        gbc.gridx = 0
        gbc.gridy = 2
        c.add(lbPuerto, gbc)
        gbc.ipadx = 100
        gbc.fill = GridBagConstraints.HORIZONTAL
        gbc.gridx = 1
        gbc.gridy = 0
        c.add(tfUsuario, gbc)
        gbc.gridx = 1
        gbc.gridy = 1
        c.add(tfHost, gbc)
        gbc.gridx = 1
        gbc.gridy = 2
        c.add(tfPuerto, gbc)
        gbc.gridx = 0
        gbc.gridy = 3
        gbc.gridwidth = 2
        gbc.insets = Insets(20, 20, 20, 20)
        c.add(btAceptar, gbc)
        pack() // Le da a la ventana el mínimo tamaño posible
        this.setLocation(450, 200) // Posición de la ventana
        this.isResizable = false // Evita que se pueda estirar la ventana
        defaultCloseOperation = DO_NOTHING_ON_CLOSE // Deshabilita el botón de cierre de la ventana
        this.isVisible = true
    }

    val usuario: String
        get() = tfUsuario.text
    val host: String
        get() = tfHost.text
    val puerto: Int
        get() = tfPuerto.text.toInt()
}
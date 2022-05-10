package ejemplo.clientechat;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClienteChat extends JFrame {

    private final Logger log = Logger.getLogger(ClienteChat.class);
    private final JTextArea mensajesChat;
    private Socket socket;

    public ClienteChat() {
        super("Cliente Chat");

        // Elementos de la ventana
        mensajesChat = new JTextArea();
        mensajesChat.setEnabled(false); // El área de mensajes del chat no se debe de poder editar
        mensajesChat.setLineWrap(true); // Las líneas se parten al llegar al ancho del textArea
        mensajesChat.setWrapStyleWord(true); // Las líneas se parten entre palabras (por los espacios blancos)
        Font font = new Font("Verdana", Font.BOLD, 12);
        mensajesChat.setFont(font);
        mensajesChat.setForeground(Color.BLUE);
        JScrollPane scrollMensajesChat = new JScrollPane(mensajesChat);
        JTextField tfMensaje = new JTextField("");
        JButton btEnviar = new JButton("Enviar");


        // Posicionamiento de los elementos en la ventana
        Container contenedor = this.getContentPane();
        contenedor.setLayout(new GridBagLayout());

        GridBagConstraints restriccionesGrid = new GridBagConstraints(); // Restricciones al colocar elementos

        restriccionesGrid.insets = new Insets(20, 20, 20, 20);

        restriccionesGrid.gridx = 0;
        restriccionesGrid.gridy = 0;
        restriccionesGrid.gridwidth = 2;
        restriccionesGrid.weightx = 1;
        restriccionesGrid.weighty = 1;
        restriccionesGrid.fill = GridBagConstraints.BOTH;
        contenedor.add(scrollMensajesChat, restriccionesGrid);

        // Restaura valores por defecto
        restriccionesGrid.gridwidth = 1;
        restriccionesGrid.weighty = 0;

        restriccionesGrid.fill = GridBagConstraints.HORIZONTAL;
        restriccionesGrid.insets = new Insets(0, 20, 20, 20);

        restriccionesGrid.gridx = 0;
        restriccionesGrid.gridy = 1;
        contenedor.add(tfMensaje, restriccionesGrid);

        // Restaura valores por defecto
        restriccionesGrid.weightx = 0;

        restriccionesGrid.gridx = 1;
        restriccionesGrid.gridy = 1;
        contenedor.add(btEnviar, restriccionesGrid);

        this.setBounds(400, 100, 400, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ventana de configuración inicial
        VentanaConfiguracion vc = new VentanaConfiguracion(this);
        String host = vc.getHost();
        int puerto = vc.getPuerto();
        String usuario = vc.getUsuario();

        log.info("Quieres conectarte a " + host + " en el puerto " + puerto + " con el nombre de usuario: "
                + usuario + ".");

        // Se crea el socket para conectar con el Servidor del Chat
        try {
            socket = new Socket(host, puerto);
        } catch (IOException ex) {
            log.error("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
        }

        // Acción para el botón enviar
        btEnviar.addActionListener(new ConexionServidor(socket, tfMensaje, usuario));

    }

    public void recibirMensajesServidor() {
        // Obtiene el flujo de entrada del socket
        DataInputStream entradaDatos = null;
        String mensaje;
        try {
            entradaDatos = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            log.error("Error al crear el stream de entrada: " + ex.getMessage());
        } catch (NullPointerException ex) {
            log.error("El socket no se creo correctamente. ");
        }

        // Bucle infinito que recibe mensajes del servidor
        boolean conectado = true;
        while (conectado) {
            try {
                assert entradaDatos != null;
                mensaje = entradaDatos.readUTF();
                mensajesChat.append(mensaje + System.lineSeparator());
            } catch (IOException ex) {
                log.error("Error al leer del stream de entrada: " + ex.getMessage());
                conectado = false;
            } catch (NullPointerException ex) {
                log.error("El socket no se creo correctamente. ");
                conectado = false;
            }
        }
    }

    public static void main(String[] args) {
        // Carga el archivo de configuración de log4J
        PropertyConfigurator.configure("src/main/resources/log4j.properties");

        ClienteChat c = new ClienteChat();
        c.recibirMensajesServidor();
    }

}

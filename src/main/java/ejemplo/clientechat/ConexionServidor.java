package ejemplo.clientechat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JTextField;
import org.apache.log4j.Logger;

public class ConexionServidor implements ActionListener {

    private final Logger log = Logger.getLogger(ConexionServidor.class);
    private final JTextField tfMensaje;
    private final String usuario;
    private DataOutputStream salidaDatos;

    public ConexionServidor(Socket socket, JTextField tfMensaje, String usuario) {
        this.tfMensaje = tfMensaje;
        this.usuario = usuario;
        try {
            this.salidaDatos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            log.error("Error al crear el stream de salida : " + ex.getMessage());
        } catch (NullPointerException ex) {
            log.error("El socket no se creo correctamente. ");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            salidaDatos.writeUTF(usuario + ": " + tfMensaje.getText() );
            tfMensaje.setText("");
        } catch (Exception ex) {
            log.error("Error al intentar enviar un mensaje: " + ex.getMessage());
        }
    }
}

package ejemplo.clientechat;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class VentanaConfiguracion extends JDialog{

    private final Logger log = Logger.getLogger(VentanaConfiguracion.class);
    private final JTextField tfUsuario;
    private final JTextField tfHost;
    private final JTextField tfPuerto;

    public VentanaConfiguracion(JFrame padre) {
        super(padre, "Configuración inicial", true);

        JLabel lbUsuario = new JLabel("Usuario:");
        JLabel lbHost = new JLabel("Host:");
        JLabel lbPuerto = new JLabel("Puerto:");

        tfUsuario = new JTextField();
        tfHost = new JTextField("localhost");
        tfPuerto = new JTextField("1234");

        JButton btAceptar = new JButton("Aceptar");
        btAceptar.addActionListener(e -> setVisible(false));

        Container c = this.getContentPane();
        c.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(20, 20, 0, 20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        c.add(lbUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        c.add(lbHost, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        c.add(lbPuerto, gbc);

        gbc.ipadx = 100;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 1;
        gbc.gridy = 0;
        c.add(tfUsuario, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        c.add(tfHost, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        c.add(tfPuerto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 20, 20);
        c.add(btAceptar, gbc);

        this.pack(); // Le da a la ventana el mínimo tamaño posible
        this.setLocation(450, 200); // Posición de la ventana
        this.setResizable(false); // Evita que se pueda estirar la ventana
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Deshabilita el botón de cierre de la ventana
        this.setVisible(true);
    }


    public String getUsuario(){
        return this.tfUsuario.getText();
    }

    public String getHost(){
        return this.tfHost.getText();
    }

    public int getPuerto(){
        return Integer.parseInt(this.tfPuerto.getText());
    }

}

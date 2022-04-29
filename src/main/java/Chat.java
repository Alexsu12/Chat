import conexion.Cliente;
import conexion.Host;

import javax.swing.*;
import java.text.ParseException;

public class Chat {
    private boolean host;

    public Chat(boolean host) {
        this.host = host;
        if (host) {
            int puerto;
            while (true) {
                try {
                    puerto = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el puerto"));
                    break;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Puerto invalido");
                }
            }
            new Host(puerto);
        } else {
            int puerto;
            String ip;
            while (true) {
                try {
                    puerto = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el puerto"));
                    ip = JOptionPane.showInputDialog("Ingrese la ip");
                    break;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Puerto invalido");
                }
            }
            try {
                new Cliente(ip, puerto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isHost() {
        return host;
    }
    public void setHost(boolean host) {
        this.host = host;
    }

    public static void main(String[] args) {
        int host = JOptionPane.showConfirmDialog(null, "¿Desea ser host?", "Host",
                JOptionPane.YES_NO_OPTION);

        if (host == JOptionPane.YES_OPTION) {
            new Chat(true);
        }
        else {
            new Chat(false);
        }
    }
}

package control;

import front.*;

import javax.swing.*;

public class Controller {

    VentanaPrincipal ventanaConfiguracion;

    public Controller() {
        ventanaConfiguracion = new VentanaPrincipal(this);
    }

    public void mostrarVentanaPrincipal() {
        ventanaConfiguracion.setVisible(true);
        setLookAndFeel();
    }

    private void setLookAndFeel() {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}

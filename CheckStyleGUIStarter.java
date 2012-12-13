import javax.swing.*;

/**
 * Starts the app.
 * @author Achim Gosse, achim.gosse+checkstyle-gui@gmail.com
 * @version 1.00
 */
class CheckStyleGUIStarter extends Thread {

    /**
     * Creates and starts the GUI.
     */
    @Override
    public void run() {
        CheckStyleGUI app = new CheckStyleGUI();
        app.setVisible(true);
    }


    /**
     * Entry point for execution.
     * @param args  not used
     */
    public static void main(String[] args) {
        CheckStyleGUIStarter starter = new CheckStyleGUIStarter();
        SwingUtilities.invokeLater(starter);
    }
}

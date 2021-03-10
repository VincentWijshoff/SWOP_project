import gui.Window;

public class Browsr {

    /**
     * Start the browser
     * @param args The main arguments
     */
    public static void main(String[] args) {
        Window gui = new Window("CoolBrowser");
        java.awt.EventQueue.invokeLater(gui::show);
    }
}

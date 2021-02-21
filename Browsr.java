import gui.GUI;

public class Browsr {

    public static void main(String[] args) {
        GUI gui = new GUI("CoolBrowser");
        java.awt.EventQueue.invokeLater(() -> {
            gui.show();
        });

    }
}

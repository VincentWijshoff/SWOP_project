import gui.GUI;
import gui.GUIRectangle;
import gui.GUIString;

public class Browsr {

    public static void main(String[] args) {
        GUI gui = new GUI("jajaja");
        java.awt.EventQueue.invokeLater(() -> {
            gui.show();
        });

        gui.draw(new GUIString("TEST", 200, 200));
        gui.draw(new GUIRectangle(100, 100, 50, 100));

        //gui.delete(0);

    }
}

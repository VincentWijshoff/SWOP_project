import gui.GUI;
import gui.GUIRectangle;
import gui.GUIString;

public class Browsr {

    public static void main(String[] args) {
        GUI gui = new GUI("CoolBrowser");
        java.awt.EventQueue.invokeLater(() -> {
            gui.show();
        });

        GUIString testString = (GUIString) gui.createGUIObject(new GUIString("TEST", 200, 200));
        GUIRectangle testRectangle = (GUIRectangle) gui.createGUIObject(new GUIRectangle(100, 100, 50, 100));

        //gui.delete(0);

    }
}

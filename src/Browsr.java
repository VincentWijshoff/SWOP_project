import gui.GUIObject;
import gui.GUIString;
import gui.GUITable;
import gui.Window;

import java.util.ArrayList;

/**
 * The main class for our Browsr
 *
 * running main will start the browsr program
 */
public class Browsr {

    /**
     * Start the browser
     * @param args The main arguments
     */
    public static void main(String[] args) {
        Window gui = new Window("CoolBrowser");
        java.awt.EventQueue.invokeLater(gui::show);

        gui.getDocArea().clearDocObjects();



        GUITable table = new GUITable(20, 200);
        ArrayList<GUIObject> row1 = new ArrayList<>();
        row1.add(new GUIString("1", 0, 0));
        row1.add(new GUIString("2", 0, 0));

        ArrayList<GUIObject> row2 = new ArrayList<>();
        row2.add(new GUIString("3", 0, 0));
        row2.add(new GUIString("4", 0, 0));

        ArrayList<ArrayList<GUIObject>> full = new ArrayList<>();
        full.add(row1);
        full.add(row2);

        table.setTableObjects(full);

        gui.getDocArea().addGUIObject(table);
    }
}

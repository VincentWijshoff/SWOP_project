package tests.GUITests;
import events.EventHandler;
import gui.Objects.*;
import gui.Window;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static tests.TestUtil.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GUIObjectTest {

    Window window;
    FontMetrics fm;

    @BeforeAll
    public void setup() throws InvocationTargetException, InterruptedException {
        this.window = new Window("TestBrowser");
        java.awt.EventQueue.invokeAndWait(this.window::show);
        fm = window.getFontMetrics();
    }

    @Test
    void ButtonTestInitializers() throws  RuntimeException {
        // there are 2 constructors for buttons, the first one is only with a string which should be the text on the button
        GUIButton button1 = new GUIButton("TestMessage");
        assertEquals("ButtonTestInitializers", button1.getText(), "TestMessage");
        // the second one takes coordinates and sixes for the button
        GUIButton button2 = new GUIButton("TestMessage2", 0, 0, 10, 10);
        assertEquals("ButtonTestInitializers", button2.getText(), "TestMessage2");
        assertEquals("ButtonTestInitializers", button2.coordX, 0);
        assertEquals("ButtonTestInitializers", button2.coordY, 0);
        assertEquals("ButtonTestInitializers", button2.width, 10);
        assertEquals("ButtonTestInitializers", button2.height, 10);
        // by default teh color off the button should be Light gray
        assertTrue("ButtonTestInitializers", button2.buttonColor.equals(Color.lightGray));
        assertTrue("ButtonTestInitializers", button1.buttonColor.equals(Color.lightGray));
    }

    @Test
    void GUITableTest() {
        GUITable table1 = new GUITable(0,0);

        GUITable nestedTable = new GUITable(0,0);
        ArrayList<GUIObject> nestedRow = new ArrayList<>();
        nestedRow.add(new GUIString("nested string"));
        nestedTable.addRow(nestedRow);

        ArrayList<GUIObject> row1 = new ArrayList<>();
        row1.add(nestedTable);
        row1.add(new GUIString("jaja"));
        table1.addRow(row1);

        ArrayList<GUIObject> row2 = new ArrayList<>();
        row2.add(new GUILink("link", "href"));
        table1.addRow(row2);

        table1.setHandler((EventHandler) window.getCurrentScreen());
        table1.updateDimensions();
        System.out.println("jaja");

        // nested table
        assertEquals("1.a", row1.get(0).height, fm.getHeight());
        assertEquals("1.b", row1.get(0).width, fm.stringWidth("nested string"));
        assertEquals("1.c", row1.get(0).coordX, 0);
        assertEquals("1.d", row1.get(0).coordY, 0);

        // string
        assertEquals("2.a", row1.get(1).height, fm.getHeight());
        assertEquals("2.b", row1.get(1).width, fm.stringWidth("jaja"));
        assertEquals("2.c", row1.get(1).coordX, fm.stringWidth("nested string") + GUITable.xMargin);
        assertEquals("2.d", row1.get(1).coordY, 0);

        // link
        assertEquals("3.a", row2.get(0).height, fm.getHeight());
        assertEquals("3.b", row2.get(0).width, fm.stringWidth("link"));
        assertEquals("3.c", row2.get(0).coordX, 0);
        assertEquals("3.d", row2.get(0).coordY, fm.getHeight());

    }
}

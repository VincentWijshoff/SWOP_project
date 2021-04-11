package tests.GUITests;
import events.EventHandler;
import gui.DefaultScreen.DefaultScreen;
import gui.Objects.*;
import gui.Window;
import html.HtmlLoader;
import localDocuments.Docs;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

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
    void ButtonTestSetAction() throws  RuntimeException {
        // we test if an action set on a button actually works
        GUIButton b = new GUIButton("TestButton", 0, 0, 10, 10);
        AtomicBoolean acted = new AtomicBoolean(false);
        b.setMouseEvent((x1, y1, id, clickCount) -> {
            acted.set(true);
        });
        // we now do a mouse down event on the button, it should not activate
        b.handleMouseEvent(1, 1, MouseEvent.MOUSE_PRESSED, 1);
        assertFalse("ButtonTestSetAction", acted.get());
        // we now do a release event on the button, now it should activate
        b.handleMouseEvent(1, 1, MouseEvent.MOUSE_RELEASED, 1);
        assertTrue("ButtonTestSetAction", acted.get());
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

    // we do not need to test the GUIInput as this functionality was tested in the address bar tests

    @Test
    void stringTest() {
        GUIString s1 = new GUIString("testString");
        GUIString s2 = new GUIString("testString", 10, 10);

        assertEquals("stringTest", "testString", s1.getText());
        assertEquals("stringTest", "testString", s2.getText());
        assertEquals("stringTest", 10, s2.coordX);
        assertEquals("stringTest", 10, s2.coordY);
    }

    @Test
    void formTest() throws InterruptedException, InvocationTargetException {
        // we want to check if a form of any size produces the correct link
        // we do this by loading a form, entering some things in to the input boxes and pressing the button
        String htmlCode = """
                <form action="http://formTest.php">
                <table>
                    <table>
                        <td><input type="text" name="test1">
                        <td><input type="text" name="test2">
                        <td><input type="text" name="test3">
                        <td><input type="text" name="test4">
                        <td><input type="text" name="test5">
                    </table>
                    <tr><td><input type="submit">
                </table>
                </form>
                """;
        // we need to load this in via loader, so we rerun the setup for a clean window
        this.setup();
        DefaultScreen screen = new DefaultScreen(window);
        HtmlLoader loader = new HtmlLoader(screen.getDocArea());
        // we load the html into the window
        screen.getDocArea().clearDocObjects();
        loader.initialise(htmlCode);
        loader.loadPage();
        // we want to input some things into each off the inputs
        // we know only the outer table is in the list off drawn gui objects
        ArrayList<GUIInput> inputs = screen.getDocArea().getDrawnGUIObjects().get(0).getInputs();
        System.out.println(screen.getDocArea().getDrawnGUIObjects().get(0).getChildObjects());
        assertEquals("formTest", inputs.size(), 5);
        assertEquals("formTest", inputs.get(0).getName(), "test1");
        assertEquals("formTest", inputs.get(1).getName(), "test2");
        assertEquals("formTest", inputs.get(2).getName(), "test3");
        assertEquals("formTest", inputs.get(3).getName(), "test4");
        assertEquals("formTest", inputs.get(4).getName(), "test5");
        // we input some things into the inputs
        int itt = 10;
        for(GUIInput inp : inputs){
            inp.setText("inptest" + itt);
            itt--;
        }
        // if we click the button now we expect the following link to be loaded
        String loadlink = "http://formTest.php?test1=inptest10&test2=inptest9&test3=inptest8&test4=inptest7&test5=inptest6";
        // we now click the button and check the result
        // TODO cuz vorige deel faalt al dus dit heb ik nog ni gedaan
    }

    // any other gui element need no tests
}

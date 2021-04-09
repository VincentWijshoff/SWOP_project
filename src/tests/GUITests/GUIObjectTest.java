package tests.GUITests;
import gui.Objects.GUIButton;
import gui.Window;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

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
}

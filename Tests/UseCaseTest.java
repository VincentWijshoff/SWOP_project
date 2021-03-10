package Tests;

import gui.GUILink;
import gui.GUIObject;
import gui.GUIString;
import gui.Window;
import org.junit.jupiter.api.Test;

import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class UseCaseTest {

    Window window = new Window("TestBrowser");

    void fail(String testName) { throw new RuntimeException(testName + " failed."); }

    void assertTrue(String testName, boolean b) {
        if (!b) fail(testName);
    }
    void assertFalse(String testName, boolean b) {
        if (b) fail(testName);
    }
    void assertEquals(String testName, Object a, Object b) {if(!a.equals(b)) fail(testName); }

    @Test
    void useCaseTest_1() throws RuntimeException {
        String testName = "bigBoiTest";

        window.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 5, 25, 1, 1, 1024); // click on address bar
        assertTrue(testName,window.getAddressBar().isInFocus());

        window.handleKeyEvent(401, 10, '\n', 0); // press enter

        Set<GUIObject> expectedObjects = new HashSet<>();
        expectedObjects.add(new GUIString("Tables", 90, 98));
        expectedObjects.add(new GUILink("a", 10, 82, "a.html"));
        expectedObjects.add(new GUILink("table", 10, 98, "table.html"));
        expectedObjects.add(new GUIString("Table cells containing table data", 90, 130));
        expectedObjects.add(new GUIString("Hyperlink anchors", 90, 82));
        expectedObjects.add(new GUILink("td", 10, 130, "td.html"));
        expectedObjects.add(new GUIString("Table rows", 90, 114));
        expectedObjects.add(new GUIString("HTML elements partially supported by Browsr:", 10, 66));
        expectedObjects.add(new GUILink("tr", 10, 114, "tr.html"));

        Set<GUIObject> renderedObjects = window.getDocArea().getDocGUIObjects();

        assertTrue(testName, expectedObjects.size() == renderedObjects.size());
        for (GUIObject obj: renderedObjects ) {
            assertTrue(testName, expectedObjects.contains(obj));
        }
    }
}

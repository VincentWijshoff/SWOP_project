package tests.GUITests;

import gui.DefaultScreen.ChildPane;
import gui.DefaultScreen.DefaultScreen;
import gui.DefaultScreen.Pane;
import gui.DefaultScreen.ParentPane;
import gui.Objects.*;
import gui.Window;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static tests.TestUtil.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PaneTest {

    Window window;

    @BeforeAll
    public void setup() throws InvocationTargetException, InterruptedException {
        this.window = new Window("TestBrowser");
        java.awt.EventQueue.invokeAndWait(this.window::show);
    }

    // this is use case 4.9

    @Test
    void testPaneInitial() throws RuntimeException, InterruptedException, InvocationTargetException {
        this.setup();
        // the initial pane should be a child pane
        assertTrue("testPaneInitial", this.window.getCurrentScreen() instanceof DefaultScreen);
        DefaultScreen scr = (DefaultScreen) this.window.getCurrentScreen();
        Pane initPane = scr.getPane();
        assertTrue( "testPaneInitial", initPane instanceof ChildPane);
        assertTrue("testPaneInitial", initPane.getFocusedPane() == initPane);
    }

    // This test is the first part of Use Case 4.7

    @Test
    void testPaneSplitHorizontal() throws RuntimeException, InterruptedException, InvocationTargetException {
        this.setup();
        String testName = "testPaneSplitHorizontal";
        // test if splitting a pane horizontal wil duplicate the pane nad draw correctly
        assertTrue(testName, this.window.getCurrentScreen() instanceof DefaultScreen);
        DefaultScreen scr = (DefaultScreen) this.window.getCurrentScreen();
        // click simulation on the screen to set focus on a child pane
        scr.handleMouseEvent(MouseEvent.MOUSE_PRESSED,10,50, 1, 0, 0);
        ChildPane initPane = scr.getFocusedPane();
        // start dimensions and gui objects
        int startX = initPane.x;
        int startY = initPane.y;
        int startWidth = initPane.width;
        int startHeight = initPane.height;
        ArrayList<GUIObject> startObjects = initPane.getDrawnGUIObjects();
        // we split the pane horizontally
        scr.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_H, 'h', KeyEvent.CTRL_DOWN_MASK);
        // the focused pane should not be the same anymore
        ChildPane p1 = scr.getFocusedPane();
        assertFalse(testName, initPane == p1);
        // the parent pane of the focused child cannot be null
        ParentPane parent = p1.parentPane;
        assertFalse(testName, parent == null);
        // we get the second child from the parent
        Pane c1 = parent.child1;
        Pane c2 = parent.child2;
        // these should be childpanes
        assertTrue(testName, c1 instanceof ChildPane);
        assertTrue(testName, c1 instanceof ChildPane);
        // we now check the dimension and position off both child panes
        assertTrue(testName, c1.x == startX || c2.x == startX);
        assertTrue(testName, c1.x == startX + startWidth/2 || c2.x == startX + startWidth/2);
        assertTrue(testName, c1.y == startY || c2.y == startY);
        assertTrue(testName, c1.y == startY && c2.y == startY);
        assertEquals(testName, c1.width, startWidth/2);
        assertEquals(testName, c2.width, startWidth/2);
        assertEquals(testName, c1.height, startHeight);
        assertEquals(testName, c2.height, startHeight);
        // we now check if the gui objects are a copy off the original
        assertEquals(testName, startObjects.size(), c1.getDrawnGUIObjects().size());
        assertEquals(testName, startObjects.size(), c2.getDrawnGUIObjects().size());
        for (int i = 0 ; i < startObjects.size(); i++){
            GUIObject startObj = startObjects.get(i);
            GUIObject c1Obj = c1.getDrawnGUIObjects().get(i);
            GUIObject c2Obj = c2.getDrawnGUIObjects().get(i);
            this.isEqualObject(startObj, c1Obj, testName);
            this.isEqualObject(startObj, c2Obj, testName);
        }
        // one of the 2 children has to be in focus
        assertTrue(testName, parent.getFocusedPane() == c1 || parent.getFocusedPane() == c2);
    }

    /**
     * will check if the 2 given gui objects have the same values
     * @param o1        the first object
     * @param o2        the second object
     * @param testName  the name of the test
     */
    void isEqualObject(GUIObject o1, GUIObject o2, String testName){
        // we do not check position as this is different
        if(o1 instanceof GUILink){
            assertTrue(testName, o2 instanceof GUILink);
            assertEquals(testName, ((GUILink) o1).getText(), ((GUILink) o2).getText());
            assertEquals(testName, ((GUILink) o1).getHref(), ((GUILink) o2).getHref());
        }else if(o1 instanceof GUIInput){
            assertTrue(testName, o2 instanceof GUIInput);
            assertEquals(testName, ((GUIInput) o1).getName(), ((GUIInput) o2).getName());
            assertEquals(testName, ((GUIInput) o1).getText(), ((GUIInput) o2).getText());
        }else if(o1 instanceof GUIButton){
            assertTrue(testName, o2 instanceof GUIButton);
            assertEquals(testName, ((GUIButton) o1).getText(), ((GUIButton) o2).getText());
        }else if(o1 instanceof GUIString){
            assertTrue(testName, o2 instanceof GUIString);
            assertEquals(testName, ((GUIString) o1).getText(), ((GUIString) o2).getText());
        }else if(o1 instanceof  GUITable){
            assertTrue(testName, o2 instanceof GUITable);
            ArrayList<GUIObject> o1child = o1.getChildObjects();
            ArrayList<GUIObject> o2child = o2.getChildObjects();
            assertEquals(testName, o1child.size(), o2child.size());
            for (int i = 0 ; i < o1child.size(); i++){
                this.isEqualObject(o1child.get(i), o2child.get(i), testName);
            }
        }
    }

    // this is the second test for use case 4.7

    @Test
    void testPaneSplitVertical() throws RuntimeException, InterruptedException, InvocationTargetException {
        this.setup();
        String testName = "testPaneSplitVertical";
        // test if splitting a pane horizontal wil duplicate the pane nad draw correctly
        assertTrue(testName, this.window.getCurrentScreen() instanceof DefaultScreen);
        DefaultScreen scr = (DefaultScreen) this.window.getCurrentScreen();
        // click simulation on the screen to set focus on a child pane
        scr.handleMouseEvent(MouseEvent.MOUSE_PRESSED,10,50, 1, 0, 0);
        ChildPane initPane = scr.getFocusedPane();
        // start dimensions and gui objects
        int startX = initPane.x;
        int startY = initPane.y;
        int startWidth = initPane.width;
        int startHeight = initPane.height;
        ArrayList<GUIObject> startObjects = initPane.getDrawnGUIObjects();
        // we split the pane vertically
        scr.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_V, 'v', KeyEvent.CTRL_DOWN_MASK);
        // the focused pane should not be the same anymore
        ChildPane p1 = scr.getFocusedPane();
        assertFalse(testName, initPane == p1);
        // the parent pane of the focused child cannot be null
        ParentPane parent = p1.parentPane;
        assertFalse(testName, parent == null);
        // we get the second child from the parent
        Pane c1 = parent.child1;
        Pane c2 = parent.child2;
        // these should be childpanes
        assertTrue(testName, c1 instanceof ChildPane);
        assertTrue(testName, c1 instanceof ChildPane);
        // we now check the dimension and position off both child panes
        assertTrue(testName, c1.y == startY || c2.y == startY);
        assertTrue(testName, c1.y == startY + startHeight/2 || c2.y == startY + startHeight/2);
        assertTrue(testName, c1.x == startX || c2.x == startX);
        assertTrue(testName, c1.x == startX && c2.x == startX);
        assertEquals(testName, c1.width, startWidth);
        assertEquals(testName, c2.width, startWidth);
        assertEquals(testName, c1.height, startHeight/2);
        assertEquals(testName, c2.height, startHeight/2);
        // we now check if the gui objects are a copy off the original
        assertEquals(testName, startObjects.size(), c1.getDrawnGUIObjects().size());
        assertEquals(testName, startObjects.size(), c2.getDrawnGUIObjects().size());
        for (int i = 0 ; i < startObjects.size(); i++){
            GUIObject startObj = startObjects.get(i);
            GUIObject c1Obj = c1.getDrawnGUIObjects().get(i);
            GUIObject c2Obj = c2.getDrawnGUIObjects().get(i);
            this.isEqualObject(startObj, c1Obj, testName);
            this.isEqualObject(startObj, c2Obj, testName);
        }
        assertTrue(testName, parent.getFocusedPane() == c1 || parent.getFocusedPane() == c2);
    }

    // this is use case 4.10

    @Test
    void testPaneDragSeparator() throws RuntimeException, InterruptedException, InvocationTargetException {
        this.setup();
        String testName = "testPaneDragSeparator";
        // test if dragging line works
        assertTrue(testName, this.window.getCurrentScreen() instanceof DefaultScreen);
        DefaultScreen scr = (DefaultScreen) this.window.getCurrentScreen();
        // click simulation on the screen to set focus on a child pane
        scr.handleMouseEvent(MouseEvent.MOUSE_PRESSED,10,50, 1, 0, 0);
        ChildPane initPane = scr.getFocusedPane();
        // we split the pane vertically
        scr.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_V, 'v', KeyEvent.CTRL_DOWN_MASK);
        // the focused pane should not be the same anymore
        ChildPane p1 = scr.getFocusedPane();
        assertFalse(testName, initPane == p1);
        // the parent pane of the focused child cannot be null
        ParentPane parent = p1.parentPane;
        assertFalse(testName, parent == null);
        int startPos = parent.linePosition;
        // should be in middle at beginning
        assertEquals(testName, startPos, parent.y + parent.height/2);
        // pressing 4 pixels below t should drag it to that position
        scr.handleMouseEvent(MouseEvent.MOUSE_PRESSED, parent.x + 10, startPos + 4, 1, 0, 0);
        assertEquals(testName, parent.linePosition, startPos + 4);
        // finally we check if one of the children was moved down, so layout was updated accordingly
        assertTrue(testName, parent.linePosition == parent.child1.y || parent.linePosition == parent.child2.y);
    }

    // this is use case 4.8

    @Test
    void testPaneRejoin() throws RuntimeException, InterruptedException, InvocationTargetException {
        this.setup();
        String testName = "testPaneRejoin";
        assertTrue(testName, this.window.getCurrentScreen() instanceof DefaultScreen);
        DefaultScreen scr = (DefaultScreen) this.window.getCurrentScreen();
        // click simulation on the screen to set focus on a child pane
        scr.handleMouseEvent(MouseEvent.MOUSE_PRESSED,10,50, 1, 0, 0);
        ChildPane initPane = scr.getFocusedPane();
        ArrayList<GUIObject> startObjects = initPane.getDrawnGUIObjects();
        // we split the pane vertically
        scr.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_V, 'v', KeyEvent.CTRL_DOWN_MASK);
        // we rejoin the pane
        scr.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_X, 'x', KeyEvent.CTRL_DOWN_MASK);
        ChildPane secondPane = scr.getFocusedPane();
        ArrayList<GUIObject> secondObjects = secondPane.getDrawnGUIObjects();
        // the 2 lists should be the same
        assertEquals(testName, startObjects.size(), secondObjects.size());
        for (int i = 0 ; i < startObjects.size(); i++){
            GUIObject startObj = startObjects.get(i);
            GUIObject c1Obj = secondObjects.get(i);
            this.isEqualObject(startObj, c1Obj, testName);
        }
    }
}

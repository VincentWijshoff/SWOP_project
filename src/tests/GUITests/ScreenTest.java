package tests.GUITests;

import gui.DefaultScreen.BookmarkBar;
import gui.DefaultScreen.DefaultScreen;
import gui.DialogScreen.SaveBookmarkScreen;
import gui.DialogScreen.SaveHtmlScreen;
import gui.Objects.*;
import gui.Screen;
import gui.Window;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static tests.TestUtil.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ScreenTest {

    Window window;
    FontMetrics fm;

    @BeforeAll
    public void setup() throws InvocationTargetException, InterruptedException {
        this.window = new Window("TestBrowser");
        java.awt.EventQueue.invokeAndWait(this.window::show);
        fm = window.getFontMetrics();
    }

    @Test
    void testScreenInitialScreen() throws  RuntimeException {
        // the initial screen created should be a DefaultScreen
        assertTrue("testScreenInitialScreen", this.window.getCurrentScreen() instanceof DefaultScreen);
    }

    @Test
    void testScreenSetAndGetScreen() throws  RuntimeException {
        // if we set a new screen, it should be switched out in the window
        Screen newScreen = new DefaultScreen(this.window);
        Screen oldScreen = this.window.getCurrentScreen();
        this.window.setScreen(newScreen);
        assertFalse("testScreenSetAndGetScreen", this.window.getCurrentScreen().equals(oldScreen));
        //reset to the old screen
        this.window.setScreen(oldScreen);
    }

    @Test
    void testScreenSaveScreen() throws  RuntimeException {
        Screen oldScreen = this.window.getCurrentScreen();
        // if ctrl + s is pressed a new dialog screen should be loaded
        this.window.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_S, 's', KeyEvent.CTRL_DOWN_MASK);
        assertTrue("testScreenSaveScreen", this.window.getCurrentScreen() instanceof SaveHtmlScreen);
        // we check if all gui objects are in the screen
        SaveHtmlScreen screen = (SaveHtmlScreen) this.window.getCurrentScreen();
        ArrayList<GUIObject> obj = screen.getGUIObjects();
        // there should be 2 GUI strings, 1 GUI input and 2 GUIButtons
        assertEquals("testScreenSaveScreen", obj.size(), 5);
        //We now chek if all items are the correct and expected items
        assertTrue("testScreenSaveScreen", obj.get(0) instanceof GUIString);
        assertTrue("testScreenSaveScreen", obj.get(1) instanceof GUIString);
        assertTrue("testScreenSaveScreen", obj.get(2) instanceof GUIInput);
        assertTrue("testScreenSaveScreen", obj.get(3) instanceof GUIButton);
        assertTrue("testScreenSaveScreen", obj.get(4) instanceof GUIButton);
        // we now check for each item some specific identifying elements
        GUIString str1 = (GUIString) obj.get(0);
        GUIString str2 = (GUIString) obj.get(1);
        GUIInput inp = (GUIInput) obj.get(2);
        GUIButton but1 = (GUIButton) obj.get(3);
        GUIButton but2 = (GUIButton) obj.get(4);
        // the first string is a title / header to the page
        assertEquals( "testScreenSaveScreen", str1.getText(), "Saving page to html file");
        // the second string should be an indicator for the the input box
        assertEquals( "testScreenSaveScreen", str2.getText(), "File name");
        // then the input should be at the exact same y level, but a higher x level, so we know it is proceeded by the text
        assertEquals("testScreenSaveScreen", str2.coordY, inp.coordY);
        assertTrue("testScreenSaveScreen", str2.coordX < inp.coordX);
        // the input should also be empty on startup
        assertEquals("testScreenSaveScreen", inp.getText(), "");
        // the first button should be the cancel button
        assertEquals("testScreenSaveScreen", but1.getText(), "Cancel");
        // the second button should be the save button
        assertEquals("testScreenSaveScreen", but2.getText(), "Save");
        // now we want to test the functionality off the buttons
        // we first test the pressing on the cancel button, it should just put back the old screen on the window
        but1.handleMouseEvent(but1.coordX + 1, but1.coordY + 1, MouseEvent.MOUSE_RELEASED, 1);
        // the old window should be reset
        assertTrue("testScreenSaveScreen", this.window.getCurrentScreen().equals(oldScreen));
        // we now want to test the save button, so we have to reactivate the save screen again
        this.window.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_S, 's', KeyEvent.CTRL_DOWN_MASK);
        assertTrue("testScreenSaveScreen", this.window.getCurrentScreen() instanceof SaveHtmlScreen);
        assertEquals("testScreenSaveScreen", obj.size(), 5);
        assertTrue("testScreenSaveScreen", obj.get(4) instanceof GUIButton);
        GUIButton but3 = (GUIButton) obj.get(4);
        assertEquals("testScreenSaveScreen", but3.getText(), "Save");
        // if we click this the current html should be saved into a file with the correct name
        // we alter the name first
        assertTrue("testScreenSaveScreen", obj.get(2) instanceof GUIInput);
        GUIInput inp2 = (GUIInput) obj.get(2);
        inp2.setText("testFile");
        // if we press the button now, the file should be made and the window should return to the old window
        but3.handleMouseEvent(but3.coordX + 1, but3.coordY + 1, MouseEvent.MOUSE_RELEASED, 1);
        assertTrue("testScreenSaveScreen", this.window.getCurrentScreen().equals(oldScreen));
        // the file should be made and containing the html off the default screen
        assertTrue("testScreenSaveScreen", oldScreen instanceof DefaultScreen);
        DefaultScreen defaultScreen = (DefaultScreen) oldScreen;
        String html = defaultScreen.getDocArea().getCurrentHtml();
        // this html should be in a file called "testFile.html"which we just created
        String content = "";

        try
        {
            content = new String ( Files.readAllBytes( Paths.get("./testFile.html") ) );
        }
        catch (IOException e)
        {
            assertTrue("Reading contents from created html file", false);
        }

        String fileContent =  content;

        assertEquals("testScreenSaveScreen", fileContent, html);
        // we now delete the test file
        File f = new File("./testFile.html");
        if(!f.delete()){
            assertTrue("file deleting failed", false);
        }
    }

    @Test
    void testScreenBookmarkScreen() throws  RuntimeException {
        Screen oldScreen = this.window.getCurrentScreen();
        assertTrue("testScreenBookmarkScreen", oldScreen instanceof DefaultScreen);
        DefaultScreen defScreen = (DefaultScreen) oldScreen;
        // we save the amount of old bookmarks for later
        int oldMarksSize = defScreen.getBookmarkBar().getBookmarks().getChildObjects().size();
        // if ctrl + d is pressed a new dialog screen should be loaded
        this.window.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_D, 'd', KeyEvent.CTRL_DOWN_MASK);
        assertTrue("testScreenBookmarkScreen", this.window.getCurrentScreen() instanceof SaveBookmarkScreen);
        // we check if all gui objects are in the screen
        SaveBookmarkScreen screen = (SaveBookmarkScreen) this.window.getCurrentScreen();
        ArrayList<GUIObject> obj = screen.getGUIObjects();
        // there should be 3 GUI strings, 2 GUI input and 2 GUIButtons
        assertEquals("testScreenBookmarkScreen", obj.size(), 7);
        //We now chek if all items are the correct and expected items
        assertTrue("testScreenBookmarkScreen", obj.get(0) instanceof GUIString);
        assertTrue("testScreenBookmarkScreen", obj.get(1) instanceof GUIString);
        assertTrue("testScreenBookmarkScreen", obj.get(2) instanceof GUIInput);
        assertTrue("testScreenBookmarkScreen", obj.get(3) instanceof GUIString);
        assertTrue("testScreenBookmarkScreen", obj.get(4) instanceof GUIInput);
        assertTrue("testScreenBookmarkScreen", obj.get(5) instanceof GUIButton);
        assertTrue("testScreenBookmarkScreen", obj.get(6) instanceof GUIButton);
        // we now check for each item some specific identifying elements
        GUIString str1 = (GUIString) obj.get(0);
        GUIString str2 = (GUIString) obj.get(1);
        GUIInput inp1 = (GUIInput) obj.get(2);
        GUIString str3 = (GUIString) obj.get(3);
        GUIInput inp2 = (GUIInput) obj.get(4);
        GUIButton but1 = (GUIButton) obj.get(5);
        GUIButton but2 = (GUIButton) obj.get(6);
        // the first string is a title / header to the page
        assertEquals( "testScreenBookmarkScreen", str1.getText(), "Saving URL to bookmark bar");
        // the second string should be an indicator for the the input box
        assertEquals( "testScreenBookmarkScreen", str2.getText(), "Name");
        // the third string should be an indicator for the the input box
        assertEquals( "testScreenBookmarkScreen", str3.getText(), "URL");
        // then the inputs should be right next to the strings
        assertEquals("testScreenBookmarkScreen", str2.coordY, inp1.coordY);
        assertTrue("testScreenBookmarkScreen", str2.coordX < inp1.coordX);
        assertEquals("testScreenBookmarkScreen", str3.coordY, inp2.coordY);
        assertTrue("testScreenBookmarkScreen", str3.coordX < inp2.coordX);
        // the input should also be empty on startup
        assertEquals("testScreenBookmarkScreen", inp1.getText(), "");
        // the second input should be defaulted to the current address
        assertEquals("testScreenBookmarkScreen", inp2.getText(), defScreen.getAddress());
        // the first button should be the cancel button
        assertEquals("testScreenBookmarkScreen", but1.getText(), "Cancel");
        // the second button should be the save button
        assertEquals("testScreenBookmarkScreen", but2.getText(), "Add Bookmark");
        // now we want to test the functionality off the buttons
        // we first test the pressing on the cancel button, it should just put back the old screen on the window
        but1.handleMouseEvent(but1.coordX + 1, but1.coordY + 1, MouseEvent.MOUSE_RELEASED, 1);
        // the old window should be reset
        assertTrue("testScreenBookmarkScreen", this.window.getCurrentScreen().equals(oldScreen));
        // we now want to test the save button, so we have to reactivate the save screen again
        this.window.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_D, 'd', KeyEvent.CTRL_DOWN_MASK);
        assertTrue("testScreenBookmarkScreen", this.window.getCurrentScreen() instanceof SaveBookmarkScreen);
        assertEquals("testScreenBookmarkScreen", obj.size(), 7);
        assertTrue("testScreenBookmarkScreen", obj.get(6) instanceof GUIButton);
        GUIButton but3 = (GUIButton) obj.get(6);
        assertEquals("testScreenBookmarkScreen", but3.getText(), "Add Bookmark");
        // if we click this, the bookmark should be saved correctly, we only have to check if a new bookmark was added
        // in the bookmark, specific bookmark tests test other functionality
        // we will alter the name off the new bookmark first
        assertTrue("testScreenBookmarkScreen", obj.get(2) instanceof GUIInput);
        GUIInput inp3 = (GUIInput) obj.get(2);
        inp3.setText("testBookmark");
        // click the add bookmark button
        but3.handleMouseEvent(but3.coordX + 1, but3.coordY + 1, MouseEvent.MOUSE_RELEASED, 1);
        assertTrue("testScreenBookmarkScreen", this.window.getCurrentScreen().equals(oldScreen));

        //check if new bookmark has been added, position depends on if 2 default bookmarks have been drawn already //?TODO
        if (defScreen.getBookmarkBar().getBookmarks().getChildObjects().size() == 1) {
            assertTrue("testScreenBookmarkScreen", containsGUILinkWithPos(5, defScreen.getBookmarkBar().relativeYPos, "testBookmark", "WelcomeDoc.html", defScreen.getBookmarkBar().getBookmarks().getChildObjects()));
        }
        else { //size == 3
            int x = fm.stringWidth("home page Bart Jacobs") + fm.stringWidth("home page Bart Jacobs 2.0") + 2*GUITable.xMargin + BookmarkBar.relativeXPos; //calculate x position of new bookmark
            assertTrue("testScreenBookmarkScreen", containsGUILinkWithPos(x, defScreen.getBookmarkBar().relativeYPos, "testBookmark", "WelcomeDoc.html", defScreen.getBookmarkBar().getBookmarks().getChildObjects()));
        }
    }

    @Test
    void testScreenReturnExactState() throws  RuntimeException {
        // we want to test if when a default screen is returned, the state is exactly the same as before
        // we test this by checking the specific contents of an input on the page
        Screen oldScreen = this.window.getCurrentScreen();
        // we will load the new testing page from the prof as of assignment part 2
        assertTrue("testScreenReturnExactState", oldScreen instanceof DefaultScreen);
        DefaultScreen def = (DefaultScreen) oldScreen;
        def.load("https://people.cs.kuleuven.be/~bart.jacobs/swop/browsrformtest.html");
        // the correct loading off this page will be tested in specific test, we will assume here it worked
        ArrayList<GUIObject> oldObjects = def.getDocArea().getDrawnGUIObjects();
        // we know 2 inputs are present in this screen, but we will take the first input to change up
        GUIInput oldInput = null;
        for (GUIObject obj : oldObjects){
            if ( obj instanceof GUIInput){
                oldInput = (GUIInput) obj;
                break;
            }
        }
        // the input should not be undefined (null)
        assertFalse("testScreenReturnExactState", oldInput == null);
        // now we set some text in the input
        oldInput.setText("This is a test input");
        assertEquals("testScreenReturnExactState", oldInput.getText(), "This is a test input");
        // we can now switch screens to one off the dialog screens
        this.window.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_D, 'd', KeyEvent.CTRL_DOWN_MASK);
        assertTrue("testScreenReturnExactState", this.window.getCurrentScreen() instanceof SaveBookmarkScreen);
        // we cancel the action, completing the action will also return the same way so we only test canceling
        SaveBookmarkScreen screen = (SaveBookmarkScreen) this.window.getCurrentScreen();
        ArrayList<GUIObject> obj = screen.getGUIObjects();
        GUIButton but1 = (GUIButton) obj.get(5);
        but1.handleMouseEvent(but1.coordX + 1, but1.coordY + 1, MouseEvent.MOUSE_RELEASED, 1);
        // we clicked the cancel button, we should be back to the old screen with the exact same value in the input
        Screen newScreen = this.window.getCurrentScreen();
        assertTrue("testScreenReturnExactState", oldScreen instanceof DefaultScreen);
        DefaultScreen newdef = (DefaultScreen) oldScreen;
        ArrayList<GUIObject> newObjects = newdef.getDocArea().getDrawnGUIObjects();
        GUIInput newInput = null;
        for (GUIObject objn : newObjects){
            if ( objn instanceof GUIInput){
                newInput = (GUIInput) objn;
                break;
            }
        }
        assertFalse("testScreenReturnExactState", newInput == null);
        // the text should still be the same
        assertEquals("testScreenReturnExactState", newInput.getText(), "This is a test input");

    }

    @Test
    void testScreenMultipleScreenCalls() throws RuntimeException, InterruptedException, InvocationTargetException {
        // we test if multiple dialog screens are created on top of a default screen, if the screens still operate
        Window w = this.window;
        DefaultScreen startScreen = new DefaultScreen(w);
        int startSize = startScreen.getBookmarkBar().getBookmarks().getChildObjects().size();
        SaveBookmarkScreen s1 = new SaveBookmarkScreen(w, startScreen, "link");
        SaveBookmarkScreen s2 = new SaveBookmarkScreen(w, s1, "link");
        SaveBookmarkScreen s3 = new SaveBookmarkScreen(w, s2, "link");
        SaveBookmarkScreen s4 = new SaveBookmarkScreen(w, s3, "link");
        SaveBookmarkScreen s5 = new SaveBookmarkScreen(w, s4, "link");
        SaveBookmarkScreen s6 = new SaveBookmarkScreen(w, s5, "link");
        SaveBookmarkScreen s7 = new SaveBookmarkScreen(w, s6, "link");
        // we now use the 7th dialog screen to create a new bookmark
        ArrayList<GUIObject> obj = s7.getGUIObjects();
        GUIButton but = (GUIButton) obj.get(6);
        but.handleMouseEvent(but.coordX + 1, but.coordY + 1, MouseEvent.MOUSE_RELEASED, 1);
        int newSize = startScreen.getBookmarkBar().getBookmarks().getChildObjects().size();
        assertEquals("testScreenMultipleScreenCalls", startSize + 1, newSize);
        this.setup();
    }
}

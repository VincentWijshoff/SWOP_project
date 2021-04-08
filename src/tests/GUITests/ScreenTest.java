package tests.GUITests;

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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

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
        // we save the amount off old bookmarks for later
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
        // click the add bookamrk button
        but3.handleMouseEvent(but3.coordX + 1, but3.coordY + 1, MouseEvent.MOUSE_RELEASED, 1);
        assertTrue("testScreenBookmarkScreen", this.window.getCurrentScreen().equals(oldScreen));
        // exactly one new bookmark should be present in the bookmark bar
        // without these prints the final test fails?
        System.out.println(defScreen.getBookmarkBar().getBookmarks().getChildObjects().size());
        System.out.println(oldMarksSize + 1);
        assertEquals("testScreenBookmarkScreen", oldMarksSize + 1,
                defScreen.getBookmarkBar().getBookmarks().getChildObjects().size());
    }
}

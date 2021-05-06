package tests;

import gui.DefaultScreen.BookmarkBar;
import gui.DefaultScreen.ChildPane;
import gui.DefaultScreen.DefaultScreen;
import gui.DefaultScreen.DocumentArea;
import gui.DialogScreen.SaveBookmarkScreen;
import gui.Objects.*;
import gui.Screen;
import gui.Window;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static tests.TestUtil.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UseCaseTest {

    Window window;
    FontMetrics fm;

    @BeforeEach
    public void setup() throws InvocationTargetException, InterruptedException {
        this.window = new Window("TestBrowser");
        java.awt.EventQueue.invokeAndWait(this.window::show);
        fm = window.getFontMetrics();
    }

    @Test
    void testUCEnterURL() {
        //1. User starts a Browsr application.
        DefaultScreen screen = (DefaultScreen) window.getCurrentScreen();
        DocumentArea docarea = screen.getDocArea();

        //2. Application shows a welcome document.
        assertEquals("UC_2.a", screen.getAddress(), "WelcomeDoc.html");
        System.out.println(screen.getDocArea().getDrawnGUIObjects().size());
        assertEquals("UC_2.b", screen.getDocArea().getDrawnGUIObjects().size(),  8);
        //Welcome doc has 6 GUIStrings and 1 GUILink

        //3. User navigates to a desired webpage. (using AddressBar)
        window.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 5, 25, 1, 1, 1024); // click on address bar
        assertTrue("UC_3.a", screen.getAddressBar().isInFocus());
        typeString(window, "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html\n");

        //4. Application shows the desired webpage.
        assertEquals("UC_4.a", screen.getAddress(), "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html");
        ArrayList<GUIObject> renderedObjects = screen.getDocArea().getDrawnGUIObjects();
        assertTrue("UC_4.b", renderedObjects.size() == 11);
        assertTrue("UC_4.j", containsGUIStringWithPos(ChildPane.xOffset, docarea.getRelativeYPos(), "HTML elements partially supported by Browsr:", renderedObjects));

        assertTrue("UC_4.d", containsGUILinkWithPos(ChildPane.xOffset, docarea.getRelativeYPos() + fm.getHeight() + GUITable.yMargin, "a", "a.html", renderedObjects));
        assertTrue("UC_4.g", containsGUIStringWithPos(ChildPane.xOffset + fm.stringWidth("table") + GUITable.xMargin, docarea.getRelativeYPos() + fm.getHeight() + GUITable.yMargin, "Hyperlink anchors", renderedObjects));

        assertTrue("UC_4.e", containsGUILinkWithPos(ChildPane.xOffset, docarea.getRelativeYPos() + 2*fm.getHeight() + 2*GUITable.yMargin, "table", "table.html", renderedObjects));
        assertTrue("UC_4.c", containsGUIStringWithPos(ChildPane.xOffset + fm.stringWidth("table") + GUITable.xMargin, docarea.getRelativeYPos() + 2*fm.getHeight() + 2*GUITable.yMargin, "Tables", renderedObjects));

        assertTrue("UC_4.h", containsGUILinkWithPos(ChildPane.xOffset, docarea.getRelativeYPos() + 3*fm.getHeight() + 3*GUITable.yMargin, "tr", "tr.html", renderedObjects));
        assertTrue("UC_4.i", containsGUIStringWithPos(ChildPane.xOffset + fm.stringWidth("table") + GUITable.xMargin, docarea.getRelativeYPos() + 3*fm.getHeight() + 3*GUITable.yMargin, "Table rows", renderedObjects));

        assertTrue("UC_4.h", containsGUILinkWithPos(ChildPane.xOffset,  docarea.getRelativeYPos()  + 4*fm.getHeight() + 4*GUITable.yMargin, "td", "td.html", renderedObjects));
        assertTrue("UC_4.f", containsGUIStringWithPos(ChildPane.xOffset + fm.stringWidth("table") + GUITable.xMargin, docarea.getRelativeYPos() + 4*fm.getHeight() + 4*GUITable.yMargin, "Table cells containing table data", renderedObjects));
        //This page has 4 GUILinks and 5 GUIStrings

        //5 User navigates to a desired webpage. (using hyperlink)
        String href = "";
        for(GUIObject obj : screen.getDocArea().getDrawnGUIObjects()){
            if(obj instanceof GUILink){
                //press a hyperlink
                obj.handleMouseEvent(ChildPane.xOffset, docarea.getRelativeYPos() + fm.getHeight() + GUITable.yMargin, MouseEvent.MOUSE_PRESSED, 1);
                //all hyperlinks will navigate to a non-browsr webpage -> show error document
                href = ((GUILink) obj).getHref();
                break;
            }
        }
        //6 Application shows the desired webpage.
        assertEquals("UC_6.a", screen.getAddress(), "https://people.cs.kuleuven.be/~bart.jacobs/" + href);
        assertTrue("UC_6.b", screen.getDocArea().getDrawnGUIObjects().size() == 3);
        //error document has 2 GUIStrings
        for(GUIObject obj : screen.getDocArea().getDrawnGUIObjects()){
            assertFalse("UC_6.c", obj instanceof GUILink);
            //error document has no GUILinks
        }

    }

    @Test
    void testUCSubmitForm() {
        //1. User starts a Browsr application.
        DefaultScreen screen = (DefaultScreen) window.getCurrentScreen();
        DocumentArea docarea = screen.getDocArea();

        //2. User navigates to a desired webpage. (using AddressBar)
        window.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 5, 25, 1, 1, 1024); // click on address bar
        typeString(window, "https://people.cs.kuleuven.be/~bart.jacobs/swop/browsrformtest.html\n");

        //3. User clicks and modifies input field
        for(GUIObject obj : screen.getDocArea().getDrawnGUIObjects()){
            if(obj instanceof GUIInput){
                GUIInput input = (GUIInput) obj;
                obj.handleMouseEvent(ChildPane.xOffset + fm.stringWidth("Maximum number of words to show") + GUITable.xMargin, docarea.getRelativeYPos() + fm.getHeight() + 3*GUITable.yMargin, MouseEvent.MOUSE_PRESSED, 1);
                assertTrue("UC_3.a", input.getInFocus());
                typeString(window, "test input");
                assertEquals("UC_3.b", input.getShownText(), "test input");

                input.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, ' ', 0); //left arrow
                input.handleKeyEvent(0, 0, ' ', KeyEvent.SHIFT_DOWN_MASK); //start shifting
                input.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_HOME, ' ', KeyEvent.SHIFT_DOWN_MASK); //home shifting
                input.handleKeyEvent(0, 0, ' ', 0); //end shifting
                input.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, 'a', 0); //replace "test inpu" with "a" -> "at" is new text
                assertEquals("UC_3.b", input.getShownText(), "at");
                break;
            }
        }

        //4. User clicks submit button
        for(GUIObject obj : screen.getDocArea().getDrawnGUIObjects()){
            if(obj instanceof GUIButton){
                GUIButton button = (GUIButton) obj;
                button.handleMouseEvent(button.coordX , button.coordY, MouseEvent.MOUSE_RELEASED, 1);
                break;
            }
        }

        //5. Correct page is shown
        assertEquals("UC_5.a", screen.getAddress(), "https://people.cs.kuleuven.be/~bart.jacobs/swop/browsrformactiontest.php?starts_with=at&max_nb_results=");
        //We didn't fill out the second input so the returned page only contains an empty table
        assertTrue("UC_5.b", docarea.getDrawnGUIObjects().size() == 1);
        assertTrue("UC_5.b", docarea.getDrawnGUIObjects().get(0) instanceof GUITable);

    }

    @Test
    void testUCActivateBookmark() throws InterruptedException, InvocationTargetException {
        //1. User starts a Browsr application.
        java.awt.EventQueue.invokeAndWait(window::show); // twee is beter dan een
        DefaultScreen screen = (DefaultScreen) window.getCurrentScreen();

        screen.getBookmarkBar().handleMouseEvent(MouseEvent.MOUSE_PRESSED, BookmarkBar.relativeXPos, screen.getBookmarkBar().relativeYPos, 1);
        assertEquals("UC_1.a", screen.getAddress(), "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html");

    }

    @Test
    void testUCAddBookmark() {
        DefaultScreen screen = (DefaultScreen) window.getCurrentScreen();
        //2. User presses ctrl + d -> open saveBookmarkScreen
        screen.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_D, 'd', KeyEvent.CTRL_DOWN_MASK);
        Screen currentScreen = window.getCurrentScreen();
        assertTrue("UC_2.a", currentScreen instanceof SaveBookmarkScreen);
        SaveBookmarkScreen saveBookmarkScreen = (SaveBookmarkScreen) window.getCurrentScreen();
        //user presses on bookmarkName input box
        currentScreen.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 100, 70, 1, 1, 1024);
        //3. User types in a name of the bookmark he want to create
        GUIInput bookmarkName = saveBookmarkScreen.getBookmarkName();
        assertTrue("UC_3.a", bookmarkName.getInFocus());
        typeString(window, "testname");
        //user presses ENTER -> out of focus
        currentScreen.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_ENTER, '\n', 0);
        assertFalse("UC_3.b", bookmarkName.getInFocus());
        assertTrue("UC_3.c", bookmarkName.getShownText().equals("testname"));
        //user presses on bookmarkAddress input box
        currentScreen.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 100, 120, 1, 1, 1024);
        //4. User types in the URL for the bookmark he want to create
        GUIInput bookmarkAddress = saveBookmarkScreen.getBookmarkAddress();
        assertTrue("UC_4.a", bookmarkAddress.getInFocus());
        typeString(window, "https://www.google.com");
        //user presses ENTER -> out of focus
        currentScreen.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_ENTER, '\n', 0);
        assertFalse("UC_4.b", bookmarkAddress.getInFocus());
        assertTrue("UC_4.c", bookmarkAddress.getShownText().equals("https://www.google.com"));
        //5. User presses confirm button
        currentScreen.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 150, 180, 1, 1, 1024);
        //default screen is showing again
        DefaultScreen newScreen = (DefaultScreen) window.getCurrentScreen();

        GUITable bookmarks = newScreen.getBookmarkBar().getBookmarks();
        assertTrue("UC_5.a", containsGUILinkWithText("testname", "https://www.google.com", bookmarks.getChildObjects()));
    }

    @Test
    void testUCCancelBookmark() {
        DefaultScreen screen = (DefaultScreen) window.getCurrentScreen();
        //2. User presses ctrl + d -> open saveBookmarkScreen
        screen.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_D, 'd', KeyEvent.CTRL_DOWN_MASK);
        Screen currentScreen = window.getCurrentScreen();
        assertTrue("UC_2.a", currentScreen instanceof SaveBookmarkScreen);
        SaveBookmarkScreen saveBookmarkScreen = (SaveBookmarkScreen) window.getCurrentScreen();
        //user presses on bookmarkName input box
        currentScreen.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 100, 70, 1, 1, 1024);
        //3. User types in a name of the bookmark he want to create
        GUIInput bookmarkName = saveBookmarkScreen.getBookmarkName();
        assertTrue("UC_3.a", bookmarkName.getInFocus());
        typeString(window, "testname");
        //4. User types in a URL
        currentScreen.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 100, 120, 1, 1, 1024);
        typeString(window, "https://www.google.com");
        //user presses ENTER -> out of focus
        currentScreen.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_ENTER, '\n', 0);
        assertFalse("UC_3.b", bookmarkName.getInFocus());
        assertTrue("UC_3.c", bookmarkName.getShownText().equals("testname"));
        //5. User presses cancel button
        currentScreen.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 20, 180, 1, 1, 1024);
        //default screen is showing again
        DefaultScreen newScreen = (DefaultScreen) window.getCurrentScreen();
        GUITable bookmarks = newScreen.getBookmarkBar().getBookmarks();
        assertFalse("UC_5.a", containsGUILinkWithText("testname", "https://www.google.com", bookmarks.getChildObjects()));
    }
}

package tests;

import gui.DefaultScreen.DefaultScreen;
import gui.DefaultScreen.DocumentArea;
import gui.DialogScreen.SaveBookmarkScreen;
import gui.Objects.*;
import gui.Screen;
import gui.Window;
import org.junit.jupiter.api.Test;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static tests.TestUtil.*;

public class UseCaseTest {

    @Test
    void testUCEnterURL() throws InvocationTargetException, InterruptedException {
        //1. User starts a Browsr application.
        Window window = new Window("useCase");
        java.awt.EventQueue.invokeAndWait(window::show);
        DefaultScreen screen = (DefaultScreen) window.getCurrentScreen();
        FontMetrics fm = window.getFontMetrics();
        DocumentArea docarea = screen.getDocArea();


        //2. Application shows a welcome document.
        assertTrue("UC_2.a", screen.getAddress().equals("WelcomeDoc.html"));
        assertTrue("UC_2.b", screen.getDocArea().getDrawnGUIObjects().size() == 8);
        //Welcome doc has 6 GUIStrings and 1 GUILink

        //3. User navigates to a desired webpage. (using AddressBar)
        window.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 5, 25, 1, 1, 1024); // click on address bar
        assertTrue("UC_3.a", screen.getAddressBar().isInFocus());
        typeString(window, "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html\n");

        //4. Application shows the desired webpage.
        assertEquals("UC_4.a", screen.getAddress(), "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html");
        ArrayList<GUIObject> renderedObjects = screen.getDocArea().getDrawnGUIObjects();
        assertTrue("UC_4.b", renderedObjects.size() == 11);
        assertTrue("UC_4.j", containsGUIStringWith(docarea.xOffset, docarea.getRelativeYPos(), "HTML elements partially supported by Browsr:", renderedObjects));

        assertTrue("UC_4.d", containsGUILinkWith(docarea.xOffset, docarea.getRelativeYPos() + fm.getHeight(), "a", "a.html", renderedObjects));
        assertTrue("UC_4.g", containsGUIStringWith(docarea.xOffset + fm.stringWidth("table") + GUITable.xMargin, docarea.getRelativeYPos() + fm.getHeight(), "Hyperlink anchors", renderedObjects));

        assertTrue("UC_4.e", containsGUILinkWith(docarea.xOffset, docarea.getRelativeYPos() + 2*fm.getHeight(), "table", "table.html", renderedObjects));
        assertTrue("UC_4.c", containsGUIStringWith(docarea.xOffset + fm.stringWidth("table") + GUITable.xMargin, docarea.getRelativeYPos() + 2*fm.getHeight(), "Tables", renderedObjects));

        assertTrue("UC_4.h", containsGUILinkWith(docarea.xOffset, docarea.getRelativeYPos() + 3*fm.getHeight(), "tr", "tr.html", renderedObjects));
        assertTrue("UC_4.i", containsGUIStringWith(docarea.xOffset + fm.stringWidth("table") + GUITable.xMargin, docarea.getRelativeYPos() + 3*fm.getHeight(), "Table rows", renderedObjects));

        assertTrue("UC_4.h", containsGUILinkWith(docarea.xOffset,  docarea.getRelativeYPos()  + 4*fm.getHeight(), "td", "td.html", renderedObjects));
        assertTrue("UC_4.f", containsGUIStringWith(docarea.xOffset + fm.stringWidth("table") + GUITable.xMargin, docarea.getRelativeYPos() + 4*fm.getHeight(), "Table cells containing table data", renderedObjects));
        //This page has 4 GUILinks and 5 GUIStrings

        //5 User navigates to a desired webpage. (using hyperlink)
        String href = "";
        for(GUIObject obj : screen.getDocArea().getDrawnGUIObjects()){
            if(obj instanceof GUILink){
                //press a hyperlink
                obj.handleMouseEvent(docarea.xOffset, docarea.getRelativeYPos() + fm.getHeight(), MouseEvent.MOUSE_PRESSED, 1);
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
    void testUCSubmitForm() throws InvocationTargetException, InterruptedException {
        //1. User starts a Browsr application.
        Window window = new Window("useCase");
        java.awt.EventQueue.invokeAndWait(window::show);
        DefaultScreen screen = (DefaultScreen) window.getCurrentScreen();
        FontMetrics fm = window.getFontMetrics();
        DocumentArea docarea = screen.getDocArea();

        //2. User navigates to a desired webpage. (using AddressBar)
        window.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 5, 25, 1, 1, 1024); // click on address bar
        typeString(window, "https://people.cs.kuleuven.be/~bart.jacobs/swop/browsrformtest.html\n");

        //3. User clicks and modifies input field
        for(GUIObject obj : screen.getDocArea().getDrawnGUIObjects()){
            if(obj instanceof GUIInput){
                GUIInput input = (GUIInput) obj;
                obj.handleMouseEvent(docarea.xOffset + fm.stringWidth("Maximum number of words to show") + GUITable.xMargin, docarea.getRelativeYPos() + fm.getHeight(), MouseEvent.MOUSE_PRESSED, 1);
                assertTrue("UC_3.a", input.getInFocus());
                typeString(window, "test input");
                assertEquals("UC_3.b", input.getText(), "test input");

                input.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, ' ', 0); //left arrow
                input.handleKeyEvent(0, 0, ' ', KeyEvent.SHIFT_DOWN_MASK); //start shifting
                input.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_HOME, ' ', KeyEvent.SHIFT_DOWN_MASK); //home shifting
                input.handleKeyEvent(0, 0, ' ', 0); //end shifting
                input.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, 'a', 0); //replace "test inpu" with "a" -> "at" is new text
                assertEquals("UC_3.b", input.getText(), "at");

                break;
            }
        }

        //4. User clicks submit button
        for(GUIObject obj : screen.getDocArea().getDrawnGUIObjects()){
            if(obj instanceof GUIButton){
                GUIButton button = (GUIButton) obj;
                button.handleMouseEvent(docarea.xOffset , docarea.getRelativeYPos() + 3*fm.getHeight(), MouseEvent.MOUSE_RELEASED, 1);
                break;
            }
        }

        //5. Correct page is shown
        assertEquals("UC_5.a", screen.getAddress(), "https://people.cs.kuleuven.be/~bart.jacobs/swop/browsrformactiontest.php?starts_with=at&max_nb_results=");
        //We didnt fill out the second input so the returned page only contains an empty table
        assertTrue("UC_5.b", docarea.getDrawnGUIObjects().size() == 1);
        assertTrue("UC_5.b", docarea.getDrawnGUIObjects().get(0) instanceof GUITable);

    }

    @Test
    void testUCActivateBookmark() throws InterruptedException, InvocationTargetException {
        //1. User starts a Browsr application.
        Window window = new Window("useCase");
        java.awt.EventQueue.invokeAndWait(window::show);
        java.awt.EventQueue.invokeAndWait(window::show); // twee is beter dan een
        DefaultScreen screen = (DefaultScreen) window.getCurrentScreen();

        screen.getBookmarkBar().handleMouseEvent(MouseEvent.MOUSE_PRESSED,0, screen.getBookmarkBar().relativeYPos, 1);
        assertEquals("UC_1.a", screen.getAddress(), "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html");

    }

    @Test
    void testUCAddBookmark() throws InvocationTargetException, InterruptedException {
        //1. User starts a Browsr application.
        Window window = new Window("useCase");
        java.awt.EventQueue.invokeAndWait(window::show);
        java.awt.EventQueue.invokeAndWait(window::show); // twee is beter dan een
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
        assertTrue("UC_3.c", bookmarkName.getText().equals("testname"));
        //user presses on bookmarkAddress input box
        currentScreen.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 100, 110, 1, 1, 1024);
        //4. User types in the URL for the bookmark he want to create
        GUIInput bookmarkAddress = saveBookmarkScreen.getBookmarkAddress();
        assertTrue("UC_4.a", bookmarkAddress.getInFocus());
        typeString(window, "https://www.google.com");
        //user presses ENTER -> out of focus
        currentScreen.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_ENTER, '\n', 0);
        assertFalse("UC_4.b", bookmarkAddress.getInFocus());
        assertTrue("UC_4.c", bookmarkAddress.getText().equals("https://www.google.com"));
        //5. User presses confirm button
        currentScreen.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 150, 180, 1, 1, 1024);
        //default screen is showing again
        DefaultScreen newScreen = (DefaultScreen) window.getCurrentScreen();
        GUITable bookmarks = newScreen.getBookmarkBar().getBookmarks();
        //TODO: see if "testname" is in bookmarks (but i dont know how to test)
    }

    @Test
    void testUCCancelBookmark() throws InvocationTargetException, InterruptedException {
        //1. User starts a Browsr application.
        Window window = new Window("useCase");
        java.awt.EventQueue.invokeAndWait(window::show);
        java.awt.EventQueue.invokeAndWait(window::show); // twee is beter dan een
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
        assertTrue("UC_3.c", bookmarkName.getText().equals("testname"));
        //5. User presses cancel button
        currentScreen.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 20, 180, 1, 1, 1024);
        //default screen is showing again
        DefaultScreen newScreen = (DefaultScreen) window.getCurrentScreen();
        GUITable bookmarks = newScreen.getBookmarkBar().getBookmarks();
        //TODO: see if "testname" is NOT in bookmarks (but i dont know how to test)
    }
}

package Tests;

import gui.GUILink;
import gui.GUIObject;
import gui.Window;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class UseCaseTest {

    void fail(String testName) { throw new RuntimeException(testName + " failed."); }

    void assertTrue(String testName, boolean b) {
        if (!b) fail(testName);
    }
    void assertFalse(String testName, boolean b) {
        if (b) fail(testName);
    }
//    void assertEquals(String testName, Object a, Object b) {if(!a.equals(b)) fail(testName); }

    void typeString(Window window, String string) {
        for (char c: string.toCharArray()) {
            KeyStroke keyStroke = KeyStroke.getKeyStroke(c);
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);

            window.handleKeyEvent(401, keyCode , keyStroke.getKeyChar(), 0);
            window.handleKeyEvent(400, 0, keyStroke.getKeyChar(), 0);
            window.handleKeyEvent(402, keyCode, keyStroke.getKeyChar(), 0);
        }
    }

    @Test
    void testUseCase(){
        //1. User starts a Browsr application.
        Window window = new Window("useCase");

        //2. Application shows a welcome document.
        assertTrue("UC_2.a", window.getAddress().equals("WelcomeDoc.html"));
        assertTrue("UC_2.b", window.getDocArea().DocGUIObjects.size() == 7);
        //Welcome doc has 6 GUIStrings and 1 GUILink

        //3. User navigates to a desired webpage. (using AddressBar)
        window.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 5, 25, 1, 1, 1024); // click on address bar
        assertTrue("UC_3.a", window.getAddressBar().isInFocus());
        typeString(window, "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html\n");

        //4. Application shows the desired webpage.
        assertTrue("UC_4.a", window.getAddress().equals("https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html"));
        assertTrue("UC_4.b", window.getDocArea().DocGUIObjects.size() == 9);
        //This page has 4 GUILinks and 5 GUIStrings

        //3. User navigates to a desired webpage. (using hyperlink)
        String href = "";
        for(GUIObject obj : window.getDocArea().DocGUIObjects){
            if(obj instanceof GUILink){
                //press a hyperlink
                obj.handleClick();
                //all hyperlinks will navigate to a non-browsr webpage -> show error document
                href = ((GUILink) obj).getHref();
                break;
            }
        }
        //4. Application shows the desired webpage.
        assertTrue("UC_4.c", window.getAddress().equals("https://people.cs.kuleuven.be/~bart.jacobs/" + href));
        assertTrue("UC_4.d", window.getDocArea().DocGUIObjects.size() == 2);
        //error document has 2 GUIStrings
        for(GUIObject obj : window.getDocArea().DocGUIObjects){
            assertFalse("UC_4.e", obj instanceof GUILink);
            //error document has no GUILinks
        }

    }
}

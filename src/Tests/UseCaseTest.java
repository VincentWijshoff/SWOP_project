import gui.GUILink;
import gui.GUIObject;
import gui.GUIString;
import gui.Window;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Set;


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

    boolean containsGUIStringWith(int x, int y, String text, Set<GUIObject> set) {
        for (GUIObject obj: set) {
            if (obj instanceof  GUIString) {
                GUIString guiString = (GUIString) obj;
                if (guiString.coordX == x &&
                        guiString.coordY == y &&
                        guiString.getText().equals(text))
                    return true;
            }
        }
        return false;
    }

    boolean containsGUILinkWith(int x, int y, String text, String href, Set<GUIObject> set) {
        for (GUIObject obj: set) {
            if (obj instanceof GUILink) {
                GUILink guiLink = (GUILink) obj;
                if (guiLink.coordX == x &&
                        guiLink.coordY == y &&
                        guiLink.getText().equals(text) &&
                        guiLink.getHref().equals(href))
                    return true;
            }
        }
        return false;
    }


    @Test
    void testUseCase(){
        //1. User starts a Browsr application.
        Window window = new Window("useCase");

        //2. Application shows a welcome document.
        assertTrue("UC_2.a", window.getAddress().equals("WelcomeDoc.html"));
        assertTrue("UC_2.b", window.getDocArea().getDrawnGUIObjects().size() == 7);
        //Welcome doc has 6 GUIStrings and 1 GUILink

        //3. User navigates to a desired webpage. (using AddressBar)
        window.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 5, 25, 1, 1, 1024); // click on address bar
        assertTrue("UC_3.a", window.getAddressBar().isInFocus());
        typeString(window, "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html\n");

        //4. Application shows the desired webpage.
        assertTrue("UC_4.a", window.getAddress().equals("https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html"));
        Set<GUIObject> renderedObjects = window.getDocArea().getDrawnGUIObjects();
        assertTrue("UC_4.b", renderedObjects.size() == 9);
        assertTrue("UC_4.c", containsGUIStringWith(90, 98, "Tables", renderedObjects));
        assertTrue("UC_4.d", containsGUILinkWith(10, 82, "a", "a.html", renderedObjects));
        assertTrue("UC_4.e", containsGUILinkWith(10, 98, "table", "table.html", renderedObjects));
        assertTrue("UC_4.f", containsGUIStringWith(90, 130, "Table cells containing table data", renderedObjects));
        assertTrue("UC_4.g", containsGUIStringWith(90, 82, "Hyperlink anchors", renderedObjects));
        assertTrue("UC_4.h", containsGUILinkWith(10, 130, "td", "td.html", renderedObjects));
        assertTrue("UC_4.i", containsGUIStringWith(90, 114, "Table rows", renderedObjects));
        assertTrue("UC_4.j", containsGUIStringWith(10, 66, "HTML elements partially supported by Browsr:", renderedObjects));
        assertTrue("UC_4.h", containsGUILinkWith(10, 114, "tr", "tr.html", renderedObjects));
        //This page has 4 GUILinks and 5 GUIStrings

        //3. User navigates to a desired webpage. (using hyperlink)
        String href = "";
        for(GUIObject obj : window.getDocArea().getDrawnGUIObjects()){
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
        assertTrue("UC_4.d", window.getDocArea().getDrawnGUIObjects().size() == 2);
        //error document has 2 GUIStrings
        for(GUIObject obj : window.getDocArea().getDrawnGUIObjects()){
            assertFalse("UC_4.e", obj instanceof GUILink);
            //error document has no GUILinks
        }

    }
}

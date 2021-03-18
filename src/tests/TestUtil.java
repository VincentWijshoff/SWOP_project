package tests;

import gui.GUILink;
import gui.GUIObject;
import gui.GUIString;
import gui.Window;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Set;

public abstract class TestUtil {

    public static void fail(String testName) { throw new RuntimeException(testName + " failed."); }

    public static void assertTrue(String testName, boolean b) {
        if (!b) fail(testName);
    }
    public static void assertFalse(String testName, boolean b) {
        if (b) fail(testName);
    }
    public static void assertEquals(String testName, String a, String b) {if(!a.equals(b)) fail(testName); }

    public static void typeString(Window window, String string) {
        for (char c: string.toCharArray()) {
            KeyStroke keyStroke = KeyStroke.getKeyStroke(c);
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);

            window.handleKeyEvent(401, keyCode , keyStroke.getKeyChar(), 0);
            window.handleKeyEvent(400, 0, keyStroke.getKeyChar(), 0);
            window.handleKeyEvent(402, keyCode, keyStroke.getKeyChar(), 0);
        }
    }

    public static boolean containsGUIStringWith(int x, int y, String text, Set<GUIObject> set) {
        for (GUIObject obj: set) {
            if (obj instanceof GUIString) {
                GUIString guiString = (GUIString) obj;
                if (guiString.coordX == x &&
                        guiString.coordY == y &&
                        guiString.getText().equals(text))
                    return true;
            }
        }
        return false;
    }

    public static boolean containsGUILinkWith(int x, int y, String text, String href, Set<GUIObject> set) {
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

}

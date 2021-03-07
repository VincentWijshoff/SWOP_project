package html;

import gui.GUILink;
import gui.GUIObject;
import gui.GUIString;
import html.Elements.*;

import java.util.ArrayList;

public abstract class HtmlRenderer {

    private final static int xOffset = 10;

    public static ArrayList<GUIObject> renderHTML(ContentSpan element, int relativeYPos) {
        return renderHTML(element, relativeYPos, xOffset);
    }

    private static ArrayList<GUIObject> renderHTML(ContentSpan element, int startY, int startX) {
        return element.render(startX, startY, new ArrayList<>());
    }

    public static void addGUIString(String text, int x, int y, ArrayList<GUIObject> objects) {
        objects.add(new GUIString(text, x, y));
    }

    public static void addGUILink(String text, String href, int x, int y, ArrayList<GUIObject> objects) {
        objects.add(new GUILink(text, x, y, href));
    }

}
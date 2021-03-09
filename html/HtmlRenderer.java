package html;

import gui.GUILink;
import gui.GUIObject;
import gui.GUIString;
import html.Elements.*;

import java.util.ArrayList;

public abstract class HtmlRenderer {

    //The offset in horizontal direction (from left)
    private final static int xOffset = 10;

    /**
     * Render the ContentSpan object
     *
     * On the ContentSpan object is the render method called
     *
     * @param element   The ContentSpan object that needs to be rendered
     * @param startY    The start coordinate of the object (vertically)
     * @return A new list of GUIObjects
     */
    public static ArrayList<GUIObject> renderHTML(ContentSpan element, int startY) {
        return element.render(xOffset, startY, new ArrayList<>());
    }

    /**
     * Add a new GUIString to the given list
     *
     * @param text      The text of the GUIString
     * @param x         The x-coordinate of the object
     * @param y         The y-coordinate of the object
     * @param objects   The list this object will be added to
     */
    public static void addGUIString(String text, int x, int y, ArrayList<GUIObject> objects) {
        objects.add(new GUIString(text, x, y));
    }

    /**
     * Add a new GUILink to the given list
     *
     * @param text      The text of the GUILink
     * @param href      The href of the object
     * @param x         The x-coordinate of the object
     * @param y         The y-coordinate of the object
     * @param objects   The list this object will be added to
     */
    public static void addGUILink(String text, String href, int x, int y, ArrayList<GUIObject> objects) {
        objects.add(new GUILink(text, x, y, href));
    }

}
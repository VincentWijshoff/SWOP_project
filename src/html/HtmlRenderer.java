package html;

import gui.GUILink;
import gui.GUIObject;
import gui.GUIString;
import gui.GUITable;
import html.Elements.*;

import java.util.ArrayList;

/**
 * Contains static functions that will transform HtmlElements into GUIObjects that can be drawn
 */
public abstract class HtmlRenderer {

    /**
     * Render the ContentSpan object
     *
     * On the ContentSpan object is the render method called
     *
     * @param element   The ContentSpan object that needs to be rendered
     * @return A new list of GUIObjects
     */
    public static ArrayList<GUIObject> renderHTML(ContentSpan element) {
        return element.render(new ArrayList<>());
    }

    /**
     * Add a new GUIString to the given list
     *
     * @param text      The text of the GUIString
     * @param objects   The list this object will be added to
     */
    public static void addGUIString(String text, ArrayList<GUIObject> objects) {
        objects.add(new GUIString(text));
    }

    /**
     * Add a new GUILink to the given list
     *
     * @param text      The text of the GUILink
     * @param href      The href of the object
     * @param objects   The list this object will be added to
     */
    public static void addGUILink(String text, String href, String address, ArrayList<GUIObject> objects) {
        objects.add(new GUILink(text, href, address));
    }

    public static void addGUITable(ArrayList<ArrayList<GUIObject>> rows , ArrayList<GUIObject> objects) {
        objects.add(new GUITable(rows));
    }

}
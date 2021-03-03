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

    public static ArrayList<GUIObject> renderHTML(ContentSpan element, int startY, int startX) {
        return element.render(startX, startY, new ArrayList<>());
    }

}
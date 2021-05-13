package gui.Objects;

import events.FontMetricsHandler;
import events.PageLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FrameWrapper extends GUIObject {

    private Set<GUIObject> guiObjects = new HashSet<>();

    /**
     * Create an empty FrameWrapper
     */
    public FrameWrapper() {
    }

    /**
     * @param guiObjects create a FrameWrapper with these objects
     */
    public FrameWrapper(Set<GUIObject> guiObjects ) {
        this.guiObjects = guiObjects;
    }

    /**
     * Draw this FrameWrapper and its objects
     * @param g the graphics needed to draw each object
     */
    @Override
    public void draw(Graphics g) {
        //TODO add calculations for which objects to show and positioning of objects

        for (GUIObject obj: guiObjects) {
            obj.draw(g);
        }
    }

    /**
     * @return a copy of this framewrapper with the same guiobjects
     */
    @Override
    public HashSet<GUIObject> copy() {
        HashSet<GUIObject> cpy = new HashSet<>();
        FrameWrapper copy = new FrameWrapper(this.guiObjects);
        cpy.add(copy);
        return cpy;
    }

    /**
     * Handle a key event
     * @param id            The id of the key pressed
     * @param keyCode       The code of the key pressed
     * @param keyChar       The char of the key pressed
     * @param modifier   The modifiers of the key pressed
     */
    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifier) {
        guiObjects.forEach(obj -> obj.handleKeyEvent(id, keyCode, keyChar, modifier));
    }

    /**
     * Handle a mouse event
     * @param id            The id of the mouse event
     * @param x             The x coordinate of the mouse event
     * @param y             The y coordinate of the mouse event
     * @param clickCount    The click count of the mouse event
     */
    @Override
    public void handleMouseEvent(int x, int y, int id, int clickCount) {
        guiObjects.forEach(obj -> obj.handleMouseEvent(x, y, id, clickCount));
    }

    /**
     * @param obj the guiobject to add to this frame
     */
    public void add(GUIObject obj) {
        guiObjects.add(obj);
    }

    /**
     * Clear all guiobjects of this frame
     */
    public void clear() {
        guiObjects.clear();
    }

    /**
     * @return all guiobjects of this frame
     */
    public Set<GUIObject> getDrawnGUIObjects() {
        return guiObjects;
    }

    /**
     * @param objs the gui objects this frame will have
     */
    public void setGUIObjects(Set<GUIObject> objs) {
        this.guiObjects = objs;
    }

    /**
     * set the handler for all objects in this table
     * @param h  the document area that needs to be set
     */
    @Override
    public void setFontMetricsHandler(FontMetricsHandler h) {
        super.setFontMetricsHandler(h);

        for (GUIObject obj: guiObjects) {
            obj.setFontMetricsHandler(h);
        }
    }

    /**
     * set the handler for all objects in this table
     * @param h  the document area that needs to be set
     */
    @Override
    public void setPageLoader(PageLoader h) {
        super.setPageLoader(h);

        for (GUIObject obj: guiObjects) {
            obj.setPageLoader(h);
        }
    }
}

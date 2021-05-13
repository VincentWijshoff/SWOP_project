package gui.Objects;

import events.FontMetricsHandler;
import events.PageLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FrameWrapper extends GUIObject {

    private Set<GUIObject> guiObjects = new HashSet<>();
    private Scrollbar scrollbar = new Scrollbar();

    public FrameWrapper() {

    }

    public FrameWrapper(Set<GUIObject> guiObjects ) {
        this.guiObjects = guiObjects;

    }

    @Override
    public void draw(Graphics g) {
        //TODO add calculations for which objects to show and positioning of objects

        for (GUIObject obj: guiObjects) {
            obj.draw(g);
        }
    }

    @Override
    public HashSet<GUIObject> copy() {
        HashSet<GUIObject> cpy = new HashSet<>();
        FrameWrapper copy = new FrameWrapper(this.guiObjects);
        cpy.add(copy);
        return cpy;
    }

    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifier) {
        guiObjects.forEach(obj -> obj.handleKeyEvent(id, keyCode, keyChar, modifier));
    }

    @Override
    public void handleMouseEvent(int x, int y, int id, int clickCount) {
        guiObjects.forEach(obj -> obj.handleMouseEvent(x, y, id, clickCount));
    }

    public void add(GUIObject obj) {
        guiObjects.add(obj);
    }

    public void clear() {
        guiObjects.clear();
    }

    public Set<GUIObject> getDrawnGUIObjects() {
        return guiObjects;
    }

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

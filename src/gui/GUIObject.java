package gui;

import events.EventHandler;
import events.KeyEventListener;
import events.MouseEventListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 This class is the superclass of all other GUIObjects. Properties all GUIObjects need are defined here,
 such as their position, dimensions and ids.
 */
public class GUIObject implements MouseEventListener, KeyEventListener {

    private UUID id;
    public int width;
    public int height;
    public int coordX;
    public int coordY;

    public EventHandler eventHandler;

    /**
     * constructor off an object, it assigns a unique id
     */
    public GUIObject() {
        this.id = UUID.randomUUID();
    }

    /**
     * constructor off an object, it assigns a unique id
     */
    public GUIObject(int x, int y, int w, int h) {
        this.id = UUID.randomUUID();
        this.coordX = x;
        this.coordY = y;
        this.width = w;
        this.height = h;
    }

    /**
     * constructor off an object, it assigns a unique id
     */
    public GUIObject(EventHandler eventHandler, int x, int y, int w, int h) {
        setHandler(eventHandler);

        this.id = UUID.randomUUID();
        this.coordX = x;
        this.coordY = y;
        this.width = w;
        this.height = h;
    }

    /**
     * set the document area for a GUIObject
     * @param h  the document area that needs to be set
     */
    public void setHandler(EventHandler h) {
        this.eventHandler = h;
    }

    public void setEventHandlers(){
        this.eventHandler.addKeyEventListener(kListener);
        this.eventHandler.addMouseEventListener(mListener);
    }

    /**
     * Check if the given coordinates collide with the position off this object
     * @param x the x coordinate
     * @param y the y coordinate
     * @return  true if the given position collides with the position off the object
     */
    public boolean isInGUIObject(int x, int y) {
        return (x >= this.coordX &&
                x <= this.coordX + this.width &&
                y >= this.coordY &&
                y <= this.coordY + this.height);
    }

    /**
     * update the position off this object
     * @param x the new x coordinate
     * @param y the new y coordinate
     */
    public void setPosition(int x, int y) {
        this.coordX = x;
        this.coordY = y;
    }

    public void updateDimensions() {
    }

    /**
     * draw the object, this function should be overridden by each specific object
     * @param g the graphics needed to draw each object
     */
    public void draw(Graphics g) { }



    public ArrayList<GUIObject> getChildObjects() {
        return new ArrayList<>();
    }

    /**
     * Handle a key-press for this Object, if a special action is needed, this should be overridden.
     */
    public boolean handleKeyEvent(int id, int keyCode, char keyChar, int modifier) {
        return false;
    }

    /**
     * Handle the click on this Object, if a special action is needed, this should be overridden.
     */
    public void handleMouseEvent(int x, int y, int id, int clickCount) {
        System.out.println("You clicked on a GUIObject");
    }

    MouseEventListener mListener = this::handleMouseEvent;
    KeyEventListener kListener = this::handleKeyEvent;
}

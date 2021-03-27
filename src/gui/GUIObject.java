package gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 This class is the superclass of all other GUIObjects. Properties all GUIObjects need are defined here,
 such as their position, dimensions and ids.
 */
public class GUIObject {

    private UUID id;
    protected DocumentArea documentArea;
    public int width;
    public int height;
    public int coordX;
    public int coordY;

    /**
     * constructor off an object, it assigns a unique id
     */
    public GUIObject() {
        this.id = UUID.randomUUID();
    }

    /**
     * set the document area for a GUIObject
     * @param documentArea  the document area that needs to be set
     */
    public void setDocumentArea(DocumentArea documentArea) {
        this.documentArea = documentArea;
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

    /**
     * Update the dimension off this GUIObject
     * @param width     The new width off this object
     * @param height    The new height off this object
     */
    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * draw the object, this function should be overridden by each specific object
     * @param g the graphics needed to draw each object
     */
    public void draw(Graphics g) { }

    /**
     * Handle the click on this Object, if a special action is needed, this should be overridden
     */
    public void handleClick(int x, int y) {
        System.out.println("You clicked on a GUIObject");
    }

    public ArrayList<GUIObject> getChildObjects() {
        return new ArrayList<>();
    }
}

package gui;

import java.awt.*;
import java.util.UUID;

/*
 This class is the superclass of all other GUIObjects. Properties all GUIObjects need are defined here,
 such as their position, dimensions and ids.
 */
public class GUIObject {

    private UUID id;
    protected DocumentArea documentArea;

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

    public int width;
    public int height;
    public int coordX;
    public int coordY;

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
    public void updateGUIPos(int x, int y) {
        this.coordX = x;
        this.coordY = y;
    }

    /**
     * Update the dimension off this GUIObject
     * @param width     The new width off this object
     * @param height    The new height off this object
     */
    public void updateGUIDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * update the position and dimension off this object
     * @param x         the new x position
     * @param y         the new y position
     * @param width     the new width
     * @param height    the new height
     */
    public void updateGUIPosAndDim(int x, int y, int width, int height) {
        this.coordX = x;
        this.coordY = y;
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
    public void handleClick() {
        System.out.println("You clicked on a GUIObject");
    }

    @Override
    public String toString() {
        return "GUIObject{" +
                "width=" + width +
                ", height=" + height +
                ", coordX=" + coordX +
                ", coordY=" + coordY +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GUIObject guiObject = (GUIObject) o;

        if (width != guiObject.width) return false;
        if (height != guiObject.height) return false;
        if (coordX != guiObject.coordX) return false;
        return coordY == guiObject.coordY;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        result = 31 * result + coordX;
        result = 31 * result + coordY;
        return result;
    }
}

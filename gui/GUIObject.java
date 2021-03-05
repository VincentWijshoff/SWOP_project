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

    public GUIObject() {
        this.id = UUID.randomUUID();
    }

    public String getId() {
        return this.id.toString();
    }

    public void setDocumentArea(DocumentArea documentArea) {
        this.documentArea = documentArea;
    }

    public int width;
    public int height;
    public int coordX;
    public int coordY;

    public boolean isInGUIObject(int x, int y) {
        return (x >= this.coordX &&
                x <= this.coordX + this.width &&
                y >= this.coordY &&
                y <= this.coordY + this.height);
    }

    public void updateGUIPos(int x, int y) {
        this.coordX = x;
        this.coordY = y;
    }
    public void updateGUIDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public void updateGUIPosAndDim(int x, int y, int width, int height) {
        this.coordX = x;
        this.coordY = y;
        this.width = width;
        this.height = height;
    }



    public void draw(Graphics g) { }

    public void handleClick() {
        System.out.println("You clicked on a GUIObject");
    }
}

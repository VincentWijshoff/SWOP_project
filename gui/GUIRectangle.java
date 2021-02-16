package gui;

import java.awt.*;

public class GUIRectangle implements GUIObject{

    // Coords left-upper corner of rectangle
    int x;
    int y;

    int width;
    int height;

    public GUIRectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isInRectangle(int coordX, int coordY) {
        return (coordX >= this.x &&
                coordX <= this.x + this.width &&
                coordY >= this.y &&
                coordY <= this.y + this.height);
    }

    public void draw(Graphics g) {
        g.drawRect(this.x, this.y, this.width, this.height);
    }

    public void updateRectangle(int x, int y, int width, int heigth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = heigth;
    }

    public void updateWidth(int width) {
        this.width = width;
    }
}

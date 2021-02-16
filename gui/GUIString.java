package gui;

import java.awt.*;

public class GUIString implements GUIObject {

    String text;
    int x;
    int y;

    public GUIString(String text, int x, int y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.drawString(this.text, this.x, this.y);
    }
}
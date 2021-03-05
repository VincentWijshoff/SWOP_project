package gui;

import java.awt.*;

public class GUIString extends GUIObject {

    String text;

    public GUIString(String text, int x, int y) {
        super();

        this.text = text;
        updateGUIPos(x, y); // Width and height are calculated during drawing (fonts?)
    }

    public void draw(Graphics g) {
        // Bounds needed for click event
        int textWidth = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
        int textHeight = (int) g.getFontMetrics().getStringBounds(text, g).getHeight();
        updateGUIDimensions(textWidth, textHeight);

        g.drawString(this.text, coordX, coordY);

    }

    /*
     Strings have a slightly altered definition for this method, since they are rendered
     with the (x,y) position as their bottom left corner, instead of their top left corner, like other objects.
     */
    public boolean isInGUIObject(int x, int y) {
        return (x >= this.coordX &&
                x <= this.coordX + this.width &&
                y <= this.coordY &&
                y >= this.coordY - this.height);
    }

    @Override
    public void handleClick() {
        System.out.println("You clicked on a GUIString");
    }
}
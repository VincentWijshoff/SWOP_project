package gui;

import java.awt.*;

public class GUIRectangle extends GUIObject{

    public GUIRectangle(int x, int y, int width, int height) {
        super();

        // Initialise dimensions of rectangle
        updateGUIPosAndDim(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.drawRect(coordX, coordY, width, height);
    }

    @Override
    public void handleClick() {
        System.out.println("You clicked on a GUIRectangle");
    }
}

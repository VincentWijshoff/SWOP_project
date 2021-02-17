package gui;

import java.awt.*;

public class AddressBar{

    final int yLimit = 50;

    GUI gui;
    int abX = 5;
    int abY = 10;
    int height = 20;
    GUIAddressBar guiAddressBar;

    /*
    * Class used to describe the entire Address Bar section of our GUI.
     */
    public AddressBar(GUI gui) {
        this.gui = gui;
        this.guiAddressBar = new GUIAddressBar(abX, abY, height);
    }

    void paintAddressBar(Graphics g) {
        int gwidth = gui.getWidth();
        this.guiAddressBar.updateWidth(gwidth); //TODO: find better place to update width of address bar (needed when canvaswindow resized).
                                                //This belongs in the handleResize() method in GUI I think
        Color oldColor = g.getColor();

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, gwidth, this.yLimit);
        g.setColor(Color.BLACK);
        g.drawLine(0, this.yLimit, gwidth, this.yLimit);

        if (guiAddressBar != null) {
            g.setColor(Color.BLACK);
            g.drawRect(guiAddressBar.x, guiAddressBar.y, gwidth-13, guiAddressBar.height); // border
            g.clearRect(guiAddressBar.x+1, guiAddressBar.y+1, gwidth-14, guiAddressBar.height-1); // actual address bar (white part)

            //create the HEADER as an GUIString object (and store it in drawnObjects)
            GUIString header = new GUIString(guiAddressBar.getaBarText(), abX+5, abY+((int) (height/1.5)));
            gui.drawnObjects.add(header);
            header.draw(g);
            //g.drawString(guiAddressBar.getaBarText(), abX+5, abY+((int) (height/1.5)));
        }

        g.setColor(oldColor);
    }
}


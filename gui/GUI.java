package gui;

import canvaswindow.CanvasWindow;

import java.awt.*;
import java.util.ArrayList;

public class GUI extends CanvasWindow{

    AddressBar addressBar;
    DocumentArea docArea;

    public GUI(String title) {
        super(title);

        this.addressBar = new AddressBar(this);
        int relativeYpos = this.addressBar.yLimit;
        this.docArea = new DocumentArea(this, relativeYpos);
    }


    @Override
    protected void handleShown() {
        repaint();
    }

    @Override
    protected void paint(Graphics g) {
        this.docArea.paintDocArea(g);
        this.addressBar.paintAddressBar(g);
    }

    @Override
    protected void handleResize() {
        repaint();
    }

    public void draw(GUIObject object) {
        // drawnObjects.add(object); //TODO handle adding new objects to the cancas area
        repaint();
    }

    //TODO: maak unieke ID om objecten bij te houden?
    public void delete(int index) {
       //  drawnObjects.remove(index); // TODO handle removing elements from the canvas area
        repaint();
    }

    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        // If clicked in address bar:
        if (this.addressBar.isOnAddressBar(x, y)) {
            System.out.println("Clicked Address Bar!");
        }
    }

}

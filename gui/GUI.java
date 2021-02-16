package gui;

import canvaswindow.CanvasWindow;

import java.awt.*;
import java.util.ArrayList;

public class GUI extends CanvasWindow{
    ArrayList<GUIObject> drawnObjects = new ArrayList<>();
    AddressBar addressBar;
    DocumentArea docArea;

    public GUI(String title) {
        super(title);

        this.addressBar = new AddressBar(this);
        this.docArea = new DocumentArea(this);
    }


    @Override
    protected void handleShown() {
        repaint();
    }

    @Override
    protected void paint(Graphics g) {
        this.addressBar.paintAddressBar(g);

        for (GUIObject object: drawnObjects) {
            if (object instanceof GUIString) {
                GUIString string = (GUIString) object;
                g.drawString(string.text, string.x, string.y);
            }
            if (object instanceof GUIRectangle) {
                GUIRectangle rect = (GUIRectangle) object;
                g.drawRect(rect.x, rect.y, rect.width, rect.heigth);
            }
        }
    }

    @Override
    protected void handleResize() {
        repaint();
    }

    public void draw(GUIObject object) {
        drawnObjects.add(object);
        repaint();
    }

    //TODO: maak unieke ID om objecten bij te houden?
    public void delete(int index) {
        drawnObjects.remove(index);
        repaint();
    }

    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        // If clicked in address bar:
        if (this.addressBar.guiAddressBar.isInRectangle(x, y)) {
            // start typing?
        }
    }

}

package gui;

import canvaswindow.CanvasWindow;

import java.awt.*;
import java.awt.event.MouseEvent;
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
            object.draw(g);
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
            System.out.println("Clicked Address Bar! ");
            if (id == MouseEvent.MOUSE_PRESSED){
                //select the HEADER in drawnObjects
                //actions:
                //  * the current HEADER is selected (blue background)
                //  * keyboard focus (with text cursor)
            }

            //if in keyboard focus and clicked outside address bar -> same action as ENTER

        }
    }

    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
        //if in keyboard focus:
        //actions:
        //  * on ENTER -> navigate to new URL                           [for header]
        //  * on ESC   -> abort (old URL back) + lose keyboard focus    [for header]
    }

}

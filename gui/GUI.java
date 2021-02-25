package gui;

import canvaswindow.CanvasWindow;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GUI extends CanvasWindow{

    AddressBar addressBar;
    DocumentArea docArea;

    public GUI(String title) {
        super(title);

        this.addressBar = new AddressBar(this);
        this.docArea = new DocumentArea(this.addressBar.yLimit);
    }

    // Should be used for all other GUIObjects that should render in docArea:
    public GUIObject createGUIObject(GUIObject obj) {
        // obj.setGUI(this);
        this.docArea.DocGUIObjects.add(obj);
        return obj;
    }


    public void load(String url) {
        System.out.println("Loading webpage: " + url);
        this.addressBar.setAddress(url);
        this.docArea.loadAddress(url);
        this.repaint();
    }

    @Override
    protected void handleShown() {
        repaint();
    }

    @Override
    protected void paint(Graphics g) {
        // Draw every GUIObject in the docArea
        for (GUIObject obj : this.docArea.DocGUIObjects) {
            obj.draw(g);
        }

        // Draw AddressBar
        this.addressBar.draw(g);
    }

    @Override
    protected void handleResize() {
        repaint();
    }

    public void draw(GUIObject object) {
        // drawnObjects.add(object); //TODO handle adding new objects to the canvas area
        repaint();
    }

    //TODO: maak unieke ID om objecten bij te houden?
    public void delete(int index) {
        //  drawnObjects.remove(index); // TODO handle removing elements from the canvas area
        repaint();
    }

    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        // Clicked inside the AddressBar
        if (this.addressBar.isOnAddressBar(x, y)) {
            this.addressBar.setInFocus();
            System.out.println("Clicked on Address Bar!");
        } else if (this.addressBar.isInFocus()){
            this.addressBar.setOutFocus();
            System.out.println("Clicked off Address Bar!");
        }
        // handle the click event accordingly
        if (this.addressBar.isInFocus()) {
            this.addressBar.handleMouseEvent(id, clickCount);
        } else {
            if (id == MouseEvent.MOUSE_PRESSED) {
                for (GUIObject obj : this.docArea.DocGUIObjects) { // Loop through all GUIObjects in docArea
                    if (obj.isInGUIObject(x, y)) {
                        if (obj instanceof GUIString) {
                            System.out.println("You clicked on a GUIString");
                        } else if (obj instanceof GUIRectangle) {
                            System.out.println("You clicked on a GUIRectangle");
                        } else {
                            System.out.println("You clicked on a GUIObject");
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
        // handle the key event accordingly
        if (this.addressBar.isInFocus()) {
            // handle the key event in the address bar area
            this.addressBar.handleKeyboardEvent(id, keyCode, keyChar);
        } else {
            // handle the key event in the document area
        }
        //if in keyboard focus:
        //actions:
        //  * on ENTER -> navigate to new URL                           [for header]
        //  * on ESC   -> abort (old URL back) + lose keyboard focus    [for header]
    }

    public DocumentArea getDocArea() {
        return docArea;
    }
}
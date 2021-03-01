package gui;

import canvaswindow.CanvasWindow;

import java.awt.*;

public class Window extends CanvasWindow{

    AddressBar addressBar;
    DocumentArea docArea;

    public Window(String title) {
        super(title);

        this.addressBar = new AddressBar();
        this.docArea = new DocumentArea(this.addressBar.yLimit);
    }


    public void load(String url) {
        try {
            System.out.println("Loading webpage: " + url);
            this.addressBar.setAddress(url);
            this.docArea.loadAddress(url);
        } catch (Exception e) {
            System.out.println("loading Error Page");
            this.docArea.loadErrorDoc();
        }
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
        this.addressBar.draw(g, this);
    }

    @Override
    protected void handleResize() {
        repaint();
    }

    public void draw(GUIObject object) {
        repaint();
    }

    public void delete(int index) {
        repaint();
    }

    public String getAddress(){
        return this.addressBar.getAddress();
    }

    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        // Clicked inside the AddressBar
        if (this.addressBar.isOnAddressBar(x, y)) {
            this.addressBar.setInFocus();
            System.out.println("Clicked on Address Bar!");
        } else if (this.addressBar.isInFocus()){
            if(this.addressBar.setOutFocus()){
                this.load(this.getAddress());
            }
            System.out.println("Clicked off Address Bar!");
        }
        // handle the click event accordingly
        if (this.addressBar.isInFocus()) {
            this.addressBar.handleMouseEvent(id, clickCount);
        } else {
            this.docArea.handleMouseEvent(id, x, y, this);
        }
        this.repaint();
    }

    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx) {
        // handle the key event accordingly
        if (this.addressBar.isInFocus()) {
            // handle the key event in the address bar area
            if(this.addressBar.handleKeyboardEvent(id, keyCode, keyChar, modifiersEx)){
                this.load(this.getAddress());
            }
            this.repaint();
        } else {
            // handle the key event in the document area
        }
    }

    public DocumentArea getDocArea() {
        return this.docArea;
    }
}
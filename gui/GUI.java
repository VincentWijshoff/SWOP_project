package gui;

import canvaswindow.CanvasWindow;
import html.Elements.ContentSpan;

import java.awt.*;

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
        this.addressBar.draw(g);
    }

    @Override
    protected void handleResize() {
        repaint();
    }

    public void renderHTML(ContentSpan element) {
        this.docArea.renderHTML(element);
    }

    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        // set the address barr in focus or out off focus
        if (this.addressBar.isOnAddressBar(x, y)) {
            this.addressBar.setInFocus();
            System.out.println("Clicked on Address Bar!");
        } else {
            this.addressBar.setOutFocus();
            System.out.println("Clicked off Address Bar!");
        }
        // handle the click event accordingly
        if (this.addressBar.isInFocus()) {
            // handle the click in the address bar area
        } else {
            // handle the click in the document area
        }
    }

    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
        // handle the key event accordingly
        if (this.addressBar.isInFocus()) {
            // handle the key event in the address bar area
        } else {
            // handle the key event in the document area
        }
        //if in keyboard focus:
        //actions:
        //  * on ENTER -> navigate to new URL                           [for header]
        //  * on ESC   -> abort (old URL back) + lose keyboard focus    [for header]
    }

}

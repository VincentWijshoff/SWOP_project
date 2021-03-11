package gui;

import canvaswindow.CanvasWindow;
import html.HtmlLoader;
import localDocuments.Docs;

import java.awt.*;

/**
 * Class that manages the window for our browsr
 * This class contains 2 large parts, the addressbar and the documentarea
 */
public class Window extends CanvasWindow{

    AddressBar addressBar;
    DocumentArea docArea;

    /**
     * Create a new window
     * @param title The title off the window
     */
    public Window(String title) {
        super(title);

        this.addressBar = new AddressBar("WelcomeDoc.html");
        this.docArea = new DocumentArea(this, this.addressBar.yLimit);

        HtmlLoader loader = new HtmlLoader(Docs.getWelcomePage());
        loader.setDocumentArea(docArea);
        loader.loadPage();
    }

    /**
     * Load a url
     * @param url   The url that needs to be loaded
     */
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

    /**
     * Repaint the canvas
     */
    @Override
    protected void handleShown() {
        repaint();
    }

    /**
     * paint the objects in this window
     * @param g This object offers the methods that allow you to paint on the canvas.
     */
    @Override
    protected void paint(Graphics g) {
        // Draw every GUIObject in the docArea
        for (GUIObject obj : this.docArea.getDrawnGUIObjects()) {
            obj.draw(g);
        }

        // Draw AddressBar
        this.addressBar.draw(g, this);
    }

    /**
     * Repaint the canvas
     */
    @Override
    protected void handleResize() {
        repaint();
    }

    /**
     * Get the current address of this window
     * @return the current address
     */
    public String getAddress(){
        return this.addressBar.getAddress();
    }

    /**
     * Handle a mouse event
     * @param id            The id off the mouse event
     * @param x             The x coordinate off the mouse event
     * @param y             The y coordinate off the mouse event
     * @param clickCount    The clickCount off the mouse event
     * @param button        The button pressed od the mouse
     * @param modifiersEx   The modifiers active on the mouse
     */
    @Override
    public void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
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
            this.docArea.handleMouseEvent(id, x, y);
        }
        this.repaint();
    }

    /**
     * Handle a key event
     * @param id            The id off the key
     * @param keyCode       The code off the key
     * @param keyChar       The char off the key
     * @param modifiersEx   The active modifiers on the key
     */
    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx) {
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

    /**
     * Get the document area off this window
     * @return the document area
     */
    public DocumentArea getDocArea() {
        return this.docArea;
    }

    /**
     * Get the address bar of this window
     * @return the address bar
     */
    public AddressBar getAddressBar() {
        return addressBar;
    }
}
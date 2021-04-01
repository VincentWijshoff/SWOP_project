package gui;

import canvaswindow.CanvasWindow;
import events.KeyEventHandler;
import events.MouseEventHandler;

import java.awt.*;

/**
 * Class that manages the window for our browsr
 * This class contains 2 large parts, the addressbar and the documentarea
 */
public class Window extends CanvasWindow{

    AddressBar addressBar; //TODO: delete?
    BookmarkBar bookmarkBar; //TODO: delete?
    DocumentArea docArea; //TODO: delete?

    Font font = new Font(Font.DIALOG, Font.PLAIN, 12); //TODO: delete?
    FontMetrics fontMetrics; //TODO: delete?

    MouseEventHandler mouseEventHandler; //TODO: delete?
    KeyEventHandler keyEventHandler;//TODO: delete?

    Screen currentScreen;

    /**
     * Create a new window
     * @param title The title off the window
     */
    public Window(String title) {
        super(title);

        this.mouseEventHandler = new MouseEventHandler();
        this.keyEventHandler = new KeyEventHandler();

        this.addressBar = new AddressBar("WelcomeDoc.html", this);
        this.bookmarkBar = new BookmarkBar(this.addressBar.yLimit, this);
        this.docArea = new DocumentArea(this, this.addressBar.yLimit + this.bookmarkBar.getHeight());

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
            e.printStackTrace();
            this.docArea.loadErrorDoc();
        }
        this.repaint();
    }

    /**
     * Runs when the canvas is shown
     */
    @Override
    protected void handleShown() {
        this.fontMetrics = getFontMetrics(font);

        this.docArea.loadWelcomeDoc();

        repaint();
    }

    /**
     * paint the objects in this window
     * @param g This object offers the methods that allow you to paint on the canvas.
     */
    @Override
    protected void paint(Graphics g) {
        g.setFont(font);

        this.docArea.draw(g);

        // Draw AddressBar
        this.bookmarkBar.draw(g, this.getWidth());
        this.addressBar.draw(g, this.getWidth());
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
    //TODO: delete?
    public void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        this.mouseEventHandler.onClick(id, x, y, clickCount);
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
    //TODO: delete?
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx) {
        this.keyEventHandler.onKeyPress(id, keyCode, keyChar, modifiersEx);
        this.repaint();
    }

    /**
     * Get the document area off this window
     * @return the document area
     */
    public DocumentArea getDocArea() {
        return this.docArea;
    }//TODO: delete?

    /**
     * Get the address bar of this window
     * @return the address bar
     */
    public AddressBar getAddressBar() {
        return addressBar;
    }//TODO: delete?

    public BookmarkBar getBookmarkBar() { return bookmarkBar; }//TODO: delete?

    public FontMetrics getFontMetrics() {
        return this.fontMetrics;
    }//TODO: delete?
}
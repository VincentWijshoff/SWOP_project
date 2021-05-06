package gui.DefaultScreen;

import gui.Objects.GUIObject;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The document area manages all GUIObjects that are drawn on the canvas
 */
public class DocumentArea {

    // necessary element for the document area
    private final int relativeYPos;
    private final DefaultScreen screen;
    private Pane pane;

    /**
     * Class used to describe the entire Document section of our GUI.
     * @param screen the screen this document area should be part of
     * @param relativeYpos y-position this document area starts at
     */
    public DocumentArea(DefaultScreen screen, int relativeYpos) {
        this.screen = screen;
        this.relativeYPos = relativeYpos;
        this.pane = new ChildPane(this);
        this.pane.setDimensions(0, this.relativeYPos, 0, 0);
        this.pane.setInFocus();
    }

    /**
     * Get the Window
     * @return The window linked to this document area
     */
    public DefaultScreen getScreen() {
        return this.screen;
    }

    /**
     * Get the relative y position for the document area, this is not 0 because off the address bar
     * @return the relative y position
     */
    public int getRelativeYPos() {
        return this.relativeYPos;
    }

    /**
     * Return currently rendered GUIObjects
     * @return Set of GUIObjects
     */
    public ArrayList<GUIObject> getDrawnGUIObjects() {
        return this.pane.getDrawnGUIObjects();
    }

    /**
     * Draw the document area
     * @param g The graphics needed to draw the document area
     */
    public void draw(Graphics g) {
        this.pane.draw(g);
        // Draw every GUIObject in the docArea
    }

    /**
     * loads a page given the url as string
     *
     * @param   url the URL we need to navigate to
     *
     * post    The new page is loaded if the given url is valid, otherwise the errorDoc is loaded
     */
    public void loadAddress(String url) throws IOException {
        this.pane.loadAddress(url);
    }

    /**
     * Load the error document because an error occurred whit the loading
     */
    public void loadErrorDoc() {
        this.pane.loadErrorDoc();
    }

    /**
     * Load the welcome document
     */
    public void loadWelcomeDoc() {
        this.pane.setDimensions(0, this.relativeYPos,
                this.screen.getWidth(),
                this.screen.getHeight() - this.relativeYPos);
        this.pane.loadWelcomeDoc();
    }

    /**
     * Get the current html of the webpage
     * @return  The html of the current webpage
     */
    public String getCurrentHtml() {
        return this.pane.getCurrentHtml();
    }

    /**
     * Load a webpage from url or from href
     * @param url   The full url or the href to the new website
     */
    public void load(String url) {
        this.screen.load(url);
    }

    /**
     * handles the key-presses for this document area
     * @param id        The id of the pressed button
     * @param keyCode   The keycode for the pressed button
     * @param keyChar   The char that was pressed
     * @param modifier  The modifier on the pressed key
     */
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifier) {
        this.pane.handleKeyEvent(id, keyCode, keyChar, modifier);
    }

    /**
     * handles the mouse-clicks for this document area
     * @param id            The id of the mouse event
     * @param clickCount    The click count of the user
     * @param x             x position of the mouse event
     * @param y             y position of the mouse event
     */
    public void handleMouseEvent(int id, int x, int y, int clickCount){
        if (this.pane.isOnPane(x, y)) {
            this.pane.handleMouseEvent(id, x, y, clickCount);
        }
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public ChildPane getFocusedPane() {
        return this.pane.getFocusedPane();
    }
}


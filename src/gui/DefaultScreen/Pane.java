package gui.DefaultScreen;

import gui.Objects.GUIObject;
import html.HtmlLoader;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Pane {
    public int x = 0;
    public int y = 0;
    public int width = 0;
    public int height = 0;
    HtmlLoader loader = null;
    DocumentArea docArea = null;
    boolean isInFocus = false;
    public ParentPane parentPane = null;

    /**
     * Set the dimension for this pane
     * @param x         The x coordinate off the top left off the pane
     * @param y         The y coordinate off the top left off the pane
     * @param width     The width off the pane
     * @param height    The height off the pane
     */
    void setDimensions(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    void setParentPane(ParentPane parentPane){
        this.parentPane = parentPane;
    }

    /**
     * Handle a key event on this pane
     * @param id        The id off the key event
     * @param keyCode   The keycode off the key event
     * @param keyChar   The char of the key pressed
     * @param modifier  The modifier on the key pressed
     */
    abstract void handleKeyEvent(int id, int keyCode, char keyChar, int modifier);

    /**
     * Handle a mouse event on this pane
     * @param id            The id off the mouse event
     * @param x             The x coordinate off the mouse event
     * @param y             The y coordinate off the mouse event
     * @param clickCount    The click count on the mouse event
     */
    abstract void handleMouseEvent(int id, int x, int y, int clickCount);

    /**
     * Load a webpage from url or from href
     * @param url   The given url or href
     */
    public void load(String url){
        if(this.docArea == null){
            throw new RuntimeException("The document area was not initialised");
        }
        this.docArea.load(url);
    }

    /**
     * Check if the coordinate is on the pane
     * @param x     The x coordinate
     * @param y     The y coordinate
     * @return      True if the coordinates are on this pane
     */
    boolean isOnPane(int x, int y){
        return x > this.x &&
                x < this.x + this.width &&
                y > this.y &&
                y < this.y + this.height;
    }

    public abstract void loadWelcomeDoc();

    public abstract void loadErrorDoc();

    public abstract void loadAddress(String url) throws IOException;

    public abstract String getCurrentHtml();

    public abstract ArrayList<GUIObject> getDrawnGUIObjects();

    public abstract void addGUIObjects(ArrayList<GUIObject> objects);

    public abstract void draw(Graphics g);

    protected abstract void setInFocus();

    protected abstract void setOutFocus();

    public abstract ChildPane getFocusedPane();

    protected abstract void updateDimensions(int x, int y, int width, int height);
}

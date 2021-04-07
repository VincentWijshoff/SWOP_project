package gui.DialogScreen;

import events.*;
import gui.DefaultScreen.DefaultScreen;
import gui.Objects.GUIObject;
import gui.Screen;
import gui.Window;

import java.awt.*;
import java.util.ArrayList;

/**
 * The dialog screen
 */
public abstract class DialogScreen implements Screen, EventHandler {

    // event handlers
    MouseEventHandler mouseEventHandler;
    KeyEventHandler keyEventHandler;

    // window and screen objects
    gui.Window window;
    DefaultScreen previousScreen;

    // list of all guiObjects
    ArrayList<GUIObject> guiObjects;

    public DialogScreen(Window window, DefaultScreen prevScreen){
        this.window = window;
        this.previousScreen = prevScreen;
        this.guiObjects = new ArrayList<>();
        this.mouseEventHandler = new MouseEventHandler();
        this.keyEventHandler = new KeyEventHandler();
    }

    /**
     * draw the dialog screen
     * @param g The graphics needed to draw the screen
     */
    @Override
    public void draw(Graphics g) {
        this.guiObjects.forEach(obj -> obj.draw(g));
    }

    /**
     * Handle the mouse event on the dialog screen
     * @param id            The id off the mouse event
     * @param x             The x coordinate off the mouse event
     * @param y             The y coordinate off the mouse event
     * @param clickCount    The click count on the mouse event
     * @param button        The button pressed on the mouse
     * @param modifiersEx   The modifier active on the mouse event
     */
    @Override
    public void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        if(this.mouseEventHandler != null) {
            this.mouseEventHandler.onClick(id, x, y, clickCount);
        }
    }

    /**
     * Handle the key event on the dialog screen
     * @param id            The id off the key event
     * @param keyCode       The code off the key pressed
     * @param keyChar       The char of the key pressed
     * @param modifiersEx   The modifiers active on the key pressed
     */
    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx) {
        if(this.keyEventHandler != null){
            this.keyEventHandler.onKeyPress(id, keyCode, keyChar, modifiersEx);
        }
    }

    /**
     * dos nothing as a dialog screen should not be active on startup
     */
    @Override
    public void handleShown() {

    }

    /**
     * Get the font metrics from the window
     * @return  The font metrics
     */
    @Override
    public FontMetrics getFontMetrics() {
        return window.getFontMetrics();
    }

    /**
     * Load a url
     * @param url   A full  url starting with http:// or a href
     */
    @Override
    public void load(String url) { }

    /**
     * Add a mouse event listener to the dialog screen
     * @param listener  The listener that will be called when a mouse event occurs
     */
    @Override
    public void addMouseEventListener(MouseEventListener listener) {
        this.mouseEventHandler.addMouseEventListener(listener);
    }

    /**
     * Add a key event listener to the dialog screen
     * @param listener  The listener that will be called when a key event occurs
     */
    @Override
    public void addKeyEventListener(KeyEventListener listener) {
        this.keyEventHandler.addKeyEventListener(listener);
    }

    /**
     * Remove a mouse event listener from the screen
     * @param listener  The mouse event that needs to be removed
     */
    @Override
    public void removeMouseEventListener(MouseEventListener listener) {
        this.mouseEventHandler.removeMouseEventListener(listener);
    }

    /**
     * Remove a key event listener from the screen
     * @param listener  The key event that needs to be removed
     */
    @Override
    public void removeKeyEventListener(KeyEventListener listener) {
        this.keyEventHandler.removeKeyEventListener(listener);
    }

    /**
     * create the specified dialog screen
     */
    abstract void create();

    /**
     * Add a GUIObject to the the list of GUIObjects
     * @param obj   The gui object that needs to be added
     */
    protected void addGUIObject(GUIObject obj){
        this.guiObjects.add(obj);
    }

    /**
     * Get all current GUIObjects
     * @return  All gui objects
     */
    protected ArrayList<GUIObject> getGUIObjects(){
        return this.guiObjects;
    }

    /**
     * Return to the previous screen
     */
    protected void returnToPreviousScreen(){
        this.window.setScreen(this.previousScreen);
    }
}

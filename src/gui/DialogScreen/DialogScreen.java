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

    // window and screen objects
    gui.Window window;
    DefaultScreen previousScreen;

    // list of all guiObjects
    ArrayList<GUIObject> guiObjects;

    /**
     * construct a dialog screen
     * @param window the window that should have this dialogscreen
     * @param prevScreen the DefaultSCreen that was shown before this dialog screen
     */
    public DialogScreen(Window window, DefaultScreen prevScreen){
        this.window = window;
        this.previousScreen = prevScreen;
        this.guiObjects = new ArrayList<>();
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
     * @param id            The id of the mouse event
     * @param x             The x coordinate of the mouse event
     * @param y             The y coordinate of the mouse event
     * @param clickCount    The click count on the mouse event
     * @param button        The button pressed on the mouse
     * @param modifiersEx   The modifier active on the mouse event
     */
    @Override
    public void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        this.guiObjects.forEach(obj -> obj.handleMouseEvent(x, y, id, clickCount));
    }

    /**
     * Handle the key event on the dialog screen
     * @param id            The id of the key event
     * @param keyCode       The code of the key pressed
     * @param keyChar       The char of the key pressed
     * @param modifiersEx   The modifiers active on the key pressed
     */
    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx) {
        this.guiObjects.forEach(obj -> obj.handleKeyEvent(id, keyCode, keyChar, modifiersEx));
    }

    /**
     * Does nothing as a dialog screen should not be active on startup
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
     * @param url   A full url starting with http:// or a href
     */
    @Override
    public void load(String url) { }

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
    public ArrayList<GUIObject> getGUIObjects(){
        return this.guiObjects;
    }

    /**
     * Return to the previous screen
     */
    protected void returnToPreviousScreen(){
        this.window.setScreen(this.previousScreen);
    }
}

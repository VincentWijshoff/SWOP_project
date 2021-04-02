package gui.DialogScreen;

import events.*;
import gui.Objects.GUIObject;
import gui.Screen;
import gui.Window;

import java.awt.*;
import java.util.ArrayList;

public abstract class DialogScreen implements Screen, EventHandler {

    MouseEventHandler mouseEventHandler;
    KeyEventHandler keyEventHandler;

    gui.Window window;
    Screen previousScreen;

    ArrayList<GUIObject> guiObjects;

    public DialogScreen(Window window, Screen prevScreen){
        this.window = window;
        this.previousScreen = prevScreen;
        this.guiObjects = new ArrayList<GUIObject>();
        this.mouseEventHandler = new MouseEventHandler();
        this.keyEventHandler = new KeyEventHandler();
    }

    @Override
    public void draw(Graphics g) {
        this.guiObjects.forEach(obj -> obj.draw(g));
    }

    @Override
    public void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        if(this.mouseEventHandler != null) {
            this.mouseEventHandler.onClick(id, x, y, clickCount);
        }
    }

    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx) {
        if(this.keyEventHandler != null){
            this.keyEventHandler.onKeyPress(id, keyCode, keyChar, modifiersEx);
        }
    }

    @Override
    public void handleShown() {

    }

    @Override
    public FontMetrics getFontMetrics() {
        return window.getFontMetrics();
    }

    @Override
    public void load(String url) { }

    @Override
    public void addMouseEventListener(MouseEventListener listener) {
        this.mouseEventHandler.addMouseEventListener(listener);
    }

    @Override
    public void addKeyEventListener(KeyEventListener listener) {
        this.keyEventHandler.addKeyEventListener(listener);
    }

    @Override
    public void removeMouseEventListener(MouseEventListener listener) {
        this.mouseEventHandler.removeMouseEventListener(listener);
    }

    @Override
    public void removeKeyEventListener(KeyEventListener listener) {
        this.keyEventHandler.removeKeyEventListener(listener);
    }

    @Override
    public void addBookmark(String name, String url) {
        if(this.previousScreen != null){
            this.previousScreen.addBookmark(name, url);
        }
    }

    abstract void create();

    protected void addGUIObject(GUIObject obj){
        this.guiObjects.add(obj);
    }

    protected ArrayList<GUIObject> getGUIObjects(){
        return this.guiObjects;
    }

    protected void returnToPreviousScreen(){
        this.window.setScreen(this.previousScreen);
    }
}

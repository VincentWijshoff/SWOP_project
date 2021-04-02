package gui.DialogScreen;

import events.*;
import gui.Objects.GUIObject;
import gui.Screen;
import gui.Window;

import java.awt.*;

public abstract class DialogScreen implements Screen, EventHandler {

    MouseEventHandler mouseEventHandler;
    KeyEventHandler keyEventHandler;

    gui.Window window;
    Screen previousScreen;

    GUIObject giuObject;

    public DialogScreen(Window window, Screen prevScreen){
        this.window = window;
        this.previousScreen = prevScreen;
        this.create();
    }

    @Override
    public void draw(Graphics g) {

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

    abstract void create();

    private void returnToPreviousScreen(){
        this.window.setScreen(this.previousScreen);
    }
}

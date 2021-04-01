package gui;

import events.*;
import gui.Objects.GUIObject;

import java.awt.*;

public class DialogScreen implements Screen, EventHandler {

    MouseEventHandler mouseEventHandler;
    KeyEventHandler keyEventHandler;

    Window window;

    GUIObject giuObject;

    public DialogScreen(Window window){
        this.window = window;
    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        this.mouseEventHandler.onClick(id, x, y, clickCount);
    }

    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx) {
        this.keyEventHandler.onKeyPress(id, keyCode, keyChar, modifiersEx);
    }

    @Override
    public void handleShown() {

    }

    public void MakeBookMarkScreen(){

    }

    public void MakeOtherScreen(){

    }

    @Override
    public FontMetrics getFontMetrics() {
        return window.getFontMetrics();
    }

    @Override
    public void load(String url) {

    }

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
}

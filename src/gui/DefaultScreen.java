package gui;

import events.*;

import java.awt.*;

public class DefaultScreen implements Screen, EventHandler {

    MouseEventHandler mouseEventHandler;
    KeyEventHandler keyEventHandler;

    AddressBar addressBar;
    BookmarkBar bookmarkBar;
    DocumentArea documentArea;

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public void handleMouseEvent(int a, int b, int c, int d, int e, int f) {

    }

    @Override
    public void handleKeyEvent(int a, int b, char c, int d) {

    }

    public String getAddress(){
        return null;
    }

    @Override
    public FontMetrics getFontMetrics() {
        return null;
    }

    @Override
    public void load(String url) {

    }

    @Override
    public void addMouseEventListener(MouseEventListener listener) {

    }

    @Override
    public void addKeyEventListener(KeyEventListener listener) {

    }

    @Override
    public void removeMouseEventListener(MouseEventListener listener) {

    }

    @Override
    public void removeKeyEventListener(KeyEventListener listener) {

    }
}

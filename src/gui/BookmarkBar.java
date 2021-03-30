package gui;

import events.EventHandler;
import events.KeyEventListener;
import events.MouseEventListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BookmarkBar implements EventHandler {

    private int relativeYPos;
    private int height = 25;
    private int width;
    private GUITable bookmarks;
    private Window window;

    public BookmarkBar(int relpos, Window w){
        this.relativeYPos = relpos;
        this.window = w;

        this.bookmarks = new GUITable(0, relativeYPos);
        this.bookmarks.addRow(new ArrayList<>());

    }

    public Window getWindow() {
        return window;
    }

    public void draw(Graphics g, int width){
        this.width = width;
        Color oldColor = g.getColor();
        int actHeight = this.height + this.relativeYPos;

        // first draw the grey enclosing area
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, this.relativeYPos, this.width, this.height);
        g.setColor(Color.BLACK);
        g.drawLine(0, actHeight, this.width, actHeight);


        bookmarks.draw(g);


        g.setColor(oldColor);

        if(first){
            this.addBookmark("home page Bart Jacobs", "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html");
            this.addBookmark("home page Bart Jacobs 2.0", "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html");
            first = false;
        }
    }

    private  boolean first = true;

    public void handleMouseEvent(int x, int y, int id, int clickCount){
        if(id == MouseEvent.MOUSE_PRESSED) {
            bookmarks.handleMouseEvent(x, y, id, clickCount);
        }
    }

    public void addBookmark(String name, String address){
        GUILink link = new GUILink(name, address, getWindow().getAddress());
        link.setHandler(this);
        bookmarks.appendToRow(link, 0);

        bookmarks.updateDimensions();
    }

    public int getHeight(){
        return this.height;
    }

    @Override
    public void load(String url){
        this.getWindow().load(url);
    }

    @Override
    public void addMouseEventListener(MouseEventListener listener) {
        this.getWindow().mouseEventHandler.addMouseEventListener(listener);
    }

    @Override
    public void addKeyEventListener(KeyEventListener listener) {
        this.getWindow().keyEventHandler.addKeyEventListener(listener);
    }

    @Override
    public FontMetrics getFontMetrics() {
        return this.getWindow().getFontMetrics();
    }

}

package gui;

import events.EventHandler;
import events.KeyEventListener;
import events.MouseEventListener;
import gui.Objects.GUILink;
import gui.Objects.GUITable;

import java.awt.*;
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


        this.getBookmarks().draw(g);


        g.setColor(oldColor);

        if(first){
            this.addBookmark("home page Bart Jacobs", "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html");
            this.addBookmark("home page Bart Jacobs 2.0", "https://people.cs.kuleuven.be/~bart.jacobs/swop/browsrformtest.html");
            first = false;
        }
    }

    private  boolean first = true;

    public void addBookmark(String name, String address){
        GUILink link = new GUILink(name, address, getWindow().getAddress());

        link.setHandler(this);
        link.setEventHandlers();

        this.getBookmarks().appendToRow(link, 0);

        this.getBookmarks().updateDimensions();
    }

    public int getHeight(){
        return this.height;
    }

    public GUITable getBookmarks() {
        return this.bookmarks;
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
    public void removeMouseEventListener(MouseEventListener listener) {
        this.getWindow().mouseEventHandler.removeMouseEventListener(listener);
    }

    @Override
    public void removeKeyEventListener(KeyEventListener listener) {
        this.getWindow().keyEventHandler.removeKeyEventListener(listener);
    }

    @Override
    public FontMetrics getFontMetrics() {
        return this.getWindow().getFontMetrics();
    }

}

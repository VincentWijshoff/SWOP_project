package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BookmarkBar implements WindowHandler{

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

    public void handleMouseEvent(int id, int x, int y){
        if(id == MouseEvent.MOUSE_PRESSED) {
            bookmarks.handleClick(x, y);
        }
    }

    public void addBookmark(String name, String address){
        GUILink link = new GUILink(name, address, window.getAddress());
        link.setHandler(this);
        bookmarks.appendToRow(link, 0);

        bookmarks.updateDimensions();
    }

    public int getHeight(){
        return this.height;
    }

    @Override
    public void load(String url){
        this.window.load(url);
    }

    @Override
    public FontMetrics getFontMetrics() {
        return this.window.getFontMetrics();
    }

}

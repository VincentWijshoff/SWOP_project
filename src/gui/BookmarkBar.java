package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BookmarkBar {

    private ArrayList<GUILink> bookmarks = new ArrayList<GUILink>();
    private int relativeYPos;
    private int height = 25;
    private int nextX = 5;
    private int w;

    public BookmarkBar(int relpos){
        this.relativeYPos = relpos;
        this.addBookmark("home page Bart Jacobs", "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html");
        this.addBookmark("home page Bart Jacobs 2.0", "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html");
    }

    public void draw(Graphics g, int width){
        this.w = width;
        Color oldColor = g.getColor();
        int actHeight = this.height + this.relativeYPos;

        // first draw the grey enclosing area
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, this.relativeYPos, this.w, this.height);
        g.setColor(Color.BLACK);
        g.drawLine(0, actHeight, this.w, actHeight);

        //draw each bookamrk
        for(GUILink link : bookmarks){
            link.draw(g);
        }


        g.setColor(oldColor);
    }

    public void handleMouseEvent(int id, int x, int y){
        if(id == MouseEvent.MOUSE_PRESSED) {
            for (GUIObject link : bookmarks) {
                System.out.println("" +x +" "+ y +" "+ link.coordX +" "+ link.coordY);
                if (link.isInGUIObject(x, y)) {
                    link.handleClick();
                    return;
                }
            }
        }
    }

    public void addBookmark(String name, String address){
        GUILink link = new GUILink(name, this.nextX, this.relativeYPos + 15, address);
        bookmarks.add(link);
        this.nextX += link.width + 5;
    }

    public int getHeight(){
        return this.height;
    }

}

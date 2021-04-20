package gui.DefaultScreen;

import commands.AddBookmarkOperation;
import gui.Objects.GUITable;

import java.awt.*;
import java.util.ArrayList;

/**
 * The bookmark bar
 */
public class BookmarkBar {

    // GUI elements
    public final int relativeYPos;
    public static final int relativeXPos = 5;
    private final int height = 25;
    private final GUITable bookmarks;
    private final DefaultScreen screen;

    /**
     * create bookmark bar
     * @param relpos y position this bookmark bar starts at
     * @param screen screen this bookmark bar is part of
     */
    public BookmarkBar(int relpos, DefaultScreen screen){
        this.relativeYPos = relpos;
        this.screen = screen;

        this.bookmarks = new GUITable(relativeXPos, relativeYPos);
        this.bookmarks.addRow(new ArrayList<>());
    }

    /**
     * Get the default screen the bookmark bar is added to
     * @return  The default screen
     */
    public DefaultScreen getScreen() {
        return screen;
    }

    /**
     * Draw the bookmark bar
     * @param g     The graphics needed to draw
     * @param width The width of the window
     */
    public void draw(Graphics g, int width){
        Color oldColor = g.getColor();
        int actHeight = this.height + this.relativeYPos;

        // first draw the grey enclosing area
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, this.relativeYPos, width, this.height);
        g.setColor(Color.BLACK);
        g.drawLine(0, actHeight, width, actHeight);

        this.getBookmarks().draw(g);

        g.setColor(oldColor);

        if(first){
            this.screen.execute(new AddBookmarkOperation("home page Bart Jacobs", "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html"));
            this.screen.execute(new AddBookmarkOperation("home page Bart Jacobs 2.0", "https://people.cs.kuleuven.be/~bart.jacobs/swop/browsrformtest.html"));
            first = false;
        }
    }

    private  boolean first = true; // initialise bookmark bar with 2 homepages from professor

    /**
     * Get the height of the bookmark bar
     * @return The height of the bookmark bar
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * Get The table of all bookmarks
     * @return  A GUITable with one row where all bookmarks are
     */
    public GUITable getBookmarks() {
        return this.bookmarks;
    }

    /**
     * handles the key-presses while the address bar is in focus
     * @param id        The id of the pressed button
     * @param keyCode   The keycode for the pressed button
     * @param keyChar   The char that was pressed
     * @param modifier  The modifier on the pressed key
     */
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifier) {
        this.bookmarks.handleKeyEvent(id, keyCode, keyChar, modifier);
    }

    /**
     * handle the mouse event on the bookmark bar
     * @param id            The id off the mouse event
     * @param x             The x coordinate of the mouse event
     * @param y             The y coordinate of the mouse event
     * @param clickCount    The click count of the mouse event
     */
    public void handleMouseEvent(int id, int x, int y, int clickCount){
        this.bookmarks.handleMouseEvent(x, y, id, clickCount);
    }

}

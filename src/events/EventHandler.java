package events;

import java.awt.*;

/**
 * An interface for GUIObjects to talk to the main window area off the application
 */
public interface EventHandler {
    /**
     * Get the font metrics off the current text
     * @return the current font metrics
     */
    FontMetrics getFontMetrics();

    /**
     * Load a new webpage via url
     * @param url   A full  url starting with http:// or a href
     */
    void load(String url);

    /**
     * Add a mouse event to the webpage
     * @param listener  The listener that will be called when a mouse event occurs
     */
    void addMouseEventListener(MouseEventListener listener);

    /**
     * Add a key event to the webpage
     * @param listener  The listener that will be called when a key event occurs
     */
    void addKeyEventListener(KeyEventListener listener);

    /**
     * Remove a mouse event from the webpage
     * @param listener  The mouse event that needs to be removed
     */
    void removeMouseEventListener(MouseEventListener listener);

    /**
     * Remove a key event from the webpage
     * @param listener  The key event that needs to be removed
     */
    void removeKeyEventListener(KeyEventListener listener);
}

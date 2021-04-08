package events;

import java.awt.*;

/**
 * An interface for GUIObjects to talk to the main window area off the application
 */
public interface EventHandler {
    /**
     * Get the font metrics of the current text
     * @return the current font metrics
     */
    FontMetrics getFontMetrics();

    /**
     * Load a new webpage via url
     * @param url   A full  url starting with http:// or a href
     */
    void load(String url);
}

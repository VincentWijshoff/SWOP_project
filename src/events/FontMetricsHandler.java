package events;

import java.awt.*;

/**
 * An interface for GUIObjects to talk to the main window area off the application
 */
public interface FontMetricsHandler {
    /**
     * Get the font metrics of the current text
     * @return the current font metrics
     */
    FontMetrics getFontMetrics();
}

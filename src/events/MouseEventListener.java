package events;

/**
 * An interface used to add mouse events
 */
public interface MouseEventListener {
    /**
     * Handle a mouse event
     * @param x             The x coordinate off tha mouse click
     * @param y             The y coordinate off the mouse click
     * @param id            The id off the mouse click
     * @param clickCount    The click count off the mouse click
     */
    void handleMouseEvent(int x, int y, int id, int clickCount);
}

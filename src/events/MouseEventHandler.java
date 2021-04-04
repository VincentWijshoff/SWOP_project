package events;

import java.util.HashMap;
import java.util.Map;

/**
 * Handle the mouse events
 */
public class MouseEventHandler {
    Map<MouseEventListener, int[]> listeners = new HashMap<>();     // map of all mouse events

    /**
     * Add a mouse event with an offset of 0
     * @param listener  The mouse event that needs to be added
     */
    public void addMouseEventListener(MouseEventListener listener) {
        listeners.put(listener, new int[] {0, 0});
    }

    /**
     * Add a mouse event with a specified offset
     * @param listener  The mouse event that needs to be added
     * @param x         The x offset
     * @param y         The y offset
     */
    public void addMouseEventListener(MouseEventListener listener, int x, int y) {
        listeners.put(listener, new int[] {x, y});
    }

    /**
     * Remove a mouse event
     * @param listener  The listener that needs to be removed
     */
    public void removeMouseEventListener(MouseEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Handle a click for all mouse events
     * @param id            The is off the click
     * @param x             The x coordinate off the click
     * @param y             The y coordinate off the click
     * @param clickCount    The click counter off the click
     */
    public void onClick(int id, int x, int y, int clickCount) {
        for (Map.Entry<MouseEventListener, int[]> pair : Map.copyOf(listeners).entrySet()) {
            MouseEventListener listener = pair.getKey();
            int[] offset = pair.getValue();
            // System.out.println("(" + x + ", " + y + ") -> (" + (x + offset[0]) + ", " + (y + offset[1]) + ")"); // compare offset to normal
            listener.handleMouseEvent(x + offset[0], y + offset[1], id, clickCount);
        }
    }
}

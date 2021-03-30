package events;

import java.util.HashMap;
import java.util.Map;

public class MouseEventHandler {
    Map<MouseEventListener, int[]> listeners = new HashMap<>();

    public void addMouseEventListener(MouseEventListener listener) {
        listeners.put(listener, new int[] {0, 0});
    }
    public void addMouseEventListener(MouseEventListener listener, int x, int y) {
        listeners.put(listener, new int[] {x, y});
    }

    public void removeMouseEventListener(MouseEventListener listener) {
        listeners.remove(listener);
    }

    public void onClick(int id, int x, int y, int clickCount) {
        for (Map.Entry<MouseEventListener, int[]> pair : Map.copyOf(listeners).entrySet()) {
            MouseEventListener listener = pair.getKey();
            int[] offset = pair.getValue();
            // System.out.println("(" + x + ", " + y + ") -> (" + (x + offset[0]) + ", " + (y + offset[1]) + ")"); // compare offset to normal
            listener.handleMouseEvent(x + offset[0], y + offset[1], id, clickCount);
        }
    }
}

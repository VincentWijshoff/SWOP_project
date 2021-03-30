package events;

import java.util.ArrayList;
import java.util.List;

public class MouseEventHandler {
    ArrayList<MouseEventListener> listeners = new ArrayList<>();

    public void addMouseEventListener(MouseEventListener listener) {
        listeners.add(listener);
    }

    public void removeMouseEventListener(MouseEventListener listener) {
        listeners.add(listener);
    }

    public void onClick(int id, int x, int y, int clickCount) {
        for (var listener : List.copyOf(listeners))
            listener.handleMouseEvent(x, y, id, clickCount);
    }
}

package events;

import java.util.ArrayList;
import java.util.List;

public class KeyEventHandler {
    ArrayList<KeyEventListener> listeners = new ArrayList<>();

    public void addKeyEventListener(KeyEventListener listener) {
        listeners.add(listener);
    }

    public void removeKeyEventListener(KeyEventListener listener) {
        listeners.add(listener);
    }

    public void onKeyPress(int id, int keyCode, char keyChar, int modifier) {
        for (var listener : List.copyOf(listeners))
            listener.handleKeyEvent(id, keyCode, keyChar, modifier);
    }

}

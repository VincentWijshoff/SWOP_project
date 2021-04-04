package events;

import java.util.ArrayList;
import java.util.List;

/**
 * Will handle all added key events
 */
public class KeyEventHandler {

    ArrayList<KeyEventListener> listeners = new ArrayList<>();  // a list of all key events

    /**
     * Add a key event listener to the webpage
     * @param listener  The listener that needs to be added
     */
    public void addKeyEventListener(KeyEventListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove a key event listener from the webpage
     * @param listener  The key event that needs to be removed
     */
    public void removeKeyEventListener(KeyEventListener listener) {
        listeners.add(listener);
    }

    /**
     * Will handle the key press event on all added key event events
     * @param id        The id off the key pressed
     * @param keyCode   The code off the key pressed
     * @param keyChar   The char off the key pressed
     * @param modifier  The modifier on the pressed key
     */
    public void onKeyPress(int id, int keyCode, char keyChar, int modifier) {
        for (var listener : List.copyOf(listeners))
            listener.handleKeyEvent(id, keyCode, keyChar, modifier);
    }

}

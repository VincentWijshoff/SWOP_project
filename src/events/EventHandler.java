package events;

import java.awt.*;

public interface EventHandler {
    FontMetrics getFontMetrics();

    void load(String url);

    void addMouseEventListener(MouseEventListener listener);
    void addKeyEventListener(KeyEventListener listener);

    void removeMouseEventListener(MouseEventListener listener);
    void removeKeyEventListener(KeyEventListener listener);
}

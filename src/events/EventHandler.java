package events;

import java.awt.*;
import java.net.URL;

public interface EventHandler {
    FontMetrics getFontMetrics();

    void load(String url);

    void addMouseEventListener(MouseEventListener listener);
    void addKeyEventListener(KeyEventListener listener);
}

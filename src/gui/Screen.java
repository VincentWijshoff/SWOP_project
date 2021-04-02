package gui;

import java.awt.*;

public interface Screen {

    void draw(Graphics g);

    void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx);

    void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx);

    void handleShown();

    void addBookmark(String name, String url);
}

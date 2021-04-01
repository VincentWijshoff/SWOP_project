package gui;

import java.awt.*;

public interface Screen {

    void draw(Graphics g);

    void handleMouseEvent(int a, int b, int c, int d, int e, int f);

    void handleKeyEvent(int a, int b, char c, int d);
}

package gui;

import java.awt.*;

/**
 * An interface so the window can talk to the different screens
 */
public interface Screen {

    /**
     * Draw the current screen
     * @param g The graphics needed to draw the screen
     */
    void draw(Graphics g);

    /**
     * Handle a mouse event
     * @param id            The id off the mouse event
     * @param x             The x coordinate off the mouse event
     * @param y             The y coordinate off the mouse event
     * @param clickCount    The click count on the mouse event
     * @param button        The button pressed on the mouse
     * @param modifiersEx   The modifier active on the mouse event
     */
    void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx);

    /**
     * Handle a key event
     * @param id            The id off the key event
     * @param keyCode       The code off the key pressed
     * @param keyChar       The char of the key pressed
     * @param modifiersEx   The modifiers active on the key pressed
     */
    void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx);

    /**
     * called on startup and needs to show the welcome document
     */
    void handleShown();
}

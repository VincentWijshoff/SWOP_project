package gui;

import canvaswindow.CanvasWindow;
import events.KeyEventHandler;
import events.MouseEventHandler;

import java.awt.*;

/**
 * Class that manages the window for our browsr
 */
public class Window extends CanvasWindow{

    Screen currentScreen;

    Font font = new Font(Font.DIALOG, Font.PLAIN, 12);
    FontMetrics fontMetrics;


    /**
     * Create a new window
     * @param title The title off the window
     */
    public Window(String title) {
        super(title);

        this.currentScreen = new DefaultScreen(this);

    }

    /**
     * Runs when the canvas is shown
     */
    @Override
    protected void handleShown() {
        this.fontMetrics = getFontMetrics(font);

        this.currentScreen.handleShown();

        repaint();
    }

    /**
     * paint the objects in this window
     * @param g This object offers the methods that allow you to paint on the canvas.
     */
    @Override
    protected void paint(Graphics g) {
        g.setFont(font);

        this.currentScreen.draw(g);
    }

    /**
     * Repaint the canvas
     */
    @Override
    protected void handleResize() {
        repaint();
    }

    /**
     * Handle a mouse event
     * @param id            The id off the mouse event
     * @param x             The x coordinate off the mouse event
     * @param y             The y coordinate off the mouse event
     * @param clickCount    The clickCount off the mouse event
     * @param button        The button pressed od the mouse
     * @param modifiersEx   The modifiers active on the mouse
     */
    @Override
    public void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        this.currentScreen.handleMouseEvent(id, x, y, clickCount, button, modifiersEx);
        this.repaint();
    }

    /**
     * Handle a key event
     * @param id            The id off the key
     * @param keyCode       The code off the key
     * @param keyChar       The char off the key
     * @param modifiersEx   The active modifiers on the key
     */
    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx) {
        this.currentScreen.handleKeyEvent(id, keyCode, keyChar, modifiersEx);
        this.repaint();
    }

    public FontMetrics getFontMetrics() {
        return this.fontMetrics;
    }

    public Screen getCurrentScreen() {
        return this.currentScreen;
    }
}
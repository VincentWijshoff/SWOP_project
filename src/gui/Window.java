package gui;

import canvaswindow.CanvasWindow;
import gui.DefaultScreen.DefaultScreen;

import java.awt.*;

/**
 * Class that manages the window for our Browsr
 * This class contains 2 large parts, the AddressBar and the DocumentArea
 */
public class Window extends CanvasWindow{

    private Screen currentScreen; //the current screen to show

    // needed parameters
    private final Font font = new Font(Font.DIALOG, Font.PLAIN, 12);
    private FontMetrics fontMetrics;


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
     * @param id            The id of the key
     * @param keyCode       The code of the key
     * @param keyChar       The char of the key
     * @param modifiersEx   The active modifiers on the key
     */
    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx) {
        this.currentScreen.handleKeyEvent(id, keyCode, keyChar, modifiersEx);
        this.repaint();
    }

    /**
     * Get the font metrics of the window
     * @return  The font metrics
     */
    public FontMetrics getFontMetrics() {
        return this.fontMetrics;
    }

    /**
     * Set a new screen to show
     * @param screen    The new screen to show
     */
    public void setScreen(Screen screen){
        this.currentScreen = screen;
    }

    /**
     * Get the current screen that is shown
     * @return  The current screen
     */
    public Screen getCurrentScreen() {
        return this.currentScreen;
    }
}
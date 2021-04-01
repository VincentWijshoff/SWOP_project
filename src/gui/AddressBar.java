package gui;

import events.KeyEventListener;
import events.MouseEventListener;
import gui.Objects.GUIInput;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * The class for the address bar, this contains all necessary code for a functioning address bar
 */
public class AddressBar {

    //graphic element
    final int yLimit = 50;

    private final GUIInput inputField;
    private final Window window;

    //GUI elements
    private final int abX = 5;
    private final int abY = this.yLimit / 6;
    private final int h = this.yLimit * 2 / 3;
    private int w = 0;

    //in focus element
    private boolean inFocus = false;

    /**
     * constructor for the address bar
     * @param startAddress  The address that should be shown on startup off the address bar
     */
    public AddressBar(String startAddress, Window w) {
        //this.w = width;
        this.inputField = new GUIInput(startAddress, this.abX, this.abY, 0, this.h);
        this.window = w;
        addListeners();
    }

    /**
     * constructor for the address bar
     */
    public AddressBar(Window w) {
        //this.w = width;
        this.inputField = new GUIInput(this.abX, this.abY, 0, this.h);
        this.window = w;
        addListeners();
    }

    private void addListeners(){
        window.keyEventHandler.addKeyEventListener(new KeyEventListener() {
            @Override
            public boolean handleKeyEvent(int id, int keyCode, char keyChar, int modifier) {

                if (isInFocus()) {
                    // handle the key event in the address bar area
                    if (handleKeyEventA(id, keyCode, keyChar, modifier)) {
                        window.load(getAddress());
                    }
                }
                return false;
            }
        });

        window.mouseEventHandler.addMouseEventListener(new MouseEventListener() {
            @Override
            public void handleMouseEvent(int x, int y, int id, int clickCount) {
                if (isOnAddressBar(x, y)) {
                    setInFocus();
                    System.out.println("Clicked on Address Bar!");
                } else if (isInFocus()){
                    if(setOutFocus()){
                        window.load(getAddress());
                    }
                    System.out.println("Clicked off Address Bar!");
                }
                // handle the click event accordingly
                if (isInFocus()) {
                    handleMouseEventA(x, y, id, clickCount);
                }
            }
        });
    }

    /**
     * an updater to redraw the address bar in its current state, this includes text-cursor and selected text
     * @param g the java drawing help
     */
    public void draw(Graphics g, int wdth) {
        int gwidth = wdth;
        this.inputField.width = this.w-(3*this.abX);
        this.w = gwidth;
        Color oldColor = g.getColor();

        // first draw the grey enclosing area
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, gwidth, this.yLimit);
        g.setColor(Color.BLACK);
        g.drawLine(0, this.yLimit, gwidth, this.yLimit);

        this.inputField.setFocus(this.inFocus);
        this.inputField.draw(g);

        g.setColor(oldColor);
    }

    /**
     * sets the address in the address bar
     * @param aBarText  the address that will be set
     */
    public void setAddress(String aBarText) {
        this.inputField.setText(aBarText);
    }

    /**
     * gets the current address from the address bar
     * @return the address currently in the address bar
     */
    public String getAddress() {
        return this.inputField.getText();
    }

    /**
     * checks if the given position is on the address bar
     * @param X the given x coordinate
     * @param Y the given y coordinate
     * @return  a boolean representing is the position is on this address bar
     */
    public boolean isOnAddressBar(int X, int Y){
        return (X >= this.abX &&
                X <= this.abX + this.w &&
                Y >= this.abY &&
                Y <= this.abY + this.h);
    }

    /**
     * When this is called, a mouse event has happened on the address bar when the address bar is in focus
     * @param id            The id off the mouse event
     * @param clickCount    The click count off the user
     *  post    When initially clicking the address bar, an insertion point will be shown ("text cursor")
     *          When initially clicking the address bar, all text is selected ( Blue background )
     */
    public void handleMouseEventA(int x, int y, int id, int clickCount){
        this.inputField.handleMouseEvent(x, y, id, clickCount);
    }

    /**
     * handles the key-presses while the address bar is in focus
     * @param id        The id off the pressed button
     * @param keyCode   The keycode for the pressed button
     * @param keyChar   The char that was pressed
     * @param modifier  The modifier on the pressed key
     * @return          true if the gui should load the webpage
     */
    public boolean handleKeyEventA(int id, int keyCode, char keyChar, int modifier) {
        if(!this.isInFocus()){
            return false;
        }
        if(this.inputField.handleKeyEvent(id, keyCode, keyChar, modifier)){
            return this.setOutFocus();
        }
        if (id == KeyEvent.KEY_PRESSED && keyCode == KeyEvent.VK_ESCAPE) {
            this.inFocus = false;
            this.inputField.setInitialClick(true);
        }
        return false;
    }

    /**
     * checks if the address bar is in focus
     * @return  true if the address bar is in focus, else false
     */
    public boolean isInFocus(){
        return this.inFocus;
    }

    /**
     * sets the address bar in focus
     */
    public void setInFocus(){
        this.inputField.start();
        this.inFocus = true;
    }

    /**
     * sets the address bar out of focus
     */
    public boolean setOutFocus(){
        this.inputField.setInitialClick(true);
        //this.inputField.selectNone();
        this.inFocus = false;
        this.inputField.start();
        return true;
    }

}
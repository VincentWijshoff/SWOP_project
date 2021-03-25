package gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * The class for the address bar, this contains all necessary code for a functioning address bar
 */
public class AddressBar {

    //graphic element
    final int yLimit = 50;

    private final InputField inputField;

    //GUI elements
    private final int abX = 5;
    private final int abY = this.yLimit / 6;
    private final int h = this.yLimit * 2 / 3;
    private int w = 0;

    //in focus element
    private boolean inFocus = false;
    private boolean initialClick = true;

    /**
     * constructor for the address bar
     * @param startAddress  The address that should be shown on startup off the address bar
     */
    public AddressBar(String startAddress) {
        this.inputField = new InputField(startAddress);
    }

    /**
     * constructor for the address bar
     */
    public AddressBar() {
        this.inputField = new InputField();
    }

    /**
     * an updater to redraw the address bar in its current state, this includes text-cursor and selected text
     * @param g the java drawing help
     */
    public void draw(Graphics g, Window gui) {
        int gwidth = gui.getWidth();
        this.w = gwidth;
        Color oldColor = g.getColor();

        // first draw the grey enclosing area
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, gwidth, this.yLimit);
        g.setColor(Color.BLACK);
        g.drawLine(0, this.yLimit, gwidth, this.yLimit);

        this.inputField.draw(g, this.abX, this.abY, gwidth-(3*this.abX), this.h, this.isInFocus());

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
    public void handleMouseEvent(int id, int clickCount){
        //the first click on the address bar
        if (id == MouseEvent.MOUSE_PRESSED && this.initialClick){

            //the current url is selected (blue background)
            this.inputField.selectAll();

            // keyboard focus (with text cursor) is done with the inFocus variable and the "|" is added
            // in the painting area

            //reset the initial click variable
            this.initialClick = false;

        } else if (id == MouseEvent.MOUSE_PRESSED && clickCount % 2 == 0){

            //a double click results in highlighted text as wel
            this.inputField.selectAll();

        } else if (id == MouseEvent.MOUSE_PRESSED){

            //now the text need no longer be selected
            this.inputField.selectNone();
        }
    }

    /**
     * handles the key-presses while the address bar is in focus
     * @param id        The id off the pressed button
     * @param keyCode   The keycode for the pressed button
     * @param keyChar   The char that was pressed
     * @param modifier  The modifier on the pressed key
     * @return          true if the gui should load the webpage
     */
    public boolean handleKeyboardEvent(int id, int keyCode, char keyChar, int modifier) {
        if(this.inputField.handleKeyEvent(id, keyCode, keyChar, modifier)){
            return this.setOutFocus();
        }
        if (id == KeyEvent.KEY_PRESSED && keyCode == KeyEvent.VK_ESCAPE) {
            this.inFocus = false;
            this.initialClick = true;
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
        this.initialClick = true;
        this.inputField.selectNone();
        this.inFocus = false;
        this.inputField.start();
        return true;
    }

}
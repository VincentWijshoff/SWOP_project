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

    private int startSelected = 0;
    private int endSelected = 0;
    private boolean initialClick = true;
    private String address = "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html";
    private String prevAddress = "";
    private int cursorPosition = address.length();

    //GUI elements
    private final int abX = 5;
    private final int abY = this.yLimit / 6;
    private final int h = this.yLimit * 2 / 3;
    private int w = 0;

    //in focus element
    private boolean inFocus = false;
    private boolean shifting = false;

    /**
     * constructor for the address bar
     * @param startAddress  The address that should be shown on startup off the address bar
     */
    public AddressBar(String startAddress) {
        this.address = startAddress;
    }

    /**
     * constructor for the address bar
     */
    public AddressBar() {

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

        g.setColor(Color.BLACK);
        g.drawRect(this.abX, this.abY, gwidth-(3*this.abX), this.h); // border
        g.clearRect(this.abX+1, this.abY+1, gwidth-(3*this.abX)-1, this.h-1); // actual address bar (white part)

        String viewedAddress = this.address;
        if(this.isInFocus() && this.startSelected == this.endSelected){
            // when the address bar is in focus, a text cursor needs to be shown at the correct position off the current string
            viewedAddress = this.addChar(viewedAddress, '|', this.cursorPosition);
        }

        if(this.startSelected != this.endSelected){
            //the text is selected so a blue background needs to be drawn
            g.setColor(Color.CYAN);
            int tmp = (int) g.getFontMetrics().getStringBounds(this.address, g).getHeight();
            int[] xCords = getSelectedPositions(this.startSelected, this.endSelected, this.address, g);
            g.fillRect(abX+5 + xCords[0],
                    abY + 3 + ((int) (this.h/1.5)) - tmp,
                    xCords[1] - xCords[0],
                    tmp); // text background
            g.setColor(Color.BLACK);
        }

        g.drawString(viewedAddress, abX+5, abY+((int) (this.h/1.5)));

        g.setColor(oldColor);
    }

    /**
     * sets the address in the address bar
     * @param aBarText  the address that will be set
     */
    public void setAddress(String aBarText) {
        this.address = aBarText;
    }

    /**
     * gets the current address from the address bar
     * @return the address currently in the address bar
     */
    public String getAddress() {
        return address;
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
     * gets the selected position from a word
     * @param start the start off the selection
     * @param fin   the end off the selection
     * @param word  the word that is selected on
     * @param g     the graphics to get the size off the words
     * @return      the position off the selected part
     */
    private int[] getSelectedPositions(int start, int fin, String word, Graphics g){
        if ( fin < start){
            return this.getSelectedPositions(fin, start, word, g);
        }
        String sub1 = word.substring(0, start);
        int x0 = (int) g.getFontMetrics().getStringBounds(sub1, g).getWidth();
        String sub2 = word.substring(0, fin);
        int x1 = (int) g.getFontMetrics().getStringBounds(sub2, g).getWidth();
        return new int[] {x0, x1};
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
            this.startSelected = 0;
            this.endSelected = this.address.length();

            // keyboard focus (with text cursor) is done with the inFocus variable and the "|" is added
            // in the painting area

            //reset the initial click variable
            this.initialClick = false;

        } else if (id == MouseEvent.MOUSE_PRESSED && clickCount % 2 == 0){

            //a double click results in highlighted text as wel
            this.startSelected = 0;
            this.endSelected = this.address.length();

        } else if (id == MouseEvent.MOUSE_PRESSED){

            //now the text need no longer be selected
            this.startSelected = 0;
            this.endSelected = 0;
        }
    }

    /**
     * handles the key-presses while the address bar is in focus
     * @param id        The id off the pressed button
     * @param keyCode   The keycode for the pressed button
     * @param keyChar   The char that was pressed
     * @return          true if the gui should load the webpage
     */
    public boolean handleKeyboardEvent(int id, int keyCode, char keyChar, int modifier) {
        if(modifier == 64){
            this.shifting = true;
        }else if(modifier == 0){
            this.shifting = false;
        }
        if (id == 401) {
            // now every key event will only happen once
            if (isChar(keyCode)) {
                // this will only happen if the pressed button is an actual char
                if (this.startSelected != this.endSelected) {
                    // now every bit off the current text must be replaced with the newly pressed character
                    this.address = replaceSelected(this.startSelected, this.endSelected, this.address, "" + keyChar);
                    this.cursorPosition = Math.min(this.startSelected, this.endSelected) + 1;
                } else {
                    // now only input new chars on the position off the text cursor
                    this.address = addChar(this.address, keyChar, this.cursorPosition);
                    this.cursorPosition += 1;
                }
                this.startSelected = this.cursorPosition;
                this.endSelected = this.cursorPosition;
            } else {
                // here the pressed button was not a char so the special button must be handled
                if (keyCode == 32) {
                    // space bar
                    if (this.startSelected != this.endSelected) {
                        // now every bit off the current selected text must be replaced with the newly pressed character
                        this.address = this.replaceSelected(this.startSelected, this.endSelected, this.address, " ");
                        // this.address = " ";
                        this.cursorPosition = Math.min(this.startSelected, this.endSelected) + 1;
                    } else {
                        // now only input new chars on the position off the text cursor
                        this.address = addChar(this.address, ' ', this.cursorPosition);
                        this.cursorPosition += 1;
                    }
                    this.startSelected = this.cursorPosition;
                    this.endSelected = this.cursorPosition;
                } else if (keyCode == 37) {
                    //left arrow
                    if(!this.shifting) {
                        if (this.startSelected != this.endSelected) {
                            this.cursorPosition = Math.min(this.startSelected, this.endSelected);
                            this.startSelected = this.cursorPosition;
                            this.endSelected = this.cursorPosition;
                        } else {
                            if (this.cursorPosition > 0) {
                                this.cursorPosition--;
                                this.startSelected = this.cursorPosition;
                                this.endSelected = this.cursorPosition;
                            }
                        }
                    } else{
                        //left arrow while pressing shift
                        if(this.endSelected > 0){
                            this.endSelected--;
                        }
                    }
                } else if (keyCode == 39) {
                    //right arrow
                    if(!this.shifting) {
                        if (this.startSelected != this.endSelected) {
                            this.cursorPosition = Math.max(this.startSelected, this.endSelected);
                            this.startSelected = this.cursorPosition;
                            this.endSelected = this.cursorPosition;
                        } else {
                            if (this.cursorPosition < this.address.length()) {
                                this.cursorPosition++;
                                this.startSelected = this.cursorPosition;
                                this.endSelected = this.cursorPosition;
                            }
                        }
                    } else{
                        if(this.endSelected < this.address.length()){
                            this.endSelected++;
                        }
                    }
                } else if (keyCode == 8) {
                    //backspace
                    if (this.startSelected != this.endSelected) {
                        this.address = replaceSelected(this.startSelected, this.endSelected, this.address, "");
                        this.cursorPosition = Math.min(this.startSelected, this.endSelected);
                        this.startSelected = this.cursorPosition;
                        this.endSelected = this.cursorPosition;
                    } else {
                        if (this.cursorPosition > 0) {
                            this.address = this.removeAt(this.address, --this.cursorPosition);
                            this.startSelected = this.cursorPosition;
                            this.endSelected = this.cursorPosition;
                        }
                    }
                } else if (keyCode == 127) {
                    //delete
                    if (this.startSelected != this.endSelected) {
                        this.address = replaceSelected(this.startSelected, this.endSelected, this.address, "");
                        this.cursorPosition = Math.min(this.startSelected, this.endSelected);
                        this.startSelected = this.cursorPosition;
                        this.endSelected = this.cursorPosition;
                    } else {
                        if (this.cursorPosition < this.address.length()) {
                            this.address = this.removeAt(this.address, this.cursorPosition);
                            this.startSelected = this.cursorPosition;
                            this.endSelected = this.cursorPosition;
                        }
                    }
                } else if (keyCode == 36) {
                    //home
                    this.cursorPosition = 0;
                    if(!this.shifting) {
                        this.startSelected = this.cursorPosition;
                        this.endSelected = this.cursorPosition;
                    } else{
                        this.endSelected = 0;
                    }
                } else if (keyCode == 35) {
                    //end
                    this.cursorPosition = this.address.length();
                    if(!this.shifting) {
                        this.startSelected = this.cursorPosition;
                    }
                    this.endSelected = this.cursorPosition;
                } else if (keyCode == 27) {
                    //escape
                    this.address = prevAddress;
                    this.cursorPosition = this.address.length();
                    this.inFocus = false;
                    this.initialClick = true;
                    this.startSelected = this.cursorPosition;
                    this.endSelected = this.cursorPosition;
                } else if (keyCode == 10) {
                    //enter
                    return this.setOutFocus();
                }
            }
        }
        return false;
    }

    /**
     * replaces selected text with other text
     * @param start         the start off the selected text
     * @param fin           the end off the selected text
     * @param word          the word that is selected on
     * @param replacement   the replacement for the selected part
     * @return              the new word with the replacement in
     */
    private String replaceSelected(int start, int fin, String word, String replacement){
        if(start > fin){
            return this.replaceSelected(fin, start, word, replacement);
        }
        String beginWord = word.substring(0, start);
        String endWord = word.substring(fin);
        return beginWord + replacement + endWord;
    }

    /**
     * remove char at a position in a string
     * @param str       the string to check
     * @param position  the position to remove
     * @return          the string without the char at the position
     */
    private String removeAt(String str, int position){
        StringBuilder sb = new StringBuilder(str);
        sb.deleteCharAt(position);
        return sb.toString();
    }

    /**
     * adds a char at a position in a string
     * @param str       the string that will be inserted in to
     * @param ch        the char that will be inserted
     * @param position  the position the char will be inserted
     * @return          the string with the inserted char at the position
     */
    private String addChar(String str, char ch, int position) {
        int len = str.length();
        char[] updatedArr = new char[len + 1];
        str.getChars(0, position, updatedArr, 0);
        updatedArr[position] = ch;
        str.getChars(position, len, updatedArr, position + 1);
        return new String(updatedArr);
    }

    /**
     * checks if the given code is a normal char from the keyboard
     * @param code  the char code that will be checked
     * @return  true if the code is a normal char code, else false
     */
    private boolean isChar(int code){
        //is a char when normal keyboard input, slashes, points or Commas...
        return KeyEvent.getKeyText(code).length() == 1 ||
                code == 47 || // the forward slash
                code == 46 || // the point
                code == 44 || // the comma
                code == 59 || // the double point
                code == 45 || // the minus
                code == 61 || // the equal sign
                code == 91 || // open bracket
                code == 93 || // close bracket
                code == 92 || // the backslash
                code == 222 || // the flying comma
                code == 110 || // all num lock special items
                code == 107 ||
                code == 109 ||
                code == 106 ||
                code == 111 ||
                code == 192 ||
                code == 513; //slash and double point
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
        this.prevAddress = address;
        this.cursorPosition = this.address.length();
        this.inFocus = true;
    }

    /**
     * sets the address bar out of focus
     */
    public boolean setOutFocus(){
        this.initialClick = true;
        this.startSelected = 0;
        this.endSelected = 0;
        this.inFocus = false;
        return this.search();
    }

    /**
     * when escape is pressed, the precious address should reappear and be reloaded
     */
    private boolean search(){
        this.prevAddress = address;
        return true;
    }
}
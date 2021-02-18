package gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.nio.file.FileAlreadyExistsException;

public class AddressBar implements GUIObject{

    final int yLimit = 50;

    private boolean selectedText = false;
    private boolean initialClick = true;
    private String address = "www.helemaalmooiV2.nl/smexie";
    private String prevAddress = "";
    private int cursorPosition = address.length();

    //GUI elements
    GUI gui;
    private int abX = 5;
    private int abY = this.yLimit / 6;
    private int height = this.yLimit * 2 / 3;
    private int width = 0;

    //in focus element
    private boolean inFocus = false;

    /*
    * Class used to describe the entire Address Bar section of our GUI.
     */
    public AddressBar(GUI gui, String startAddress) {
        this.gui = gui;
        this.address = startAddress;
    }

    public AddressBar(GUI gui) {
        this.gui = gui;
    }

    public void draw(Graphics g) {
        int gwidth = gui.getWidth();

        this.width = gwidth;

        Color oldColor = g.getColor();
        // first draw the grey enclosing area
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, gwidth, this.yLimit);
        g.setColor(Color.BLACK);
        g.drawLine(0, this.yLimit, gwidth, this.yLimit);

        g.setColor(Color.BLACK);
        g.drawRect(this.abX, this.abY, gwidth-(3*this.abX), this.height); // border
        g.clearRect(this.abX+1, this.abY+1, gwidth-(3*this.abX)-1, this.height-1); // actual address bar (white part)

        String viewedAddress = this.address;
        if(this.isInFocus()){
            // when the address bar is in focus, a text cursor needs to be shown at the correct position off the current string
            viewedAddress = this.addChar(viewedAddress, '|', cursorPosition);
        }

        if(this.selectedText){
            //the text is selected so a blue background needs to be drawn
            g.setColor(Color.CYAN);
            int tmp = (int) g.getFontMetrics().getStringBounds(this.address, g).getHeight();
            g.fillRect(abX+5,
                    abY + 3 + ((int) (height/1.5)) - tmp,
                    (int) g.getFontMetrics().getStringBounds(this.address, g).getWidth(),
                    tmp); // text background
            g.setColor(Color.BLACK);
        }

        g.drawString(viewedAddress, abX+5, abY+((int) (height/1.5)));

        g.setColor(oldColor);
    }

    private void repaint(){
        this.gui.handleShown();
    }

    public void setAddress(String aBarText) {
        this.address = aBarText;
        this.repaint();
    }

    public String getAddress() {
        return address;
    }

    public boolean isOnAddressBar(int coordX, int coordY){
        return (coordX >= this.abX &&
                coordX <= this.abX + this.width &&
                coordY >= this.abY &&
                coordY <= this.abY + this.height);
    }

    /**
     * When this is called, a mouse event has happened on the address bar when the address bar is in focus
     * @param id            The id off the mouse event
     * @param clickCount    The click count off the user
     * @post    When initially clicking the address bar, an insertion point will be shown ("text cursor")
     *          When initially clicking the address bar, all text is selected ( Blue background )
     */
    public void handleMouseEvent(int id, int clickCount){
        //the first click on the address bar
        if (id == MouseEvent.MOUSE_PRESSED && this.initialClick){

            //the current url is selected (blue background)
            this.selectedText = true;

            // keyboard focus (with text cursor) is done with the inFocus variable and the "|" is added
            // in the painting area

            //reset the initial click variable
            this.initialClick = false;

        } else if (id == MouseEvent.MOUSE_PRESSED && clickCount % 2 == 0){

            //a double click results in highlighted text as wel
            this.selectedText = true;

        } else if (id == MouseEvent.MOUSE_PRESSED){

            //now the text need no longer be selected
            this.selectedText = false;
        }
        // repaint to actually see the changes
        this.repaint();
    }

    /**
     * handles the keypresses while the address bar is in focus
     * @param id        The id off the pressed button
     * @param keyCode   The keycode for the pressed button
     * @param keyChar   The char that was pressed
     */
    public void handleKeyboardEvent(int id, int keyCode, char keyChar){
        if(id == 401){
            // now every key event will only happen once
            if(isChar(keyCode)) {
                // this will only happen if the pressed button is an actual char
                if (this.selectedText) {
                    // now every bit off the current text must be replaced with the newly pressed character
                    this.selectedText = false;
                    this.address = "" + keyChar;
                    this.cursorPosition = this.address.length();
                } else {
                    // now only input new chars on the position off the text cursor
                    this.address = addChar(this.address, keyChar, this.cursorPosition);
                    this.cursorPosition += 1;
                }
            } else {
                // here the pressed button was not a char so the special button must be handled
                debug("not a char");
            }

        }
        this.repaint();
    }

    private String addChar(String str, char ch, int position) {
        int len = str.length();
        char[] updatedArr = new char[len + 1];
        str.getChars(0, position, updatedArr, 0);
        updatedArr[position] = ch;
        str.getChars(position, len, updatedArr, position + 1);
        return new String(updatedArr);
    }

    private boolean isChar(int code){
        return KeyEvent.getKeyText(code).length() == 1;
    }

    private void debug(String a){
        System.out.println("AddressBar: " + a);
    }

    public boolean isInFocus(){
        return this.inFocus;
    }

    public void setInFocus(){
        this.prevAddress = address;
        this.inFocus = true;
    }

    public void setOutFocus(){
        this.search();
        this.initialClick = true;
        this.selectedText = false;
        this.inFocus = false;
        this.repaint();
    }

    public void search(){
        this.prevAddress = address;
        this.gui.load(address);
    }
}


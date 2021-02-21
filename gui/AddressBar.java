package gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

//TODO need to implement the selecting with the moouse?
//TODO shift has no release event, how to know when to stop selecting?

public class AddressBar implements GUIObject{

    final int yLimit = 50;

    // private boolean selectedText = false;
    private int startSelected = 0;
    private int endSelected = 0;
    private boolean initialClick = true;
    private String address = "www.helemaalmooiV2.nl/smexie";
    private String prevAddress = "";
    private int cursorPosition = address.length();

    //GUI elements
    private GUI gui;
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

        if(this.startSelected != this.endSelected){
            //the text is selected so a blue background needs to be drawn
            g.setColor(Color.CYAN);
            int tmp = (int) g.getFontMetrics().getStringBounds(this.address, g).getHeight();
            int xcoords[] = getSelectedPositions(this.startSelected, this.endSelected, this.address, g);
            g.fillRect(abX+5 + xcoords[0],
                    abY + 3 + ((int) (height/1.5)) - tmp,
                    xcoords[1],
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

    private int[] getSelectedPositions(int start, int fin, String word, Graphics g){
        if ( fin < start){
            return this.getSelectedPositions(fin, start, word, g);
        }
        String sub1 = word.substring(0, start);
        int x0 = (int) g.getFontMetrics().getStringBounds(sub1, g).getWidth();
        String sub2 = word.substring(0, fin);
        int x1 = (int) g.getFontMetrics().getStringBounds(sub2, g).getWidth();
        debug(x0 + " " + x1);
        return new int[] {x0, x1};
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
                if (this.startSelected != this.endSelected) {
                    // now every bit off the current text must be replaced with the newly pressed character
                    this.startSelected = 0;
                    this.endSelected = 0;
                    this.address = "" + keyChar;
                    this.cursorPosition = this.address.length();
                } else {
                    // now only input new chars on the position off the text cursor
                    this.address = addChar(this.address, keyChar, this.cursorPosition);
                    this.cursorPosition += 1;
                }
            } else {
                // here the pressed button was not a char so the special button must be handled
                if(keyCode == 32){
                    // space bar
                    if (this.startSelected != this.endSelected) {
                        // now every bit off the current selected text must be replaced with the newly pressed character
                        // TODO make the replacement only in the selected area
                        this.address = this.replaceSelected(this.startSelected, this.endSelected, this.address, " ");
                        // this.address = " ";
                        this.cursorPosition = Math.min(this.startSelected, this.endSelected);
                        this.startSelected = 0;
                        this.endSelected = 0;
                    } else {
                        // now only input new chars on the position off the text cursor
                        this.address = addChar(this.address, ' ', this.cursorPosition);
                        this.cursorPosition += 1;
                    }
                } else if (keyCode == 37){
                    //left arrow
                    // TODO what has to happen here?
                    if (this.startSelected != this.endSelected) {
                        this.startSelected = 0;
                        this.endSelected = 0;
                        this.cursorPosition = 0;
                    } else {
                        if (this.cursorPosition > 0) {
                            this.cursorPosition--;
                        }
                    }
                } else if (keyCode == 39){
                    //right arrow
                    // TODO what has to happen here?
                    if(this.startSelected != this.endSelected){
                        this.startSelected = 0;
                        this.endSelected = 0;
                        this.cursorPosition = this.address.length();
                    } else {
                        if (this.cursorPosition < this.address.length()) {
                            this.cursorPosition++;
                        }
                    }
                } else if (keyCode == 8){
                    //backspace
                    if(this.startSelected != this.endSelected){
                        this.address = replaceSelected(this.startSelected, this.endSelected, this.address, "");
                        this.cursorPosition = Math.min(this.startSelected, this.endSelected);
                        this.startSelected = 0;
                        this.endSelected = 0;
                    } else {
                        if (this.cursorPosition > 0) {
                            this.address = this.removeAt(this.address, --this.cursorPosition);
                        }
                    }
                } else if (keyCode == 127){
                    //delete
                    if(this.startSelected != this.endSelected){
                        this.address = replaceSelected(this.startSelected, this.endSelected, this.address, "");
                        this.cursorPosition = Math.min(this.startSelected, this.endSelected);
                        this.startSelected = 0;
                        this.endSelected = 0;
                    } else {
                        if (this.cursorPosition < this.address.length()) {
                            this.address = this.removeAt(this.address, this.cursorPosition);
                        }
                    }
                } else if (keyCode == 36){
                    //home
                    this.cursorPosition = 0;
                    // TODO what has to happen here
                    if (this.startSelected != this.endSelected) {
                        this.startSelected = 0;
                        this.endSelected = 0;
                    }
                } else if (keyCode == 35){
                    //end
                    // TODO what has to happen here?
                    this.cursorPosition = this.address.length();
                    if (this.startSelected != this.endSelected) {
                        this.startSelected = 0;
                        this.endSelected = 0;
                    }
                } else if (keyCode == 27){
                    //escape
                    this.address = prevAddress;
                    this.cursorPosition = this.address.length();
                    this.inFocus = false;
                    this.initialClick = true;
                    this.startSelected = 0;
                    this.endSelected = 0;
                } else if (keyCode == 10){
                    //enter
                    this.setOutFocus();
                }
            }
        }
        this.repaint();
    }

    // TODO make this work
    private String replaceSelected(int start, int fin, String word, String replacement){
        if(start > fin){
            return this.replaceSelected(fin, start, word, replacement);
        }
        String beginword = word.substring(0, start);
        String endword = word.substring(fin, word.length());
        return beginword + replacement + endword;
    }

    private String removeAt(String str, int position){
        StringBuilder sb = new StringBuilder(str);
        sb.deleteCharAt(position);
        return sb.toString();
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
                code == 110 || // all numlock special items
                code == 107 ||
                code == 109 ||
                code == 106 ||
                code == 111 ||
                code == 192;
    }

    private void debug(String a){
        System.out.println("AddressBar: " + a);
    }

    public boolean isInFocus(){
        return this.inFocus;
    }

    public void setInFocus(){
        this.prevAddress = address;
        this.cursorPosition = this.address.length();
        this.inFocus = true;
    }

    public void setOutFocus(){
        this.search();
        this.initialClick = true;
        this.startSelected = 0;
        this.endSelected = 0;
        this.inFocus = false;
        this.repaint();
    }

    public void search(){
        this.prevAddress = address;
        this.gui.load(address);
    }
}


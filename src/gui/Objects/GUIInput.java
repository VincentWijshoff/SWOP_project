package gui.Objects;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * A input bar
 */
public class GUIInput extends GUIObject{

    // needed parameters
    private int startSelected = 0;      //The starting selected position
    private int endSelected = 0;        //The ending selected position
    private String text = "";           //The text in the input field
    private String prevText = "";       //The previous text in the input field
    private boolean shifting = false;   //A parameter to check if the user is pressing shift
    private int cursorPosition;         //The current cursor position of the user
    private boolean inFocus = false;
    private boolean initialClick = true;

    /**
     * Constructor, it will set the current text as the given parameter
     * @param startTxt  The starting text in the input field
     */
    public GUIInput(String startTxt, int x, int y, int width, int height){
        super(x, y, width, height);
        this.text = startTxt;
    }

    /**
     * constructor
     * @param x         THe x coordinate of the input
     * @param y         The y coordinate of the input
     * @param width     The width of the input
     * @param height    The height of the input
     */
    public GUIInput(int x, int y, int width, int height){
        super(x, y, width, height);
        this.text = "";
    }

    /**
     * constructor
     * @param name  Set the default text in the input bar
     */
    public GUIInput(String name) {
        super();
        this.text = name;
        this.width = 70;
        this.height = 15;
    }

    /**
     * constructor
     */
    public GUIInput(){
        super();
        this.width = 70;
        this.height = 15;
    }

    /**
     * Handle the mouse event on this input
     * @param x             The x coordinate of the mouse event
     * @param y             The y coordinate of hte mouse event
     * @param id            The id of the event
     * @param clickCount    The click count of the event
     */
    public void handleMouseEvent(int x, int y, int id, int clickCount){
        if (!this.isInGUIObject(x, y)) {
            this.selectNone();
            this.inFocus = false;
            this.initialClick = true;
            return;
        }
        this.inFocus = true;

        //the first click on the address bar
        if (id == MouseEvent.MOUSE_PRESSED && this.initialClick){

            //the current url is selected (blue background)
            this.selectAll();
            this.prevText = this.text;

            // keyboard focus (with text cursor) is done with the inFocus variable and the "|" is added
            // in the painting area

            //reset the initial click variable
            this.initialClick = false;

        } else if (id == MouseEvent.MOUSE_PRESSED && clickCount % 2 == 0){

            //a double click results in highlighted text as wel
            this.selectAll();

        } else if (id == MouseEvent.MOUSE_PRESSED){

            //now the text need no longer be selected
            this.selectNone();
        }
    }

    /**
     * Sets the initial click to the given boolean
     * @param i The boolean to what the initial click will be set
     */
    public void setInitialClick(boolean i){
        this.initialClick = i;
        if(i){
            this.selectNone();
        }
    }

    /**
     * handles the key-presses while the address bar is in focus
     * @param id        The id of the pressed button
     * @param keyCode   The keycode for the pressed button
     * @param keyChar   The char that was pressed
     * @param modifier  The modifier on the pressed key
     * @return          true if the ENTER key was pressed
     *                  false a any other key press
     */
    @Override
    public boolean handleKeyEvent(int id, int keyCode, char keyChar, int modifier) {
        if(!this.inFocus){
            this.initialClick = true;
            return false;
        }
        if(modifier == KeyEvent.SHIFT_DOWN_MASK){
            this.shifting = true;
        }else if(modifier == 0){
            this.shifting = false;
        }
        if (id == KeyEvent.KEY_PRESSED) {
            // now every key event will only happen once
            if (KeyEvent.getKeyText(keyCode).length() == 1) {
                this.onCharPress(keyChar);
            } else {
                // here the pressed button was not a char so the special button must be handled
                if (keyCode == KeyEvent.VK_SPACE) {
                    this.onSpacePress();
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    this.onLeftArrow();
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    this.onRightArrow();
                } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
                    this.onBackSpace();
                } else if (keyCode == KeyEvent.VK_DELETE) {
                    this.onDelete();
                } else if (keyCode == KeyEvent.VK_HOME) {
                    this.onHome();
                } else if (keyCode == KeyEvent.VK_END) {
                    this.onEnd();
                } else if (keyCode == KeyEvent.VK_ESCAPE) {
                    this.inFocus = false;
                    this.selectNone();
                    this.initialClick = true;
                    this.onEscape();
                } else if (keyCode == KeyEvent.VK_ENTER) {
                    this.inFocus = false;
                    this.initialClick = true;
                    this.selectNone();
                    return true;
                } else if(keyCode != KeyEvent.VK_SHIFT && keyCode != KeyEvent.VK_CONTROL && keyCode != KeyEvent.VK_ALT){
                    // we assume a key was pressed that needs to be shown but is not a normal char
                    this.onCharPress(keyChar);
                }
            }
        }
        return false;
    }

    /**
     * When the user pressed a char on the keyboard
     * @param keyChar the char that was pressed
     */
    private void onCharPress(char keyChar){
        // this will only happen if the pressed button is an actual char
        if (this.startSelected != this.endSelected) {
            // now every bit of the current text must be replaced with the newly pressed character
            this.text = replaceSelected(this.startSelected, this.endSelected, this.text, "" + keyChar);
            this.cursorPosition = Math.min(this.startSelected, this.endSelected) + 1;
        } else {
            // now only input new chars on the position of the text cursor
            this.text = addChar(this.text, keyChar, this.cursorPosition);
            this.cursorPosition += 1;
        }
        this.startSelected = this.cursorPosition;
        this.endSelected = this.cursorPosition;
    }

    /**
     * When the user pressed the space bar
     */
    private void onSpacePress(){
        // space bar
        if (this.startSelected != this.endSelected) {
            // now every bit  the current selected text must be replaced with the newly pressed character
            this.text = this.replaceSelected(this.startSelected, this.endSelected, this.text, " ");
            // this.address = " ";
            this.cursorPosition = Math.min(this.startSelected, this.endSelected) + 1;
        } else {
            // now only input new chars on the position of the text cursor
            this.text = addChar(this.text, ' ', this.cursorPosition);
            this.cursorPosition += 1;
        }
        this.startSelected = this.cursorPosition;
        this.endSelected = this.cursorPosition;
    }

    /**
     * When the user pressed the left arrow button
     */
    private void onLeftArrow(){
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
    }

    /**
     * When the user presses the right arrow button
     */
    private void onRightArrow(){
        //right arrow
        if(!this.shifting) {
            if (this.startSelected != this.endSelected) {
                this.cursorPosition = Math.max(this.startSelected, this.endSelected);
                this.startSelected = this.cursorPosition;
                this.endSelected = this.cursorPosition;
            } else {
                if (this.cursorPosition < this.text.length()) {
                    this.cursorPosition++;
                    this.startSelected = this.cursorPosition;
                    this.endSelected = this.cursorPosition;
                }
            }
        } else{
            if(this.endSelected < this.text.length()){
                this.endSelected++;
            }
        }
    }

    /**
     * When the user pressed the backspace button
     */
    private void onBackSpace(){
        //backspace
        if (this.startSelected != this.endSelected) {
            this.text = replaceSelected(this.startSelected, this.endSelected, this.text, "");
            this.cursorPosition = Math.min(this.startSelected, this.endSelected);
            this.startSelected = this.cursorPosition;
            this.endSelected = this.cursorPosition;
        } else {
            if (this.cursorPosition > 0) {
                this.text = this.removeAt(this.text, --this.cursorPosition);
                this.startSelected = this.cursorPosition;
                this.endSelected = this.cursorPosition;
            }
        }
    }

    /**
     * When the user pressed the delete button
     */
    private void onDelete(){
        //delete
        if (this.startSelected != this.endSelected) {
            this.text = replaceSelected(this.startSelected, this.endSelected, this.text, "");
            this.cursorPosition = Math.min(this.startSelected, this.endSelected);
            this.startSelected = this.cursorPosition;
            this.endSelected = this.cursorPosition;
        } else {
            if (this.cursorPosition < this.text.length()) {
                this.text = this.removeAt(this.text, this.cursorPosition);
                this.startSelected = this.cursorPosition;
                this.endSelected = this.cursorPosition;
            }
        }
    }

    /**
     * When the user pressed the home button
     */
    private void onHome(){
        //home
        this.cursorPosition = 0;
        if(!this.shifting) {
            this.startSelected = this.cursorPosition;
            this.endSelected = this.cursorPosition;
        } else{
            this.endSelected = 0;
        }
    }

    /**
     * When the user pressed the end button
     */
    private void onEnd(){
        //end
        this.cursorPosition = this.text.length();
        if(!this.shifting) {
            this.startSelected = this.cursorPosition;
        }
        this.endSelected = this.cursorPosition;
    }

    /**
     * When the user pressed the escape button
     */
    private void onEscape(){
        //escape
        this.text = prevText;
        this.cursorPosition = this.text.length();
        this.startSelected = this.cursorPosition;
        this.endSelected = this.cursorPosition;
    }

    /**
     * replaces selected text with other text
     * @param start         the start of the selected text
     * @param fin           the end of the selected text
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
     * Get the current text in the input field
     * @return  The current text
     */
    public String getText(){
        return this.text;
    }

    /**
     * Set the text in the input field
     * @param txt   The text that needs to be set
     */
    public void setText(String txt){
        this.text = txt;
    }

    /**
     * Get the current cursor position in the text
     * @return  The current cursor position
     */
    private int getCursorPosition(){
        return this.cursorPosition;
    }

    /**
     * Check if any part of the text in the input field is selected
     * @return  true if any part of the text s selected
     */
    private boolean isSelecting(){
        return this.startSelected != this.endSelected;
    }

    /**
     * Get the coordinates of the currently selected part of the text
     * @param g     The graphics needed to calculate the positions
     * @return      A list of length 2 with the start and ending x coordinates of the selected parts
     */
    private int[] getSelectedPositions(Graphics g){
        return this.getSelectedPositions(this.startSelected, this.endSelected, this.text, g);
    }

    /**
     * Make the input field select all text
     */
    private void selectAll(){
        this.startSelected = 0;
        this.endSelected = this.text.length();
    }

    /**
     * Make the input field not select anything anymore
     */
    private void selectNone(){
        this.startSelected = 0;
        this.endSelected = 0;
    }

    /**
     * Set the start of the input field by setting the previous address to the current address and set the current
     * position of the cursor correctly
     */
    public void start(){
        this.prevText = text;
        this.cursorPosition = this.text.length();
    }

    /**
     * gets the selected position from a word
     * @param start the start of the selection
     * @param fin   the end of the selection
     * @param word  the word that is selected on
     * @param g     the graphics to get the size of the words
     * @return      the position of the selected part
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
     * Draw the input box and only the input box containing the current text
     * @param g         the java drawing graphics
     */
    @Override
    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.drawRect(this.coordX, this.coordY, width, height); // border
        g.clearRect(this.coordX+1, this.coordY+1, width-1, height-1); // actual address bar (white part)

        String viewedAddress = this.getText();
        if(inFocus && !this.isSelecting()){
            // when the address bar is in focus, a text cursor needs to be shown at the correct position of the current string
            viewedAddress = this.addChar(viewedAddress, '|', this.getCursorPosition());
        }

        if(this.isSelecting()){
            //the text is selected so a blue background needs to be drawn
            g.setColor(Color.CYAN);
            int tmp = (int) g.getFontMetrics().getStringBounds(this.getText(), g).getHeight();
            int[] xCords = this.getSelectedPositions(g);
            g.fillRect(this.coordX+5 + xCords[0],
                    this.coordY + 3 + ((int) (height/1.5)) - tmp,
                    xCords[1] - xCords[0],
                    tmp); // text background
            g.setColor(Color.BLACK);
        }

        g.drawString(viewedAddress, this.coordX+5, this.coordY+((int) (height/1.5)));
    }

    public void setFocus(boolean f){
        this.inFocus = f;
    }

    // Form necessary things

    /**
     * Get the inputs related to this input
     * @return  This input
     */
    public ArrayList<GUIInput> getInputs(){
        ArrayList<GUIInput> inp = new ArrayList<>();
        inp.add(this);
        return inp;
    }

    String name;    //the name of this input

    /**
     * set the name of this input
     * @param name The new name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Calculate the output to the form from this input
     * @return  This name + "=" + the utf-8 encoding of the given input
     */
    public String getFormOutput(){
        return this.name + "=" + URLEncoder.encode(this.text, StandardCharsets.UTF_8);
    }
}

package gui.Objects;

import gui.Objects.ScrollBars.HorizontalScrollBar;
import gui.Objects.ScrollBars.ScrollBar;
import gui.Objects.ScrollBars.Scrollable;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * An input bar
 */
public class GUIInput extends GUIObject implements Scrollable {

    //the horizontal scrollbar attached
    private HorizontalScrollBar scrollBar;
    //the offset of the text
    private int offset = 0;

    // needed parameters
    private int startSelected = 0;              // The starting selected position
    private int endSelected = 0;                // The ending selected position
    private String text = "";                   // The text in the input field
    private String prevText = "";               // The previous text in the input field
    private boolean shifting = false;           // A parameter to check if the user is pressing shift
    private int cursorPosition;                 // The current cursor position of the user
    private boolean inFocus = false;            // is the input in focus?
    private boolean initialClick = true;        // is the click an initial click
    private boolean pageLoaderInput = false;    // is the input the address bar input?

    /**
     * Constructor, it will set the current text as the given parameter
     * @param startTxt  The starting text in the input field
     */
    public GUIInput(String startTxt, int x, int y, int width, int height){
        super(x, y, width, height);
        this.text = startTxt;
        this.scrollBar = new HorizontalScrollBar(this);
        this.height += scrollBar.getScrollbarHeight();
    }

    /**
     * Construct a GUIInput
     * @param startTxt      starting text filled in to the input field
     * @param x             x position of this GUIInput
     * @param y             y position of this GUIInput
     * @param width         width of this GUIInput
     * @param height        height of this GUIInput
     * @param pageLoader    boolean signaling if this GUIInput is a pageLoader
     */
    public GUIInput(String startTxt, int x, int y, int width, int height, boolean pageLoader){
        super(x, y, width, height);
        this.text = startTxt;
        this.pageLoaderInput = pageLoader;
        this.scrollBar = new HorizontalScrollBar(this);
        this.height += scrollBar.getScrollbarHeight();
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
        this.scrollBar = new HorizontalScrollBar(this);
        this.height += scrollBar.getScrollbarHeight();
    }

    /**
     * Construct a GUIInput
     */
    public GUIInput(){
        super();
        this.width = 100;
        this.height = 15;
        this.scrollBar = new HorizontalScrollBar(this);
        this.height += scrollBar.getScrollbarHeight();
    }

    /**
     * Handle the updating of the dimensions of this string
     */
    @Override
    public void updateDimensions() {
        // set the height to the height of a string + scrollbar height
        this.height = this.getFontMetrics().getHeight() + scrollBar.getScrollbarHeight();
    }

    /**
     * Handle the mouse event on this input
     * @param x             The x coordinate of the mouse event
     * @param y             The y coordinate of hte mouse event
     * @param id            The id of the event
     * @param clickCount    The click count of the event
     */
    public void handleMouseEvent(int x, int y, int id, int clickCount){
        if (this.scrollBar.isOnScrollBar(x, y)) {
            this.scrollBar.handleMouseEvent(id, x, y, clickCount);
            this.inFocus = true;
            return;
        } else if (!this.isInGUIObject(x, y)) {
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
     * handles the key-presses
     * @param id        The id of the pressed button
     * @param keyCode   The keycode for the pressed button
     * @param keyChar   The char that was pressed
     * @param modifier  The modifier on the pressed key
     */
    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifier) {
        if(!this.inFocus){
            this.initialClick = true;
            return;
        }
        if(modifier == KeyEvent.SHIFT_DOWN_MASK){
            if (!this.isSelecting()) {
                this.startSelected = this.cursorPosition;
                this.endSelected = this.cursorPosition;
            }
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
                    if(this.pageLoaderInput){ // if this is the address bar input, a page should load now
                        this.pageLoader.load(this.getText());
                    }
                } else if(keyCode != KeyEvent.VK_SHIFT && keyCode != KeyEvent.VK_CONTROL && keyCode != KeyEvent.VK_ALT){
                    // we assume a key was pressed that needs to be shown but is not a normal char
                    this.onCharPress(keyChar);
                }
                this.getScrollBar().getSlider().setWidth(this.getScrollBar().calculateSliderWidth());
                this.getScrollBar().getSlider().setCoordX(this.getScrollBar().calculateSliderX());
            }
        }
    }

    /**
     * @return the width of the given text
     */
    public int getStringWidth(String text) {
        return this.getFontMetrics().stringWidth(text);
    }

    /**
     * Check if the given text fits the inputField
     */
    private boolean textFits(String text) {
        return this.getStringWidth(text) <= this.width - 5;
    }

    /**
     * @return the offset
     */
    public int getOffset() { return this.offset; }

    /**
     * @return the x-coordinate of the inputField
     */
    @Override
    public int getX() {
        return coordX;
    }

    /**
     * @return the y-coordinate of the inputField
     */
    @Override
    public int getY() {
        return coordY;
    }

    /**
     * @return the height of the inputField
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * @return the width of the inputField
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * @return the xOffset of the inputField (same as offset)
     */
    @Override
    public int getXOffset() {
        return getOffset();
    }

    /**
     * @param amount the new offset (if allowed)
     */
    @Override
    public void setXOffset(int amount) {
        if (amount > 0) this.offset = 0;
        else this.offset = Math.max(amount, this.scrollBar.calcMaxOffset());

        this.scrollBar.getSlider().setCoordX(this.scrollBar.calculateSliderX());
    }

    /**
     * @return the yOffset of the inputField, always 0 since GUIInput has no vertical aspect
     */
    @Override
    public int getYOffset() {
        return 0;
    }

    @Override
    public void setYOffset(int amount) {
        return;
    }

    /**
     * @return the availableWidth == width
     */
    @Override
    public int getAvailableWidth() {
        return Math.max(width, 0);
    }

    /**
     * @return the availableHeight == height
     */
    @Override
    public int getAvailableHeight() {
        return Math.max(height, 0);
    }

    /**
     * @return the width of the text inside the inputField
     */
    @Override
    public int getContentWidth() {
        return getFontMetrics().stringWidth(getText());
    }

    /**
     * @return the height of the inputField
     */
    @Override
    public int getContentHeight() {
    return Math.max(height, 0);
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
        //check if the string needs to be moved (if it became too big for the inputField)
        if(!this.textFits(text) && this.cursorPosition == text.length()){
            //set the position so the last character is on last position of the inputField
            this.setXOffset(this.scrollBar.calcMaxOffset());
        } else if(textFits(text)){
            //the entire string fits, so position = 0
            this.setXOffset(0);
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
            this.cursorPosition = Math.min(this.startSelected, this.endSelected) + 1;
        } else {
            // now only input new chars on the position of the text cursor
            this.text = addChar(this.text, ' ', this.cursorPosition);
            this.cursorPosition += 1;
        }

        //check if the string needs to be moved (if it became too big for the inputField)
        if(!this.textFits(text) && this.cursorPosition == text.length()){
            //set the position so the last character is on last position of the inputField
            this.setXOffset(this.scrollBar.calcMaxOffset());

        }else if(this.textFits(text)){
            //the entire string fits, so position = 0
            this.setXOffset(0);
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
            }
            else {
                if (this.cursorPosition != 0) this.cursorPosition--; // Move cursor if possible

                // If all the text fits, keep the offset of the text 0.
                if (this.textFits(text)) {
                    this.setXOffset(0);
                    return;
                }
            }
        } else{
            if(this.endSelected > 0){
                this.endSelected--;
            }
        }
        this.updateOffsetOnLeftArrow();
    }

    /**
     * update the offset, if left arrow is pressed
     */
    private void updateOffsetOnLeftArrow() {
        int positionToUse = cursorPosition;
        if (isSelecting()) {
            positionToUse = this.endSelected;
        }
        // If cursor at the end, no offset
        if (positionToUse == 0) {
            this.setXOffset(0);
        }
        else {
            int pos = positionToUse == text.length() ? text.length()-1 : positionToUse;
            int afterCursorTextLen = this.getStringWidth(text.substring(pos, text.length()-1));
            int currentOffset = getOffset();
            int futureOffset = Math.min(0, this.scrollBar.calcMaxOffset() + this.getStringWidth(text.substring(pos, text.length()-1) + "|") - this.scrollBar.getScrollbarWidth() + 10);
            // If cursor at the edge, change offset
            if (afterCursorTextLen >= this.width - 20 && currentOffset < futureOffset) {
                this.setXOffset(futureOffset);
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
            }

            else {
                if (this.cursorPosition != this.text.length()) this.cursorPosition++; // Move cursor if possible

                // If all the text fits, keep the offset of the text 0.
                if (this.textFits(text)) {
                    this.setXOffset(0);
                    return;
                }
            }
        } else{
            if(this.endSelected < this.text.length()){
                this.endSelected++;
            }
        }
        this.updateOffsetOnRightArrow();
    }

    /**
     * update the offset, if right arrow is pressed
     */
    private void updateOffsetOnRightArrow() {
        int positionToUse = cursorPosition;
        if (isSelecting()) {
            positionToUse = this.endSelected;
        }

        // If cursor at the end, max offset
        if (positionToUse == this.text.length()) {
            this.setXOffset(this.scrollBar.calcMaxOffset());
        }
        else {
            int preCursorTextLen = this.getStringWidth(text.substring(0, positionToUse));
            int currentOffset = getOffset();
            int futureOffset = (-1)*(this.getStringWidth(text.substring(0, positionToUse) + "|") - this.scrollBar.getScrollbarWidth() + 10);
            // If cursor at the edge, change offset
            if (preCursorTextLen >= this.width - 20 && futureOffset < currentOffset) {
                this.setXOffset(futureOffset);
            }
        }
    }

    /**
     * When the user pressed the backspace button
     */
    private void onBackSpace(){
        int widthToRemove;
        if (this.isSelecting()) {
            widthToRemove = this.getStringWidth(this.getSelectedText());
            this.text = replaceSelected(this.startSelected, this.endSelected, this.text, "");
            this.cursorPosition = Math.min(this.startSelected, this.endSelected);
        } else {
            if (this.cursorPosition == 0) return;
            widthToRemove = this.getStringWidth(String.valueOf(text.charAt(cursorPosition-1)));
            this.text = this.removeAt(this.text, --this.cursorPosition);
        }
        if (this.textFits(text) || cursorPosition == 0) this.setXOffset(0);
        else if (!this.textFits(text) && this.cursorPosition == text.length()) this.setXOffset(this.scrollBar.calcMaxOffset());
        else this.setXOffset(getOffset() + widthToRemove);

        this.startSelected = this.cursorPosition;
        this.endSelected = this.cursorPosition;
    }

    /**
     * When the user pressed the delete button
     */
    private void onDelete(){
        if (this.isSelecting()) {
            this.text = replaceSelected(this.startSelected, this.endSelected, this.text, "");
            this.cursorPosition = Math.min(this.startSelected, this.endSelected);
        } else {
            if (this.cursorPosition == text.length()) return;
            this.text = this.removeAt(this.text, this.cursorPosition);
        }
        if (this.textFits(text) || cursorPosition == 0) this.setXOffset(0);
        else if (!this.textFits(text) && this.cursorPosition == text.length()) this.setXOffset(this.scrollBar.calcMaxOffset());

        this.startSelected = this.cursorPosition;
        this.endSelected = this.cursorPosition;
    }

    /**
     * When the user pressed the home button
     */
    private void onHome(){
        //home
        this.cursorPosition = 0;
        this.setXOffset(0);
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
        if(!this.textFits(text) && this.cursorPosition == text.length()){
            //set the position so the last character is on last position of the inputField
            this.setXOffset(this.scrollBar.calcMaxOffset());
        } else{
            this.setXOffset(0);
        }
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
     * @return the part of the text that is selected
     */
    private String getSelectedText() {
        int a = this.startSelected;
        int b = this.endSelected;
        if (a < b) return text.substring(a, b);
        else return text.substring(b, a);
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
        try {
            str.getChars(0, position, updatedArr, 0);
        }catch (StringIndexOutOfBoundsException e){
            str.getChars(0, position-1, updatedArr, 0);
        }
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
        this.inFocus = false;
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
        this.inFocus = true;
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
     * @param g the java drawing graphics
     * @param paneOffsets the amount of pixels to add because of pane scrolling
     */
    @Override
    public void draw(Graphics g, int... paneOffsets) {
        int xOffset = paneOffsets.length == 2 ? paneOffsets[0] : 0;
        int yOffset = paneOffsets.length == 2 ? paneOffsets[1] : 0;
        int x = this.coordX + xOffset;
        int y = this.coordY + yOffset;

        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height); // border
        g.clearRect(x+1, y+1, width-1, height-1); // actual address bar (white part)

        Shape oldClip = g.getClip();
        Rectangle2D newClip = new Rectangle(x, y, this.width, this.height);
        Rectangle2D intersection = newClip.createIntersection((Rectangle2D) oldClip);
        g.setClip(intersection);

        String viewedAddress = this.getText();
        if(inFocus && !this.isSelecting()){
            // when the address bar is in focus, a text cursor needs to be shown at the correct position of the current string
            viewedAddress = this.addChar(viewedAddress, '|', this.getCursorPosition());
        }

        setXOffset(getOffset()); // Is needed to check for edge cases

        if(this.isSelecting()){
            //the text is selected so a blue background needs to be drawn
            g.setColor(Color.CYAN);
            int tmp = (int) g.getFontMetrics().getStringBounds(this.getText(), g).getHeight();
            int[] xCords = this.getSelectedPositions(g);
            g.fillRect(x+5 + this.getOffset() + xCords[0],
                    y+3 + ((int) ((height - scrollBar.getScrollbarHeight())/1.5)) - tmp,
                    xCords[1] - xCords[0],
                    tmp); // text background
            g.setColor(Color.BLACK);
        }

        g.drawString(viewedAddress, x+ScrollBar.getBuffer() + getOffset(), y +((int) ((height - scrollBar.getScrollbarHeight())/1.5)));
        g.setClip(oldClip);
        this.scrollBar.draw(g, xOffset, yOffset);
    }

    /**
     * Set the focus on this input
     * @param f     How the focus should be set
     */
    public void setFocus(boolean f){
        this.inFocus = f;
    }

    /**
     * Get the inFocus field off the input
     * @return      The inFocus field
     */
    public boolean getInFocus() {
        return this.inFocus;
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

    @Override
    public HashSet<GUIObject> copy() {
        HashSet<GUIObject> cpy = new HashSet<>();
        GUIInput copy = new GUIInput(this.text, this.coordX, this.coordY, this.width, this.height - scrollBar.getScrollbarHeight());
        copy.name = this.name;
        copy.pageLoaderInput = this.pageLoaderInput;
        copy.prevText = this.prevText;
        cpy.add(copy);
        return cpy;
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
     * Get the name of the input
     * @return      The name of the input
     */
    public String getName(){
        return this.name;
    }

    /**
     * Calculate the output to the form from this input
     * @return  This name + "=" + the utf-8 encoding of the given input
     */
    public String getFormOutput(){
        return this.name + "=" + URLEncoder.encode(this.text, StandardCharsets.UTF_8);
    }

    public HorizontalScrollBar getScrollBar() { return this.scrollBar; }
}

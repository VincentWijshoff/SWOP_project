package gui.Objects;

import events.KeyEventListener;
import events.MouseEventListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * A button object
 */
public class GUIButton extends GUIObject{
    //the text in the button
    String text;
    // the events that will happen when on button
    MouseEventListener mouseListener;
    KeyEventListener keyListener;
    //the current color of the button
    Color buttonColor = Color.lightGray;

    /**
     * Constructor
     * @param startTxt  The text on the button
     * @param x         The x coordinate of the button
     * @param y         The y coordinate of the button
     * @param width     THe width of the button
     * @param height    The height of the button
     */
    public GUIButton(String startTxt, int x, int y, int width, int height){
        super(x, y, width, height);
        this.text = startTxt;
        this.mouseListener = (x1, y1, id, clickCount) -> { };
        this.keyListener = (id, keyCode, keyChar, modifier) -> false;
    }

    /**
     * Constructor
     * @param startTxt  The text on the button
     */
    public GUIButton(String startTxt){
        super();
        this.text = startTxt;
        this.mouseListener = (x1, y1, id, clickCount) -> { };
        this.keyListener = (id, keyCode, keyChar, modifier) -> false;
    }

    /**
     * Handle the mouse event on the button
     * @param x             The x coordinate of the mouse event
     * @param y             The y coordinate of hte mouse event
     * @param id            The id of the event
     * @param clickCount    The click count of the event
     */
    @Override
    public void handleMouseEvent(int x, int y, int id, int clickCount){
        if(!this.isInGUIObject(x, y)){
            this.buttonColor = Color.LIGHT_GRAY;
            return;
        }
        if(id == MouseEvent.MOUSE_RELEASED){
            this.mouseListener.handleMouseEvent(x, y, id, clickCount);
            this.buttonColor = Color.LIGHT_GRAY;
        }else{
            this.buttonColor = Color.DARK_GRAY;
        }
    }

    /**
     * Handle the key event
     * @param id            The id of the event
     * @param keyCode       The keycode of the event
     * @param keyChar       The key char of the event
     * @param modifier      The modifier on the event
     * @return the return of the self set key event
     */
    @Override
    public boolean handleKeyEvent(int id, int keyCode, char keyChar, int modifier) {
        return this.keyListener.handleKeyEvent(id, keyCode, keyChar, modifier);
    }

    /**
     * Set a mouse event that will be called when the button was pressed
     * @param l The mouse event
     */
    public void setMouseEvent(MouseEventListener l){
        this.mouseListener = l;
    }

    /**
     * update the dimension of the button
     */
    @Override
    public void updateDimensions() {
        this.width = 70;
        this.height = 15;
    }

    /**
     * Set the key event to be called on a key event
     * @param l The key event
     */
    public void setKeyEvent(KeyEventListener l){
        this.keyListener = l;
    }

    /**
     * Draw the button
     * @param g the graphics needed to draw each object
     */
    @Override
    public void draw(Graphics g){
        g.setColor(buttonColor);
        g.fillRect(this.coordX, this.coordY, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(this.coordX, this.coordY, width, height); // border
        g.drawString(this.text, this.coordX+5, this.coordY+((int) (height/1.5)));
    }

    /**
     * Get all buttons related to this button
     * @return  This button
     */
    public ArrayList<GUIButton> getButtons(){
        ArrayList<GUIButton> inp = new ArrayList<GUIButton>();
        inp.add(this);
        return inp;
    }

    // is this button a submit button?
    public boolean isSubmit = false;

    /**
     * Set the button as a submit button
     */
    public void setSubmit() {
        this.isSubmit = true;
    }

    // needed for the form application
    String output = "";

    /**
     * Add an input to the button
     * @param formOutput    The string from the input
     */
    public void addInput(String formOutput) {
        output += formOutput + "&";
    }

    /**
     * Get the final output
     * @return  The final output
     */
    public String getOutput(){
        return output;
    }
}

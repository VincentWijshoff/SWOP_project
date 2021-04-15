package gui.Objects;

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
    Runnable mouseListener;
    //the current color of the button
    public Color buttonColor = Color.lightGray;

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
        this.mouseListener = new Runnable() {
            @Override
            public void run() { }
        };
    }

    /**
     * Constructor
     * @param startTxt  The text on the button
     */
    public GUIButton(String startTxt){
        super();
        this.text = startTxt;
        this.mouseListener = new Runnable() {
            @Override
            public void run() { }
        };
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
            this.mouseListener.run();
            this.buttonColor = Color.LIGHT_GRAY;
        }else{
            this.buttonColor = Color.DARK_GRAY;
        }
    }

    /**
     * Set a mouse event that will be called when the button was pressed
     * @param l The mouse event
     */
    public void setMouseEvent(Runnable l){
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
        ArrayList<GUIButton> inp = new ArrayList<>();
        inp.add(this);
        return inp;
    }

    /**
     * Get the text in the button
     * @return  The text in teh button
     */
    public String getText(){
        return this.text;
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

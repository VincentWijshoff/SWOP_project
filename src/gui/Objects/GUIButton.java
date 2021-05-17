package gui.Objects;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;

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
        this.mouseListener = () -> { };
    }

    /**
     * Constructor
     * @param startTxt  The text on the button
     */
    public GUIButton(String startTxt){
        super();
        this.text = startTxt;
        this.mouseListener = () -> { };
    }

    /**
     * Handle the mouse event on the button
     * @param x             The x coordinate of the mouse event
     * @param y             The y coordinate of the mouse event
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
        // want string in the middle and will be drawn 5 pixels from left side
        this.width = this.fontMetricsHandler.getFontMetrics().stringWidth(text) + 10;
        this.height = this.fontMetricsHandler.getFontMetrics().getHeight();
        this.coordY += (int) (height/1.5);
    }

    /**
     * Draw the button
     * @param g the graphics needed to draw each object
     */
    @Override
    public void draw(Graphics g, int... paneOffsets){
        int xOffset = paneOffsets.length == 2 ? paneOffsets[0] : 0;
        int yOffset = paneOffsets.length == 2 ? paneOffsets[1] : 0;
        int x = this.coordX + xOffset;
        int y = this.coordY + yOffset;

        g.setColor(buttonColor);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height); // border
        int txtPos = (this.height / 2) + y + (this.fontMetricsHandler.getFontMetrics().getHeight() / 2) - 5;
        // this.coordY+((int) (height/1.5))
        g.drawString(this.text, x+5, txtPos);
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

    @Override
    public HashSet<GUIObject> copy() {
        HashSet<GUIObject> cpy = new HashSet<>();
        GUIButton copy = new GUIButton(this.text, this.coordX, this.coordY, this.width, this.height);
        copy.mouseListener = this.mouseListener;
        copy.isSubmit = this.isSubmit;
        copy.output = this.output;
        cpy.add(copy);
        return cpy;
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

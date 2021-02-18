package gui;

import java.awt.*;
import java.awt.event.MouseEvent;

public class AddressBar extends GUIObject {

    final int yLimit = 50;

    private String address = "www.helemaalmooiV2.nl/smexie";

    //in focus element
    private boolean inFocus = false;

    /*
    * Class used to describe the entire Address Bar section of our GUI.
     */
    public AddressBar(String startAddress) {
        super();

        this.address = startAddress;
        updateGUIPosAndDim(5, ((int) (this.yLimit / 6)), 0, ((int) this.yLimit * 2 / 3));
    }

    public AddressBar() {
        super();

        updateGUIPosAndDim(5, ((int) (this.yLimit / 6)), 0, ((int) this.yLimit * 2 / 3));
    }



    public void draw(Graphics g) {
        int gwidth = gui.getWidth();
        updateGUIDimensions(gwidth, height);
        Color oldColor = g.getColor();

        // first draw the grey enclosing area
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, gwidth, this.yLimit);
        g.setColor(Color.BLACK);
        g.drawLine(0, this.yLimit, gwidth, this.yLimit);

        g.setColor(Color.BLACK);
        g.drawRect(coordX, coordY, gwidth-(3*coordX), height); // border
        g.clearRect(coordX+1, coordY+1, gwidth-(3*coordX)-1, height-1); // actual address bar (white part)
        g.drawString(this.address, coordX+5, coordY+((int) (height/1.5)));

        g.setColor(oldColor);
    }

    public void setAddress(String aBarText) {
        this.address = aBarText;
    }

    public String getAddress() {
        return address;
    }

    public void handleClick(int id, int x, int y, int clickCount){
        if (id == MouseEvent.MOUSE_PRESSED){
            //select the HEADER in drawnObjects
            //actions:
            //  * the current HEADER is selected (blue background)
            //  * keyboard focus (with text cursor)

        }

        //if in keyboard focus and clicked outside address bar -> same action as ENTER
    }

    public boolean isInFocus(){
        return this.inFocus;
    }

    public void setInFocus(){
        this.inFocus = true;
    }

    public void setOutFocus(){
        this.inFocus = false;
    }
}


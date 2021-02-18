package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.nio.file.FileAlreadyExistsException;

public class AddressBar {

    final int yLimit = 50;

    private String address = "www.helemaalmooiV2.nl/smexie";

    GUI gui;
    private int abX = 5;
    private int abY = this.yLimit / 6;
    private int height = this.yLimit * 2 / 3;
    private int width = 0;

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

    public void paintAddressBar(Graphics g) {
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
        g.drawString(this.address, abX+5, abY+((int) (height/1.5)));

        g.setColor(oldColor);
    }

    public void setAddress(String aBarText) {
        this.address = aBarText;
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
        return false;
    }

    public void setInFocus(){

    }

    public void setOutFocus(){

    }
}


package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.nio.file.FileAlreadyExistsException;

public class AddressBar implements GUIObject{

    final int yLimit = 50;

    private boolean selectedText = false;
    private boolean initialClick = true;
    private String address = "www.helemaalmooiV2.nl/smexie";

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
            // when the address bar is in focus, a text cursor needs to be shown at the end off the current string
            viewedAddress += "|";
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

    public boolean isInFocus(){
        return this.inFocus;
    }

    public void setInFocus(){
        this.inFocus = true;
    }

    public void setOutFocus(){
        this.initialClick = true;
        this.selectedText = false;
        this.inFocus = false;
        this.repaint();
    }
}


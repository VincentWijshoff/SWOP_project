package gui;

import java.awt.*;

public class GUIAddressBar extends GUIRectangle {

    private String aBarText = "www.helemaalmooi.nl";

    /*
    * Class used for the actual GUIRectangle users click to enter URLs.
    *
     */
    public GUIAddressBar(int x, int y, int height) {
        super(x, y, 0, height);
    }

    public void setaBarText(String text){
        aBarText = text;
    }

    public String getaBarText(){
        return aBarText;
    }



}

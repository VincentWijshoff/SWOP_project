package gui;

import java.awt.*;

public class GUILink extends GUIString {

    private String href;

    public GUILink(String text, int x, int y, String href) {
        super(text, x, y);
        this.href = href;
    }

    public String getHref() {
        return href;
    }

    public void draw(Graphics g) {
        // Bounds needed for click event
        int textWidth = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
        int textHeight = (int) g.getFontMetrics().getStringBounds(text, g).getHeight();
        updateGUIDimensions(textWidth, textHeight);

        Color oldColor = g.getColor();
        g.setColor(Color.BLUE);
        g.drawString(text, coordX, coordY);
        g.drawLine(coordX, coordY, coordX+textWidth, coordY);
        g.setColor(oldColor);
    }

    public String getFullAddress() {
        String currURL = this.documentArea.getWindow().getAddress();
        return getModifiedAddress(currURL, this.getHref());
    }

    /**
     * creates the new address.
     * a new address, when pressed a HYPERLINK is:
     *      the current address until the last '/' followed by href
     * example: "https://google.be/wikipedia/swop.html" with href = "p&o.html"
     * becomes: "https://google.be/wikipedia/p&o.html"
     *
     * @param   aBarText    the current address
     * @param   href        the href of the hyperlink
     * @return the new address as string
     */
    private String getModifiedAddress(String aBarText, String href) {
        char[] chars = aBarText.toCharArray();
        for(int i = chars.length-1; i>=0; i--){
            if(chars[i] == '/'){
                return createAddress(chars) + href;
            }else{
                chars[i] = ' ';
            }
        }
        return href;

    }

    /**
     * creates the new address given an array of chars
     * since there cannot be any space in an address -> when space return
     *
     * @param   chars the array of characters of the address
     * @return the new address (without spaces)
     */
    private String createAddress(char[] chars) {
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0; i<chars.length; i++){
            if(chars[i] == ' ')
                return stringBuffer.toString(); //no spaces in address bar
            else
                stringBuffer.append(chars[i]);
        }
        return stringBuffer.toString();
    }

    @Override
    public void handleClick() {
        //href is an absolute URL
        if(this.getHref().startsWith("http")){
            this.documentArea.getWindow().load(getHref());
        }else{
            System.out.println("You clicked on a GUILink, href = " + this.getFullAddress());
            this.documentArea.getWindow().load(this.getFullAddress());
            }
    }
}
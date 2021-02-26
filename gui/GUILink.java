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
}
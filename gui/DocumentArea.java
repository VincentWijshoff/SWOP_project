package gui;

import html.HtmlA;
import html.HtmlElement;
import html.HtmlTable;
import html.HtmlText;

import java.awt.*;
import java.util.ArrayList;

public class DocumentArea {

    ArrayList<GUIObject> drawnObjects = new ArrayList<>();

    private int relativeYPos;
    private GUI gui;

    /*
    * Class used to describe the entire Document section of our GUI.
     */
    public DocumentArea(GUI gui, int relativeYpos) {
        this.relativeYPos = relativeYpos;
        this.gui = gui;
    }

    public void paintDocArea(Graphics g){
        for (GUIObject obj: drawnObjects ) {
            obj.draw(g);
        }
    }

    public void draw(GUIObject obj) {
        drawnObjects.add(obj);
    }

    public void renderHTML(HtmlElement element) {
        renderHTML(element, relativeYPos);
    }

    public void renderHTML(HtmlElement element, int startY) {
        if (element instanceof HtmlTable) {
            HtmlTable table = ((HtmlTable) element);
            ArrayList<HtmlElement> tableRows = table.getTableRows();

            int currentY = startY;
            for (HtmlElement row: tableRows) {
                renderHTML(row, currentY);
                currentY += row.getHeight();
            }
        }
        else if (element instanceof HtmlText) {
            HtmlText text = (HtmlText) element;
            drawnObjects.add(new GUIString(text.getText(), 0, startY + text.getHeight()));
        }
        else if (element instanceof HtmlA) {
            HtmlA link = (HtmlA) element;
            drawnObjects.add(new GUIString(link.getText(), 0, startY + link.getHeight()));
            //TODO add hyperlink functionality: make GUILink instead of plain GUIString?
        }
    }

}


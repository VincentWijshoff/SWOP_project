package gui;

import html.Elements.*;

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

    public void renderHTML(ContentSpan element) {
        renderHTML(element, relativeYPos);
    }

    public void renderHTML(ContentSpan element, int startY) {
        if (element instanceof HtmlTable) {
            HtmlTable table = ((HtmlTable) element);
            ArrayList<HtmlTableRow> tableRows = table.getTableRows();

            int currentY = startY;
            for (ContentSpan row: tableRows) {
                renderHTML(row, currentY);
                currentY += row.getHeight();
            }
        }
        else if (element instanceof TextSpan) {
            TextSpan text = (TextSpan) element;
            drawnObjects.add(new GUIString(text.getText(), 0, startY + text.getHeight()));
        }
        else if (element instanceof Hyperlink) {
            Hyperlink link = (Hyperlink) element;
            drawnObjects.add(new GUIString(link.getText(), 0, startY + link.getHeight()));
            //TODO add hyperlink functionality: make GUILink instead of plain GUIString?
        }
    }

}


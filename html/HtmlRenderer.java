package html;

import gui.GUILink;
import gui.GUIObject;
import gui.GUIString;
import html.Elements.*;

import java.util.ArrayList;

public abstract class HtmlRenderer {

    private final static int xOffset = 10;

    public static ArrayList<GUIObject> renderHTML(ContentSpan element, int relativeYPos) {
        return renderHTML(element, relativeYPos, xOffset);
    }

    public static ArrayList<GUIObject> renderHTML(ContentSpan element, int startY, int startX) {
        ArrayList<GUIObject> guiObjects = new ArrayList<>();

        if (element instanceof HtmlTable) {
            HtmlTable table = ((HtmlTable) element);
            ArrayList<HtmlTableRow> tableRows = table.getTableRows();
            int currentY = startY;
            for (ContentSpan row: tableRows) {
                guiObjects.addAll(renderHTML(row, currentY, startX));
                currentY += row.getHeight();
            }
        }
        else if (element instanceof TextSpan) {
            TextSpan text = (TextSpan) element;
            guiObjects.add(new GUIString(text.getText(), startX, startY + text.getHeight()));
        }
        else if (element instanceof Hyperlink) {
            Hyperlink link = (Hyperlink) element;
            guiObjects.add(new GUILink(link.getText(), startX, startY + link.getHeight(), link.getHref()));

        }
        else if (element instanceof HtmlTableRow) {
            HtmlTableRow tableRow = ((HtmlTableRow) element);
            ArrayList<HtmlTableCell> tableCells = tableRow.getTableData();
            int currentX = startX;
            for(HtmlTableCell cell : tableCells){
                guiObjects.addAll(renderHTML(cell, startY, currentX));
                currentX += cell.getColumnWidth();
            }
        }
        else if (element instanceof HtmlTableCell){
            HtmlTableCell tableCell = (HtmlTableCell) element;
            guiObjects.addAll(renderHTML(tableCell.getData(), startY, startX));
        }

        return guiObjects;
    }

}
package gui;

import html.Elements.*;
import html.HtmlLoader;

import java.awt.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.net.URL;

public class DocumentArea extends GUIObject {

    public Set<GUIObject> DocGUIObjects = new HashSet<>();
    private int relativeYPos;

    /*
    * Class used to describe the entire Document section of our GUI.
     */
    public DocumentArea(int relativeYpos) {
        super();

        this.relativeYPos = relativeYpos;
    }

    public void draw(GUIObject obj) {
        DocGUIObjects.add(obj);
    }

    public void loadAddress(String url){
        URL address = null;
        try{
            address = new URL(url);
        }catch(MalformedURLException e){
            System.out.println("loading URL failed!"); //TODO make an error page appear
        }
        if(address != null){

            this.DocGUIObjects.clear(); //remove guiobjects from previous page

            HtmlLoader loader = new HtmlLoader(address);
            loader.setDocumentArea(this);
            loader.loadPage();
        }
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
            DocGUIObjects.add(new GUIString(text.getText(), 0, startY + text.getHeight()));
            relativeYPos += text.getHeight();
        }
        else if (element instanceof Hyperlink) {
            Hyperlink link = (Hyperlink) element;
            DocGUIObjects.add(new GUIString(link.getText(), 0, startY + link.getHeight()));
            relativeYPos += link.getHeight();
            //TODO add hyperlink functionality: make GUILink instead of plain GUIString?
        }else if (element instanceof HtmlTableRow) {
            HtmlTableRow tableRow = ((HtmlTableRow) element);
            ArrayList<HtmlTableCell> tableCells = tableRow.getTableData();
            //x-coord?
            for(ContentSpan data : tableCells){
                renderHTML(data, startY);
            }
        }else if (element instanceof HtmlTableCell){
            HtmlTableCell tableCell = (HtmlTableCell) element;
            renderHTML(tableCell.getData(), startY);
        }
    }

}


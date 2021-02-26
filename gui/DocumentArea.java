package gui;

import browsrhtml.BrowsrDocumentValidator;
import html.Elements.*;
import html.HtmlLoader;
import localDocuments.Docs;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.net.URL;

public class DocumentArea {

    public Set<GUIObject> DocGUIObjects = new HashSet<>();
    private int relativeYPos;

    /*
    * Class used to describe the entire Document section of our GUI.
     */
    public DocumentArea(int relativeYpos) {
        super();

        this.relativeYPos = relativeYpos;
    }

    public GUIObject addGUIObject(GUIObject obj) {
        this.DocGUIObjects.add(obj);
        return obj;
    }

    /**
     * loads a page given the url as string
     *
     * @param   url the URL we need to navigate to
     *
     * @post    The new page is loaded if the given url is valid, otherwise the errorDoc is loaded
     */
    public void loadAddress(String url){
        URL address = generateAddress(url);
        this.DocGUIObjects.clear(); //remove GUIObjects from previous page
        HtmlLoader loader = generateLoader(address);

        loader.setDocumentArea(this);
        loader.loadPage();
    }

    /**
     * generates the URL given the url (in string format)
     *
     * @param   url string format of the URL of the next page
     * @return  the new URL (is null if an error occurred)
     */
    private URL generateAddress(String url) {
        URL address = null;
        try{
            address = new URL(url);
        }catch(MalformedURLException e){
            System.out.println("loading URL failed!");
        }
        return address;
    }

    /**
     * generates the HtmlLoader object given the URL
     *
     * @param   address the URL of the new page (or null if loading URL failed)
     *
     * @return a new HtmlLoader object with the corresponding browsr page (url or errorDoc)
     */
    private HtmlLoader generateLoader(URL address) {
        HtmlLoader loader;
        if (address != null) {
            try{
                BrowsrDocumentValidator.assertIsValidBrowsrDocument(address); //check if new page is valid Browsr page
                loader = new HtmlLoader(address);
            } catch (Exception e) {
                loader = new HtmlLoader(Docs.getErrorPage());
            }
        }else
            loader = new HtmlLoader(Docs.getErrorPage());
        return loader;
    }

    public void renderHTML(ContentSpan element) {
        renderHTML(element, relativeYPos, 0);
    }

    public void renderHTML(ContentSpan element, int startY, int startX) {
        if (element instanceof HtmlTable) {
            HtmlTable table = ((HtmlTable) element);
            ArrayList<HtmlTableRow> tableRows = table.getTableRows();
            int currentY = startY;
            for (ContentSpan row: tableRows) {
                renderHTML(row, currentY, startX);
                currentY += row.getHeight();
            }
        }
        else if (element instanceof TextSpan) {
            TextSpan text = (TextSpan) element;
            addGUIObject(new GUIString(text.getText(), startX, startY + text.getHeight()));
        }
        else if (element instanceof Hyperlink) {
            Hyperlink link = (Hyperlink) element;
            addGUIObject(new GUILink(link.getText(), startX, startY + link.getHeight()));
        }
        else if (element instanceof HtmlTableRow) {
            HtmlTableRow tableRow = ((HtmlTableRow) element);
            ArrayList<HtmlTableCell> tableCells = tableRow.getTableData();
            int currentX = startX;
            for(HtmlTableCell cell : tableCells){
                renderHTML(cell, startY, currentX);
                currentX += cell.getColumnWidth();
            }
        }
        else if (element instanceof HtmlTableCell){
            HtmlTableCell tableCell = (HtmlTableCell) element;
            renderHTML(tableCell.getData(), startY, startX);
            System.out.println("painting cell: " + startX + ", " + startY);
        }
    }

}


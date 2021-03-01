package gui;

import browsrhtml.BrowsrDocumentValidator;
import html.Elements.*;
import html.HtmlLoader;
import localDocuments.Docs;

import java.awt.event.MouseEvent;
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

    public void loadAddress(String url, String href) throws IOException {
        URL address = generateAddress(url, href);
        this.DocGUIObjects.clear(); //remove GUIObjects from previous page
        isValidBrowsrPage(address);

        HtmlLoader loader = new HtmlLoader(address);
        loader.setDocumentArea(this);
        loader.loadPage();
    }

    /**
     * loads a page given the url as string
     *
     * @param   url the URL we need to navigate to
     *
     * @post    The new page is loaded if the given url is valid, otherwise the errorDoc is loaded
     */
    public void loadAddress(String url) throws IOException {
        URL address = generateAddress(url, "");
        this.DocGUIObjects.clear(); //remove GUIObjects from previous page
        isValidBrowsrPage(address);

        HtmlLoader loader = new HtmlLoader(address);
        loader.setDocumentArea(this);
        loader.loadPage();

    }

    /**
     * generates the URL given the url (in string format)
     *
     * @param   url string format of the URL of the next page
     * @return  the new URL (is null if an error occurred)
     */
    private URL generateAddress(String url, String href) throws MalformedURLException {
        return new URL(new URL(url), href);
    }

    /**
     * checks if given address is a valid Browsr page
     *
     * @param   address the URL of the new page (or null if loading URL failed)
     *
     */
    private void isValidBrowsrPage(URL address) throws IOException {
        BrowsrDocumentValidator.assertIsValidBrowsrDocument(address); //check if new page is valid Browsr page

    }

    private final int xOffset = 10;

    public void renderHTML(ContentSpan element) {
        renderHTML(element, relativeYPos, xOffset);
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
            addGUIObject(new GUILink(link.getText(), startX, startY + link.getHeight(), ((Hyperlink) element).getHref()));
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

    public void loadErrorDoc() {
        this.DocGUIObjects.clear();
        HtmlLoader loader = new HtmlLoader(Docs.getErrorPage());
        loader.setDocumentArea(this);
        loader.loadPage();
    }

    public void handleMouseEvent(int id, int x, int y, Window gui){
        if (id == MouseEvent.MOUSE_PRESSED) {
            for (GUIObject obj : this.DocGUIObjects) { // Loop through all GUIObjects in docArea
                if (obj.isInGUIObject(x, y)) {
                    if(obj instanceof GUILink) {
                        System.out.println("You clicked on a GUILink, href = " + ((GUILink) obj).getHref());
                        ((GUILink) obj).load(gui.getAddress(), gui);
                        break;
                    }else if (obj instanceof GUIString) {
                        System.out.println("You clicked on a GUIString");
                    } else if (obj instanceof GUIRectangle) {
                        System.out.println("You clicked on a GUIRectangle");
                    } else {
                        System.out.println("You clicked on a GUIObject");
                    }
                }
            }
        }
    }
}


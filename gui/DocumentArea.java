package gui;

import browsrhtml.BrowsrDocumentValidator;
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
    private Window window;

    /*
    * Class used to describe the entire Document section of our GUI.
     */
    public DocumentArea(Window window, int relativeYpos) {
        this.window = window;
        this.relativeYPos = relativeYpos;
    }

    public Window getWindow() {
        return this.window;
    }
    public int getRelativeYPos() {
        return this.relativeYPos;
    }

    public GUIObject addGUIObject(GUIObject obj) {
        this.DocGUIObjects.add(obj);
        return obj;
    }

    public void addGUIObjects(ArrayList<GUIObject> objects) {
        for (GUIObject obj: objects) {
            addGUIObject(obj);
            obj.setDocumentArea(this);
        }
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

    public void loadErrorDoc() {
        this.DocGUIObjects.clear();
        HtmlLoader loader = new HtmlLoader(Docs.getErrorPage());
        loader.setDocumentArea(this);
        loader.loadPage();
    }

    public void handleMouseEvent(int id, int x, int y){
        if (id == MouseEvent.MOUSE_PRESSED) {
            for (GUIObject obj : this.DocGUIObjects) { // Loop through all GUIObjects in docArea
                if (obj.isInGUIObject(x, y)) {
                    obj.handleClick();
                    return;
                    }
                }
            }
    }
}


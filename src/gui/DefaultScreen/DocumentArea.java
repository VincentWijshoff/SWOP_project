package gui.DefaultScreen;

import browsrhtml.BrowsrDocumentValidator;
import gui.DefaultScreen.DefaultScreen;
import gui.Objects.GUIObject;
import html.HtmlLoader;
import localDocuments.Docs;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.net.URL;

/**
 * The documentarea manages all GUIObjects that are drawn on the canvas
 */
public class DocumentArea {

    private Set<GUIObject> drawnGUIObjects = new HashSet<>();
    private int relativeYPos;
    public final int xOffset = 5;
    private DefaultScreen screen;
    private HtmlLoader loader;

    /*
    * Class used to describe the entire Document section of our GUI.
     */
    public DocumentArea(DefaultScreen screen, int relativeYpos) {
        this.screen = screen;
        this.relativeYPos = relativeYpos;
        this.loader = new HtmlLoader(this);
    }

    /**
     * Get the Window
     * @return The window linked to this document area
     */
    public DefaultScreen getScreen() {
        return this.screen;
    }

    /**
     * Get the relative y position for the document area, this is not 0 because off the address bar
     * @return the relative y position
     */
    public int getRelativeYPos() {
        return this.relativeYPos;
    }

    /**
     * Return currently rendered GUIObjects
     * @return Set of GUIObjects
     */
    public ArrayList<GUIObject> getDrawnGUIObjects() {
        ArrayList<GUIObject> objs = new ArrayList<>();
        for (GUIObject obj: drawnGUIObjects) {
            objs.add(obj);
            objs.addAll(obj.getChildObjects());
        }
        return objs;
    }

    /**
     * add a GUIObject to the list off gui objects
     * @param obj the object that needs to be added
     * @return the object
     */
    public GUIObject addGUIObject(GUIObject obj) {
        this.drawnGUIObjects.add(obj);

        obj.setPosition(obj.coordX + xOffset, obj.coordY+relativeYPos);

        obj.setHandler(this.screen);
        obj.updateDimensions();

        return obj;
    }

    /**
     * Add a list off gui objects to the current list off gui objects, also set the document area for each off these
     * GUIObjects to this
     * @param objects the array off GUIObjects
     */
    public void addGUIObjects(ArrayList<GUIObject> objects) {
        for (GUIObject obj: objects) {
            addGUIObject(obj);
        }
    }

    public void draw(Graphics g) {
        // Draw every GUIObject in the docArea
        for (GUIObject obj : this.getDrawnGUIObjects()) {
            obj.draw(g);
        }
    }

     /**
     * Clears the DocGUIObjects so a new page can be loaded
     */
    public void clearDocObjects(){
        for (GUIObject obj: this.getDrawnGUIObjects()) {
            obj.removeEventHandlers();
        }
        this.drawnGUIObjects.clear();
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
        this.clearDocObjects(); //remove GUIObjects from previous page
        //isValidBrowsrPage(address);
        this.loader.initialise(address);
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

    /**
     * Load the error document because an error occurred whit the loading
     */
    public void loadErrorDoc() {
        this.drawnGUIObjects.clear();
        this.loader.initialise(Docs.getErrorPage());
        loader.loadPage();
    }

    public void loadWelcomeDoc() {
        this.drawnGUIObjects.clear();
        this.loader.initialise(Docs.getWelcomePage());
        loader.loadPage();
    }

    public String getCurrentHtml() {
        return this.loader.getHtmlCode();
    }

    public void load(String finaladdition) {
        this.screen.load(finaladdition);
    }
}


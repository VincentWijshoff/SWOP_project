package gui.DefaultScreen;

import browsrhtml.BrowsrDocumentValidator;
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
 * The document area manages all GUIObjects that are drawn on the canvas
 */
public class DocumentArea {

    // necessary element for the document area
    private final Set<GUIObject> drawnGUIObjects = new HashSet<>();
    private final int relativeYPos;
    public final int xOffset = 5;
    private final DefaultScreen screen;
    private final HtmlLoader loader;

    /**
     * Class used to describe the entire Document section of our GUI.
     * @param screen the screen this document area should be part of
     * @param relativeYpos y-position this document area starts at
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
     */
    public void addGUIObject(GUIObject obj) {
        this.drawnGUIObjects.add(obj);

        obj.setPosition(obj.coordX + xOffset, obj.coordY+relativeYPos);

        obj.setFontMetricsHandler(this.screen);
        obj.setPageLoader(this.screen);
        obj.updateDimensions();

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

    /**
     * Draw the document area
     * @param g The graphics needed to draw the document area
     */
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
        this.drawnGUIObjects.clear();
    }

    /**
     * loads a page given the url as string
     *
     * @param   url the URL we need to navigate to
     *
     * post    The new page is loaded if the given url is valid, otherwise the errorDoc is loaded
     */
    public void loadAddress(String url) throws IOException {
        URL address = generateAddress(url, "");
        this.clearDocObjects(); //remove GUIObjects from previous page
        isValidBrowsrPage(address);
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

    /**
     * Load the welcome document
     */
    public void loadWelcomeDoc() {
        this.drawnGUIObjects.clear();
        this.loader.initialise(Docs.getWelcomePage());
        loader.loadPage();
    }

    /**
     * Get the current html of the webpage
     * @return  The html of the current webpage
     */
    public String getCurrentHtml() {
        return this.loader.getHtmlCode();
    }

    /**
     * Load a webpage from url or from href
     * @param url   The full url or the href to the new website
     */
    public void load(String url) {
        this.screen.load(url);
    }

    /**
     * handles the key-presses for this document area
     * @param id        The id of the pressed button
     * @param keyCode   The keycode for the pressed button
     * @param keyChar   The char that was pressed
     * @param modifier  The modifier on the pressed key
     */
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifier) {
        drawnGUIObjects.forEach(obj -> obj.handleKeyEvent(id, keyCode, keyChar, modifier));
    }

    /**
     * handles the mouse-clicks for this document area
     * @param id            The id off the mouse event
     * @param clickCount    The click count off the user
     * @param x             x position of the mouse event
     * @param y             y position of the mouse event
     */
    public void handleMouseEvent(int id, int x, int y, int clickCount){
        drawnGUIObjects.forEach(obj -> obj.handleMouseEvent(x, y, id, clickCount));
    }
}


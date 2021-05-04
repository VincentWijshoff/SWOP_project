package gui.DefaultScreen;

import browsrhtml.BrowsrDocumentValidator;
import gui.Objects.GUIObject;
import html.HtmlLoader;
import localDocuments.Docs;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ChildPane extends Pane {

    private Set<GUIObject> drawnGUIObjects = new HashSet<>();

    ChildPane(DocumentArea docArea){
        this.docArea = docArea;
        this.loader = new HtmlLoader(this);
    }

    /**
     * Handle a key event on this pane
     *
     * @param id       The id off the key event
     * @param keyCode  The keycode off the key event
     * @param keyChar  The char of the key pressed
     * @param modifier The modifier on the key pressed
     */
    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifier) {
        if(modifier == KeyEvent.CTRL_DOWN_MASK && id == KeyEvent.KEY_PRESSED){
            if(keyCode == KeyEvent.VK_H){
                // split with draggable vertical separator
                this.makeParentVertical();
            }else if(keyCode == KeyEvent.VK_V){
                //split with draggable horizontal separator
                this.makeParentHorizontal();
            }
        }
        drawnGUIObjects.forEach(obj -> obj.handleKeyEvent(id, keyCode, keyChar, modifier));
    }

    /**
     * Handle a mouse event on this pane
     *
     * @param id         The id off the mouse event
     * @param x          The x coordinate off the mouse event
     * @param y          The y coordinate off the mouse event
     * @param clickCount The click count on the mouse event
     */
    @Override
    public void handleMouseEvent(int id, int x, int y, int clickCount) {
        drawnGUIObjects.forEach(obj -> obj.handleMouseEvent(x, y, id, clickCount));
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
        this.drawnGUIObjects.clear();
        isValidBrowsrPage(address);
        this.loader.initialise(address);
        loader.loadPage();
    }

    /**
     * generates the URL given the url (in string format)
     *
     * @param   url     string format of the URL of the next page
     * @param   href    the href to add
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

    @Override
    public ArrayList<GUIObject> getDrawnGUIObjects() {
        ArrayList<GUIObject> objs = new ArrayList<>();
        for (GUIObject obj: drawnGUIObjects) {
            objs.add(obj);
            objs.addAll(obj.getChildObjects());
        }
        return objs;
    }

    @Override
    public void addGUIObjects(ArrayList<GUIObject> objects) {
        for (GUIObject obj: objects) {
            addGUIObject(obj);
        }
    }

    @Override
    public void draw(Graphics g) {
        if(this.isInFocus){
            // TODO we want a thick rectangle
            g.drawRect(this.x, this.y, this.width, this.height);
        }
        for (GUIObject obj : this.getDrawnGUIObjects()) {
            obj.draw(g);
        }
    }

    @Override
    protected void setInFocus() {
        this.isInFocus = true;
    }

    @Override
    protected void setOutFocus() {
        this.isInFocus = false;
    }

    @Override
    public ChildPane getFocusedPane() {
        return this;
    }

    /**
     * add a GUIObject to the list off gui objects
     * @param obj the object that needs to be added
     */
    public void addGUIObject(GUIObject obj) {
        this.drawnGUIObjects.add(obj);

        obj.setPosition(obj.coordX + this.x, obj.coordY+this.y);

        obj.setFontMetricsHandler(this.docArea.getScreen());
        obj.setPageLoader(this.docArea.getScreen());
        obj.updateDimensions();

    }

    /**
     * make this pane into a parent with 2 children separated with a horizontal line
     */
    private void makeParentHorizontal(){
        // we change this into a parent pane
        ParentPane parent = new ParentPane();
        parent.setDimensions(this.x, this.y, this.width, this.height);
        if(this.parentPane != null){
            parent.setParentPane(this.parentPane);
            this.parentPane.changeChild(parent, this);
        }else{
            // this is the upper most pane
            this.docArea.setPane(parent);
        }
        // we then make 2 child panes exactly as this one is with a horizontal line
        int y1 = this.y;
        int y2 = this.y + this.height / 2;
        ChildPane c1 = new ChildPane(this.docArea); // upper child
        c1.setParentPane(parent);
        c1.setDimensions(this.x, y1, this.width, this.height/2);
        c1.setGUIObjects(this.copyOfObjects());
        //c1.updateGUIPositions(0, 0);
        ChildPane c2 = new ChildPane(this.docArea); // lower child
        c2.setParentPane(parent);
        c2.setDimensions(this.x, y2, this.width, this.height/2);
        c2.setGUIObjects(this.copyOfObjects());
        c2.updateGUIPositions(0, this.height/2);
        // set children
        parent.setChildren(c1, c2);
        //we set the first one in focus
        parent.isInFocus = true;
        c1.isInFocus = true;
    }

    private void setGUIObjects(Set<GUIObject> drawnGUIObjects) {
        this.drawnGUIObjects = drawnGUIObjects;
    }

    /**
     * we want to update the positions off all gui objects, so we will add the x and y divs to their old positions
     * @param xDiv  the difference in x from the old position
     * @param yDiv  the difference in y from their old position
     */
    private void updateGUIPositions(int xDiv, int  yDiv){
        for(GUIObject obj : this.drawnGUIObjects){
            obj.setPosition(obj.coordX + xDiv, obj.coordY + yDiv);
            obj.updateDimensions();
        }
    }

    private Set<GUIObject> copyOfObjects(){
        return Set.copyOf(this.drawnGUIObjects);
    }

    /**
     * make this pane into a parent with 2 children separated with a vertical line
     */
    private void makeParentVertical(){
        // we change this into a parent pane
        ParentPane parent = new ParentPane();
        parent.setDimensions(this.x, this.y, this.width, this.height);
        if(this.parentPane != null){
            parent.setParentPane(this.parentPane);
            this.parentPane.changeChild(parent, this);
        }else{
            this.docArea.setPane(parent);
        }
        // we then make 2 child panes exactly as this one is with a vertical line
        int x1 = this.x;
        int x2 = this.x + this.width / 2;
        ChildPane c1 = new ChildPane(this.docArea); // left child
        c1.setParentPane(parent);
        c1.setDimensions(x1, this.y, this.width/2, this.height);
        c1.setGUIObjects(this.copyOfObjects());
        //c1.updateGUIPositions(0, 0);
        ChildPane c2 = new ChildPane(this.docArea); // right child
        c2.setParentPane(parent);
        c2.setDimensions(x2, this.y, this.width/2, this.height);
        c2.setGUIObjects(this.copyOfObjects());
        c2.updateGUIPositions(this.width / 2, 0);
        // set children
        parent.setChildren(c1, c2);
        //we set the first one in focus
        parent.isInFocus = true;
        c1.isInFocus = true;
    }
}

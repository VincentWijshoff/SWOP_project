package gui.DefaultScreen;

import browsrhtml.BrowsrDocumentValidator;
import gui.Objects.GUIObject;
import gui.Objects.ScrollBars.HorizontalScrollBar;
import gui.Objects.ScrollBars.Scrollable;
import gui.Objects.ScrollBars.VerticalScrollBar;
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

public class ChildPane extends Pane implements Scrollable {

    private HorizontalScrollBar horScrollBar;
    private VerticalScrollBar verScrollBar;
    private int xOffset = 0;
    private int yOffset = 0;

    private Set<GUIObject> drawnGUIObjects = new HashSet<>(); // the set off gui objects
    public static final int xBorderOffset = 5; // the x offset to draw all objects
    private String address;


    /**
     * constructor
     * @param screen   the default screen
     */
    ChildPane(PaneManager screen){
        this.screen = screen;
        this.address = screen.getAddress();
        this.loader = new HtmlLoader(this);
        this.horScrollBar = new HorizontalScrollBar(this);
        this.verScrollBar = new VerticalScrollBar(this);
    }

    public int getXOffset() { return this.xOffset; }

    public void setXOffset(int amount) {
        if (amount >= 0) this.xOffset = 0;
        else this.xOffset = Math.max(amount, this.horScrollBar.calcMaxOffset());

        this.horScrollBar.getSlider().coordX = this.horScrollBar.calculateSliderX();
    }

    public int getYOffset() { return this.yOffset; }

    public void setYOffset(int amount) {
        if (amount > 0) this.yOffset = 0;
        else this.yOffset = Math.max(amount, this.verScrollBar.calcMaxOffset());

        //this.verScrollBar.getSlider().coordY = this.verScrollBar.calculateSliderY();
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
            }else if(keyCode == KeyEvent.VK_X){
                if(this.parentPane == null){
                    this.loadWelcomeDoc();
                }else{
                    // remove this from parent pane, parent will do the rest
                    this.parentPane.removeChild(this);
                }
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
        if(this.isOnPane(x, y)){
            this.setInFocus();
        }else{
            this.setOutFocus();
        }
        if (this.getHorScrollBar().isOnScrollBar(x, y)) this.getHorScrollBar().handleMouseEvent(id, x, y, clickCount);
        else if (this.getVerScrollBar().isOnScrollBar(x, y)) this.getVerScrollBar().handleMouseEvent(id, x, y, clickCount);
        else drawnGUIObjects.forEach(obj -> obj.handleMouseEvent(x, y, id, clickCount));
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
        this.address = address.toString();
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

    /**
     * get all gui objects from this pane
     * @return  All gui objects on this pane
     */
    @Override
    public ArrayList<GUIObject> getDrawnGUIObjects() {
        ArrayList<GUIObject> objs = new ArrayList<>();
        for (GUIObject obj: drawnGUIObjects) {
            objs.add(obj);
            objs.addAll(obj.getChildObjects());
        }
        return objs;
    }

    /**
     * add an array off gui objects to current gui objects
     * @param objects gui objects to add
     */
    @Override
    public void addGUIObjects(ArrayList<GUIObject> objects) {
        for (GUIObject obj: objects) {
            addGUIObject(obj);
        }
    }

    /**
     * draw all gui object sin this pane
     * @param g the graphics needed to draw
     */
    @Override
    public void draw(Graphics g) {
        if(this.isInFocus){
            g.drawRect(this.x + 2, this.y + 2, this.width - 4, this.height - 4);
        }

        Shape oldClip = g.getClip();
        g.setClip(this.x, this.y, this.width, this.height);
        this.setXOffset(this.getXOffset()); // Needed to check edge cases
        this.setYOffset(this.getYOffset()); // Needed to check edge cases
        int xScrollOffset = this.getXOffset();
        int yScrollOffset = this.getYOffset();
        for (GUIObject obj : this.getDrawnGUIObjects()) {
            obj.draw(g, xScrollOffset, yScrollOffset);
        }
        g.setClip(oldClip);
        if (this.getHorScrollBar() != null) this.getHorScrollBar().draw(g);
        if (this.getVerScrollBar() != null) this.getVerScrollBar().draw(g);
    }

    /**
     * set this pane in focus
     */
    @Override
    protected void setInFocus() {
        this.isInFocus = true;
        this.screen.setAddress(this.address);
    }

    /**
     * set this pane out of focus
     */
    @Override
    protected void setOutFocus() {
        this.isInFocus = false;
    }

    /**
     * get the ane that is in focus, this can only be called if this pane is in focus
     * @return  this pane
     */
    @Override
    public ChildPane getFocusedPane() {
        return this;
    }

    /**
     * update the dimensions off this pane and update positions off gui objects if needed
     * @param x         the new x position off the pane
     * @param y         the new y position of the pane
     * @param width     the new width of the pane
     * @param height    the new height of the pane
     */
    @Override
    protected void updateDimensions(int x, int y, int width, int height) {
        int xDiv = x - this.x;
        int yDiv = y - this.y;
        this.updateGUIPositions(xDiv, yDiv);
        this.setDimensions(x, y, width, height);
        this.getHorScrollBar().updateDimensions();
        this.getVerScrollBar().updateDimensions();
    }

    /**
     * gets the right most position off the pane
     */
    @Override
    int getRightMost() {
        return this.x;
    }

    /**
     * gets the left most position off the pane
     */
    @Override
    int getLeftMost() {
        return this.x + this.width;
    }

    /**
     * gets the top most position off the pane
     */
    @Override
    int getTopMost() {
        return this.y + this.height;
    }

    /**
     * gets the bottom most position off the pane
     */
    @Override
    int getBottomMost() {
        return this.y;
    }

    /**
     * add a GUIObject to the list off gui objects
     * @param obj the object that needs to be added
     */
    public void addGUIObject(GUIObject obj) {
        this.drawnGUIObjects.add(obj);

        obj.setPosition(obj.coordX + this.x + getXOffset() + ChildPane.xBorderOffset, obj.coordY + this.y);

        obj.setFontMetricsHandler(this.screen.getFontMetricsHandler());
        obj.setPageLoader(this.screen.getPageLoader());
        obj.updateDimensions();

    }

    /**
     * make this pane into a parent with 2 children separated with a horizontal line
     */
    private void makeParentHorizontal(){
        // we change this into a parent pane
        ParentPane parent = new ParentPane(this.screen);
        parent.setDimensions(this.x, this.y, this.width, this.height);
        if(this.parentPane != null){
            parent.setParentPane(this.parentPane);
            this.parentPane.changeChild(parent, this);
        }else{
            // this is the upper most pane
            this.screen.setPane(parent);
        }
        // we then make 2 child panes exactly as this one is with a horizontal line
        int y1 = this.y;
        int y2 = this.y + this.height / 2;
        ChildPane c1 = new ChildPane(this.screen); // upper child
        c1.setParentPane(parent);
        c1.setDimensions(this.x, y1, this.width, this.height/2);
        c1.setGUIObjects(this.copyOfObjects());
        //c1.updateGUIPositions(0, 0);
        ChildPane c2 = new ChildPane(this.screen); // lower child
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

    /**
     * set the gui objects off this pane
     * @param drawnGUIObjects   the gui objects to set
     */
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

    /**
     * get a copy of all gui objects in this pane
     * @return  a copy of al gui objects
     */
    private Set<GUIObject> copyOfObjects(){
        Set<GUIObject> copy = new HashSet<>();
        for(GUIObject obj : this.drawnGUIObjects){
            HashSet<GUIObject> cpy = obj.copy();
            for(GUIObject guiCopy : cpy){
                guiCopy.setPageLoader(this.screen.getPageLoader());
                guiCopy.setFontMetricsHandler(this.screen.getFontMetricsHandler());
            }
            copy.addAll(cpy);
        }
        return copy;
    }

    /**
     * make this pane into a parent with 2 children separated with a vertical line
     */
    private void makeParentVertical(){
        // we change this into a parent pane
        ParentPane parent = new ParentPane(this.screen);
        parent.setDimensions(this.x, this.y, this.width, this.height);
        if(this.parentPane != null){
            parent.setParentPane(this.parentPane);
            this.parentPane.changeChild(parent, this);
        }else{
            this.screen.setPane(parent);
        }
        // we then make 2 child panes exactly as this one is with a vertical line
        int x1 = this.x;
        int x2 = this.x + this.width / 2;
        ChildPane c1 = new ChildPane(this.screen); // left child
        c1.setParentPane(parent);
        c1.setDimensions(x1, this.y, this.width/2, this.height);
        c1.setGUIObjects(this.copyOfObjects());
        //c1.updateGUIPositions(0, 0);
        ChildPane c2 = new ChildPane(this.screen); // right child
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

    /**
     * Clears the GUIObjects so a new page can be loaded
     */
    public void clearDocObjects(){
        this.drawnGUIObjects.clear();
    }

    public HorizontalScrollBar getHorScrollBar() { return this.horScrollBar; }

    public VerticalScrollBar getVerScrollBar() { return this.verScrollBar; }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getOffset(boolean horizontal) {
        if(horizontal)
            return getXOffset();
        else
            return getYOffset();
    }

    @Override
    public void setOffset(int offset, boolean horizontal) {
        if(horizontal)
            setXOffset(offset);
        else
            setYOffset(offset);
    }

    @Override
    public int getAvailableWidth() {
        return width  - 15;
    }

    @Override
    public int getAvailableHeight() {
        return height - 15;
    }

    public int getContentWidth() {
        int max = 0;
        for (GUIObject guiObject : this.getDrawnGUIObjects()) {
            //System.out.println(guiObject.getClass().getName() + " = " + guiObject.coordX + " + "+ guiObject.width);
            max = Math.max(max, guiObject.coordX - this.x + guiObject.width);
        }
        return max;
    }

    public int getContentHeight() {
        int max = 0;
        for (GUIObject guiObject : this.getDrawnGUIObjects()) {
            //System.out.println(guiObject.getClass().getName() + " = " + guiObject.coordX + " + "+ guiObject.width);
            max = Math.max(max, guiObject.coordY + guiObject.height - this.y);
        }
        return max;
    }

}

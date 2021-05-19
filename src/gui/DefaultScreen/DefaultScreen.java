package gui.DefaultScreen;

import commands.BrowsrOperation;
import events.*;
import gui.DialogScreen.SaveBookmarkScreen;
import gui.DialogScreen.SaveHtmlScreen;
import gui.Screen;
import gui.Window;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * The default screen
 */
public class DefaultScreen implements Screen, FontMetricsHandler, PageLoader, PaneManager {

    // the elements that are on a default screen
    AddressBar addressBar;
    BookmarkBar bookmarkBar;
    Pane rootPane;
    // the window
    gui.Window window;

    /**
     * construct default browser screen
     * @param window the window this screen should be part of
     */
    public DefaultScreen(Window window){
        this.window = window;

        this.addressBar = new AddressBar("WelcomeDoc.html", this);
        this.bookmarkBar = new BookmarkBar(this.addressBar.yLimit, this);
        this.rootPane = new ChildPane(this);
        this.rootPane.setDimensions(0, this.addressBar.yLimit + this.bookmarkBar.getHeight(), 0, 0);
        this.rootPane.setInFocus();
    }

    /**
     * Load a url
     * @param url   The url that needs to be loaded
     */
    public void load(String url) {
        url = this.createNewAddress(url);
        try {
            System.out.println("Loading webpage: " + url);
            this.addressBar.setAddress(url);
            this.rootPane.loadAddress(url);
        } catch (Exception e) {
            System.out.println("loading Error Page");
            this.rootPane.loadErrorDoc();
        }
    }

    /**
     * Create a new address with the given address
     * @param address   The address
     * @return          The given address if it is a normal url
     *                  A newly composed url with the current address if the given address is a href
     */
    private String createNewAddress(String address){
        if(address.startsWith("http")){
            return address;
        }else{
            return this.getFullAddress(address);
        }
    }

    /**
     * Calculate the new address
     * @param address   The href for the new address
     * @return          The new address
     */
    private String getFullAddress(String address) {
        return getModifiedAddress(this.getAddress(), address);
    }

    /**
     * Make the modified address with the current address and a href
     * @param aBarText  The current address off the webpage
     * @param href      The href to the new website
     * @return          The composed new address
     */
    private String getModifiedAddress(String aBarText, String href) {
        char[] chars = aBarText.toCharArray();
        for(int i = chars.length-1; i>=0; i--){
            if(chars[i] == '/'){
                return createAddress(chars) + href;
            }else{
                chars[i] = ' ';
            }
        }
        return href;

    }

    /**
     * Create the new address
     * @param chars The current address in char form without the final part behind the last /
     * @return      The new address as a string
     */
    private String createAddress(char[] chars) {
        StringBuilder stringBuffer = new StringBuilder();
        for (char aChar : chars) {
            if (aChar == ' ')
                return stringBuffer.toString(); //no spaces in address bar
            else
                stringBuffer.append(aChar);
        }
        return stringBuffer.toString();
    }

    /**
     * Called on startup, needs to show the welcome document
     */
    public void handleShown() {
        this.rootPane.setDimensions(0, this.rootPane.y,
                this.getWidth(),
                this.getHeight() - this.rootPane.y);
        this.rootPane.loadWelcomeDoc();
    }


    /**
     * Execute an operation
     * @param operation the operation to execute
     */
    @Override
    public void execute(BrowsrOperation operation) {
        operation.execute(this);
    }

    /**
     * draw the screen
     * @param g The graphics needed to draw the screen
     */
    @Override
    public void draw(Graphics g) {
        int paneYPos = this.addressBar.yLimit + this.bookmarkBar.getHeight();
        this.rootPane.updateDimensions(0, paneYPos, this.getWidth(), this.getHeight() - paneYPos);
        this.rootPane.draw(g);
        // Draw AddressBar
        this.bookmarkBar.draw(g, this.getWidth());
        this.addressBar.draw(g, this.getWidth());
    }

    public int getWidth() { return this.window.getWidth(); }

    public int getHeight(){
        return this.window.getHeight();
    }

    /**
     * Get the current address of this window
     * @return the current address
     */
    public String getAddress(){
        return this.addressBar.getAddress();
    }

    /**
     * Handle a mouse event
     * @param id            The id of the mouse event
     * @param x             The x coordinate of the mouse event
     * @param y             The y coordinate of the mouse event
     * @param clickCount    The click count of the mouse event
     * @param button        The button pressed on the mouse
     * @param modifiersEx   The modifiers active on the mouse
     */
    @Override
    public void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        this.bookmarkBar.handleMouseEvent(id, x, y, clickCount);
        this.addressBar.handleMouseEvent(id, x, y, clickCount);

        //if (this.rootPane.isOnPane(x, y)) {
            this.rootPane.handleMouseEvent(id, x, y, clickCount);
        //}
    }

    /**
     * Handle a key event
     * @param id            The id of the key pressed
     * @param keyCode       The code of the key pressed
     * @param keyChar       The char of the key pressed
     * @param modifiersEx   The modifiers of the key pressed
     */
    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx) {
        if(modifiersEx == KeyEvent.CTRL_DOWN_MASK && id == KeyEvent.KEY_PRESSED){
            if(keyCode == KeyEvent.VK_S){
                this.makeSaveHtmlScreen();
                return;
            } else if (keyCode == KeyEvent.VK_D){
                this.makeSaveBookmarkScreen();
                return;
            }
        }
        this.bookmarkBar.handleKeyEvent(id, keyCode, keyChar, modifiersEx);
        this.addressBar.handleKeyEvent(id, keyCode, keyChar, modifiersEx);
        this.rootPane.handleKeyEvent(id, keyCode, keyChar, modifiersEx);

    }

    /**
     * Get the font metrics form the window area
     * @return  The font metrics
     */
    @Override
    public FontMetrics getFontMetrics() {
        return window.getFontMetrics();
    }

    /**
     * Get the address bar of this window
     * @return the address bar
     */
    public AddressBar getAddressBar() {
        return addressBar;
    }

    /**
     * Get the bookmark bar
     * @return  The bookmark bar
     */
    public BookmarkBar getBookmarkBar() { return bookmarkBar; }


    /**
     * Make the new dialog screen to save a bookmark
     */
    private void makeSaveBookmarkScreen(){
        SaveBookmarkScreen s = new SaveBookmarkScreen(this.window, this, getAddress());
        this.window.setScreen(s);
    }

    /**
     * Make the new dialog screen to save the webpage as a html file
     */
    private void makeSaveHtmlScreen(){
        SaveHtmlScreen s = new SaveHtmlScreen(this.window, this, this.rootPane.getCurrentHtml());
        this.window.setScreen(s);
    }

    /**
     * @return this
     */
    @Override
    public PageLoader getPageLoader() {
        return this;
    }

    /**
     * @return this
     */
    @Override
    public FontMetricsHandler getFontMetricsHandler() {
        return this;
    }

    /**
     * @param pane the pane to set as root pane for this defaultscreen
     */
    public void setPane(Pane pane) {
        this.rootPane = pane;
    }

    /**
     * @return the pane that is currently in focus
     */
    public ChildPane getFocusedPane() {
        return this.rootPane.getFocusedPane();
    }

    /**
     * @return the root pane of this screen
     */
    public Pane getPane() {
        return this.rootPane;
    }

    /**
     * set the address in the address bar
     * @param address the address to set
     */
    @Override
    public void setAddress(String address) {
        this.addressBar.setAddress(address);
    }
}

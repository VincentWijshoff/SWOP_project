package gui.DefaultScreen;

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
public class DefaultScreen implements Screen, FontMetricsHandler, PageLoader {

    // the elements that are on a default screen
    AddressBar addressBar;
    BookmarkBar bookmarkBar;
    DocumentArea documentArea;
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
        this.documentArea = new DocumentArea(this, this.addressBar.yLimit + this.bookmarkBar.getHeight());
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
            this.documentArea.loadAddress(url);
        } catch (Exception e) {
            System.out.println("loading Error Page");
            this.documentArea.loadErrorDoc();
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
        this.documentArea.loadWelcomeDoc();
    }

    /**
     * draw the screen
     * @param g The graphics needed to draw the screen
     */
    @Override
    public void draw(Graphics g) {
        this.documentArea.draw(g);

        // Draw AddressBar
        this.bookmarkBar.draw(g, this.window.getWidth());
        this.addressBar.draw(g, this.window.getWidth());
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
     * @param id            The id off the mouse event
     * @param x             The x coordinate of the mouse event
     * @param y             The y coordinate of the mouse event
     * @param clickCount    The click count of the mouse event
     * @param button        The button pressed on the mouse
     * @param modifiersEx   The modifiers active on the mouse
     */
    @Override
    public void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        this.documentArea.handleMouseEvent(id, x, y, clickCount);
        this.bookmarkBar.handleMouseEvent(id, x, y, clickCount);
        this.addressBar.handleMouseEvent(id, x, y, clickCount);
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
        this.documentArea.handleKeyEvent(id, keyCode, keyChar, modifiersEx);
        this.bookmarkBar.handleKeyEvent(id, keyCode, keyChar, modifiersEx);
        this.addressBar.handleKeyEvent(id, keyCode, keyChar, modifiersEx);
    }

    /**
     * Get the font metrics form the window area
     * @return  The font metrics
     */
    public FontMetrics getFontMetrics() {
        return window.getFontMetrics();
    }

    /**
     * Get the document area of this window
     * @return the document area
     */
    public DocumentArea getDocArea() {
        return this.documentArea;
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
     * Add a bookmark to the bookmark bar
     * @param name  The name of the bookmark
     * @param url   The url of the bookmark
     */
    public void addBookmark(String name, String url) {
        this.bookmarkBar.addBookmark(name, url);
    }

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
        SaveHtmlScreen s = new SaveHtmlScreen(this.window, this, this.documentArea.getCurrentHtml());
        this.window.setScreen(s);
    }
}

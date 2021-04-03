package gui.DefaultScreen;

import events.*;
import gui.DialogScreen.SaveBookmarkBarScreen;
import gui.DialogScreen.SaveHtmlScreen;
import gui.Screen;
import gui.Window;

import java.awt.*;
import java.awt.event.KeyEvent;

public class DefaultScreen implements Screen, EventHandler {

    MouseEventHandler mouseEventHandler;
    KeyEventHandler keyEventHandler;

    AddressBar addressBar;
    BookmarkBar bookmarkBar;
    DocumentArea documentArea;

    gui.Window window;

    public DefaultScreen(Window w){
        this.window = w;

        this.mouseEventHandler = new MouseEventHandler();
        this.keyEventHandler = new KeyEventHandler();

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

    private String createNewAddress(String address){
        if(address.startsWith("http")){
            return address;
        }else{
            return this.getFullAddress(address);
        }
    }

    private String getFullAddress(String address) {
        return getModifiedAddress(this.getAddress(), address);
    }

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

    private String createAddress(char[] chars) {
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0; i<chars.length; i++){
            if(chars[i] == ' ')
                return stringBuffer.toString(); //no spaces in address bar
            else
                stringBuffer.append(chars[i]);
        }
        return stringBuffer.toString();
    }

    public void handleShown() {
        this.documentArea.loadWelcomeDoc();
    }

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

    @Override
    public void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        this.mouseEventHandler.onClick(id, x, y, clickCount);
    }

    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx) {
        if(modifiersEx == KeyEvent.CTRL_DOWN_MASK && id == KeyEvent.KEY_PRESSED){
            if(keyCode == KeyEvent.VK_S){
                this.makeSaveHtmlScreen();
                return;
            } else if (keyCode == KeyEvent.VK_D){
                this.makeSaveBookmarkBarScreen();
                return;
            }
        }
        this.keyEventHandler.onKeyPress(id, keyCode, keyChar, modifiersEx);
    }

    public FontMetrics getFontMetrics() {
        return window.getFontMetrics();
    }

    /**
     * Get the document area off this window
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

    public BookmarkBar getBookmarkBar() { return bookmarkBar; }

    @Override
    public void addMouseEventListener(MouseEventListener listener) {
        this.mouseEventHandler.addMouseEventListener(listener);
    }

    @Override
    public void addKeyEventListener(KeyEventListener listener) {
        this.keyEventHandler.addKeyEventListener(listener);
    }

    @Override
    public void removeMouseEventListener(MouseEventListener listener) {
        this.mouseEventHandler.removeMouseEventListener(listener);
    }

    @Override
    public void removeKeyEventListener(KeyEventListener listener) {
        this.keyEventHandler.removeKeyEventListener(listener);
    }

    @Override
    public void addBookmark(String name, String url) {
        this.bookmarkBar.addBookmark(name, url);
    }

    private void makeSaveBookmarkBarScreen(){
        SaveBookmarkBarScreen s = new SaveBookmarkBarScreen(this.window, this, getAddress());
        this.window.setScreen(s);
    }

    private void makeSaveHtmlScreen(){
        SaveHtmlScreen s = new SaveHtmlScreen(this.window, this, this.documentArea.getCurrentHtml());
        this.window.setScreen(s);
    }
}

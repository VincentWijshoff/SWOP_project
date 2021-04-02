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
        try {
            System.out.println("Loading webpage: " + url);
            this.addressBar.setAddress(url);
            this.documentArea.loadAddress(url);
        } catch (Exception e) {
            System.out.println("loading Error Page");
            this.documentArea.loadErrorDoc();
        }
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

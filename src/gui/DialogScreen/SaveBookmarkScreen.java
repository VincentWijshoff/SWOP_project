package gui.DialogScreen;

import commands.AddBookmarkOperation;
import gui.Objects.GUIButton;
import gui.Objects.GUIInput;
import gui.Objects.GUIString;
import gui.Screen;
import gui.Window;

/**
 * The dialog screen to save a bookmark
 */
public class SaveBookmarkScreen extends DialogScreen{

    // some needed objects
    private final String currAddress;
    private GUIInput bookmarkName;
    private GUIInput bookmarkAddress;

    /**
     * Construct this SaveBookmarkScreen
     * @param window the window this SaveBookmarkScreen should be part of
     * @param prevscreen the default screen that was shown before this screen
     * @param address the current address to fill in
     */
    public SaveBookmarkScreen(Window window, Screen prevscreen, String address){
        super(window, prevscreen);
        this.currAddress = address;
        this.create();
    }

//      a text input field for
//      the name of the bookmark, preceded by a label "Name", and a text input field
//      for the URL of the bookmark, preceded by a label "URL", with the current URL
//      pre-filled, and two buttons: an Add "Bookmark" button and a "Cancel" button.
//      Clicking the Add Bookmark button adds the bookmark to the end of the list of
//      bookmarks and closes the dialog screen; clicking the Cancel button simply closes
//      the dialog screen.

    /**
     * Create the full save bookmark screen
     */
    protected void create(){
        System.out.println("Creating save bookmark screen");
        this.addGUIObject(new GUIString("Saving URL to bookmark bar", 30, 30));
        this.addGUIObject(new GUIString("Name", 20, 70));
        this.bookmarkName = new GUIInput(100, 70, 400, 30);
        this.addGUIObject(this.bookmarkName);
        this.addGUIObject(new GUIString("URL", 20, 110));
        this.bookmarkAddress = new GUIInput(this.currAddress, 100, 110, 400, 30);
        this.addGUIObject(this.bookmarkAddress);
        // 2 buttons
        GUIButton cnclBtn = new GUIButton("Cancel", 20, 180, 100, 30);
        GUIButton addBtn = new GUIButton("Add Bookmark", 150, 180, 100, 30);
        cnclBtn.setMouseEvent(this::onCancel);
        addBtn.setMouseEvent(this::onAddBookmark);
        this.addGUIObject(cnclBtn);
        this.addGUIObject(addBtn);
        this.getGUIObjects().forEach(obj -> obj.setFontMetricsHandler(this));
    }

    /**
     * Called when the cancel button was pressed
     */
    private void onCancel(){
        System.out.println("canceling the adding off the bookmark");
        this.returnToPreviousScreen();
    }

    /**
     * Get the name off the bookmark
     * @return  The name off the bookmark
     */
    public GUIInput getBookmarkName(){
        return this.bookmarkName;
    }

    /**
     * Get the address off the bookmark
     * @return  The address of the bookmark
     */
    public GUIInput getBookmarkAddress() {
        return bookmarkAddress;
    }

    /**
     * Needs to be executed when the add bookmark button was pressed, this will create a new add bookmark operation
     */
    private void onAddBookmark() {
        System.out.println("Adding bookmark with name: " + this.bookmarkName.getText() + ", on the URL: " + this.bookmarkAddress.getText());

        this.execute(new AddBookmarkOperation(this.bookmarkName.getText(), this.bookmarkAddress.getText()));

        this.returnToPreviousScreen();
    }

}

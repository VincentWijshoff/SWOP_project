package gui.DialogScreen;

import commands.SaveHTMLOperation;
import gui.Objects.GUIButton;
import gui.Objects.GUIInput;
import gui.Objects.GUIString;
import gui.Screen;
import gui.Window;

/**
 * Save a screen to an .html file
 */
public class SaveHtmlScreen extends DialogScreen{

    // needed objects
    private final String htmlCode;
    private GUIInput fileName;

    /**
     * Construct savehtmlscreen
     * @param window the window this SaveBookmarkScreen should be part of
     * @param prevScreen the default screen that was shown before this screen
     * @param html the html to save
     */
    public SaveHtmlScreen(Window window, Screen prevScreen, String html){
        super(window, prevScreen);
        this.htmlCode = html;
        this.create();
    }

//    The user can save the current document as a .html file by pressing Ctrl+S.
//    This shows the Save As dialog screen, with a text input field for the filename,
//    preceded by a label "File name", and two buttons: a "Save" button and a "Cancel"
//    button. Clicking the "Save" button creates a file with the given name and writes
//    the currently shown HTML document to it, and closes the dialog screen. Clicking
//    the "Cancel" button simply closes the dialog screen.

    /**
     * Create the save to html file dialog screen
     */
    protected void create(){
        System.out.println("Creating save html screen");
        this.addGUIObject(new GUIString("Saving page to html file", 30, 30));
        this.addGUIObject(new GUIString("File name", 20, 90));
        this.fileName = new GUIInput(100, 90, 400, 30);
        this.addGUIObject(this.fileName);
        // 2 buttons
        GUIButton cnclBtn = new GUIButton("Cancel", 20, 180, 100, 30);
        GUIButton saveBtn = new GUIButton("Save", 150, 180, 100, 30);

        cnclBtn.setMouseEvent(this::onCancel);
        saveBtn.setMouseEvent(() -> SaveHtmlScreen.this.onSaveFile());
        this.addGUIObject(cnclBtn);
        this.addGUIObject(saveBtn);
        this.getGUIObjects().forEach(obj -> obj.setFontMetricsHandler(this));
    }

    /**
     * called when the cancel button was pressed
     */
    private void onCancel(){
        System.out.println("canceling the saving off html");
        this.returnToPreviousScreen();
    }

    /**
     * Called when the save to html button was pressed
     */
    private void onSaveFile() {
        System.out.println("Saving to file: " + this.fileName.getText());
        String file = this.fileName.getText();

        this.execute(new SaveHTMLOperation(file, this.htmlCode));

        this.returnToPreviousScreen();
    }

}

package gui.DialogScreen;

import commands.SaveHTMLOperation;
import gui.DefaultScreen.DefaultScreen;
import gui.Objects.GUIButton;
import gui.Objects.GUIInput;
import gui.Objects.GUIString;
import gui.Window;

import java.io.FileOutputStream;
import java.io.IOException;

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
    public SaveHtmlScreen(Window window, DefaultScreen prevScreen, String html){
        super(window, prevScreen);
        this.htmlCode = html;
        this.create();
    }

//    The user can save the current document as a .html file by pressing Ctrl+S.
//    This shows the Save As dialog screen, with a text input field for the filename,
//    preceded by a label “File name”, and two buttons: a “Save” button and a “Cancel”
//    button. Clicking the “Save” button creates a file with the given name and writes
//    the currently shown HTML document to it, and closes the dialog screen. Clicking
//    the “Cancel” button simply closes the dialog screen.

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
        saveBtn.setMouseEvent(() -> {
            try {
                SaveHtmlScreen.this.onSaveFile();
            } catch (IOException e){
                SaveHtmlScreen.this.onCancel();
            }
        });
        this.addGUIObject(cnclBtn);
        this.addGUIObject(saveBtn);
        this.getGUIObjects().forEach(obj -> obj.setFontMetricsHandler(this));
        // this.getGUIObjects().forEach(GUIObject::setEventHandlers);
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
     * @throws IOException  Throws an error when the saving to a file went wrong
     */
    private void onSaveFile() throws IOException {
        System.out.println("Saving: " + this.htmlCode + " to file: " + this.fileName.getText());
        String file = this.fileName.getText();

        this.execute(new SaveHTMLOperation(file, this.htmlCode));

        this.returnToPreviousScreen();
    }

}

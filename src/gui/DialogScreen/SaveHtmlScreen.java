package gui.DialogScreen;

import gui.Screen;
import gui.Window;

public class SaveHtmlScreen extends DialogScreen{
    public SaveHtmlScreen(Window window, Screen prevScreen){
        super(window, prevScreen);
        this.create();
    }

//    The user can save the current document as a .html file by pressing Ctrl+S.
//    This shows the Save As dialog screen, with a text input field for the filename,
//    preceded by a label “File name”, and two buttons: a “Save” button and a “Cancel”
//    button. Clicking the “Save” button creates a file with the given name and writes
//    the currently shown HTML document to it, and closes the dialog screen. Clicking
//    the “Cancel” button simply closes the dialog screen.

    protected void create(){
        System.out.println("Creating save html screen");
    }

}

package gui.DialogScreen;

import gui.Objects.GUIButton;
import gui.Objects.GUIInput;
import gui.Objects.GUIObject;
import gui.Objects.GUIString;
import gui.Screen;
import gui.Window;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class SaveHtmlScreen extends DialogScreen{

    private String htmlCode;
    private GUIInput fileName;

    public SaveHtmlScreen(Window window, Screen prevScreen, String html){
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

    protected void create(){
        System.out.println("Creating save html screen");
        this.addGUIObject(new GUIString("Saving page to html file", 30, 30));
        this.addGUIObject(new GUIString("File name", 20, 90));
        this.fileName = new GUIInput(100, 90, 400, 30);
        this.addGUIObject(this.fileName);
        // 2 buttons
        GUIButton cnclBtn = new GUIButton("Cancel", 20, 180, 100, 30);
        GUIButton saveBtn = new GUIButton("Save", 150, 180, 100, 30);
        cnclBtn.setMouseEvent((x1, y1, id, clickCount) -> {this.onCancel();});
        saveBtn.setMouseEvent((x1, y1, id, clickCount) -> {
            try {
                this.onSaveFile();
            } catch (IOException e){
                this.onCancel();
            }
        });
        this.addGUIObject(cnclBtn);
        this.addGUIObject(saveBtn);
        this.getGUIObjects().forEach(obj -> obj.setHandler(this));
        this.getGUIObjects().forEach(GUIObject::setEventHandlers);
    }

    private void onCancel(){
        System.out.println("canceling the saving off html");
        this.returnToPreviousScreen();
    }

    private void onSaveFile() throws IOException {
        System.out.println("Saving: " + this.htmlCode + " to file: " + this.fileName.getText());
        String file = this.fileName.getText();
        if(!file.endsWith(".html")){
            file += ".html";
        }

        FileOutputStream outputStream = new FileOutputStream(file);
        byte[] strToBytes = this.htmlCode.getBytes();
        outputStream.write(strToBytes);

        outputStream.close();

        this.returnToPreviousScreen();
    }

}

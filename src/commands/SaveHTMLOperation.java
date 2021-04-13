package commands;

import gui.DefaultScreen.DefaultScreen;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveHTMLOperation implements BookmarkOperation{

    private String name;
    private String htmlCode;

    public SaveHTMLOperation(String name, String htmlCode) {
        this.name = name;
        this.htmlCode = htmlCode;
    }

    @Override
    public void execute(DefaultScreen screen) {
        if(!this.name.endsWith(".html")){
            this.name += ".html";
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(this.name);
            byte[] strToBytes = this.htmlCode.getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
        }catch(Exception e){
            System.out.println("Something went wrong saving the file!");
        }
    }


}

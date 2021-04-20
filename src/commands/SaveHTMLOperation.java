package commands;

import gui.DefaultScreen.DefaultScreen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveHTMLOperation implements BrowsrOperation {

    private String name;        // The name of the new file
    private final String htmlCode;    // the html to put in the file

    /**
     * Create a save html operation
     * @param name      The name of the html file
     * @param htmlCode  the html code to put in the file
     */
    public SaveHTMLOperation(String name, String htmlCode) {
        this.name = name;
        this.htmlCode = htmlCode;
    }

    /**
     * Execute this operation
     * @param screen    The default screen needed for the operation
     */
    @Override
    public void execute(DefaultScreen screen) {
        if(!this.name.endsWith(".html")){
            this.name += ".html";
        }
        try {
            File myObj = new File(this.name);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter myWriter = new FileWriter(this.name);
            myWriter.write(this.htmlCode);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        }catch(IOException e){
            System.out.println("Something went wrong saving the file!");
        }
    }


}

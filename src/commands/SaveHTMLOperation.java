package commands;

import gui.DefaultScreen.DefaultScreen;

import java.io.FileOutputStream;

public class SaveHTMLOperation implements BrowsrOperation {

    private String name;        // The name of the new file
    private String htmlCode;    // the html to put in the file

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
            FileOutputStream outputStream = new FileOutputStream(this.name);
            byte[] strToBytes = this.htmlCode.getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
        }catch(Exception e){
            System.out.println("Something went wrong saving the file!");
        }
    }


}

package localDocuments;

import java.io.File;
import java.util.Scanner;

public abstract class Docs {

    private final static String welcomePath = "localDocuments/WelcomeDoc.html";
    private final static String errorPath = "localDocuments/ErrorDoc.html";

    /**
     * Used to get content of our local documents.
     *
     * @param path    The path to the file
     * @return        The content of the file in a String object.
     * @throws Exception when our ErrorPage can't be loaded.
     */
    public static String getPage(String path) {
        String content = "";
        try {
            File welcomePageHtmlFile = new File(path);
            Scanner myReader = new Scanner(welcomePageHtmlFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                content += data;
            }
            myReader.close();

        } catch (Exception e) {
            if (path != null && path.equals(errorPath)) {
                content = "Couldn't load ErrorPage...";
                e.printStackTrace();
            } else {
                content = getErrorPage();
            }
        }
        return content;
    }

    public static String getWelcomePage() {
        return getPage(welcomePath);
    }

    public static String getErrorPage() {
        return getPage(errorPath);
    }
}

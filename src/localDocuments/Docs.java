package localDocuments;

import java.io.File;
import java.util.Scanner;

public abstract class Docs {

    private final static String welcomePath = "/localDocuments/WelcomeDoc.html";
    private final static String errorPath = "/localDocuments/ErrorDoc.html";

    /**
     * Used to get content of our local documents.
     *
     * @param path    The path to the file
     * @return        The content of the file in a String object.
     */
    public static String getPage(String path) {
        StringBuilder content = new StringBuilder();
        try {
            Scanner myReader = new Scanner(Docs.class.getResourceAsStream(path));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                content.append(data);
            }
            myReader.close();

        } catch (Exception e) {
            if (path != null && path.equals(errorPath)) {
                content = new StringBuilder("Couldn't load ErrorPage...");
                e.printStackTrace();
            } else {
                content = new StringBuilder(getErrorPage());
            }
        }
        return content.toString();
    }

    public static String getWelcomePage() {
        return getPage(welcomePath);
    }

    public static String getErrorPage() {
        return getPage(errorPath);
    }
}

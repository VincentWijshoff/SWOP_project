package browsrhtml;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;

/**
 * Class for loading gui given URL
 */
public class HtmlLoader {

    private URL url;
    private String htmlCode;

    /**
     * called when new url has been entered in address bar
     *
     * @param url  The url typed in the address bar
     */
    public HtmlLoader(URL url) {
        try {
            this.url = new URL(url, "");
            this.htmlCode = loadHtml();
            loadPage();
        }catch(Exception e){
            System.out.println("exception parsing URL"); //TODO error document in DocumentArea
        }
    }

    /**
     *called when pressed a hyperlink
     *
     * @param url     The current url
     * @param href    The href (given in html code) -> example: <a href="a.html">
     */
    public HtmlLoader(URL url, String href) {
        try{
            this.url = new URL(url, href);
            this.htmlCode = loadHtml();
            loadPage();
        }catch(Exception e){
            System.out.println("exception parsing URL"); //TODO error document in DocumentArea
        }
    }

    /**
     * Method for loading html code from URL
     *
     * @return    The entire html code of the page
     *
     * inspiration from https://www.programcreek.com/java-api-examples/?class=java.net.URL&method=openStream
     */
    public String loadHtml() throws IOException {
        InputStream inputStream = url.openStream();
        byte[] buf = new byte[1024];
        StringBuilder sb = new StringBuilder();;
        while(-1 != inputStream.read(buf)) {
            sb.append(new String(buf));
        }
        return sb.toString();
    }

    /**
     * Load the page
     */
    public void loadPage(){
        HtmlLexer lexer = new HtmlLexer(new StringReader(htmlCode));
        //case analysis on different objects (a, table, tr, td)

    }



}

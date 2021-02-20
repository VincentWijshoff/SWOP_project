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
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();

        while(type != HtmlLexer.TokenType.END_OF_FILE){
            if(type == HtmlLexer.TokenType.OPEN_START_TAG){
                if(value.equals("a")){
                    HtmlA aTag = new HtmlA();
                    lexer = updateATag(lexer, aTag); //update lexer (after the a-tag)
                    aTag.createHyperlink();
                }
            }
            
            lexer.eatToken();
            type = lexer.getTokenType();
            value = lexer.getTokenValue();
        }

    }

    /**
     * This method will update the aTag object (given the html code)
     *
     * @param lexer     the lexer for the html code
     * @param aTag      the a-tag object
     * @return the updated lexer (so it doesn't do the code again)
     *
     * The a-tag is restricted to only a "href" attribute inside the tag, to upgrade: add to if-statement
     */
    private HtmlLexer updateATag(HtmlLexer lexer, HtmlA aTag){
        lexer.eatToken();
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();
        String currentIdentifier;
        boolean insideTag = true;
        while(type != HtmlLexer.TokenType.OPEN_END_TAG && !value.equals("a")){
            if(type == HtmlLexer.TokenType.IDENTIFIER){
                //Order: IDENTIFIER -> EQUALS -> QUOTED_STRING
                currentIdentifier = value;
                lexer.eatToken(); //This token is always EQUALS
                lexer.eatToken(); //This token is QUOTED_STRING

                if("href".equals(currentIdentifier)) {
                    aTag.setHref(lexer.getTokenValue());
                }else{
                    throw new Error(currentIdentifier + " is not supported inside a-tag");
                }
            }else if(type == HtmlLexer.TokenType.CLOSE_TAG && insideTag){ //insideTag not needed right now (only text between <a>)
                insideTag = false;
            }else if(type == HtmlLexer.TokenType.TEXT){
                if(!aTag.getText().equals(""))
                    aTag.setText(aTag.getText() + " " + value); //Already been a TEXT token -> add space
                else
                    aTag.setText(value);
            }

            lexer.eatToken();
            type = lexer.getTokenType();
            value = lexer.getTokenValue();
        }
        return lexer;
    }



}

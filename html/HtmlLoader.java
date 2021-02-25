package html;

import browsrhtml.HtmlLexer;
import gui.DocumentArea;
import html.Elements.*;
import localDocuments.Docs;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;

/**
 * Class for loading gui given URL
 *
 * usage: make new HtmlLoader object, then call loadPage on that object
 */
public class HtmlLoader {

    private URL url;
    private String htmlCode;
    private DocumentArea documentArea; //the documentArea object

    public void setDocumentArea(DocumentArea documentArea) {
        this.documentArea = documentArea;
    }

    /**
    only for tests
     */
    public void setHtmlCode(String htmlCode) {
        this.htmlCode = htmlCode;
    }


    /**
     * called when loading a local document
     *
     * @param htmlCode
     */
    public HtmlLoader(String htmlCode) {
        try {
            this.htmlCode = htmlCode;
        }catch(Exception e){
            this.htmlCode = Docs.getErrorPage();
        }
    }


    /**
     * called when new url has been entered in address bar
     *
     * @param url  The url typed in the address bar
     */
    public HtmlLoader(URL url) {
        try {
            this.url = new URL(url, "");
            this.htmlCode = loadHtml();
        }catch(Exception e){
            this.htmlCode = Docs.getErrorPage();
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
        }catch(Exception e){
            this.htmlCode = Docs.getErrorPage();
        }
    }

    /**
     * Method for loading html code from URL
     *
     * @return    The entire html code of the page
     *
     * inspiration from https://www.programcreek.com/java-api-examples/?class=java.net.URL&method=openStream
     */
    private String loadHtml() throws IOException {
        InputStream inputStream = url.openStream();
        byte[] buf = new byte[1024];
        StringBuilder sb = new StringBuilder();
        while(-1 != inputStream.read(buf)) {
            sb.append(new String(buf));
        }
        return sb.toString();
    }

    private boolean isTable(String s){
        return s.equals("Table") || s.equals("table");
    }

    /**
     * Load the page
     */
    public void loadPage(){
        HtmlLexer lexer = new HtmlLexer(new StringReader(htmlCode));
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();

        while(type != HtmlLexer.TokenType.END_OF_FILE){
            if(type == HtmlLexer.TokenType.OPEN_START_TAG){
                if(value.equals("a")){
                    Hyperlink aTag = new Hyperlink();
                    lexer.eatToken();
                    lexer = updateATag(lexer, aTag); //update lexer (after the a-tag)
                    documentArea.renderHTML(aTag);
                }else if(isTable(value)){
                    HtmlTable tableTag = new HtmlTable();
                    lexer.eatToken();
                    lexer = updateTableTag(lexer, tableTag);
                    documentArea.renderHTML(tableTag);
                }
            }
            lexer.eatToken();
            type = lexer.getTokenType();
            value = lexer.getTokenValue();
        }

    }

    private HtmlLexer updateTableTag(HtmlLexer lexer, HtmlTable tableTag) {
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();
        while(!(type == HtmlLexer.TokenType.OPEN_END_TAG && isTable(value))){
            if(type == HtmlLexer.TokenType.OPEN_START_TAG && value.equals("tr")){
                HtmlTableRow tr = tableTag.addRow();
                lexer.eatToken(); //otherwise if statement in updateTableRowTag is falsely true
                lexer = updateTableRowTag(lexer, tr);
            }else {
                lexer.eatToken();
            }
            type = lexer.getTokenType();
            value = lexer.getTokenValue();
        }
        return lexer;
    }

    /**
     * this method will update the tableRow tag object
     *
     * @param lexer     the lexer for the html code
     * @param tr   the tableRow object
     * @return the updated lexer
     */
    private HtmlLexer updateTableRowTag(HtmlLexer lexer, HtmlTableRow tr) {
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();
        while(!(type == HtmlLexer.TokenType.OPEN_START_TAG && (value.equals("tr"))) && !(isTable(value) && type == HtmlLexer.TokenType.OPEN_END_TAG)){ //start of a new tr element or end table
            if(type == HtmlLexer.TokenType.OPEN_START_TAG && value.equals("td")){
                HtmlTableCell td = tr.addData();
                lexer = updateTableDataTag(lexer, td);
            }else {
                lexer.eatToken();
            }
            type = lexer.getTokenType();
            value = lexer.getTokenValue();
        }
        return lexer;
    }

    /**
     * updates the table data object
     *
     * 3 possibilities:
     *      - the data object is a hyperlink (a tag)
     *      - the data object is a table object
     *      - the data object is a text object
     * @param lexer     the lexer of the html code
     * @param td   the table data object
     * @return the updated lexer
     */
    private HtmlLexer updateTableDataTag(HtmlLexer lexer, HtmlTableCell td) {
        lexer.eatToken();
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();

        if(type == HtmlLexer.TokenType.CLOSE_TAG){
            lexer.eatToken();
            type = lexer.getTokenType();
            value = lexer.getTokenValue();
        }

        if(type == HtmlLexer.TokenType.OPEN_START_TAG){
            if(value.equals("a")){ // td is an a object
                Hyperlink aTag = new Hyperlink();
                lexer = updateATag(lexer, aTag);
                aTag.createHyperlink(); //not sure if this is the right place to do this
                td.setData(aTag);
            }else if(isTable(value)){ //td is a table
                HtmlTable tableTag = new HtmlTable();
                lexer = updateTableTag(lexer, tableTag);
                td.setData(tableTag);
            }
        }else if(type == HtmlLexer.TokenType.TEXT){
            TextSpan text = new TextSpan();
            text.setText(value);
            lexer = updateText(lexer, text);
            td.setData(text);
        }
        return lexer;
    }

    /**
     * update the text object
     * @param lexer     the lexer of the html code
     * @param text      the text object
     * @return the updated lexer
     */
    private HtmlLexer updateText(HtmlLexer lexer, TextSpan text) {
        lexer.eatToken();
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();

        while(type == HtmlLexer.TokenType.TEXT){
            text.setText(text.getText() + " " + value);
            lexer.eatToken();
            type = lexer.getTokenType();
            value = lexer.getTokenValue();
        }
        return lexer;
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
    private HtmlLexer updateATag(HtmlLexer lexer, Hyperlink aTag){
        lexer.eatToken();
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();
        String currentIdentifier; //if there is an identifier in the tag
        boolean insideTag = true;
        while(!(type == HtmlLexer.TokenType.OPEN_END_TAG && value.equals("a"))){
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

package html;

import browsrhtml.HtmlLexer;
import gui.DefaultScreen.DocumentArea;
import gui.Objects.GUIButton;
import gui.Objects.GUIInput;
import gui.Objects.GUIObject;
import html.Elements.*;
import localDocuments.Docs;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Class for loading gui given URL
 *
 * usage: make new HtmlLoader object, then call loadPage on that object
 */
public class HtmlLoader {

    private URL url; //the URL of the page
    private String htmlCode; //the string format of the html code
    private DocumentArea documentArea; //the documentArea object
    private final ContentSpanVisitor contentSpanVisitor;

    /**
     * Setter of the documentArea
     */
    public void setDocumentArea(DocumentArea documentArea) {
        this.documentArea = documentArea;
    }


    /**
     * Create a new HtmlLoader object, initialized with the given html code (as string format)
     *
     * Called when loading a local document
     */
    public HtmlLoader(DocumentArea doc) {
        this.contentSpanVisitor = new GUIRenderer();
        setDocumentArea(doc);
    }

    /**
     * initialise the loader with the html code
     * @param htmlCode  the html code as a string
     */
    public void initialise(String htmlCode) {
        try {
            this.htmlCode = htmlCode;
        }catch(Exception e){
            this.htmlCode = Docs.getErrorPage();
        }
    }

    /**
     * initialise the loader with a url
     * @param url   the url that needs to be loaded in
     */
    public void initialise(URL url) {
        try {
            this.url = new URL(url, "");
            this.htmlCode = loadHtml();
        }catch(Exception e){
            this.htmlCode = Docs.getErrorPage();
        }
    }

    /**
     * Get the currently loaded html code
     * @return  the currently loaded html code as a string
     */
    public String getHtmlCode(){
        return this.htmlCode;
    }

    /**
     * Initialise the loader with a url and a href
     * @param url   the url
     * @param href  the href
     */
    public void initialise(URL url, String href) {
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
     * @return    The entire html code of the page as string format
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

    /**
     * Check if the string is equal to "table" (sometimes with capital T)
     */
    private boolean isTable(String s){
        return s.equals("Table") || s.equals("table");
    }

    /**
     * Load the page
     *
     * Basic idea:
     *      - Use the given HtmlLexer to scan the html code
     *      - As long as the next token isn't "END_OF_FILE", check if it is an open tag
     *          * if not, go to the next token
     *          * if true, check if the tag is <a> or <table> and create a new ContentSpan (resp. Hyperlink or HtmlTable)
     *            And update that object in the corresponding method
     *            Finally add the object to the list in documentArea
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
                    updateATag(lexer, aTag);//update lexer (after the a-tag)
                    documentArea.addGUIObjects(aTag.create(this.contentSpanVisitor));
                }else if(isTable(value)){
                    HtmlTable tableTag = new HtmlTable();
                    lexer.eatToken();
                    lexer = updateTableTag(lexer, tableTag);
                    documentArea.addGUIObjects(tableTag.create(this.contentSpanVisitor));
                }else if(value.equals("form")){
                    Form formTag = new Form();
                    lexer.eatToken();
                    updateFormTag(lexer, formTag);
                    ArrayList<GUIObject> lst = formTag.create(this.contentSpanVisitor);
                    ArrayList<GUIInput> inputs = new ArrayList<>();
                    ArrayList<GUIButton> buttons = new ArrayList<>();
                    lst.forEach(obj -> inputs.addAll(obj.getInputs()));
                    lst.forEach(obj -> buttons.addAll(obj.getButtons()));
                    buttons.stream().filter(button -> button.isSubmit).forEach(btn -> btn.setMouseEvent(
                            (x1, y1, id, clickCount) -> {
                                // this will fire if the submit button on the form was clicked
                                // form action
                                String action = formTag.getAction() + "?";
                                //every input name with its value
                                inputs.forEach(inp -> btn.addInput(inp.getFormOutput()));
                                String output = btn.getOutput().substring(0, btn.getOutput().length() - 1);
                                // finally we have the addition needed for the url
                                String finalized = action + output;
                                System.out.println(finalized);
                                this.documentArea.load(finalized);
                            }));
                    documentArea.addGUIObjects(lst);
                }
            }
            lexer.eatToken();
            type = lexer.getTokenType();
            value = lexer.getTokenValue();
        }

    }


    /**
     ##############################################################
     ##################### New in iteration 2 #####################
     ##############################################################
     */
    private void updateFormTag(HtmlLexer lexer, Form formTag){
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();
        while(!(type == HtmlLexer.TokenType.OPEN_END_TAG && value.equals("form"))){
            if(type == HtmlLexer.TokenType.IDENTIFIER && value.equals("action")){
                lexer.eatToken(); //this is EQUALS
                lexer.eatToken();
                value = lexer.getTokenValue();
                formTag.setAction(value);
            }else if(type == HtmlLexer.TokenType.OPEN_START_TAG && value.equals("a")){
                Hyperlink aTag = new Hyperlink();
                updateATag(lexer, aTag);
                formTag.setData(aTag);
            }else if(type == HtmlLexer.TokenType.OPEN_START_TAG && isTable(value)){
                HtmlTable table = new HtmlTable();
                updateTableTag(lexer, table);
                formTag.setData(table);
            }
            lexer.eatToken();
            type = lexer.getTokenType();
            value = lexer.getTokenValue();
        }
    }

    /**
     * This method will update the given HtmlTable object
     *
     * Basic idea:
     *      - As long as the </table> tag isn't the next token, use the lexer to get the next tag
     *      - If the next tag is <tr>, create a new HtmlTableRow and add it to the given HtmlTable object
     *      - Update this HtmlTableRow object in its corresponding method
     *
     * @param lexer     the lexer for the html code
     * @param tableTag  the given HtmlTable object
     * @return the updated lexer
     */
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
        lexer.eatToken();
        return lexer;
    }

    /**
     * This method will update the HtmlTableRow object
     *
     * Basic idea:
     *      - As long as the next tag isn't <tr> or </table>, use the lexer to get the next tag
     *      - If this next tag is equal to <td>, create a new HtmlTableCell and add it to the HtmlTableRow object
     *      - Update the new object in its corresponding method
     *
     * @param lexer     the lexer for the html code
     * @param tr        the tableRow object
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
     * This method will update the HtmlTableCell object
     *
     * Basic idea:
     *      - Get the next token of the lexer, if it is equal to ">", get the next one
     *      - If the next token is "<", check the corresponding tag ("a" or "table")
     *              * Create a new (corresponding) ContentSpan and update it and set it as the data of the
     *                HtmlTableCell object
     *      - Else the data is a text object, so create a new TextSpan object and update it and set it as the data
     *        of the HtmlTableCell object
     *
     * 3 possibilities:
     *      - the data object is a hyperlink (a tag)
     *      - the data object is a table object
     *      - the data object is a text object
     * @param lexer     the lexer of the html code
     * @param td        the table data object
     * @return the updated lexer
     */
    private HtmlLexer updateTableDataTag(HtmlLexer lexer, HtmlTableCell td) {
        lexer.eatToken();
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();

        if(type == HtmlLexer.TokenType.CLOSE_TAG){ //get the next token
            lexer.eatToken();
            type = lexer.getTokenType();
            value = lexer.getTokenValue();
        }

        if(type == HtmlLexer.TokenType.OPEN_START_TAG){
            if(value.equals("a")){ // td is an a object
                Hyperlink aTag = new Hyperlink();
                updateATag(lexer, aTag);
                td.setData(aTag);
            }else if(isTable(value)){ //td is a table
                HtmlTable tableTag = new HtmlTable();
                lexer = updateTableTag(lexer, tableTag);
                td.setData(tableTag);
            }else if(value.equals("input")){
                handleInputTag(lexer, td);
            }
        }else if(type == HtmlLexer.TokenType.TEXT){
            TextSpan text = new TextSpan();
            text.setText(value);
            updateText(lexer, text);
            td.setData(text);
        }
        return lexer;
    }

    /**
     ##############################################################
     ##################### New in iteration 2 #####################
     ##############################################################
     */
    private void handleInputTag(HtmlLexer lexer, HtmlTableCell td){
        lexer.eatToken();
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();

        while(!(type == HtmlLexer.TokenType.CLOSE_TAG)){
            if(type == HtmlLexer.TokenType.IDENTIFIER && value.equals("type")){
                lexer.eatToken(); //this is a EQUALS
                lexer.eatToken();
                value = lexer.getTokenValue();
                if(value.equals("submit")){
                    td.setData(new SubmitButton());
                }else if(value.equals("text")){
                    TextInputField inputField = new TextInputField();
                    updateTextInputField(lexer, inputField);
                    td.setData(inputField);
                }
            }
            lexer.eatToken();
            type = lexer.getTokenType();
            value = lexer.getTokenValue();
        }
    }

    /**
     ##############################################################
     ##################### New in iteration 2 #####################
     ##############################################################
     */
    private void updateTextInputField(HtmlLexer lexer, TextInputField inputField){
        lexer.eatToken();
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();

        if(type == HtmlLexer.TokenType.IDENTIFIER && value.equals("name")){
            lexer.eatToken(); //this is EQUALS
            lexer.eatToken();
            value = lexer.getTokenValue();
            inputField.setName(value);
        }else{
            System.out.println("there is something wrong in the html code");
        }

    }

    /**
     * update the text object
     * @param lexer     the lexer of the html code
     * @param text      the text object
     */
    private void updateText(HtmlLexer lexer, TextSpan text) {
        lexer.eatToken();
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();

        while(type == HtmlLexer.TokenType.TEXT){
            text.setText(text.getText() + " " + value);
            lexer.eatToken();
            type = lexer.getTokenType();
            value = lexer.getTokenValue();
        }
    }

    /**
     * This method will update the Hyperlink object (given the html code)
     *
     * Basic idea:
     *      - As long as the next token isn't </a>, use the lexer to get the next token
     *      - If the next token is an IDENTIFIER, get the value of the attribute (QUOTED_STRING)
     *        (IDENTIFIER -> EQUALS -> QUOTED_STRING)
     *          * update the Hyperlink object
     *      - If the next token is TEXT, update the Hyperlink object
     *
     * @param lexer     the lexer for the html code
     * @param aTag      the a-tag object
     */
    private void updateATag(HtmlLexer lexer, Hyperlink aTag){
        lexer.eatToken();
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();
        String currentIdentifier; //Checks if there is an identifier in the tag
        boolean insideTag = true; //Checks if its inside the <a> tag (e.g. <a ...)
        while(!(type == HtmlLexer.TokenType.OPEN_END_TAG && value.equals("a"))){
            if(type == HtmlLexer.TokenType.IDENTIFIER){
                //Order: IDENTIFIER -> EQUALS -> QUOTED_STRING
                currentIdentifier = value;
                lexer.eatToken(); //This token is always EQUALS
                lexer.eatToken(); //This token is QUOTED_STRING
                //The a-tag is restricted to only a "href" attribute inside the tag, to upgrade: add to if-statement
                if("href".equals(currentIdentifier)) {
                    aTag.setHref(lexer.getTokenValue());
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
    }

}

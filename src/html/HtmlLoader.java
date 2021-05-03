package html;

import browsrhtml.HtmlLexer;
import gui.DefaultScreen.DocumentArea;
import gui.Objects.GUIButton;
import gui.Objects.GUIInput;
import gui.Objects.GUIObject;
import html.Elements.*;
import localDocuments.Docs;

import java.io.*;
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
    private HtmlLexer lexer;

    /**
     * Create a new HtmlLoader object
     */
    public HtmlLoader(DocumentArea doc) {
        this.contentSpanVisitor = new GUIRenderer();
        setDocumentArea(doc);
    }

    /**
     * Setter of the documentArea
     */
    private void setDocumentArea(DocumentArea documentArea) {
        this.documentArea = documentArea;
    }

    /**
     * Initialise the loader with the html code
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
     * Initialise the loader with a url
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
     * Get the currently loaded html code
     * @return  the currently loaded html code as a string
     */
    public String getHtmlCode(){
        return this.htmlCode;
    }



    /**
     * Method for loading html code from URL
     * @return    The entire html code of the page as string format
     *
     * inspiration from https://www.programcreek.com/java-api-examples/?class=java.net.URL&method=openStream
     */
    private String loadHtml() throws IOException {

        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));

        String inputLine;
        StringBuilder result = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            result.append(inputLine);
        in.close();
        return result.toString();
    }

    /**
     * Load the page
     *
     * Basic idea:
     *      - Use the given HtmlLexer to scan the html code
     *      - As long as the next token isn't "END_OF_FILE", check if it is an open tag
     *          * if not, go to the next token
     *          * if true, check if the tag is <a>, <table> or <form> and go to the corresponding method
     */
    public void loadPage(){
        this.lexer = new HtmlLexer(new StringReader(htmlCode)); //update the lexer with the html of the to-loaded page
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();

        while(type != HtmlLexer.TokenType.END_OF_FILE){
            if(type == HtmlLexer.TokenType.OPEN_START_TAG){
                if(value.equals("a"))
                    handleA();
                else if(isTable(value))
                    handleTable();
                else if(value.equals("form"))
                    handleForm();
                else if(value.equals("iframe"))
                    handleIframe();
            }
            lexer.eatToken();
            type = lexer.getTokenType();
            value = lexer.getTokenValue();
        }
    }

    private void handleIframe() {
        Iframe iframe = new Iframe();
        lexer.eatToken();
        updateIframe(iframe);
        iframe.accept(this.contentSpanVisitor);
        documentArea.addGUIObjects(this.contentSpanVisitor.getObjects());
    }

    /**
     * Update the iframe object
     * @param iframe
     */
    private void updateIframe(Iframe iframe) {
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();
        while(!(type == HtmlLexer.TokenType.OPEN_END_TAG && value.equals("iframe"))){
            if(type == HtmlLexer.TokenType.IDENTIFIER){
                addProperty(iframe, value);
            }
            lexer.eatToken();
            type = lexer.getTokenType();
            value = lexer.getTokenValue();
        }
    }

    /**
     * Setter of a property of the iframe object
     * src, height or width
     */
    private void addProperty(Iframe iframe, String property){
        lexer.eatToken();//EQUALS
        lexer.eatToken();
        String value = lexer.getTokenValue();

        if(property.equals("src")){
            iframe.setSrc(value);
        }else if (property.equals("height")){
            iframe.setHeight(Integer.parseInt(value));
        }else if(property.equals("width")){
            iframe.setWidth(Integer.parseInt(value));
        }
    }

    /**
     * Handle the A-tag
     *
     * Basic idea:
     *      1. Create a new Hyperlink object and initialise it in the updateATag method
     *      2. Use the visitor to create the corresponding GUIObject
     *      3. Add this GUIObject to the GUIObjects list in documentArea
     */
    private void handleA(){
        Hyperlink aTag = new Hyperlink();
        lexer.eatToken();
        updateATag(aTag);
        aTag.accept(this.contentSpanVisitor);
        documentArea.addGUIObjects(this.contentSpanVisitor.getObjects());
    }

    /**
     * Handle the Table-tag
     *
     * Basic idea:
     *      1. Create a new HtmlTable object and initialise it in the updateTableTag method
     *      2. Use the visitor to create the corresponding list of GUIObjects
     *      3. Add this list of GUIObjects to the GUIObjects list in documentArea
     */
    private void handleTable(){
        HtmlTable tableTag = new HtmlTable();
        lexer.eatToken();
        updateTableTag(tableTag);
        tableTag.accept(this.contentSpanVisitor);
        documentArea.addGUIObjects(this.contentSpanVisitor.getObjects());
    }

    /**
     * Handle the Form-tag
     *
     * Basic idea:
     *      1. Create a new Form object and initialise it in the updateFormTag method
     *      2. Use the visitor to create the corresponding list of GUIObjects
     *      3. Initialize all submit buttons correctly (so they perform the correct action)
     *      4. Add this list of GUIObjects to the GUIObjects list in documentArea
     */
    private void handleForm(){
        Form formTag = new Form();
        lexer.eatToken();
        updateFormTag(formTag);
        //guiObjects is the list containing all GUIObjects to represent the Form object
        formTag.accept(this.contentSpanVisitor);
        ArrayList<GUIObject> guiObjects = this.contentSpanVisitor.getObjects();
        ArrayList<GUIInput> inputs = new ArrayList<>();
        ArrayList<GUIButton> buttons = new ArrayList<>();
        guiObjects.forEach(obj -> inputs.addAll(obj.getInputs()));   //get all GUIInput objects from guiObjects
        guiObjects.forEach(obj -> buttons.addAll(obj.getButtons())); //get all GUIButton objects from guiObjects
        buttons.stream().filter(button -> button.isSubmit).forEach(btn -> btn.setMouseEvent(
                () -> {
                    // this will fire if the submit button on the form was clicked
                    // form action
                    String action = formTag.getAction() + "?";
                    //every input name with its value
                    inputs.forEach(inp -> btn.addInput(inp.getFormOutput()));
                    String output = btn.getOutput().substring(0, btn.getOutput().length() - 1);
                    // finally we have the addition needed for the url
                    String finalized = action + output;
                    this.documentArea.load(finalized); //if this submit button has been clicked -> load this address
                }));
        documentArea.addGUIObjects(guiObjects);
    }

    /**
     * Check if the string is equal to "table" (sometimes with capital T)
     */
    private boolean isTable(String s){
        return s.equals("Table") || s.equals("table");
    }

    /**
     * This method will update the given Form object
     *
     * Basic idea:
     *      - As long as the </form> tag isn't the next token, use the lexer to get the next tag
     *      - Initialise the Form object with the correct action and contentSpan object (HtmlTable or Hyperlink)
     *
     * @param formTag  the given formTag object
     */
    private void updateFormTag(Form formTag){
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
                updateATag(aTag);
                formTag.setData(aTag);
            }else if(type == HtmlLexer.TokenType.OPEN_START_TAG && isTable(value)){
                HtmlTable table = new HtmlTable();
                updateTableTag(table);
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
     * @param tableTag  the given HtmlTable object
     */
    private void updateTableTag(HtmlTable tableTag) {
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();
        while(!(type == HtmlLexer.TokenType.OPEN_END_TAG && isTable(value))){
            if(type == HtmlLexer.TokenType.OPEN_START_TAG && value.equals("tr")){
                HtmlTableRow tr = tableTag.addRow();
                lexer.eatToken(); //otherwise if statement in updateTableRowTag is falsely true
                updateTableRowTag(tr);
            }else {
                lexer.eatToken();
            }
            type = lexer.getTokenType();
            value = lexer.getTokenValue();
        }
        lexer.eatToken();
    }

    /**
     * This method will update the HtmlTableRow object
     *
     * Basic idea:
     *      - As long as the next tag isn't <tr> or </table>, use the lexer to get the next tag
     *      - If this next tag is equal to <td>, create a new HtmlTableCell and add it to the HtmlTableRow object
     *      - Update the new object in its corresponding method
     *
     * @param tr    the tableRow object
     */
    private void updateTableRowTag(HtmlTableRow tr) {
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();
        while(!(type == HtmlLexer.TokenType.OPEN_START_TAG && (value.equals("tr"))) && !(isTable(value) && type == HtmlLexer.TokenType.OPEN_END_TAG)){ //start of a new tr element or end table
            if(type == HtmlLexer.TokenType.OPEN_START_TAG && value.equals("td")){
                HtmlTableCell td = tr.addData();
                updateTableDataTag(td);
            }else {
                lexer.eatToken();
            }
            type = lexer.getTokenType();
            value = lexer.getTokenValue();
        }
    }

    /**
     * This method will update the HtmlTableCell object
     *
     * Basic idea:
     *      - Get the next token of the lexer, if it is equal to ">", get the next one
     *      - If the next token is "<", check the corresponding tag ("a", "table" or "input")
     *              * Create a new (corresponding) ContentSpan and update it and set it as the data of the
     *                HtmlTableCell object
     *      - Else the data is a text object, so create a new TextSpan object and update it and set it as the data
     *        of the HtmlTableCell object
     *
     * 3 possibilities:
     *      - the data object is a Hyperlink (a tag)
     *      - the data object is a HtmlTable object
     *      - the data object is a TextSpan object
     *      - the data object is a TextInputField object
     * @param td        the table data object
     */
    private void updateTableDataTag(HtmlTableCell td) {
        lexer.eatToken();
        HtmlLexer.TokenType type = lexer.getTokenType();
        String value = lexer.getTokenValue();

        if(type == HtmlLexer.TokenType.CLOSE_TAG){ //get the next token
            lexer.eatToken();
            type = lexer.getTokenType();
            value = lexer.getTokenValue();
        }

        if(type == HtmlLexer.TokenType.OPEN_START_TAG){
            if(value.equals("a")){ // td is an a Hyperlink
                Hyperlink aTag = new Hyperlink();
                updateATag(aTag);
                td.setData(aTag);
            }else if(isTable(value)){ //td is a HtmlTable
                HtmlTable tableTag = new HtmlTable();
                updateTableTag(tableTag);
                td.setData(tableTag);
            }else if(value.equals("input")){ //td is a TextInputField
                handleInputTag(td);
            }
        }else if(type == HtmlLexer.TokenType.TEXT){
            TextSpan text = new TextSpan();
            text.setText(value);
            updateText(text);
            td.setData(text);
        }
    }

    /**
     * This method will handle a input tag
     *
     * Basic idea:
     *      - Get the next token of the lexer, if it is equal to ">", eat a token and return
     *      - else check if the type is an IDENTIFIER and the value is "type"
     *      - if so eat 2 tokens and check if the input is either:
     *              * "submit" -> create a SubmitButton
     *              * "text"   -> create a new TextInputField and update it (in updateTextInputField method)
     * @param td    the table data object (this input tag belongs to)
     */
    private void handleInputTag(HtmlTableCell td){
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
                    updateTextInputField(inputField);
                    td.setData(inputField);
                }
            }
            lexer.eatToken();
            type = lexer.getTokenType();
            value = lexer.getTokenValue();
        }
    }

    /**
     * This method will update the given TextInputField object
     *
     * Basic idea:
     *      - the next token should be an IDENTIFIER with value "name"
     *          -> update the name parameter of the object
     * @param inputField    the table data object (this input tag belongs to)
     */
    private void updateTextInputField(TextInputField inputField){
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
     * @param text  the text object
     */
    private void updateText(TextSpan text) {
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
     * @param aTag      the a-tag object
     */
    private void updateATag(Hyperlink aTag){
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

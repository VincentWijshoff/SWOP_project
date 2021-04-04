package html;

import gui.Objects.GUIObject;
import html.Elements.*;

import java.util.ArrayList;

/**
 * interface that can create all html elements
 */
public interface Creator {

    ArrayList<GUIObject> create(Form form);                     // create a from element

    ArrayList<GUIObject> create(HtmlTable table);               // create a table element

    ArrayList<GUIObject> create(HtmlTableCell tableCell);       // create a table cell element

    ArrayList<GUIObject> create(HtmlTableRow tableRow);         // create a html table row element

    ArrayList<GUIObject> create(Hyperlink hyperlink);           // create a hyperlink element

    ArrayList<GUIObject> create(SubmitButton submitButton);     // create a submit button element

    ArrayList<GUIObject> create(TextInputField inputField);     // create a text input field element

    ArrayList<GUIObject> create(TextSpan textSpan);             // create a test element

    ArrayList<GUIObject> create(ContentSpan contentSpan);       // create a content span element

}

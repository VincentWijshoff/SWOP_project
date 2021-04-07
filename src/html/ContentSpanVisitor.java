package html;

import gui.Objects.GUIObject;
import html.Elements.*;

import java.util.ArrayList;

/**
 * interface that can create all html elements
 */
public interface ContentSpanVisitor {

    ArrayList<GUIObject> createForm(Form form);                             // create a from element

    ArrayList<GUIObject> createHtmlTable(HtmlTable table);                  // create a table element

    ArrayList<GUIObject> createHtmlTableCell(HtmlTableCell tableCell);      // create a table cell element

    ArrayList<GUIObject> createHtmlTableRow(HtmlTableRow tableRow);         // create a html table row element

    ArrayList<GUIObject> createHyperlink(Hyperlink hyperlink);              // create a hyperlink element

    ArrayList<GUIObject> createSubmitButton(SubmitButton submitButton);     // create a submit button element

    ArrayList<GUIObject> createTextInputField(TextInputField inputField);   // create a text input field element

    ArrayList<GUIObject> createTextSpan(TextSpan textSpan);                 // create a test element

}

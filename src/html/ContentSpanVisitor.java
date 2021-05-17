package html;

import gui.Objects.GUIObject;
import html.Elements.*;

import java.util.ArrayList;

/**
 * interface that can create all html elements
 */
public interface ContentSpanVisitor {

    void visitForm(Form form);                             // create a from element

    void visitHtmlTable(HtmlTable table);                  // create a table element

    void visitHtmlTableCell(HtmlTableCell tableCell);      // create a table cell element

    void visitHtmlTableRow(HtmlTableRow tableRow);         // create a html table row element

    void visitHyperlink(Hyperlink hyperlink);              // create a hyperlink element

    void visitSubmitButton(SubmitButton submitButton);     // create a submit button element

    void visitTextInputField(TextInputField inputField);   // create a text input field element

    void visitTextSpan(TextSpan textSpan);                 // create a text element

    ArrayList<GUIObject> getObjects();
}

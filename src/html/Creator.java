package html;

import gui.Objects.GUIObject;
import html.Elements.*;

import java.util.ArrayList;

public interface Creator {

    ArrayList<GUIObject> create(Form form);

    ArrayList<GUIObject> create(HtmlTable table);

    ArrayList<GUIObject> create(HtmlTableCell tableCell);

    ArrayList<GUIObject> create(HtmlTableRow tableRow);

    ArrayList<GUIObject> create(Hyperlink hyperlink);

    ArrayList<GUIObject> create(SubmitButton submitButton);

    ArrayList<GUIObject> create(TextInputField inputField);

    ArrayList<GUIObject> create(TextSpan textSpan);

    ArrayList<GUIObject> create(ContentSpan contentSpan);

}

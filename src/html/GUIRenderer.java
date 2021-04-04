package html;

import gui.Objects.*;
import html.Elements.*;

import java.util.ArrayList;

/**
 * Create GUI objects depending on the html elements that they need to represent
 */
public class GUIRenderer implements Creator{


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    @Override
    public ArrayList<GUIObject> create(Form form) {
        return form.getData().create(this);
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    @Override
    public ArrayList<GUIObject> create(HtmlTable table) {
        ArrayList<GUIObject> objects = new ArrayList<>();

        ArrayList<ArrayList<GUIObject>> rows = new ArrayList<>();

        for (HtmlTableRow row: table.getTableRows()) {
            ArrayList<GUIObject> rows1 = row.create(this);
            rows.add(rows1);
        }

        GUITable table1 = new GUITable(rows);
        objects.add(table1);

        return objects;
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    @Override
    public ArrayList<GUIObject> create(HtmlTableCell tableCell) {
        return tableCell.getData().create(this);
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    @Override
    public ArrayList<GUIObject> create(HtmlTableRow tableRow) {
        ArrayList<GUIObject> objects = new ArrayList<>();
        ArrayList<HtmlTableCell> tableCells = tableRow.getTableData();
        for(HtmlTableCell cell : tableCells){
            objects.addAll(cell.create(this));
        }
        return objects;
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    @Override
    public ArrayList<GUIObject> create(Hyperlink hyperlink) {
        ArrayList<GUIObject> objects = new ArrayList<>();
        objects.add(new GUILink(hyperlink.getText(), hyperlink.getHref()));
        return objects;
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    @Override
    public ArrayList<GUIObject> create(SubmitButton submitButton) {
        ArrayList<GUIObject> objects = new ArrayList<>();
        GUIButton btn = new GUIButton("Submit");
        btn.setSubmit();
        btn.setMouseEvent((x1, y1, id, clickCount) -> {System.out.println("go to url");});
        objects.add(btn);
        return objects;
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    @Override
    public ArrayList<GUIObject> create(TextInputField inputField) {
        ArrayList<GUIObject> objects = new ArrayList<>();
        GUIInput inp = new GUIInput();
        inp.setName(inputField.getName());
        objects.add(inp);

        return objects;
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    @Override
    public ArrayList<GUIObject> create(TextSpan textSpan) {
        ArrayList<GUIObject> objects = new ArrayList<>();
        objects.add(new GUIString(textSpan.getText()));
        return objects;
    }

    /**
     * Create a content span GUIObject
     * @return null as a content span cannot be created as a GUI object by itself
     */
    @Override
    public ArrayList<GUIObject> create(ContentSpan contentSpan) {
        System.out.println("this should never fire");
        return null;
    }
}

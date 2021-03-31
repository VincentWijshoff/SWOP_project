package html;

import gui.*;
import html.Elements.*;

import java.util.ArrayList;

public class GUIRenderer implements Creator{


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    public ArrayList<GUIObject> create(Form form) {
        return form.getData().create(this);
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    public ArrayList<GUIObject> create(HtmlTable table) {
        ArrayList<GUIObject> objects = new ArrayList<GUIObject>();

        ArrayList<ArrayList<GUIObject>> rows = new ArrayList<>();
        for (HtmlTableRow row: table.getTableRows()) {
            rows.add(row.create(this));
        }
        objects.add(new GUITable(rows));
        return objects;
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    public ArrayList<GUIObject> create(HtmlTableCell tableCell) {
        return tableCell.getData().create(this);
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    public ArrayList<GUIObject> create(HtmlTableRow tableRow) {
        ArrayList<GUIObject> objects = new ArrayList<GUIObject>();
        ArrayList<HtmlTableCell> tableCells = tableRow.getTableData();
        for(HtmlTableCell cell : tableCells){
            objects.addAll(cell.create(this));
        }
        return objects;
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    public ArrayList<GUIObject> create(Hyperlink hyperlink) {
        ArrayList<GUIObject> objects = new ArrayList<GUIObject>();
        objects.add(new GUILink(hyperlink.getText(), hyperlink.getHref(), hyperlink.getAddress()));
        return objects;
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    public ArrayList<GUIObject> create(SubmitButton submitButton) {
        return new ArrayList<GUIObject>();
        //TODO: create GUIButton
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    public ArrayList<GUIObject> create(TextInputField inputField) {
        ArrayList<GUIObject> objects = new ArrayList<GUIObject>();
        //TODO: add real coordinates and height/width
        objects.add(new GUIInput(inputField.getName(), 50, 50, 100, 100));

        return objects;
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    public ArrayList<GUIObject> create(TextSpan textSpan) {
        ArrayList<GUIObject> objects = new ArrayList<GUIObject>();
        objects.add(new GUIString(textSpan.getText()));
        return objects;
    }

    public ArrayList<GUIObject> create(ContentSpan contentSpan) {
        System.out.println("this is stupid");
        return null;
    }
}

package html;

import gui.GUILink;
import gui.GUIObject;
import gui.GUIString;
import gui.GUITable;
import html.Elements.*;

import java.util.ArrayList;

public class GUIRenderer implements Creator{


    public ArrayList<GUIObject> create(Form form) {
        ArrayList<GUIObject> objects = new ArrayList<GUIObject>();

        objects = (form.getData().create(this));

        return objects;
    }


    public ArrayList<GUIObject> create(HtmlTable table) {
        ArrayList<GUIObject> objects = new ArrayList<GUIObject>();

        ArrayList<ArrayList<GUIObject>> rows = new ArrayList<>();
        for (HtmlTableRow row: table.getTableRows()) {
            rows.add(row.create(this));
        }
        objects.add(new GUITable(rows));
        return objects;
    }


    public ArrayList<GUIObject> create(HtmlTableCell tableCell) {
        ArrayList<GUIObject> objects = tableCell.getData().create(this);
        return objects;
    }


    public ArrayList<GUIObject> create(HtmlTableRow tableRow) {
        ArrayList<GUIObject> objects = new ArrayList<GUIObject>();

        ArrayList<HtmlTableCell> tableCells = tableRow.getTableData();
        for(HtmlTableCell cell : tableCells){
            objects.addAll(cell.create(this));
        }
        return objects;
    }


    public ArrayList<GUIObject> create(Hyperlink hyperlink) {
        ArrayList<GUIObject> objects = new ArrayList<GUIObject>();

        objects.add(new GUILink(hyperlink.getText(), hyperlink.getHref(), hyperlink.getAddress()));
        return objects;
    }


    public ArrayList<GUIObject> create(SubmitButton submitButton) {
        return new ArrayList<GUIObject>();
    }


    public ArrayList<GUIObject> create(TextInputField inputField) {
        ArrayList<GUIObject> objects = new ArrayList<GUIObject>();


        return objects;
    }


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

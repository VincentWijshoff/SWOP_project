package html.Elements;

import gui.Objects.GUIObject;
import html.Creator;

import java.util.ArrayList;

public class HtmlTable extends ContentSpan {

    //List with all row elements of that table
    ArrayList<HtmlTableRow> tableRows;

    /**
     * Create an empty HtmlTable object
     */
    public HtmlTable(){
        tableRows = new ArrayList<>();
    }

    /**
     * Create a HtmlTable object initialised with the given rows
     *
     * @param elements  the rows of the HtmlTable object
     */
    public HtmlTable(ArrayList<HtmlTableRow> elements) {
        this.tableRows = elements;
    }

    /**
     * Add a new (empty) row to the table
     */
    public HtmlTableRow addRow() {
        HtmlTableRow row = new HtmlTableRow(this);
        tableRows.add(row);
        return row;
    }

    public ArrayList<HtmlTableRow> getTableRows() {
        return tableRows;
    }

    @Override
    public ArrayList<GUIObject> create(Creator c){
        return c.create(this);
    }
}

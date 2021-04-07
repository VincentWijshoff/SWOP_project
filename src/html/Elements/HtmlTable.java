package html.Elements;

import gui.Objects.GUIObject;
import html.ContentSpanVisitor;

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

    /**
     * Get the table rows of this table
     * @return  a list of table rows
     */
    public ArrayList<HtmlTableRow> getTableRows() {
        return tableRows;
    }

    /**
     * Create the table
     * @param c The creator which will create the visible object
     * @return  A list of elements that make up this table
     */
    @Override
    public ArrayList<GUIObject> create(ContentSpanVisitor c){
        return c.createHtmlTable(this);
    }
}

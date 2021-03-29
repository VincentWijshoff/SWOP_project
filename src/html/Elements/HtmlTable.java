package html.Elements;

import gui.GUIObject;
import html.GUIRenderer;
import html.HtmlRenderer;

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
        row.setTable(this);
        tableRows.add(row);
        return row;
    }

    public ArrayList<HtmlTableRow> getTableRows() {
        return tableRows;
    }

    /**
     * Render the HtmlTable object (add it to the DocGUIObjects list of the DocumentArea)
     *
     * This also calls the render method on all its rows
     *
     * @param objects   the current DocGUIObjects of the DocumentArea
     * @return          the updated DocGUIObjects

    @Override
    public ArrayList<GUIObject> render(ArrayList<GUIObject> objects) {

        ArrayList<ArrayList<GUIObject>> rows = new ArrayList<>();

        for (HtmlTableRow row: tableRows) {
            rows.add(row.render(new ArrayList<>()));
        }

        HtmlRenderer.addGUITable(rows, objects);
        return objects;
    }
    */

    @Override
    public ArrayList<GUIObject> create() {
        return GUIRenderer.create(this);
    }
}

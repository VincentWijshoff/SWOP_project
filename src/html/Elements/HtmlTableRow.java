package html.Elements;

import gui.Objects.GUIObject;
import html.Creator;

import java.util.ArrayList;

public class HtmlTableRow extends ContentSpan {

    private ArrayList<HtmlTableCell> tableData; //List with all data elements of that row
    private HtmlTable table;                    //The table this row belongs to

    /**
     * Create a new HtmlTableRow object, and initialize it with the given table
     */
    public HtmlTableRow(HtmlTable table){
        this.table = table;
        tableData = new ArrayList<>();
    }

    /**
     * Add a data element to the list of all data elements of this row
     *
     * @return a new HtmlTableData object (so we can update this)
     */
    public HtmlTableCell addData(){
        HtmlTableCell td = new HtmlTableCell();
        tableData.add(td);
        return td;
    }

    /**
     * Setter of the table parameter
     */
    public void setTable(HtmlTable table){
        this.table = table;
    }

    /**
     * Getter of the table parameter
     */
    public HtmlTable getTable() {
        return this.table;
    }

    /**
     * Getter of the tableData parameter
     */
    public ArrayList<HtmlTableCell> getTableData() {
        return tableData;
    }

    @Override
    public ArrayList<GUIObject> create(Creator c){
        return c.create(this);
    }
}
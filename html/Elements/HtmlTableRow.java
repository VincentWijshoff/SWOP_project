package html.Elements;

import java.util.ArrayList;

public class HtmlTableRow extends ContentSpan {

    private ArrayList<HtmlTableCell> tableData; //List with all data elements of that row
    private HtmlTable table; //The table this row belongs to

    public HtmlTableRow(){
        tableData = new ArrayList<>();
    }

    /**
     * add a data element to the list of all data elements of this row
     *
     * @return a new HtmlTableData object (so we can update this)
     */
    public HtmlTableCell addData(){
        HtmlTableCell td = new HtmlTableCell();
        td.setRow(this);
        tableData.add(td);
        return td;
    }

    public void setTable(HtmlTable table){
        this.table = table;
    }

}
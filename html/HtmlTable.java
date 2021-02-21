package html;

import java.util.ArrayList;

public class HtmlTable extends HtmlElement{

    ArrayList<HtmlTableRow> tableRows; //List with all row elements of that table

    public HtmlTable(){
        tableRows = new ArrayList<>();
    }

    /**
     * add a new row to the table
     *
     * @return a new HtmlTableRow object (so we can update this object)
     */
    public HtmlTableRow addRow(){
        HtmlTableRow row = new HtmlTableRow();
        tableRows.add(row);
        return row;
    }



}

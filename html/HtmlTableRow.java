package html;

import java.util.ArrayList;

public class HtmlTableRow extends HtmlElement {

    ArrayList<HtmlTableData> tableData; //List with all data elements of that row

    public HtmlTableRow(){
        tableData = new ArrayList<>();
    }

    /**
     * add a data element to the list of all data elements of this row
     *
     * @return a new HtmlTableData object (so we can update this)
     */
    public HtmlTableData addData(){
        HtmlTableData td = new HtmlTableData();
        tableData.add(td);
        return td;
    }

}

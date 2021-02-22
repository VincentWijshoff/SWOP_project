package html;

import java.util.ArrayList;

public class HtmlTable extends HtmlElement{

    ArrayList<HtmlTableRow> tableRows; //List with all row elements of that table

    public HtmlTable(){
        tableRows = new ArrayList<>();
    }

    public HtmlTable(ArrayList<HtmlTableRow> elements) {
        this.tableRows = elements;
    }

    public ArrayList<HtmlTableRow> getTableRows() {
        return tableRows;
    }

    @Override
    public int getHeight() {
        int height = 0;
        for (HtmlElement e:tableRows) {
            height += e.getHeight();
        }
        return height;
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

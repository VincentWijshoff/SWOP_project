package html.Elements;

import java.util.ArrayList;

public class HtmlTable extends ContentSpan {

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
        for (ContentSpan e:tableRows) {
            height += e.getHeight();
        }
        return height;
    }

    public HtmlTableRow addRow() {
        HtmlTableRow row = new HtmlTableRow();
        row.setTable(this);
        tableRows.add(row);
        return row;
    }
}

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

    @Override
    public int getWidth() {
        int max = 0;
        for (HtmlTableRow row:tableRows) {
            if (row.getWidth() > max) max = row.getWidth();
        }
        return max;
    }

    public HtmlTableRow addRow() {
        HtmlTableRow row = new HtmlTableRow(this);
        row.setTable(this);
        tableRows.add(row);
        return row;
    }

    // width of column is the width of the widest cell in the column
    public int getColumnWidth(int index) {
        int max = 0;
        for (HtmlTableRow row: tableRows) {
            if (row.getTableData().get(index).getWidth() > max)
                max = row.getTableData().get(index).getWidth();
        }
        return max;
    }
}

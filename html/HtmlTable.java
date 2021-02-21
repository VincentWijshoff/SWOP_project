package html;

import java.util.ArrayList;

public class HtmlTable extends HtmlElement{

    ArrayList<HtmlElement> tableRows; //List with all row elements of that table

    public HtmlTable(){
        tableRows = new ArrayList<>();
    }

    public HtmlTable(ArrayList<HtmlElement> elements) {
        this.tableRows = elements;
    }

    public ArrayList<HtmlElement> getTableRows() {
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

    public HtmlElement addRow() {
        HtmlElement row = new HtmlElement();
        tableRows.add(row);
        return row;
    }
}

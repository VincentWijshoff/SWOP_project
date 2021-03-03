package html.Elements;

import gui.GUIObject;

import java.util.ArrayList;

public class HtmlTableRow extends ContentSpan {

    private ArrayList<HtmlTableCell> tableData; //List with all data elements of that row
    private HtmlTable table; //The table this row belongs to

    public HtmlTableRow(HtmlTable table){
        this.table = table;
        tableData = new ArrayList<>();
    }

    /**
     * add a data element to the list of all data elements of this row
     *
     * @return a new HtmlTableData object (so we can update this)
     */
    public HtmlTableCell addData(){
        HtmlTableCell td = new HtmlTableCell(this);
        td.setRow(this);
        tableData.add(td);
        return td;
    }

    public void setTable(HtmlTable table){
        this.table = table;
    }
    public HtmlTable getTable() {
        return this.table;
    }

    public ArrayList<HtmlTableCell> getTableData() {
        return tableData;
    }

    @Override
    public int getHeight() {
        int max = 0;
        for (HtmlTableCell cell:tableData) {
            if (cell.getHeight() > max) max = cell.getHeight();
        }
        return max;
    }

    @Override
    public ArrayList<GUIObject> render(int startX, int startY, ArrayList<GUIObject> objects) {
        ArrayList<HtmlTableCell> tableCells = getTableData();
        int currentX = startX;
        for(HtmlTableCell cell : tableCells){
            objects.addAll(cell.render(currentX, startY, objects));
            currentX += cell.getColumnWidth();
        }
        return objects;
    }
}
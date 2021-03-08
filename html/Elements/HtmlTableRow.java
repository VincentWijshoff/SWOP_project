package html.Elements;

import gui.GUIObject;

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
        HtmlTableCell td = new HtmlTableCell(this);
        td.setRow(this);
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

    /**
     * Returns the height of the object
     *
     * The height is equal to the maximum height of its HtmlTableCell objects
     */
    @Override
    public int getHeight() {
        int max = 0;
        for (HtmlTableCell cell:tableData) {
            if (cell.getHeight() > max) max = cell.getHeight();
        }
        return max;
    }

    /**
     * Render the HtmlTableRow object (add it to the DocGUIObjects list of the DocumentArea)
     *
     * Also call the render method on all its HtmlTableCells
     *
     * @param startX         x-coordinate
     * @param startY         y-coordinate
     * @param objects   the current DocGUIObjects of the DocumentArea
     * @return          the updated DocGUIObjects
     */
    @Override
    public ArrayList<GUIObject> render(int startX, int startY, ArrayList<GUIObject> objects) {
        ArrayList<HtmlTableCell> tableCells = getTableData();
        int currentX = startX;
        for(HtmlTableCell cell : tableCells){
            objects = cell.render(currentX, startY, objects);
            currentX += cell.getColumnWidth();
        }
        return objects;
    }
}
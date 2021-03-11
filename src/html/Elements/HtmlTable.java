package html.Elements;

import gui.GUIObject;

import java.util.ArrayList;

public class HtmlTable extends ContentSpan {

    //List with all row elements of that table
    ArrayList<HtmlTableRow> tableRows;

    /**
     * Create an empty HtmlTable object
     */
    public HtmlTable(){
        tableRows = new ArrayList<>();
    }

    /**
     * Create a HtmlTable object initialised with the given rows
     *
     * @param elements  the rows of the HtmlTable object
     */
    public HtmlTable(ArrayList<HtmlTableRow> elements) {
        this.tableRows = elements;
    }

    /**
     * Returns the tableRows of the HtmlTable object
     */
    public ArrayList<HtmlTableRow> getTableRows() {
        return tableRows;
    }

    /**
     * Returns the height of the object
     *
     * The height is equal to the sum of the height of its rows
     */
    @Override
    public int getHeight() {
        int height = 0;
        for (ContentSpan e:tableRows) {
            height += e.getHeight();
        }
        return height;
    }

    /**
     * Returns the width of the object
     *
     * The height is equal to the maximum of the width of its rows
     */
    @Override
    public int getWidth() {
        int max = 0;
        for (HtmlTableRow row:tableRows) {
            if (row.getWidth() > max) max = row.getWidth();
        }
        return max;
    }

    /**
     * Add a new (empty) row to the table
     */
    public HtmlTableRow addRow() {
        HtmlTableRow row = new HtmlTableRow(this);
        row.setTable(this);
        tableRows.add(row);
        return row;
    }

    /**
     * Returns the width of the column with given index
     *
     * The width of a column is the width of its widest cell
     */
    public int getColumnWidth(int index) {
        int max = 0;
        for (HtmlTableRow row: tableRows) {
            if (row.getTableData().size() <= index) continue; // if this row doesn't have a cell at this index
            if (row.getTableData().get(index).getWidth() > max)
                max = row.getTableData().get(index).getWidth();
        }
        return max;
    }


    /**
     * Render the HtmlTable object (add it to the DocGUIObjects list of the DocumentArea)
     *
     * This also calls the render method on all its rows
     *
     * @param startX         x-coordinate
     * @param startY         y-coordinate
     * @param objects   the current DocGUIObjects of the DocumentArea
     * @return          the updated DocGUIObjects
     */
    @Override
    public ArrayList<GUIObject> render(int startX, int startY, ArrayList<GUIObject> objects) {
        ArrayList<HtmlTableRow> tableRows = getTableRows();
        int currentY = startY;
        for (HtmlTableRow row: tableRows) {
            objects = row.render(startX, currentY, objects);
            currentY += row.getHeight();
        }
        return objects;
    }
}

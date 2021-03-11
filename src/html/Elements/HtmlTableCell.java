package html.Elements;

import gui.GUIObject;

import java.util.ArrayList;

public class HtmlTableCell extends ContentSpan {

    private ContentSpan data; //The data of this object
    private HtmlTableRow row; //The row this belongs to
    private final HtmlTable table; //The table this belongs to

    /**
     * Create a new HtmlTableCell object, and initialise it with the given row
     */
    public HtmlTableCell(HtmlTableRow row){
        this.row = row;
        this.table = row.getTable();
    }

    /**
     * Setter of the data parameter
     */
    public void setData(ContentSpan data) {
        this.data = data;
    }

    /**
     * Getter of data parameter
     */
    public ContentSpan getData(){
        return this.data;
    }

    /**
     * Setter of the row parameter
     */
    public void setRow(HtmlTableRow row){
        this.row = row;
    }

    /**
     * Returns the height of the object
     */
    @Override
    public int getHeight() {
        if (data == null) return 0;
        return data.getHeight();
    }

    /**
     * Returns the width of the object
     */
    @Override
    public int getWidth() {
        if (data == null) return 0;
        return data.getWidth();
    }

    /**
     * Returns the width of the column
     */
    public int getColumnWidth() {
        int index = row.getTableData().indexOf(this);
        return table.getColumnWidth(index);
    }

    /**
     * Render the HtmlTableCell object (add it to the DocGUIObjects list of the DocumentArea)
     *
     * @param startX         x-coordinate
     * @param startY         y-coordinate
     * @param objects   the current DocGUIObjects of the DocumentArea
     * @return          the updated DocGUIObjects
     */
    @Override
    public ArrayList<GUIObject> render(int startX, int startY, ArrayList<GUIObject> objects) {
        objects = getData().render(startX, startY, objects);
        return objects;
    }
}
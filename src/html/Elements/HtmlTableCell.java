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
     * Render the HtmlTableCell object (add it to the DocGUIObjects list of the DocumentArea)
     *
     * @param objects   the current DocGUIObjects of the DocumentArea
     * @return          the updated DocGUIObjects
     */
    @Override
    public ArrayList<GUIObject> render(ArrayList<GUIObject> objects) {
        objects = getData().render(objects);
        return objects;
    }
}
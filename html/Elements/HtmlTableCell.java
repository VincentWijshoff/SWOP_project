package html.Elements;

import gui.GUIObject;
import html.Elements.ContentSpan;

import java.util.ArrayList;

public class HtmlTableCell extends ContentSpan {

    private ContentSpan data;
    private HtmlTableRow row; //the row this belongs to
    private HtmlTable table; //table this belongs to

    public HtmlTableCell(HtmlTableRow row){
        this.row = row;
        this.table = row.getTable();
    }

    public void setData(ContentSpan data) {
        this.data = data;
    }

    /**
     * getter of data
     * @return this.data
     */
    public ContentSpan getData(){
        return this.data;
    }

    public void setRow(HtmlTableRow row){
        this.row = row;
    }

    @Override
    public int getHeight() {
        if (data == null) return 0;
        return data.getHeight();
    }

    @Override
    public int getWidth() {
        if (data == null) return 0;
        return data.getWidth();
    }

    //Get width of the column this cell is in
    public int getColumnWidth() {
        int index = row.getTableData().indexOf(this);
        return table.getColumnWidth(index);
    }

    @Override
    public ArrayList<GUIObject> render(int startX, int startY, ArrayList<GUIObject> objects) {
        objects.addAll(getData().render(startX, startY, objects));
        return objects;
    }
}
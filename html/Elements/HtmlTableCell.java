package html.Elements;

import html.Elements.ContentSpan;

public class HtmlTableCell extends ContentSpan {

    private ContentSpan data;
    private HtmlTableRow row; //the row this belongs to

    public HtmlTableCell(){
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

    public int getHeight() {
        return data.getHeight();
    }

}
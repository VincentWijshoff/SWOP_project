package html;

public class HtmlTableData extends HtmlElement{

    private HtmlElement data;

    public HtmlTableData(){
    }

    public void setData(HtmlElement data) {
        this.data = data;
    }

    /**
     * getter of data
     * @return this.data
     */
    public HtmlElement getData(){
        return this.data;
    }

}

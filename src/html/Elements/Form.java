package html.Elements;

public class Form extends ContentSpan{

    private String action;
    private ContentSpan data;

    public Form(){
        data = null; //in case of an empty form
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setData(ContentSpan data) {
        this.data = data;
    }
}

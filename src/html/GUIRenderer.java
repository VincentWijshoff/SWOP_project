package html;

import gui.Objects.*;
import html.Elements.*;

import java.util.ArrayList;

/**
 * Create GUI objects depending on the html elements that they need to represent
 */
public class GUIRenderer implements ContentSpanVisitor {


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    @Override
    public void visitForm(Form form) {
        form.getData().accept(this);
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    @Override
    public void visitHtmlTable(HtmlTable table) {
        ArrayList<GUIObject> objects = new ArrayList<>();

        ArrayList<ArrayList<GUIObject>> rows = new ArrayList<>();

        for (HtmlTableRow row: table.getTableRows()) {
            row.accept(this);
            rows.add(this.objects);
        }

        GUITable table1 = new GUITable(rows);
        objects.add(table1);

        this.objects = objects;
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    @Override
    public void visitHtmlTableCell(HtmlTableCell tableCell) {
        tableCell.getData().accept(this);
        //this.objects = tableCell.getData().accept(this);
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    @Override
    public void visitHtmlTableRow(HtmlTableRow tableRow) {
        ArrayList<GUIObject> objects = new ArrayList<>();
        ArrayList<HtmlTableCell> tableCells = tableRow.getTableData();
        for(HtmlTableCell cell : tableCells){
            cell.accept(this);
            objects.addAll(this.objects);
        }
        this.objects = objects;
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    @Override
    public void visitHyperlink(Hyperlink hyperlink) {
        ArrayList<GUIObject> objects = new ArrayList<>();
        objects.add(new GUILink(hyperlink.getText(), hyperlink.getHref()));
        this.objects = objects;
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    @Override
    public void visitSubmitButton(SubmitButton submitButton) {
        ArrayList<GUIObject> objects = new ArrayList<>();
        GUIButton btn = new GUIButton("Submit");
        btn.setSubmit();
        btn.setMouseEvent(() -> System.out.println("go to url"));
        objects.add(btn);
        this.objects = objects;
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    @Override
    public void visitTextInputField(TextInputField inputField) {
        ArrayList<GUIObject> objects = new ArrayList<>();
        GUIInput inp = new GUIInput();
        inp.setName(inputField.getName());
        objects.add(inp);

        this.objects = objects;
    }


    /**
     * Create the corresponding GUIObject and return (as list of GUIObjects)
     */
    @Override
    public void visitTextSpan(TextSpan textSpan) {
        ArrayList<GUIObject> objects = new ArrayList<>();
        objects.add(new GUIString(textSpan.getText()));
        this.objects = objects;
    }

    private ArrayList<GUIObject> objects = new ArrayList<>();

    public ArrayList<GUIObject> getObjects(){
        return this.objects;
    }

}

import gui.GUI;


public class Browsr {

    public static void main(String[] args) {
        GUI gui = new GUI("CoolBrowser");
        java.awt.EventQueue.invokeLater(gui::show);

        /* 

        //Demo om renderHTML() te testen
        ArrayList<HtmlElement> elements = new ArrayList<>();
        elements.add(new HtmlText("test tekst"));
        elements.add(new HtmlText("test tekst2"));

        //Geneste tabel in de 1ste tabel
        ArrayList<HtmlTableRow> nestedElements = new ArrayList<>();
        //nestedElements.add(new HtmlText("nested"));
        elements.add(new HtmlTable(nestedElements));

        elements.add(new HtmlText("test tekst3"));

        //gui.renderHTML(new HtmlTable(elements));

    */

    }
}

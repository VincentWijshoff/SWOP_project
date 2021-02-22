import gui.GUI;
import html.HtmlLoader;


public class Browsr {

    public static void main(String[] args) {
        GUI gui = new GUI("CoolBrowser");
        java.awt.EventQueue.invokeLater(gui::show);

        String input = """
                <Table>
                    <tr><td>Welcome Document
                    <tr>
                    <tr>
                    <tr><td>Type a valid URL in the address bar to navigate to that page.
                </Table>
                """;
        HtmlLoader loader = new HtmlLoader(input);
        loader.setDocumentArea(gui.getDocArea());
        loader.loadPage();


        /* 

        /* kapot
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

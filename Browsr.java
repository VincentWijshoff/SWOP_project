import gui.GUI;
import html.HtmlLoader;


public class Browsr {

    public static void main(String[] args) {
        GUI gui = new GUI("CoolBrowser");
        java.awt.EventQueue.invokeLater(gui::show);

        String input = """
                <Table>
                    <tr><td>Welcome Document
                    <tr><td> -
                    <tr><td> -
                    <tr><td>Type a valid URL in the address bar to navigate to that page.
                </Table>
                """;
        HtmlLoader loader = new HtmlLoader(input);
        loader.setDocumentArea(gui.getDocArea());
        loader.loadPage();

    }
}

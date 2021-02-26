import gui.GUI;
import html.HtmlLoader;
import localDocuments.Docs;

public class Browsr {

    public static void main(String[] args) {
        GUI gui = new GUI("CoolBrowser");
        java.awt.EventQueue.invokeLater(gui::show);

        HtmlLoader loader = new HtmlLoader(Docs.getWelcomePage());
        loader.setDocumentArea(gui.getDocArea());
        loader.loadPage();
    }
}

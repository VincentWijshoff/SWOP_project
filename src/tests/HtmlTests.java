package tests;

import gui.GUIObject;
import gui.Window;
import html.HtmlLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static tests.TestUtil.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HtmlTests {

    Window window;
    HtmlLoader loader;

    @BeforeAll
    public void setup() throws InvocationTargetException, InterruptedException {
        this.window = new Window("TestBrowser");
        this.loader = new HtmlLoader(window.getDocArea());
        java.awt.EventQueue.invokeAndWait(this.window::show);

    }

    @Test
    void html_1_aTag(){
        String testName = "html_1_aTag";
        String htmlCode = """
                <a href="a.html">TEXT</a>
                """;
        window.getDocArea().clearDocObjects();
        this.loader.initialise(htmlCode);
        loader.loadPage();

        assertTrue(testName, window.getDocArea().getDrawnGUIObjects().size() == 1);
        assertTrue(testName, containsGUILinkWith(0, 0, "TEXT", "", window.getDocArea().getDrawnGUIObjects()));
    }

    @Test
    void html_3_aTag(){
        String testName = "html_3_aTag";
        String htmlCode = """
                <table>
                    <tr> <td><a href="a.html">TEXT</a>
                    <tr> <td><a>TEXT</a>
                    <tr> <td><a href="b.html">Text</a>
                </table>
                """;
        window.getDocArea().clearDocObjects();
        this.loader.initialise(htmlCode);
        loader.loadPage();

        ArrayList<GUIObject> renderedObjects = window.getDocArea().getDrawnGUIObjects();
        assertTrue(testName, window.getDocArea().getDrawnGUIObjects().size() == 4);
        assertTrue(testName, containsGUILinkWith(0, 0, "TEXT", "a.html", renderedObjects));
        assertTrue(testName, containsGUILinkWith(0, 32, "Text", "b.html", renderedObjects));
        assertTrue(testName, containsGUILinkWith(0, 16, "TEXT", "", renderedObjects));

    }

    @Test
    void html_1_tableTag(){
        String testName = "html_1_tableTag";
        String htmlCode = """
                <table>
                    <tr><td>DATA
                    <tr><td>DATA<td>SECOND COLUMN
                </table>
                """;
        window.getDocArea().clearDocObjects();
        this.loader.initialise(htmlCode);
        loader.loadPage();

        ArrayList<GUIObject> renderedObjects = window.getDocArea().getDrawnGUIObjects();
        assertTrue(testName, window.getDocArea().getDrawnGUIObjects().size() == 4); //3 GUIStrings
        assertTrue(testName, containsGUIStringWith(0, 0, "DATA", renderedObjects));
        assertTrue(testName, containsGUIStringWith(35, 16, "SECOND COLUMN", renderedObjects));
        assertTrue(testName, containsGUIStringWith(0, 16, "DATA", renderedObjects));
    }

    @Test
    void html_2_tableTag(){
        String testName = "html_2_tableTag";
        String htmlCode = """
				<table>
				  <tr><td>HTML elements partially supported by Browsr:
				  <tr><td>
				    <table>
				      <tr><td><a href="a.html">a</a><td>Hyperlink anchors
				      <tr><td><a href="table.html">table</a><td>Tables
				      <tr><td><a href="tr.html">tr</a><td>Table rows
				      <tr><td><a href="td.html">td</a><td>Table cells containing table data
				    </table>
				</table>
				""";
        window.getDocArea().clearDocObjects();
        this.loader.initialise(htmlCode);
        loader.loadPage();

        ArrayList<GUIObject> renderedObjects = window.getDocArea().getDrawnGUIObjects();
        assertTrue(testName, window.getDocArea().getDrawnGUIObjects().size() == 11); //9 GUIString
        assertTrue(testName, containsGUIStringWith(32, 32, "Tables", renderedObjects));
        assertTrue(testName, containsGUILinkWith(0, 16, "a", "a.html", renderedObjects));
        assertTrue(testName, containsGUILinkWith(0, 32, "table", "table.html", renderedObjects));
        assertTrue(testName, containsGUIStringWith(32, 64, "Table cells containing table data", renderedObjects));
        assertTrue(testName, containsGUIStringWith(32, 16, "Hyperlink anchors", renderedObjects));
        assertTrue(testName, containsGUILinkWith(0, 64, "td", "td.html", renderedObjects));
        assertTrue(testName, containsGUIStringWith(32, 48, "Table rows", renderedObjects));
        assertTrue(testName, containsGUIStringWith(0, 0, "HTML elements partially supported by Browsr:", renderedObjects));
        assertTrue(testName, containsGUILinkWith(0, 48, "tr", "tr.html", renderedObjects));
    }

    /**
     * Iteration 2
     */
    @Test
    void html_homePage_iteration_2(){
        String testName = "html_homePage_iteration_2";
        String htmlCode = """
                <form action="browsrformactiontest.php">
                <table>
                    <tr><td>List words from the Woordenlijst Nederlandse Taal
                    <tr><td>
                    <table>
                        <tr>
                        <td>Starts with:
                        <td><input type="text" name="starts_with">
                        <tr>
                        <td>Max. results:
                        <td><input type="text" name="max_nb_results">
                    </table>
                    <tr><td><input type="submit">
                </table>
                </form>
                """;
        window.getDocArea().clearDocObjects();
        this.loader.initialise(htmlCode);
        loader.loadPage();


    }
}


package tests;

import gui.GUIObject;
import gui.GUITable;
import gui.Window;
import html.HtmlLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static tests.TestUtil.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HtmlTests {

    Window window;
    HtmlLoader loader;
    FontMetrics fm;

    @BeforeAll
    public void setup() throws InvocationTargetException, InterruptedException {
        this.window = new Window("TestBrowser");
        this.loader = new HtmlLoader(window.getDocArea());
        java.awt.EventQueue.invokeAndWait(this.window::show);
        fm = window.getFontMetrics();

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
        assertTrue(testName, containsGUILinkWith(0, 2*fm.getHeight(), "Text", "b.html", renderedObjects));
        assertTrue(testName, containsGUILinkWith(0, fm.getHeight(), "TEXT", "", renderedObjects));

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
        assertTrue(testName, containsGUIStringWith(fm.stringWidth("DATA") + GUITable.xMargin, fm.getHeight(), "SECOND COLUMN", renderedObjects));
        assertTrue(testName, containsGUIStringWith(0, fm.getHeight(), "DATA", renderedObjects));
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
        assertTrue("UC_4.j", containsGUIStringWith(0, 0, "HTML elements partially supported by Browsr:", renderedObjects));

        assertTrue("UC_4.d", containsGUILinkWith(0, fm.getHeight(), "a", "a.html", renderedObjects));
        assertTrue("UC_4.g", containsGUIStringWith(fm.stringWidth("table") + GUITable.xMargin, fm.getHeight(), "Hyperlink anchors", renderedObjects));

        assertTrue("UC_4.e", containsGUILinkWith(0, 2*fm.getHeight(), "table", "table.html", renderedObjects));
        assertTrue("UC_4.c", containsGUIStringWith(fm.stringWidth("table") + GUITable.xMargin, 2*fm.getHeight(), "Tables", renderedObjects));

        assertTrue("UC_4.h", containsGUILinkWith(0, 3*fm.getHeight(), "tr", "tr.html", renderedObjects));
        assertTrue("UC_4.i", containsGUIStringWith(fm.stringWidth("table") + GUITable.xMargin, 3*fm.getHeight(), "Table rows", renderedObjects));

        assertTrue("UC_4.h", containsGUILinkWith(0, 4*fm.getHeight(), "td", "td.html", renderedObjects));
        assertTrue("UC_4.f", containsGUIStringWith(fm.stringWidth("table") + GUITable.xMargin, 4*fm.getHeight(), "Table cells containing table data", renderedObjects));
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


package tests;

import gui.DefaultScreen;
import gui.DocumentArea;
import gui.Objects.GUIObject;
import gui.Objects.GUITable;
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
    DefaultScreen screen;
    HtmlLoader loader;
    FontMetrics fm;

    @BeforeAll
    public void setup() throws InvocationTargetException, InterruptedException {
        this.window = new Window("TestBrowser");
        this.screen = new DefaultScreen(window);
        this.loader = new HtmlLoader(screen.getDocArea());
        java.awt.EventQueue.invokeAndWait(this.window::show);
        fm = window.getFontMetrics();

    }

    @Test
    void html_1_aTag(){
        String testName = "html_1_aTag";
        String htmlCode = """
                <a href="a.html">TEXT</a>
                """;
        screen.getDocArea().clearDocObjects();
        this.loader.initialise(htmlCode);
        loader.loadPage();
        DocumentArea docarea = screen.getDocArea();

        assertTrue(testName, screen.getDocArea().getDrawnGUIObjects().size() == 1);
        assertTrue(testName, containsGUILinkWith(docarea.xOffset, docarea.getRelativeYPos(), "TEXT", "", screen.getDocArea().getDrawnGUIObjects()));
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
        screen.getDocArea().clearDocObjects();
        this.loader.initialise(htmlCode);
        loader.loadPage();
        DocumentArea docarea = screen.getDocArea();

        ArrayList<GUIObject> renderedObjects = screen.getDocArea().getDrawnGUIObjects();
        assertTrue(testName, screen.getDocArea().getDrawnGUIObjects().size() == 4);
        assertTrue(testName, containsGUILinkWith(docarea.xOffset, docarea.getRelativeYPos(), "TEXT", "a.html", renderedObjects));
        assertTrue(testName, containsGUILinkWith(docarea.xOffset, docarea.getRelativeYPos() + 2*fm.getHeight(), "Text", "b.html", renderedObjects));
        assertTrue(testName, containsGUILinkWith(docarea.xOffset, docarea.getRelativeYPos() + fm.getHeight(), "TEXT", "", renderedObjects));

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
        screen.getDocArea().clearDocObjects();
        this.loader.initialise(htmlCode);
        loader.loadPage();
        DocumentArea docarea = screen.getDocArea();

        ArrayList<GUIObject> renderedObjects = screen.getDocArea().getDrawnGUIObjects();
        assertTrue(testName, screen.getDocArea().getDrawnGUIObjects().size() == 4); //3 GUIStrings
        assertTrue(testName, containsGUIStringWith(docarea.xOffset, docarea.getRelativeYPos(), "DATA", renderedObjects));
        assertTrue(testName, containsGUIStringWith(docarea.xOffset + fm.stringWidth("DATA") + GUITable.xMargin, docarea.getRelativeYPos() + fm.getHeight(), "SECOND COLUMN", renderedObjects));
        assertTrue(testName, containsGUIStringWith(docarea.xOffset, docarea.getRelativeYPos() + fm.getHeight(), "DATA", renderedObjects));
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
        screen.getDocArea().clearDocObjects();
        this.loader.initialise(htmlCode);
        loader.loadPage();
        DocumentArea docarea = screen.getDocArea();

        ArrayList<GUIObject> renderedObjects = screen.getDocArea().getDrawnGUIObjects();
        assertTrue(testName, screen.getDocArea().getDrawnGUIObjects().size() == 11); //9 GUIString
        assertTrue("UC_4.j", containsGUIStringWith(docarea.xOffset, docarea.getRelativeYPos(), "HTML elements partially supported by Browsr:", renderedObjects));

        assertTrue("UC_4.d", containsGUILinkWith(docarea.xOffset, docarea.getRelativeYPos() + fm.getHeight(), "a", "a.html", renderedObjects));
        assertTrue("UC_4.g", containsGUIStringWith(docarea.xOffset + fm.stringWidth("table") + GUITable.xMargin, docarea.getRelativeYPos() + fm.getHeight(), "Hyperlink anchors", renderedObjects));

        assertTrue("UC_4.e", containsGUILinkWith(docarea.xOffset, docarea.getRelativeYPos() + 2*fm.getHeight(), "table", "table.html", renderedObjects));
        assertTrue("UC_4.c", containsGUIStringWith(docarea.xOffset + fm.stringWidth("table") + GUITable.xMargin, docarea.getRelativeYPos() + 2*fm.getHeight(), "Tables", renderedObjects));

        assertTrue("UC_4.h", containsGUILinkWith(docarea.xOffset, docarea.getRelativeYPos() + 3*fm.getHeight(), "tr", "tr.html", renderedObjects));
        assertTrue("UC_4.i", containsGUIStringWith(docarea.xOffset + fm.stringWidth("table") + GUITable.xMargin, docarea.getRelativeYPos() + 3*fm.getHeight(), "Table rows", renderedObjects));

        assertTrue("UC_4.h", containsGUILinkWith(docarea.xOffset,  docarea.getRelativeYPos()  + 4*fm.getHeight(), "td", "td.html", renderedObjects));
        assertTrue("UC_4.f", containsGUIStringWith(docarea.xOffset + fm.stringWidth("table") + GUITable.xMargin, docarea.getRelativeYPos() + 4*fm.getHeight(), "Table cells containing table data", renderedObjects));
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
        screen.getDocArea().clearDocObjects();
        this.loader.initialise(htmlCode);
        loader.loadPage();


    }
}


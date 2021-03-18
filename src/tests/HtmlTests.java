package tests;

import gui.GUIObject;
import gui.Window;
import html.Elements.*;
import html.HtmlLoader;
import org.junit.jupiter.api.Test;
import java.util.Set;

import static tests.TestUtil.*;

public class HtmlTests {

    Window window = new Window("TestBrowser");

    @Test
    void html_1_aTag(){
        String testName = "html_1_aTag";
        String htmlCode = """
                <a href="a.html">TEXT</a>
                """;
        window.getDocArea().clearDocObjects();
        HtmlLoader loader = new HtmlLoader(htmlCode);
        loader.setDocumentArea(window.getDocArea());
        loader.loadPage();

        assertTrue(testName, window.getDocArea().getDrawnGUIObjects().size() == 1);
        assertTrue(testName, containsGUILinkWith(10, 66, "TEXT", "", window.getDocArea().getDrawnGUIObjects()));
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
        HtmlLoader loader = new HtmlLoader(htmlCode);
        loader.setDocumentArea(window.getDocArea());
        loader.loadPage();

        Set<GUIObject> renderedObjects = window.getDocArea().getDrawnGUIObjects();
        assertTrue(testName, window.getDocArea().getDrawnGUIObjects().size() == 3);
        assertTrue(testName, containsGUILinkWith(10, 66, "TEXT", "a.html", renderedObjects));
        assertTrue(testName, containsGUILinkWith(10, 98, "Text", "b.html", renderedObjects));
        assertTrue(testName, containsGUILinkWith(10, 82, "TEXT", "", renderedObjects));

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
        HtmlLoader loader = new HtmlLoader(htmlCode);
        loader.setDocumentArea(window.getDocArea());
        loader.loadPage();

        Set<GUIObject> renderedObjects = window.getDocArea().getDrawnGUIObjects();
        assertTrue(testName, window.getDocArea().getDrawnGUIObjects().size() == 3); //3 GUIStrings
        assertTrue(testName, containsGUIStringWith(10, 82, "DATA", renderedObjects));
        assertTrue(testName, containsGUIStringWith(74, 82, "SECOND COLUMN", renderedObjects));
        assertTrue(testName, containsGUIStringWith(10, 66, "DATA", renderedObjects));
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
        HtmlLoader loader = new HtmlLoader(htmlCode);
        loader.setDocumentArea(window.getDocArea());
        loader.loadPage();

        Set<GUIObject> renderedObjects = window.getDocArea().getDrawnGUIObjects();
        assertTrue(testName, window.getDocArea().getDrawnGUIObjects().size() == 9); //9 GUIString
        assertTrue(testName, containsGUIStringWith(90, 98, "Tables", renderedObjects));
        assertTrue(testName, containsGUILinkWith(10, 82, "a", "a.html", renderedObjects));
        assertTrue(testName, containsGUILinkWith(10, 98, "table", "table.html", renderedObjects));
        assertTrue(testName, containsGUIStringWith(90, 130, "Table cells containing table data", renderedObjects));
        assertTrue(testName, containsGUIStringWith(90, 82, "Hyperlink anchors", renderedObjects));
        assertTrue(testName, containsGUILinkWith(10, 130, "td", "td.html", renderedObjects));
        assertTrue(testName, containsGUIStringWith(90, 114, "Table rows", renderedObjects));
        assertTrue(testName, containsGUIStringWith(10, 66, "HTML elements partially supported by Browsr:", renderedObjects));
        assertTrue(testName, containsGUILinkWith(10, 114, "tr", "tr.html", renderedObjects));
    }

    @Test
    void getcolumnwidth_1() {
        String testname = "getcolumnwidth_1";

        HtmlTable table = new HtmlTable();
        HtmlTableRow row1 = table.addRow();
        HtmlTableRow row2 = table.addRow();
        HtmlTableCell cell1a = row1.addData();
        HtmlTableCell cell2 = row2.addData();
        HtmlTableCell cell1b = row1.addData();
        cell1a.setData(new TextSpan("JAJAJAJAJA")); // 10 chars
        cell2.setData(new TextSpan("JAJA")); // 4 chars
        cell1b.setData(new TextSpan("JAJAJA")); // 6 chars

        assertTrue(testname, table.getColumnWidth(0) == 16*10); // 10 chars * 16 lengte per char
        assertTrue(testname, table.getColumnWidth(1) == 16*6); // 6 chars * 16 lengte per char

    }
}

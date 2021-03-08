package Tests;

import browsrhtml.HtmlLexer;
import gui.GUIObject;
import gui.Window;
import html.Elements.*;
import html.HtmlLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HtmlTests {

    Window window = new Window("TestBrowser");

    void fail(String testName) { throw new RuntimeException(testName + " failed."); }

    void assertTrue(String testName, boolean b) {
        if (!b) fail(testName);
    }
    void assertFalse(String testName, boolean b) {
        if (b) fail(testName);
    }

    @Test
    void html_1_aTag(){
        String testName = "html_1_aTag";
        String htmlCode = """
                <a href="a.html">TEXT</a>
                """;
        HtmlLoader loader = new HtmlLoader(htmlCode);
        loader.setDocumentArea(window.getDocArea());
        loader.loadPage();
        assertTrue(testName, window.getDocArea().DocGUIObjects.size() == 1);
    }

    @Test
    void html_3_aTag(){
        String testName = "html_3_aTag";
        String htmlCode = """
                <a href="a.html">TEXT</a>
                <a>TEXT</a>
                <a href="b.html">Text</a>
                """;
        HtmlLoader loader = new HtmlLoader(htmlCode);
        loader.setDocumentArea(window.getDocArea());
        loader.loadPage();
        assertTrue(testName, window.getDocArea().DocGUIObjects.size() == 3);
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
        HtmlLoader loader = new HtmlLoader(htmlCode);
        loader.setDocumentArea(window.getDocArea());
        loader.loadPage();
        assertTrue(testName, window.getDocArea().DocGUIObjects.size() == 3); //3 GUIStrings
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
        HtmlLoader loader = new HtmlLoader(htmlCode);
        loader.setDocumentArea(window.getDocArea());
        loader.loadPage();
        assertTrue(testName, window.getDocArea().DocGUIObjects.size() == 9); //9 GUIString
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

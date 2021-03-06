package html;

import browsrhtml.HtmlLexer;
import gui.Window;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

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
        assertTrue(testName, window.getDocArea().DocGUIObjects.size() == 3);
    }


    @Test
    void test() throws IOException {
        String input = """
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
        HtmlLexer lexer = new HtmlLexer(new StringReader(input));
        while(lexer.getTokenType() != HtmlLexer.TokenType.END_OF_FILE){
            System.out.println(lexer.getTokenType() + "\n" + lexer.getTokenValue() + "\n");
            lexer.eatToken();
        }


    }



}

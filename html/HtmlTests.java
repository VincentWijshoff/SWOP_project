package html;

import browsrhtml.HtmlLexer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

public class HtmlTests {

    void fail(String testName) { throw new RuntimeException(testName + " failed."); }

    void assertTrue(String testName, boolean b) {
        if (!b) fail(testName);
    }
    void assertFalse(String testName, boolean b) {
        if (b) fail(testName);
    }

    /**
     * set debug at aTag.createHyperlink in loadPage()
     *
     * There is not yet a way to create a real test of this
     */
    @Test
    void htmlATag() throws MalformedURLException {
        String testName = "htmlATag";
        String htmlCode = """
                <a href="a.html">TEXT</a>
                """;
        URL url = new URL("https://people.cs.kuleuven.be/~bart.jacobs/index.html");
        HtmlLoader loader = new HtmlLoader(url, "browsrtest.html");
        loader.setHtmlCode(htmlCode);
        loader.loadPage();
    }

    /**
     * Not a test, just to see what it returns
     */
    @Test
    void testing() throws IOException {
        URL url = new URL(new URL("https://people.cs.kuleuven.be/~bart.jacobs/index.html"), "browsrtest.html");
        InputStream inputStream = url.openStream();
        byte[] buf = new byte[1024];
        StringBuilder sb = new StringBuilder();;
        while(-1 != inputStream.read(buf)) {
            sb.append(new String(buf));
        }
        System.out.println(sb.toString());

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

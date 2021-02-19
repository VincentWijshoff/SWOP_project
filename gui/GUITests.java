package gui;

import browsrhtml.BrowsrDocumentValidator;
import browsrhtml.HtmlLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;


class GUITests {

	void fail(String testName) { throw new RuntimeException(testName + " failed."); }

	void assertTrue(String testName, boolean b) {
		if (!b) fail(testName);
	}
	void assertFalse(String testName, boolean b) {
		if (b) fail(testName);
	}

	@Test
	void testRectangleBounds() throws  RuntimeException {
		final String testName = "RectangleBounds";

		GUIRectangle rectangle = new GUIRectangle(10, 10, 100, 100);

		assertTrue(testName, rectangle.isInRectangle(50, 50));
		assertTrue(testName, rectangle.isInRectangle(10, 10));
		assertTrue(testName, rectangle.isInRectangle(100, 100));

		assertFalse(testName, rectangle.isInRectangle(150, 150));
		assertFalse(testName, rectangle.isInRectangle(100, 150));
		assertFalse(testName, rectangle.isInRectangle(9, 9));
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

}

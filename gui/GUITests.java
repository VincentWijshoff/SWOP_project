package gui;

import browsrhtml.BrowsrDocumentValidator;
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



	//load html code from url: (copy paste from https://www.programcreek.com/java-api-examples/?class=java.net.URL&method=openStream)
	/*
	URL url = new URL("https://...")
	InputStream is = url.openStream()
	byte[] buf = new byte[1024];
	StringBuffer sb = new StringBuffer();;
		while(-1 != inputStream.read(buf)) {
			sb.append(new String(buf));
		}
	 */
	//sb contains entire html code
}

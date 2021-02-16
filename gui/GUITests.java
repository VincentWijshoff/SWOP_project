package gui;

import org.junit.jupiter.api.Test;


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
}

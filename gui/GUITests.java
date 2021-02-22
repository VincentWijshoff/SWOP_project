package gui;

import org.junit.jupiter.api.Test;


class GUITests {

	GUI gui = new GUI("TestBrowser");

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


		GUIRectangle rectangle = (GUIRectangle) gui.createGUIObject(new GUIRectangle(10, 10, 100, 100));

		assertTrue(testName, rectangle.isInGUIObject(50, 50));
		assertTrue(testName, rectangle.isInGUIObject(10, 10));
		assertTrue(testName, rectangle.isInGUIObject(100, 100));

		assertFalse(testName, rectangle.isInGUIObject(150, 150));
		assertFalse(testName, rectangle.isInGUIObject(100, 150));
		assertFalse(testName, rectangle.isInGUIObject(9, 9));
	}
}

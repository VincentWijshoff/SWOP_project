package gui;

import org.junit.jupiter.api.Test;

import java.awt.event.MouseEvent;


class GUITests {

	GUI gui = new GUI("TestBrowser");

	void fail(String testName) { throw new RuntimeException(testName + " failed."); }

	void assertTrue(String testName, boolean b) {
		if (!b) fail(testName);
	}
	void assertFalse(String testName, boolean b) {
		if (b) fail(testName);
	}
	void assertEquals(String testName, String a, String b) {if(!a.equals(b)) fail(testName); }

	@Test
	void testRectangleBounds() throws  RuntimeException {
		final String testName = "RectangleBounds";


		GUIRectangle rectangle = (GUIRectangle) gui.getDocArea().addGUIObject(new GUIRectangle(10, 10, 100, 100));

		assertTrue(testName, rectangle.isInGUIObject(50, 50));
		assertTrue(testName, rectangle.isInGUIObject(10, 10));
		assertTrue(testName, rectangle.isInGUIObject(100, 100));

		assertFalse(testName, rectangle.isInGUIObject(150, 150));
		assertFalse(testName, rectangle.isInGUIObject(100, 150));
		assertFalse(testName, rectangle.isInGUIObject(9, 9));
	}

	@Test
	void testAddressBarSetAndGetAddress() throws  RuntimeException {
		final String testName = "testAddressBarSetAndGetAddress";

		AddressBar a = new AddressBar(gui, "testAddressBar");

		a.setAddress("www.newaddress.com");

		assertEquals(testName, a.getAddress(), "www.newaddress.com");

	}

	@Test
	void testAddressBarSetAndGetFocus() throws  RuntimeException {
		final String testName = "testAddressBarSetAndGetFocus";

		AddressBar a = new AddressBar(gui, "testAddressBar");

		a.setInFocus();

		assertTrue(testName, a.isInFocus());

		a.setOutFocus();

		assertFalse(testName, a.isInFocus());

	}

	@Test
	void testAddressBarInitialChar() throws  RuntimeException {
		final String testName = "testAddressBarInitialChar";
		// when an initial char is given after given focus, the entire addres should be changed to the char

		AddressBar a = new AddressBar(gui, "testAddressBar");

		a.setInFocus();
		a.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 1);
		a.handleKeyboardEvent(401, 47, '/', 0);
		a.setOutFocus();

		assertEquals(testName, a.getAddress(), "/");

	}

	@Test
	void testAddressBarInitialRemove() throws  RuntimeException {
		final String testName = "testAddressBarInitialRemove";

		AddressBar a = new AddressBar(gui, "testAddressBar");

		a.setInFocus();
		a.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 1);
		a.handleKeyboardEvent(401, 8, ' ', 0); //backspace
		a.setOutFocus();

		assertEquals(testName, a.getAddress(), "");

	}

	@Test
	void testAddressBarArrowUse() throws  RuntimeException {
		final String testName = "testAddressBarArrowUse";

		AddressBar a = new AddressBar(gui, "testAddressBar");

		String initialAddress = a.getAddress();

		a.setInFocus();
		a.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 1);
		a.handleKeyboardEvent(401, 39, ' ', 0); //right arrow
		a.handleKeyboardEvent(401, 39, ' ', 0);

		assertEquals(testName, a.getAddress(), initialAddress);

		a.handleKeyboardEvent(401, 37, ' ', 0); //left arrow
		a.handleKeyboardEvent(401, 37, ' ', 0);

		assertEquals(testName, a.getAddress(), initialAddress);

		a.handleKeyboardEvent(401, 36, ' ', 0); //home
		a.handleKeyboardEvent(401, 36, ' ', 0);

		assertEquals(testName, a.getAddress(), initialAddress);

		a.handleKeyboardEvent(401, 35, ' ', 0); //end
		a.handleKeyboardEvent(401, 35, ' ', 0);

		a.setOutFocus();

		assertEquals(testName, a.getAddress(), initialAddress);

	}

	@Test
	void testAddressBarDoubleClickRemove() throws  RuntimeException {
		final String testName = "testAddressBarDoubleClickRemove";

		AddressBar a = new AddressBar(gui, "testAddressBar");

		String initialAddress = a.getAddress();

		a.setInFocus();
		a.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 1);

		a.handleKeyboardEvent(401, 47, '/', 0);
		a.handleKeyboardEvent(401, 47, '/', 0);
		a.handleKeyboardEvent(401, 47, '/', 0);
		a.handleKeyboardEvent(401, 47, '/', 0);
		a.handleKeyboardEvent(401, 47, '/', 0);

		assertFalse(testName, a.getAddress().equals(initialAddress));

		a.handleKeyboardEvent(401, 27, ' ', 0); //escape

		assertEquals(testName, a.getAddress(), initialAddress);

	}

	@Test
	void testAddressBarEscapeOutTyping() throws  RuntimeException {
		final String testName = "testAddressBarEscapeOutTyping";

		AddressBar a = new AddressBar(gui, "testAddressBar");

		a.setInFocus();
		a.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 1);

		a.handleKeyboardEvent(401, 47, '/', 0);
		a.handleKeyboardEvent(401, 47, '/', 0);
		a.handleKeyboardEvent(401, 47, '/', 0);

		assertEquals(testName, a.getAddress(), "///");

		a.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 2); //double click
		a.handleKeyboardEvent(401, 8, ' ', 0); //backspace

		assertEquals(testName, a.getAddress(), "");

		a.setOutFocus();

	}

	@Test
	void testAddressBarPartialSelectingArrows() throws  RuntimeException {
		final String testName = "testAddressBarPartialSelectingArrows";

		AddressBar a = new AddressBar(gui, "testAddressBar");

		a.setInFocus();
		a.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 1);

		a.handleKeyboardEvent(401, 39, ' ', 0);//right arrow

		a.handleKeyboardEvent(0, 0, ' ', 64); //start shifting
		a.handleKeyboardEvent(401, 37, ' ', 64);//left arrow shifting
		a.handleKeyboardEvent(401, 37, ' ', 64);
		a.handleKeyboardEvent(401, 37, ' ', 64);
		a.handleKeyboardEvent(0, 0, ' ', 0); //end shifting
		a.handleKeyboardEvent(401, 47, '/', 0);

		//only the selected bit should be changed
		assertFalse(testName, a.getAddress().length() == 0);

		a.handleKeyboardEvent(401, 36, ' ', 0);//go to beginning
		a.handleKeyboardEvent(0, 0, ' ', 64); //start shifting
		a.handleKeyboardEvent(401, 39, ' ', 64);//right arrow shifting
		a.handleKeyboardEvent(401, 39, ' ', 64);
		a.handleKeyboardEvent(401, 39, ' ', 64);
		a.handleKeyboardEvent(0, 0, ' ', 0); //end shifting

		//the address should still be partially there
		assertFalse(testName, a.getAddress().length() == 0);
	}

	@Test
	void testAddressBarPartialSelectingHomeEnd() throws  RuntimeException {
		final String testName = "testAddressBarPartialSelectingHomeEnd";

		AddressBar a = new AddressBar(gui, "testAddressBar");

		a.setInFocus();
		a.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 1);

		a.handleKeyboardEvent(401, 39, ' ', 0);//right arrow

		a.handleKeyboardEvent(0, 0, ' ', 64); //start shifting
		a.handleKeyboardEvent(401, 36, ' ', 64);//home shifting
		a.handleKeyboardEvent(0, 0, ' ', 0); //end shifting
		a.handleKeyboardEvent(401, 47, '/', 0);

		//the entire address should have been selected and replaced
		assertTrue(testName, a.getAddress().length() == 1);

		a.setAddress("testAddressBar");

		a.handleKeyboardEvent(401, 36, ' ', 0);//go to beginning
		a.handleKeyboardEvent(401, 39, ' ', 0);//go right 3 spaces
		a.handleKeyboardEvent(401, 39, ' ', 0);
		a.handleKeyboardEvent(401, 39, ' ', 0);
		a.handleKeyboardEvent(0, 0, ' ', 64); //start shifting
		a.handleKeyboardEvent(401, 35, ' ', 64);//to end shifting
		a.handleKeyboardEvent(0, 0, ' ', 0); //end shifting
		a.handleKeyboardEvent(401, 8, ' ', 0); //backspace

		//only the 3 skipped bits should be there
		assertTrue(testName, a.getAddress().length() == 3);
	}
}

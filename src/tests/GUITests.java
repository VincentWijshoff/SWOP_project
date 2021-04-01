package tests;

import gui.*;
import org.junit.jupiter.api.Test;

import javax.print.Doc;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import static tests.TestUtil.*;


class GUITests {

	Window window = new Window("TestBrowser");

	static final int aBarX = 5; // position of the input field of the addressbar
	static final int aBarY = 8;

	@Test
	void testRectangleBounds() throws  RuntimeException {
		final String testName = "RectangleBounds";


		GUIRectangle rectangle = (GUIRectangle) window.getDocArea().addGUIObject(new GUIRectangle(10, 10, 100, 100));
		DocumentArea docarea = window.getDocArea();

		assertTrue(testName, rectangle.isInGUIObject(docarea.xOffset + 50, docarea.relativeYPos + 50));
		assertTrue(testName, rectangle.isInGUIObject(docarea.xOffset + 10, docarea.relativeYPos + 10));
		assertTrue(testName, rectangle.isInGUIObject(docarea.xOffset + 100, docarea.relativeYPos + 100));

		assertFalse(testName, rectangle.isInGUIObject(docarea.xOffset + 150, docarea.relativeYPos + 150));
		assertFalse(testName, rectangle.isInGUIObject(docarea.xOffset + 100, docarea.relativeYPos + 150));
		assertFalse(testName, rectangle.isInGUIObject(docarea.xOffset + 9, docarea.relativeYPos + 9));
	}

	@Test
	void testAddressBarSetAndGetAddress() throws  RuntimeException {
		final String testName = "testAddressBarSetAndGetAddress";

		AddressBar a = new AddressBar("testAddressBar", window);

		a.setAddress("www.newaddress.com");

		assertEquals(testName, a.getAddress(), "www.newaddress.com");

	}

	@Test
	void testAddressBarSetAndGetFocus() throws  RuntimeException {
		final String testName = "testAddressBarSetAndGetFocus";

		AddressBar a = new AddressBar("testAddressBar", window);

		a.setInFocus();

		assertTrue(testName, a.isInFocus());

		a.setOutFocus();

		assertFalse(testName, a.isInFocus());

	}

	@Test
	void testAddressBarInitialChar() throws  RuntimeException {
		final String testName = "testAddressBarInitialChar";
		// when an initial char is given after given focus, the entire addres should be changed to the char

		AddressBar a = new AddressBar("testAddressBar", window);

		a.setInFocus();
		a.handleMouseEventA(aBarX, aBarY, MouseEvent.MOUSE_PRESSED, 1);
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);
		a.setOutFocus();

		assertEquals(testName, a.getAddress(), "/");

	}

	@Test
	void testAddressBarInitialRemove() throws  RuntimeException {
		final String testName = "testAddressBarInitialRemove";

		AddressBar a = new AddressBar("testAddressBar", window);

		a.setInFocus();
		a.handleMouseEventA(aBarX,aBarY,MouseEvent.MOUSE_PRESSED, 1);
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_BACK_SPACE, ' ', 0); //backspace
		a.setOutFocus();

		assertEquals(testName, a.getAddress(), "");

	}

	@Test
	void testAddressBarArrowUse() throws  RuntimeException {
		final String testName = "testAddressBarArrowUse";

		AddressBar a = new AddressBar("testAddressBar", window);

		String initialAddress = a.getAddress();

		a.setInFocus();
		a.handleMouseEventA(aBarX,aBarY,MouseEvent.MOUSE_PRESSED, 1);
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', 0); //right arrow
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', 0);

		assertEquals(testName, a.getAddress(), initialAddress);

		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, ' ', 0); //left arrow
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, ' ', 0);

		assertEquals(testName, a.getAddress(), initialAddress);

		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_HOME, ' ', 0); //home
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_HOME, ' ', 0);

		assertEquals(testName, a.getAddress(), initialAddress);

		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_END, ' ', 0); //end
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_END, ' ', 0);

		a.setOutFocus();

		assertEquals(testName, a.getAddress(), initialAddress);

	}

	@Test
	void testAddressBarDoubleClickRemove() throws  RuntimeException {
		final String testName = "testAddressBarDoubleClickRemove";

		AddressBar a = new AddressBar("testAddressBar", window);

		String initialAddress = a.getAddress();

		a.setInFocus();
		a.handleMouseEventA(aBarX,aBarY,MouseEvent.MOUSE_PRESSED, 1);
		a.handleMouseEventA(aBarX,aBarY,MouseEvent.MOUSE_PRESSED, 1);

		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_SPACE, ' ', 0);
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);

		assertFalse(testName, a.getAddress().equals(initialAddress));

		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_ESCAPE, ' ', 0); //escape

		assertEquals(testName, a.getAddress(), initialAddress);

	}

	@Test
	void testAddressBarEscapeOutTyping() throws  RuntimeException {
		final String testName = "testAddressBarEscapeOutTyping";

		AddressBar a = new AddressBar(window);

		a.setInFocus();
		a.handleMouseEventA(aBarX,aBarY,MouseEvent.MOUSE_PRESSED, 1);

		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);

		assertEquals(testName, a.getAddress(), "///");

		a.handleMouseEventA(aBarX,aBarY,MouseEvent.MOUSE_PRESSED, 2); //double click
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_BACK_SPACE, ' ', 0); //backspace

		assertEquals(testName, a.getAddress(), "");

		a.setOutFocus();

	}

	@Test
	void testAddressBarPartialSelectingArrows() throws  RuntimeException {
		final String testName = "testAddressBarPartialSelectingArrows";

		AddressBar a = new AddressBar("testAddressBar", window);

		a.setInFocus();
		a.handleMouseEventA(aBarX,aBarY,MouseEvent.MOUSE_PRESSED, 1);

		a.handleKeyEventA(KeyEvent.KEY_PRESSED, 39, ' ', 0);//right arrow

		a.handleKeyEventA(0, 0, ' ', KeyEvent.SHIFT_DOWN_MASK); //start shifting
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, ' ', KeyEvent.SHIFT_DOWN_MASK);//left arrow shifting
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, ' ', KeyEvent.SHIFT_DOWN_MASK);
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, ' ', KeyEvent.SHIFT_DOWN_MASK);
		a.handleKeyEventA(0, 0, ' ', 0); //end shifting
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);

		//only the selected bit should be changed
		assertTrue(testName, a.getAddress().equals("testAddress/"));

		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_HOME, ' ', 0);//go to beginning
		a.handleKeyEventA(0, 0, ' ', KeyEvent.SHIFT_DOWN_MASK); //start shifting
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', KeyEvent.SHIFT_DOWN_MASK);//right arrow shifting
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', KeyEvent.SHIFT_DOWN_MASK);
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', KeyEvent.SHIFT_DOWN_MASK);
		a.handleKeyEventA(0, 0, ' ', 0); //end shifting

		//the address should still be partially there
		assertTrue(testName, a.getAddress().equals("testAddress/"));
	}

	@Test
	void testAddressBarPartialSelectingHomeEnd() throws  RuntimeException {
		final String testName = "testAddressBarPartialSelectingHomeEnd";

		AddressBar a = new AddressBar("testAddressBar", window);

		a.setInFocus();
		a.handleMouseEventA(aBarX,aBarY,MouseEvent.MOUSE_PRESSED, 1);

		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', 0);//right arrow

		a.handleKeyEventA(0, 0, ' ', KeyEvent.SHIFT_DOWN_MASK); //start shifting
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_HOME, ' ', KeyEvent.SHIFT_DOWN_MASK);//home shifting
		a.handleKeyEventA(0, 0, ' ', 0); //end shifting
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);

		//the entire address should have been selected and replaced
		assertTrue(testName, a.getAddress().equals("/"));

		a.setAddress("testAddressBar");

		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_HOME, ' ', 0);//go to beginning
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', 0);//go right 3 spaces
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', 0);
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', 0);
		a.handleKeyEventA(0, 0, ' ', KeyEvent.SHIFT_DOWN_MASK); //start shifting
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_END, ' ', KeyEvent.SHIFT_DOWN_MASK);//to end shifting
		a.handleKeyEventA(0, 0, ' ', 0); //end shifting
		a.handleKeyEventA(KeyEvent.KEY_PRESSED, KeyEvent.VK_BACK_SPACE, ' ', 0); //backspace

		//only the 3 skipped bits should be there
		assertTrue(testName, a.getAddress().equals("tes"));
	}

	@Test
	void testAddressBarCreationAndHandlingViaWindow() throws  RuntimeException {
		final String testName = "testAddressBarCreationAndHandlingViaWindow";

		Window w = new Window("TestWindow");

		String startAddress = w.getAddress();

		assertTrue(testName, startAddress.equals("WelcomeDoc.html"));
	}

}

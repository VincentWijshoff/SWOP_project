package tests.GUITests;

import gui.*;
import gui.DefaultScreen.AddressBar;
import gui.DefaultScreen.DefaultScreen;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;

import static tests.TestUtil.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddressBarTest {

	Window window;
	DefaultScreen screen;

	static final int aBarX = 5; // position of the input field of the addressbar
	static final int aBarY = 8;

	@BeforeAll
	public void setup() throws InvocationTargetException, InterruptedException {
		this.window = new Window("TestBrowser");
		this.screen = new DefaultScreen(window);
		java.awt.EventQueue.invokeAndWait(this.window::show);
	}

	@Test
	void testAddressBarSetAndGetAddress() throws  RuntimeException {
		final String testName = "testAddressBarSetAndGetAddress";

		AddressBar a = new AddressBar("testAddressBar", screen);

		a.setAddress("www.newaddress.com");

		assertEquals(testName, a.getAddress(), "www.newaddress.com");

	}

	@Test
	void testAddressBarSetAndGetFocus() throws  RuntimeException {
		final String testName = "testAddressBarSetAndGetFocus";

		AddressBar a = new AddressBar("testAddressBar", screen);

		a.setInFocus();

		assertTrue(testName, a.isInFocus());

		a.setOutFocus();

		assertFalse(testName, a.isInFocus());

	}

	@Test
	void testAddressBarInitialChar() throws  RuntimeException {
		final String testName = "testAddressBarInitialChar";
		// when an initial char is given after given focus, the entire addres should be changed to the char

		AddressBar a = new AddressBar("testAddressBar", screen);

		a.setInFocus();
		a.handleMouseEvent(MouseEvent.MOUSE_PRESSED,aBarX, aBarY, 1);
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);
		a.setOutFocus();

		assertEquals(testName, a.getAddress(), "/");

	}

	@Test
	void testAddressBarInitialRemove() throws  RuntimeException {
		final String testName = "testAddressBarInitialRemove";

		AddressBar a = new AddressBar("testAddressBar", screen);

		a.setInFocus();
		a.handleMouseEvent(MouseEvent.MOUSE_PRESSED,aBarX,aBarY, 1);
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_BACK_SPACE, ' ', 0); //backspace
		a.setOutFocus();

		assertEquals(testName, a.getAddress(), "");

	}

	@Test
	void testAddressBarArrowUse() throws  RuntimeException {
		final String testName = "testAddressBarArrowUse";

		AddressBar a = new AddressBar("testAddressBar", screen);


		String initialAddress = a.getAddress();

		a.setInFocus();
		a.handleMouseEvent(MouseEvent.MOUSE_PRESSED,aBarX,aBarY, 1);
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', 0); //right arrow
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', 0);

		assertEquals(testName, a.getAddress(), initialAddress);

		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, ' ', 0); //left arrow
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, ' ', 0);

		assertEquals(testName, a.getAddress(), initialAddress);

		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_HOME, ' ', 0); //home
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_HOME, ' ', 0);

		assertEquals(testName, a.getAddress(), initialAddress);

		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_END, ' ', 0); //end
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_END, ' ', 0);

		a.setOutFocus();

		assertEquals(testName, a.getAddress(), initialAddress);

	}

	@Test
	void testAddressBarDoubleClickRemove() throws  RuntimeException {
		final String testName = "testAddressBarDoubleClickRemove";

		AddressBar a = new AddressBar("testAddressBar", screen);

		String initialAddress = a.getAddress();

		a.setInFocus();
		a.handleMouseEvent(MouseEvent.MOUSE_PRESSED,aBarX,aBarY, 1);
		a.handleMouseEvent(MouseEvent.MOUSE_PRESSED,aBarX,aBarY, 1);

		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_SPACE, ' ', 0);
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);

		assertFalse(testName, a.getAddress().equals(initialAddress));

		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_ESCAPE, ' ', 0); //escape

		assertEquals(testName, a.getAddress(), initialAddress);

	}

	@Test
	void testAddressBarEscapeOutTyping() throws  RuntimeException {
		final String testName = "testAddressBarEscapeOutTyping";

		AddressBar a = new AddressBar(screen);

		a.setInFocus();
		a.handleMouseEvent(MouseEvent.MOUSE_PRESSED,aBarX,aBarY, 1);

		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);

		assertEquals(testName, a.getAddress(), "///");

		a.handleMouseEvent(MouseEvent.MOUSE_PRESSED,aBarX,aBarY, 2); //double click
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_BACK_SPACE, ' ', 0); //backspace

		assertEquals(testName, a.getAddress(), "");

		a.setOutFocus();

	}

	@Test
	void testAddressBarPartialSelectingArrows() throws  RuntimeException {
		final String testName = "testAddressBarPartialSelectingArrows";

		AddressBar a = new AddressBar("testAddressBar", screen);

		a.setInFocus();
		a.handleMouseEvent(MouseEvent.MOUSE_PRESSED,aBarX,aBarY, 1);

		a.handleKeyEvent(KeyEvent.KEY_PRESSED, 39, ' ', 0);//right arrow

		a.handleKeyEvent(0, 0, ' ', KeyEvent.SHIFT_DOWN_MASK); //start shifting
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, ' ', KeyEvent.SHIFT_DOWN_MASK);//left arrow shifting
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, ' ', KeyEvent.SHIFT_DOWN_MASK);
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, ' ', KeyEvent.SHIFT_DOWN_MASK);
		a.handleKeyEvent(0, 0, ' ', 0); //end shifting
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);

		//only the selected bit should be changed
		assertTrue(testName, a.getAddress().equals("testAddress/"));

		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_HOME, ' ', 0);//go to beginning
		a.handleKeyEvent(0, 0, ' ', KeyEvent.SHIFT_DOWN_MASK); //start shifting
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', KeyEvent.SHIFT_DOWN_MASK);//right arrow shifting
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', KeyEvent.SHIFT_DOWN_MASK);
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', KeyEvent.SHIFT_DOWN_MASK);
		a.handleKeyEvent(0, 0, ' ', 0); //end shifting

		//the address should still be partially there
		assertTrue(testName, a.getAddress().equals("testAddress/"));
	}

	@Test
	void testAddressBarPartialSelectingHomeEnd() throws  RuntimeException {
		final String testName = "testAddressBarPartialSelectingHomeEnd";

		AddressBar a = new AddressBar("testAddressBar", screen);

		a.setInFocus();
		a.handleMouseEvent(MouseEvent.MOUSE_PRESSED,aBarX,aBarY, 1);

		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', 0);//right arrow

		a.handleKeyEvent(0, 0, ' ', KeyEvent.SHIFT_DOWN_MASK); //start shifting
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_HOME, ' ', KeyEvent.SHIFT_DOWN_MASK);//home shifting
		a.handleKeyEvent(0, 0, ' ', 0); //end shifting
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_SLASH, '/', 0);

		//the entire address should have been selected and replaced
		assertTrue(testName, a.getAddress().equals("/"));

		a.setAddress("testAddressBar");
		a.setInFocus();

		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_HOME, ' ', 0);//go to beginning
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', 0);//go right 3 spaces
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', 0);
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', 0);
		a.handleKeyEvent(0, 0, ' ', KeyEvent.SHIFT_DOWN_MASK); //start shifting
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_END, ' ', KeyEvent.SHIFT_DOWN_MASK);//to end shifting
		a.handleKeyEvent(0, 0, ' ', 0); //end shifting
		a.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_BACK_SPACE, ' ', 0); //backspace

		//only the 3 skipped bits should be there
		assertTrue(testName, a.getAddress().equals("tes"));
	}

}

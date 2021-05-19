package tests.GUITests;

import gui.DefaultScreen.AddressBar;
import gui.DefaultScreen.ChildPane;
import gui.DefaultScreen.DefaultScreen;
import gui.Objects.ScrollBars.HorizontalScrollBar;
import gui.Objects.ScrollBars.ScrollbarSlider;
import gui.Objects.ScrollBars.VerticalScrollBar;
import gui.Window;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;

import static tests.TestUtil.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ScrollBarTest {

	Window window;
	DefaultScreen screen;

	static final int aBarX = 5; // position of the input field of the addressbar
	static final int aBarY = 8;

	@BeforeAll
	public void setup() throws InvocationTargetException, InterruptedException {
		this.window = new Window("TestBrowser");
		this.screen = new DefaultScreen(window);
		System.out.println("hello");
	}

	@Test
	void testScrollBarsPresent() {
		System.out.println("WHY");
		assertTrue("testScrollBarsPresent", this.screen.getPane() instanceof ChildPane);
		ChildPane pane = (ChildPane) this.screen.getPane();


		// Horizontal
		assertTrue("testScrollBarsPresent", pane.getHorScrollBar() != null && pane.getHorScrollBar().getSlider() != null);
		HorizontalScrollBar sb1 = pane.getHorScrollBar();
		ScrollbarSlider slider1 = pane.getHorScrollBar().getSlider();

		assertTrue("testScrollBarsPresent", sb1.getScrollbarWidth() == 0 && sb1.getScrollbarHeight() == HorizontalScrollBar.getScrollBarHeight());


		// Vertical
		assertTrue("testScrollBarsPresent", true);
		assertTrue("testScrollBarsPresent", true);
		assertTrue("testScrollBarsPresent", true);
		assertTrue("testScrollBarsPresent", true);



	}

	// assertTrue("testScrollBarsPresent", true);
}

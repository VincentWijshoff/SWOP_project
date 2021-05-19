package tests.GUITests;

import gui.DefaultScreen.AddressBar;
import gui.DefaultScreen.ChildPane;
import gui.DefaultScreen.DefaultScreen;
import gui.Objects.GUIInput;
import gui.Objects.GUIString;
import gui.Objects.ScrollBars.HorizontalScrollBar;
import gui.Objects.ScrollBars.ScrollBar;
import gui.Objects.ScrollBars.ScrollbarSlider;
import gui.Objects.ScrollBars.VerticalScrollBar;
import gui.Window;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;

import static tests.TestUtil.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ScrollBarTest {

	Window window;
	DefaultScreen screen;
	FontMetrics fm;

	ChildPane pane;
	HorizontalScrollBar horScrollBar;
	ScrollbarSlider horSlider;
	VerticalScrollBar verScrollBar;
	ScrollbarSlider verSlider;

	@BeforeEach
	public void setup() throws InvocationTargetException, InterruptedException {
		this.window = new Window("TestBrowser");
		this.screen = (DefaultScreen) this.window.getCurrentScreen();
		java.awt.EventQueue.invokeAndWait(this.window::show);
		this.fm = screen.getFontMetrics();

		assertTrue("setup", this.screen.getPane() instanceof ChildPane);
		this.pane = (ChildPane) this.screen.getPane();

		this.horScrollBar = this.pane.getHorScrollBar();
		this.horSlider = this.pane.getHorScrollBar().getSlider();
		this.verScrollBar = this.pane.getVerScrollBar();
		this.verSlider = this.pane.getVerScrollBar().getSlider();
		assertTrue("setup", this.horScrollBar != null && this.horSlider != null);
		assertTrue("setup", this.verScrollBar != null && this.verSlider != null);
		this.horScrollBar.updateDimensions();
		this.verScrollBar.updateDimensions();
	}

	@Test
	void testHorizontalInitialProperties() {
		final String testName = "testHorizontalInitialProperties";
		// Check if the horizontal scrollbar has the right properties
		assertEquals(testName, horScrollBar.getScrollbarWidth(), pane.getAvailableWidth() );
		assertEquals(testName, horScrollBar.getScrollbarHeight(), HorizontalScrollBar.getScrollBarHeight());
		assertEquals(testName, horScrollBar.getScrollbarCoordX(), pane.getX());
		assertEquals(testName, horScrollBar.getScrollbarCoordY(), pane.getY() + pane.getHeight() - horScrollBar.getScrollbarHeight());

		// Slider Dimensions
		assertTrue(testName, horSlider.isHorizontal());
		assertTrue(testName, horSlider.getSliderColor() == Color.WHITE);
		assertEquals(testName, horSlider.getCoordX(), horScrollBar.getSliderStart());
		assertEquals(testName, horSlider.getCoordY(), horScrollBar.getSliderStartY());
		assertEquals(testName, horSlider.getWidth(), horScrollBar.getMaxSliderWidth());
		assertEquals(testName, horSlider.getHeight(), HorizontalScrollBar.getSliderHeight());
	}


	@Test
	void testVerticalInitialProperties() {
		final String testName = "testVerticalInitialProperties";
		// Check if the vertical scrollbar has the right properties
		assertEquals(testName, verScrollBar.getScrollbarCoordX(), pane.getX() + pane.getAvailableWidth());
		assertEquals(testName, verScrollBar.getScrollbarCoordY(), pane.getY());
		assertEquals(testName, verScrollBar.getScrollbarWidth(), VerticalScrollBar.getScrollBarWidth());
		assertEquals(testName, verScrollBar.getScrollbarHeight(), pane.getAvailableHeight());

		// Slider Dimensions
		assertFalse(testName, verSlider.isHorizontal());
		assertTrue(testName, verSlider.getSliderColor() == Color.WHITE);
		assertEquals(testName, verSlider.getCoordX(), verScrollBar.getSliderStartX());
		assertEquals(testName, verSlider.getCoordY(), verScrollBar.getSliderStart());
		assertEquals(testName, verSlider.getWidth(), VerticalScrollBar.getSliderWidth());
		assertEquals(testName, verSlider.getHeight(), verScrollBar.getMaxSliderHeight());
	}

	@Test
	void testHorizontalScalingScrollBar() {
		final String testName = "testHorizontalScalingScrollBar";
		final int initialScrollBarWidth = horScrollBar.getScrollbarWidth();
		final int initialSliderWidth = horSlider.getWidth();
		final int initialContentWidth = pane.getContentWidth();
		final int availableWidth = horScrollBar.getAvailableWidth() - ScrollBar.getBuffer()*2;

		// Initial horizontal values
		final int initialSliderX = horSlider.getCoordX();
		final int initialSliderY = horSlider.getCoordY();
		final int initialScrollBarX = horScrollBar.getScrollbarCoordX();
		final int initialScrollBarY = horScrollBar.getScrollbarCoordY();
		// Initial vertical values
		final int initialSliderXv = verSlider.getCoordX();
		final int initialSliderYv = verSlider.getCoordY();
		final int initialScrollBarXv = verScrollBar.getScrollbarCoordX();
		final int initialScrollBarYv = verScrollBar.getScrollbarCoordY();

		// Add longer and longer strings, one by one
		for (int i = 1; i < 10; i++) {
			String newLongestString = repeatString("v", 20 * i);
			GUIString newString = new GUIString(newLongestString);
			newString.setFontMetricsHandler(screen);
			pane.addGUIObject(newString);

			// If the content width hasn't changed, the scrollbar should not change.
			if (pane.getContentWidth() == initialContentWidth)
				assertEquals(testName, initialSliderWidth, horSlider.getWidth());
			// Else the added string changed the width of the content.
			else {
				horSlider.setWidth(horScrollBar.calculateSliderWidth()); // Normally happens in draw()
				assertEquals(testName, pane.getContentWidth(), newString.coordX - pane.getX() + newString.width);

				// There is still room for longer strings, slider didn't change
				if (availableWidth >= pane.getContentWidth())
					assertTrue(testName, initialSliderWidth == horSlider.getWidth());
				else { // The string goes beyond the available width, the slider changes width
					assertFalse(testName, initialSliderWidth == horSlider.getWidth());
					assertEquals(testName, horSlider.getWidth(), (initialSliderWidth*initialSliderWidth)/pane.getContentWidth());
				}
			}
			// The scrollbar itself doesn't change
			assertEquals(testName, initialScrollBarWidth, horScrollBar.getScrollbarWidth());

			// The coordinates of the slider and scrollbar don't change.
			assertEquals(testName, initialScrollBarX, horScrollBar.getScrollbarCoordX());
			assertEquals(testName, initialScrollBarY, horScrollBar.getScrollbarCoordY());
			assertEquals(testName, initialSliderX, horSlider.getCoordX());
			assertEquals(testName, initialSliderY, horSlider.getCoordY());
			// The vertical scrollbar should not change either.
			assertEquals(testName, initialScrollBarXv, verScrollBar.getScrollbarCoordX());
			assertEquals(testName, initialScrollBarYv, verScrollBar.getScrollbarCoordY());
			assertEquals(testName, initialSliderXv, verSlider.getCoordX());
			assertEquals(testName, initialSliderYv, verSlider.getCoordY());

		}
	}

	@Test
	void testVerticalScalingScrollBar() {
		final String testName = "testVerticalScalingScrollBar";
		final int initialScrollBarHeight = verScrollBar.getScrollbarHeight();
		final int initialSliderHeight = verSlider.getHeight();
		final int initialContentHeight = pane.getContentHeight();
		final int availableHeight = verScrollBar.getAvailableHeight() - ScrollBar.getBuffer()*2;

		// Initial horizontal values
		final int initialSliderX = horSlider.getCoordX();
		final int initialSliderY = horSlider.getCoordY();
		final int initialScrollBarX = horScrollBar.getScrollbarCoordX();
		final int initialScrollBarY = horScrollBar.getScrollbarCoordY();
		// Initial vertical values
		final int initialSliderXv = verSlider.getCoordX();
		final int initialSliderYv = verSlider.getCoordY();
		final int initialScrollBarXv = verScrollBar.getScrollbarCoordX();
		final int initialScrollBarYv = verScrollBar.getScrollbarCoordY();

		// Add strings with increasing y-coords, one by one
		for (int i = 0; i < 15; i++) {
			String newLongestString = "This is a new string.";
			GUIString newString = new GUIString(newLongestString);
			newString.setFontMetricsHandler(screen);
			newString.coordY += i * 50;
			pane.addGUIObject(newString);

			// If the content height hasn't changed, the scrollbar should not change.
			if (pane.getContentHeight() == initialContentHeight)
				assertEquals(testName, initialSliderHeight, verSlider.getHeight());
			// Else the added string changed the width of the content.
			else {
				verSlider.setHeight(verScrollBar.calculateSliderHeight()); // Normally happens in draw()
				assertEquals(testName, pane.getContentHeight(), newString.coordY + newString.height - pane.getY());

				// There is still room for more strings, slider didn't change
				if (availableHeight >= pane.getContentHeight())
					assertTrue(testName, initialSliderHeight == verSlider.getHeight());
				else { // The string goes below the available height, the slider changes height
					assertFalse(testName, initialSliderHeight == verSlider.getHeight());
					assertEquals(testName, verSlider.getHeight(), (initialSliderHeight*initialSliderHeight)/pane.getContentHeight());
				}
			}
			// The scrollbar itself doesn't change
			assertEquals(testName, initialScrollBarHeight, verScrollBar.getScrollbarHeight());

			// The horizontal scrollbar should not change.
			assertEquals(testName, initialScrollBarX, horScrollBar.getScrollbarCoordX());
			assertEquals(testName, initialScrollBarY, horScrollBar.getScrollbarCoordY());
			assertEquals(testName, initialSliderX, horSlider.getCoordX());
			assertEquals(testName, initialSliderY, horSlider.getCoordY());
			// The coordinates of the vertical slider and scrollbar don't change.
			assertEquals(testName, initialScrollBarXv, verScrollBar.getScrollbarCoordX());
			assertEquals(testName, initialScrollBarYv, verScrollBar.getScrollbarCoordY());
			assertEquals(testName, initialSliderXv, verSlider.getCoordX());
			assertEquals(testName, initialSliderYv, verSlider.getCoordY());

		}
	}

	@Test
	void testInputScrollBar() {
		final String testName = "testSlidingWithMouse";
		final String startTxt = "This is a pretty long test message to test scrollbars for InputFields";
		GUIInput inputField = new GUIInput(startTxt, 10, 0, 100, 20);
		inputField.setFontMetricsHandler(screen);
		HorizontalScrollBar scrollBar = inputField.getScrollBar();
		assertTrue(testName, scrollBar != null);
		scrollBar.updateDimensions();
		scrollBar.getSlider().setWidth(scrollBar.calculateSliderWidth());

		assertEquals(testName, scrollBar.getScrollbarCoordX(), 10);
		assertEquals(testName, scrollBar.getScrollbarCoordY(), 20);
		assertEquals(testName, scrollBar.getScrollbarWidth(), 100);
		assertEquals(testName, scrollBar.getScrollbarHeight(), HorizontalScrollBar.getScrollBarHeight());
		assertEquals(testName, scrollBar.getSlider().getCoordX(), scrollBar.getSliderStart());
		assertEquals(testName, scrollBar.getSlider().getCoordY(), scrollBar.getSliderStartY());
		assertEquals(testName, scrollBar.getSlider().getHeight(), HorizontalScrollBar.getSliderHeight());
		int initialSliderWidth = (scrollBar.getMaxSliderWidth()*scrollBar.getMaxSliderWidth())/inputField.getContentWidth();
		assertEquals(testName, scrollBar.getSlider().getWidth(), initialSliderWidth);

		// Remove one char at the end:
		inputField.handleMouseEvent(11, 1, MouseEvent.MOUSE_PRESSED, 1);
		inputField.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', 0); // right arrow
		// Slider should have moved to the max right position
		assertEquals(testName, scrollBar.getSlider().getCoordX(), scrollBar.getSliderEnd() - scrollBar.getSlider().getWidth());
		inputField.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_BACK_SPACE, ' ', 0); // backspace
		// Slider width should have changed
		assertFalse(testName, scrollBar.getSlider().getWidth() == initialSliderWidth);
		assertEquals(testName, scrollBar.getSlider().getWidth(), (scrollBar.getMaxSliderWidth()*scrollBar.getMaxSliderWidth())/inputField.getContentWidth());
		// Slider should still be in the max right position
		assertEquals(testName, scrollBar.getSlider().getCoordX(), scrollBar.getSliderEnd() - scrollBar.getSlider().getWidth());

		// Move all the way to the left
		for (int i = 0; i < 5; i++) {
			// First few don't move the scrollbar
			inputField.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, ' ', 0); // left arrow
			assertEquals(testName, scrollBar.getSlider().getCoordX(), scrollBar.getSliderEnd() - scrollBar.getSlider().getWidth());
		}
		for (int i = 0; i < inputField.getText().length()/2; i++) {
			// Move cursor to about the middle of the InputField text
			inputField.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, ' ', 0); // left arrow
		}
		// The scrollbar slider moved
		assertEquals(testName, scrollBar.getSlider().getCoordX(), scrollBar.getSliderStart() + (scrollBar.getOffset()*(-1) * scrollBar.getAvailableWidth() / scrollBar.getContentWidth()));
		for (int i = 0; i < inputField.getText().length()/2; i++) {
			// Move cursor to start of InputField text
			inputField.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, ' ', 0); // left arrow
		}
		assertEquals(testName, scrollBar.getSlider().getCoordX(), scrollBar.getSliderStart());

		// Clear the text of the InputField
		inputField.setText("");
		scrollBar.updateDimensions();
		scrollBar.getSlider().setWidth(scrollBar.calculateSliderWidth());

		// Only the width of the slider should have changed
		assertEquals(testName, scrollBar.getScrollbarCoordX(), 10);
		assertEquals(testName, scrollBar.getScrollbarCoordY(), 20);
		assertEquals(testName, scrollBar.getScrollbarWidth(), 100);
		assertEquals(testName, scrollBar.getScrollbarHeight(), HorizontalScrollBar.getScrollBarHeight());
		assertEquals(testName, scrollBar.getSlider().getCoordX(), scrollBar.getSliderStart());
		assertEquals(testName, scrollBar.getSlider().getCoordY(), scrollBar.getSliderStartY());
		assertEquals(testName, scrollBar.getSlider().getHeight(), HorizontalScrollBar.getSliderHeight());
		assertEquals(testName, scrollBar.getSlider().getWidth(), scrollBar.getMaxSliderWidth());
	}

	@Test
	void testSlidingWithMouse() {
		final String testName = "testSlidingWithMouse";
		final String startTxt = "This is a pretty long test message to test scrollbars for InputFields";
		GUIInput inputField = new GUIInput(startTxt, 10, 10, 100, 20);
		//TODO
	}
}

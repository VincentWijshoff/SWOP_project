package tests.GUITests;

import gui.DefaultScreen.ChildPane;
import gui.DefaultScreen.DefaultScreen;
import gui.Objects.GUIInput;
import gui.Objects.GUIString;
import gui.Objects.ScrollBars.HorizontalScrollBar;
import gui.Objects.ScrollBars.ScrollBar;
import gui.Objects.ScrollBars.ScrollbarSlider;
import gui.Objects.ScrollBars.VerticalScrollBar;
import gui.Window;
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
		final String testName = "testInputScrollBar";
		final String startTxt = "This is a pretty long test message to test scrollbars for InputFields";
		GUIInput inputField = new GUIInput(startTxt, 10, 0, 100, 20);
		inputField.setFontMetricsHandler(screen);
		HorizontalScrollBar scrollBar = inputField.getScrollBar();
		assertTrue(testName, scrollBar != null);
		scrollBar.updateDimensions();
		scrollBar.getSlider().setWidth(scrollBar.calculateSliderWidth());

		// Scrollbar & slider
		assertEquals(testName, scrollBar.getScrollbarCoordX(), 10);
		assertEquals(testName, scrollBar.getScrollbarCoordY(), 20);
		assertEquals(testName, scrollBar.getScrollbarWidth(), 100);
		assertEquals(testName, scrollBar.getScrollbarHeight(), HorizontalScrollBar.getScrollBarHeight());
		assertEquals(testName, scrollBar.getSlider().getCoordX(), scrollBar.getSliderStart());
		assertEquals(testName, scrollBar.getSlider().getCoordY(), scrollBar.getSliderStartY());
		assertEquals(testName, scrollBar.getSlider().getHeight(), HorizontalScrollBar.getSliderHeight());
		int initialSliderWidth = (scrollBar.getMaxSliderWidth()*scrollBar.getMaxSliderWidth())/inputField.getContentWidth();
		assertEquals(testName, scrollBar.getSlider().getWidth(), initialSliderWidth);
		assertEquals(testName, inputField.getOffset(), 0); // Should be 0, no offset

		// Move cursor to most right position
		inputField.handleMouseEvent(11, 1, MouseEvent.MOUSE_PRESSED, 1);
		inputField.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT, ' ', 0); // right arrow
		// Slider should have moved to the max right position
		assertEquals(testName, scrollBar.getSlider().getCoordX(), scrollBar.getSliderEnd() - scrollBar.getSlider().getWidth());
		// Offset should be max
		int maxOffsetA = scrollBar.calcMaxOffset();
		assertEquals(testName, inputField.getOffset(), maxOffsetA);

		// Remove 3 chars at the end:
		int charsToRemoveLength = fm.stringWidth(inputField.getText().substring(inputField.getText().length()-3));
		inputField.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_BACK_SPACE, ' ', 0); // backspace
		inputField.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_BACK_SPACE, ' ', 0); // backspace
		inputField.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_BACK_SPACE, ' ', 0); // backspace
		// Slider width should have changed
		assertFalse(testName, scrollBar.getSlider().getWidth() == initialSliderWidth);
		assertEquals(testName, scrollBar.getSlider().getWidth(), (scrollBar.getMaxSliderWidth()*scrollBar.getMaxSliderWidth())/inputField.getContentWidth());
		// Slider should still be in the max right position
		assertEquals(testName, scrollBar.getSlider().getCoordX(), scrollBar.getSliderEnd() - scrollBar.getSlider().getWidth());
		// Offset should be max, but closer to 0 than before deleting chars
		int offsetA = inputField.getOffset();
		assertEquals(testName, offsetA, maxOffsetA + charsToRemoveLength);


		// Move all the way to the left
		for (int i = 0; i < 5; i++) {
			// First few don't move the scrollbar
			inputField.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, ' ', 0); // left arrow
			assertEquals(testName, scrollBar.getSlider().getCoordX(), scrollBar.getSliderEnd() - scrollBar.getSlider().getWidth());
			assertEquals(testName, inputField.getOffset(), offsetA);
		}
		for (int i = 0; i < inputField.getText().length()/2; i++) {
			// Move cursor to about the middle of the InputField text
			inputField.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, ' ', 0); // left arrow
		}
		// Offset should be closer to 0, meaning string moved to the right
		assertTrue(testName, inputField.getOffset() > offsetA);
		// The scrollbar slider moved
		assertEquals(testName, scrollBar.getSlider().getCoordX(), scrollBar.getSliderStart() + (scrollBar.getOffset()*(-1) * scrollBar.getAvailableWidth() / scrollBar.getContentWidth()));
		for (int i = 0; i < inputField.getText().length()/2; i++) {
			// Move cursor to start of InputField text
			inputField.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, ' ', 0); // left arrow
		}
		assertEquals(testName, inputField.getOffset(), 0);
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
		assertEquals(testName, inputField.getOffset(), 0);
	}

	@Test
	void testSlidingWithMouse() {
		final String testName = "testSlidingWithMouse";
		final String startTxt = "This is a pretty long test message to test scrollbars for InputFields";
		GUIInput inputField = new GUIInput(startTxt, 0, 0, 50, 20);
		inputField.setFontMetricsHandler(screen);
		HorizontalScrollBar scrollBar = inputField.getScrollBar();
		assertTrue(testName, scrollBar != null);
		scrollBar.updateDimensions();
		scrollBar.getSlider().setWidth(scrollBar.calculateSliderWidth());

		// Check initial state
		assertEquals(testName, scrollBar.getScrollbarCoordX(), 0);
		assertEquals(testName, scrollBar.getScrollbarCoordY(), 20);
		assertEquals(testName, scrollBar.getScrollbarWidth(), 50);
		assertEquals(testName, scrollBar.getScrollbarHeight(), HorizontalScrollBar.getScrollBarHeight());
		int initialSliderX =  scrollBar.getSlider().getCoordX();
		assertEquals(testName, initialSliderX, scrollBar.getSliderStart());
		assertEquals(testName, scrollBar.getSlider().getCoordY(), scrollBar.getSliderStartY());
		assertEquals(testName, scrollBar.getSlider().getHeight(), HorizontalScrollBar.getSliderHeight());
		int initialSliderWidth = (scrollBar.getMaxSliderWidth()*scrollBar.getMaxSliderWidth())/inputField.getContentWidth();
		assertEquals(testName, scrollBar.getSlider().getWidth(), initialSliderWidth);
		assertEquals(testName, inputField.getOffset(), 0); // Should be 0, no offset

		// Click on slider
		assertEquals(testName, scrollBar.getSlider().getSliderColor(), Color.WHITE);
		scrollBar.handleMouseEvent(MouseEvent.MOUSE_PRESSED, initialSliderX+1, scrollBar.getSlider().getCoordY()+1, 1);
		assertEquals(testName, scrollBar.getSlider().getSliderColor(), Color.LIGHT_GRAY);
		assertTrue(testName, scrollBar.getSlider().isSliding());
		assertEquals(testName, inputField.getOffset(), 0); // Should be 0, no offset

		// Move 20 pixels right
		scrollBar.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, initialSliderX+21, scrollBar.getSlider().getCoordY()+1, 1);
		assertEquals(testName, scrollBar.getSlider().getSliderColor(), Color.LIGHT_GRAY);
		assertTrue(testName, scrollBar.getSlider().isSliding());
		// Width should be the same, but the x coordinate of the slider should have changed.
		assertEquals(testName, scrollBar.getSlider().getWidth(), initialSliderWidth);
		int xAfterSlidingRight = scrollBar.getSlider().getCoordX();
		assertTrue(testName,xAfterSlidingRight > scrollBar.getSliderStart());
		assertEquals(testName, xAfterSlidingRight, scrollBar.getSliderStart() + (scrollBar.getOffset()*(-1) * scrollBar.getAvailableWidth() / scrollBar.getContentWidth()));
		assertEquals(testName, xAfterSlidingRight, scrollBar.calculateSliderX());
		// Text movement
		int offsetAfterSlidingRight = inputField.getOffset();
		assertTrue(testName, offsetAfterSlidingRight < 0);
		assertEquals(testName, offsetAfterSlidingRight, -20 * (int) (((double) scrollBar.getContentWidth())/((double) scrollBar.getMaxSliderWidth())));

		// Move 10 pixels left
		scrollBar.handleMouseEvent(MouseEvent.MOUSE_PRESSED, initialSliderX+21, scrollBar.getSlider().getCoordY()+1, 1);
		scrollBar.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, initialSliderX+11, scrollBar.getSlider().getCoordY()+1, 1);
		assertEquals(testName, scrollBar.getSlider().getSliderColor(), Color.LIGHT_GRAY);
		assertTrue(testName, scrollBar.getSlider().isSliding());
		// Width should be the same, but the x coordinate of the slider should have changed.
		assertEquals(testName, scrollBar.getSlider().getWidth(), initialSliderWidth);
		int xAfterSlidingLeft = scrollBar.getSlider().getCoordX();
		assertTrue(testName,xAfterSlidingLeft > scrollBar.getSliderStart());
		assertTrue(testName,xAfterSlidingLeft < xAfterSlidingRight);
		assertEquals(testName, xAfterSlidingLeft, scrollBar.getSliderStart() + (scrollBar.getOffset()*(-1) * scrollBar.getAvailableWidth() / scrollBar.getContentWidth()));
		assertEquals(testName, xAfterSlidingLeft, scrollBar.calculateSliderX());
		// Text movement
		int offsetAfterSlidingLeft = inputField.getOffset();
		assertTrue(testName, offsetAfterSlidingLeft < 0);
		assertTrue(testName, offsetAfterSlidingLeft > offsetAfterSlidingRight);
		assertEquals(testName, offsetAfterSlidingLeft, -10 * (int) (((double) scrollBar.getContentWidth())/((double) scrollBar.getMaxSliderWidth())));

		// Release click
		scrollBar.handleMouseEvent(MouseEvent.MOUSE_RELEASED, initialSliderX+11, scrollBar.getSlider().getCoordY()+1, 1);
		assertEquals(testName, scrollBar.getSlider().getSliderColor(), Color.WHITE);
		assertFalse(testName, scrollBar.getSlider().isSliding());
		int xAfterReleasing = scrollBar.getSlider().getCoordX();
		assertFalse(testName,xAfterReleasing == scrollBar.getSliderStart());
		assertFalse(testName,xAfterReleasing == xAfterSlidingRight);
		assertTrue(testName,xAfterReleasing == xAfterSlidingLeft);
		assertEquals(testName, xAfterReleasing, scrollBar.getSliderStart() + (scrollBar.getOffset()*(-1) * scrollBar.getAvailableWidth() / scrollBar.getContentWidth()));
		assertEquals(testName, xAfterReleasing, scrollBar.calculateSliderX());
		assertEquals(testName, inputField.getOffset(), offsetAfterSlidingLeft);
	}

}

package tests.GUITests;
import commands.AddBookmarkOperation;
import gui.DefaultScreen.BookmarkBar;
import gui.DefaultScreen.DefaultScreen;
import gui.Objects.GUILink;
import gui.Objects.GUITable;
import gui.Window;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

import static tests.TestUtil.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookmarkBarTest {

    Window window;
    DefaultScreen screen;
    FontMetrics fm;

    @BeforeAll
    public void setup() throws InvocationTargetException, InterruptedException {
        this.window = new Window("TestBrowser");
        this.screen = new DefaultScreen(window);
        java.awt.EventQueue.invokeAndWait(this.window::show);
        fm = window.getFontMetrics();
    }

    @Test
    void testBookmarkBarInitialEmpty() throws  RuntimeException {
        BookmarkBar b = new BookmarkBar(0, screen);
        // the bookmarks are kept in a GUITable
        GUITable marks = b.getBookmarks();
        // the table should consist off only 1 row which is the
        assertEquals("testBookmarkBarInitialEmpty", marks.getChildObjects().size(), 0);
    }

    @Test
    void testBookmarkBarAddOneLink() throws  RuntimeException {
        BookmarkBar b = new BookmarkBar(0, screen);
        // we add a new bookmark to the bookmark bar
        this.screen.execute(new AddBookmarkOperation("testMark", "testAddress"));  // TODO //b.addBookmark("testMark", "testAddress");
        // we now check if the bookmark was added to the list off bookmarks
        GUITable marks = b.getBookmarks();
        // should be length 1
        assertEquals("testBookmarkBarAddOneLink", marks.getChildObjects().size(), 1);
        // the item should be a GUILink
        // we do not need specific tests on the handling of a click on the bookmark bar
        // because if the test here succeeded and the item is in deed a GUILink, we may
        // assume the link works as intended because off other test testing this functionality
        assertTrue("testBookmarkBarAddOneLink", marks.getChildObjects().get(0) instanceof GUILink);
        GUILink link = (GUILink) marks.getChildObjects().get(0);
        // we now check the name and the link in the link from the bookmark bar
        assertEquals("testBookmarkBarAddOneLink", link.getText(), "testMark");
        assertEquals("testBookmarkBarAddOneLink", link.getHref(), "testAddress");
    }

    @Test
    void testBookmarkBarMultipleLinks() throws  RuntimeException {
        BookmarkBar b = new BookmarkBar(0, screen);
        // we add new bookmarks to the bookmark bar
        this.screen.execute(new AddBookmarkOperation("testMark", "testAddress"));
        /*b.addBookmark("testMark1", "testAddress1");
        b.addBookmark("testMark2", "testAddress2");
        b.addBookmark("testMark3", "testAddress3");
        b.addBookmark("testMark4", "testAddress4");
        b.addBookmark("testMark5", "testAddress5");*/
        // we now check if the bookmark was added to the list off bookmarks
        GUITable marks = b.getBookmarks();
        // should be length 5
        assertEquals("testBookmarkBarAddOneLink", marks.getChildObjects().size(), 5);
        // the items should all be GUILinks
        assertTrue("testBookmarkBarAddOneLink", marks.getChildObjects().get(0) instanceof GUILink);
        assertTrue("testBookmarkBarAddOneLink", marks.getChildObjects().get(1) instanceof GUILink);
        assertTrue("testBookmarkBarAddOneLink", marks.getChildObjects().get(2) instanceof GUILink);
        assertTrue("testBookmarkBarAddOneLink", marks.getChildObjects().get(3) instanceof GUILink);
        assertTrue("testBookmarkBarAddOneLink", marks.getChildObjects().get(4) instanceof GUILink);
        GUILink link1 = (GUILink) marks.getChildObjects().get(0);
        GUILink link2 = (GUILink) marks.getChildObjects().get(1);
        GUILink link3 = (GUILink) marks.getChildObjects().get(2);
        GUILink link4 = (GUILink) marks.getChildObjects().get(3);
        GUILink link5 = (GUILink) marks.getChildObjects().get(4);
        // we now check the name and the link in the link from the bookmark bar
        assertEquals("testBookmarkBarAddOneLink", link1.getText(), "testMark1");
        assertEquals("testBookmarkBarAddOneLink", link1.getHref(), "testAddress1");
        assertEquals("testBookmarkBarAddOneLink", link2.getText(), "testMark2");
        assertEquals("testBookmarkBarAddOneLink", link2.getHref(), "testAddress2");
        assertEquals("testBookmarkBarAddOneLink", link3.getText(), "testMark3");
        assertEquals("testBookmarkBarAddOneLink", link3.getHref(), "testAddress3");
        assertEquals("testBookmarkBarAddOneLink", link4.getText(), "testMark4");
        assertEquals("testBookmarkBarAddOneLink", link4.getHref(), "testAddress4");
        assertEquals("testBookmarkBarAddOneLink", link5.getText(), "testMark5");
        assertEquals("testBookmarkBarAddOneLink", link5.getHref(), "testAddress5");
    }
}

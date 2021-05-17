package commands;

import gui.DefaultScreen.DefaultScreen;
import gui.Objects.GUILink;

public class AddBookmarkOperation implements BrowsrOperation {

    private final String name;        // the name off the bookmark
    private final String address;     // the address off the bookmark

    /**
     * Create the add bookmark operation
     * @param name      The name off the new bookmark
     * @param address   The address off the bookmark
     */
    public AddBookmarkOperation(String name, String address) {
        this.name = name;
        this.address = address;
    }

    /**
     * Execute this operation
     * @param screen    The default screen needed for the operation
     */
    @Override
    public void execute(DefaultScreen screen) {
        GUILink link = new GUILink(this.name, this.address);

        link.setFontMetricsHandler(screen.getBookmarkBar().getScreen());
        link.setPageLoader(screen.getBookmarkBar().getScreen());

        screen.getBookmarkBar().getBookmarks().appendToRow(link, 0);
        screen.getBookmarkBar().getBookmarks().updateDimensions();
    }

}

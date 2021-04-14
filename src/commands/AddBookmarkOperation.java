package commands;

import gui.DefaultScreen.DefaultScreen;
import gui.Objects.GUILink;

public class AddBookmarkOperation implements BrowsrOperation {

    private String name;
    private String address;

    public AddBookmarkOperation(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public void execute(DefaultScreen screen) {
        GUILink link = new GUILink(this.name, this.address);

        link.setFontMetricsHandler(screen.getBookmarkBar().getScreen());
        link.setPageLoader(screen.getBookmarkBar().getScreen());

        screen.getBookmarkBar().getBookmarks().appendToRow(link, 0);
        screen.getBookmarkBar().getBookmarks().updateDimensions();
    }

}

package commands;

import gui.DefaultScreen.DefaultScreen;

/**
 * an interface for a browsr operation
 */
public interface BrowsrOperation {
    /**
     * Execute the operation
     * @param screen    The default screen needed for the operation
     */
    void execute(DefaultScreen screen);
}

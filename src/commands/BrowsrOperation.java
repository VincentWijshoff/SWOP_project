package commands;

import gui.Screen;

/**
 * an interface for a browsr operation
 */
public interface BrowsrOperation {
    /**
     * Execute the operation
     * @param screen    The default screen needed for the operation
     */
    void execute(Screen screen);

    /**
     * Checks if the specific screen is used to execute the operation
     * @param screen    the screen that is possibly needed
     * @return          true if the given screen is needed to execute the operation
     */
    boolean uses(Screen screen);
}

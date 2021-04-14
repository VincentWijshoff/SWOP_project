package events;

/**
 * An interface for GUIObjects to load a page
 */
public interface PageLoader {

    /**
     * Load a new webpage via url
     * @param url   A full  url starting with http:// or a href
     */
    void load(String url);
}

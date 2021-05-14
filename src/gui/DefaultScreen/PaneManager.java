package gui.DefaultScreen;

import events.FontMetricsHandler;
import events.PageLoader;

public interface PaneManager {
    PageLoader getPageLoader();

    FontMetricsHandler getFontMetricsHandler();

    void setPane(Pane pane);

    void load(String url);

    /**
     * set the address in the address bar
     * @param address the address to set
     */
    void setAddress(String address);

    /**
     * @return the address that is currently in the address bar
     */
    String getAddress();
}

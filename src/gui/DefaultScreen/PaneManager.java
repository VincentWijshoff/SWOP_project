package gui.DefaultScreen;

import events.FontMetricsHandler;
import events.PageLoader;

public interface PaneManager {
    PageLoader getPageLoader();

    FontMetricsHandler getFontMetricsHandler();

    void setPane(Pane pane);

    void load(String url);
}

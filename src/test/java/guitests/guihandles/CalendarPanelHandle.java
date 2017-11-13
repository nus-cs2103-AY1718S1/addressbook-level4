package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

//@@author yilun-zhu
/**
 * A handler for the {@code CalendarPanel} of the UI.
 */
public class CalendarPanelHandle extends NodeHandle<Node> {

    public static final String CALENDAR_ID = "#calendar";

    private boolean isWebViewLoaded = true;

    public CalendarPanelHandle(Node calendarPanelNode) {
        super(calendarPanelNode);

        WebView webView = getChildNode(CALENDAR_ID);
        WebEngine engine = webView.getEngine();
        new GuiRobot().interact(() -> engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.RUNNING) {
                isWebViewLoaded = false;
            } else if (newState == Worker.State.SUCCEEDED) {
                isWebViewLoaded = true;
            }
        }));
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(CALENDAR_ID));
    }

    /**
     * Returns true if the calendarPanel is done loading a page, or if this calendarPanel has yet to load any page.
     */
    public boolean isLoaded() {
        return isWebViewLoaded;
    }
}

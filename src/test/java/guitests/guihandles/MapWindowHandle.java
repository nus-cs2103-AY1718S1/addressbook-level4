package guitests.guihandles;

import static seedu.address.ui.MapWindow.TITLE;

import java.net.URL;

import guitests.GuiRobot;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

//@@author liliwei25
/**
 * A handler for the {@code MapWindow} of the UI.
 */
public class MapWindowHandle extends StageHandle {
    private static final String BROWSER_ID = "#map";

    private boolean isWebViewLoaded;

    public MapWindowHandle(Stage mapWindowStage) {
        super(mapWindowStage);
        WebView webView = getChildNode(BROWSER_ID);
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
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(BROWSER_ID));
    }

    /**
     * Returns true if the browser is done loading a page, or if this browser has yet to load any page.
     */
    public boolean isLoaded() {
        return isWebViewLoaded;
    }
}

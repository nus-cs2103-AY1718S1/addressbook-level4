package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.stage.Stage;

//@@author yewshengkai
/**
 * A handle to the {@code AboutWindow} of the application.
 */
public class AboutWindowHandle extends StageHandle {

    public static final String ABOUT_WINDOW_TITLE = "About";

    private static final String ABOUT_WINDOW_BROWSER_ID = "#browser";

    public AboutWindowHandle(Stage aboutWindowStage) {
        super(aboutWindowStage);
    }

    /**
     * Returns true if a about window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(ABOUT_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(ABOUT_WINDOW_BROWSER_ID));
    }
}

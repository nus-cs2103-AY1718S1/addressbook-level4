package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code ThemesWindow} of the application.
 */
public class ThemesWindowHandle extends StageHandle {

    public static final String THEMES_WINDOW_TITLE = "Themes";

    private static final String THEMES_WINDOW_BROWSER_ID = "#browser";

    public ThemesWindowHandle(Stage themesWindowStage) {
        super(themesWindowStage);
    }

    /**
     * Returns true if a themes window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(THEMES_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(THEMES_WINDOW_BROWSER_ID));
    }
}

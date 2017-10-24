package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.stage.Stage;


/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class CalendarWindowHandle extends StageHandle {

    public static final String CALENDAR_WINDOW_TITLE = "Calendar";

    private static final String CALENDAR_WINDOW_BROWSER_ID = "#browser";

    public CalendarWindowHandle(Stage helpWindowStage) {
        super(helpWindowStage);
    }

    /**
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(CALENDAR_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(CALENDAR_WINDOW_BROWSER_ID));
    }
}

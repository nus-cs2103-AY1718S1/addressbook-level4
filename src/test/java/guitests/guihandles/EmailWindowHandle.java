package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;

import java.net.URL;


/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class EmailWindowHandle extends StageHandle {

    public static final String EMAIL_WINDOW_TITLE = "Email";

    private static final String EMAIL_WINDOW_BROWSER_ID = "#browser";

    public EmailWindowHandle(Stage stage) {
        super(stage);
    }

    /**
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(EMAIL_WINDOW_TITLE);
    }


    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(EMAIL_WINDOW_BROWSER_ID));
    }
}

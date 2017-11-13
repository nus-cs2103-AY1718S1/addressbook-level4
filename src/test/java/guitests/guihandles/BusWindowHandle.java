//@@author jshoung

package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code BusWindow} of the application.
 */
public class BusWindowHandle extends StageHandle {

    public static final String BUS_WINDOW_TITLE = "Bus Routes";

    private static final String BUS_WINDOW_BROWSER_ID = "#browser";

    public BusWindowHandle(Stage busWindowStage) {
        super(busWindowStage);
    }

    /**
     * Returns true if a bus window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(BUS_WINDOW_TITLE);
    }
}

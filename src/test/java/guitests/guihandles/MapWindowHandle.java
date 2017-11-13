//@@author jshoung

package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code MapWindow} of the application.
 */
public class MapWindowHandle extends StageHandle {

    public static final String MAP_WINDOW_TITLE = "NUS Map";

    private static final String MAP_WINDOW_BROWSER_ID = "#browser";

    public MapWindowHandle(Stage mapWindowStage) {
        super(mapWindowStage);
    }

    /**
     * Returns true if a map window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(MAP_WINDOW_TITLE);
    }
}

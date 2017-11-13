package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code ViewAliasWindow} of the application.
 */
public class ViewAliasWindowHandle extends StageHandle {

    public static final String VIEWALIAS_WINDOW_TITLE = "View Aliases";


    public ViewAliasWindowHandle(Stage viewAliasWindowStage) {

        super(viewAliasWindowStage);
    }

    /**
     * Returns true if a viewalias window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(VIEWALIAS_WINDOW_TITLE);
    }
}

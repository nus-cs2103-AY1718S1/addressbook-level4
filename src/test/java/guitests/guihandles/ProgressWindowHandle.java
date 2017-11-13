//@@author danielbrzn
package guitests.guihandles;

import guitests.GuiRobot;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.stage.Stage;
import seedu.address.ui.ProgressWindow;

/**
 * A handle to the {@code ProgressWindow} of the application.
 */
public class ProgressWindowHandle extends StageHandle {

    public static final String PROGRESS_WINDOW_TITLE = "Import Progress";

    public ProgressWindowHandle(Stage progWindowStage) {
        super(progWindowStage);
    }

    /**
     * Returns true if a progress window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(PROGRESS_WINDOW_TITLE);
    }

    /**
     * Returns the progress of the currently shown progress window.
     */
    public ReadOnlyDoubleProperty getProgress(ProgressWindow progWindow) {
        return progWindow.getProgress();
    }
}

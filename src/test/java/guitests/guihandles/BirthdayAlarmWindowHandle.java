package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class BirthdayAlarmWindowHandle extends StageHandle {

    public static final String BirthdayAlarm_WINDOW_TITLE = "Birthday Alarm";

    public BirthdayAlarmWindowHandle(Stage BirthdayAlarmWindowStage) {
        super(BirthdayAlarmWindowStage);
    }

    /**
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(BirthdayAlarm_WINDOW_TITLE);
    }
}

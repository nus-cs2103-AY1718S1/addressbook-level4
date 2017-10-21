package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;

/**
 * To have a pop up window to remind user about the meetings they have today
 */
public class MeetingAlert extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(MeetingAlert.class);
    private static final String ICON = "/images/alert_icon.png";
    private static final String FXML = "MeetingAlert.fxml";
    private static final String TITLE = "REMINDER!!";

    private final Stage dialogStage;

    public MeetingAlert() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaxHeight(600);
        dialogStage.setMaxWidth(1000);
        dialogStage.setX(475);
        dialogStage.setY(300);
        FxViewUtil.setStageIcon(dialogStage, ICON);

    }
    /**
     * Shows the Alert window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing alert page about the application.");
        dialogStage.showAndWait();
    }
    @FXML
    private void handleExit() {
        dialogStage.close();
    }
}

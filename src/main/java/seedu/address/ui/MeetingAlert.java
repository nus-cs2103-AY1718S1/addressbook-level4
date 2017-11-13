package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.meeting.ReadOnlyMeeting;
//@@author Melvin-leo
/**
 * To have a pop up window to remind user about the meetings they have today
 */
public class MeetingAlert extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(MeetingAlert.class);
    private static final String ICON = "/images/alert_icon.png";
    private static final String FXML = "MeetingAlert.fxml";
    private static final String TITLE = "REMINDER!!";
    private static final String MESSAGE = "Your Next Meeting is : ";

    private final Stage dialogStage;

    @FXML
    private Label warningMessage;

    @FXML
    private Label firstMeeting;

    @FXML
    private Label nameMeeting;


    public MeetingAlert(ObservableList<ReadOnlyMeeting> list) {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaxHeight(600);
        dialogStage.setMaxWidth(1000);
        dialogStage.setX(475);
        dialogStage.setY(300);
        FxViewUtil.setStageIcon(dialogStage, ICON);
        warningMessage.setText(MESSAGE);
        if (isGroupMeeting(list)) {
            int indexDate = list.get(0).getDate().toString().indexOf(' ');
            firstMeeting.setText("Group Meeting at "
                    + list.get(0).getDate().toString().substring(indexDate + 1) + " for");
            nameMeeting.setText(list.get(0).getName().toString());
        } else {
            int indexDate = list.get(0).getDate().toString().indexOf(' ');
            firstMeeting.setText("Meeting with " + list.get(0).getPersonsMeet().get(0).getName().toString()
                    + " at " + list.get(0).getDate().toString().substring(indexDate + 1) + " for");
            nameMeeting.setText(list.get(0).getName().toString());
        }
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
    /**
    *Get the number of individual meetings to be shown to user
     */
    private boolean isGroupMeeting(ObservableList<ReadOnlyMeeting> list) {
        if (list.get(0).getPersonsMeet().size() > 1) {
            return true;
        }
        return false;
    }
    @FXML
    private void handleExit() {
        dialogStage.close();
    }
}

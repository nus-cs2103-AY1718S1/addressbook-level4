//@@author 17navasaw
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.schedule.Schedule;

/**
 * Controller for a reminder pop-up
 */
public class ReminderWindow extends UiPart<Region> {

    private static final String FXML = "ReminderWindow.fxml";
    private static final String TITLE = "Reminder";
    private static final String ICON = "/images/info_icon.png";
    private static final Logger logger = LogsCenter.getLogger(ReminderWindow.class);

    private final Stage dialogStage;

    @FXML
    private ListView<ScheduleCard> reminderSchedulesListView;
    @FXML
    private StackPane reminderWindowBottom;

    public ReminderWindow(ObservableList<Schedule> reminderSchedulesList) {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        FxViewUtil.setStageIcon(dialogStage, ICON);

        //set connections
        reminderSchedulesListView.prefWidthProperty().bind(reminderWindowBottom.prefWidthProperty());
        setConnections(reminderSchedulesList);
    }

    /**
     * Creates a list of {@code ScheduleCard} from {@code scheduleList}, sets them to the {@code scheduleCardListView}
     * and adds listener to {@code scheduleCardListView} for selection change.
     */
    private void setConnections(ObservableList<Schedule> scheduleList) {
        for (Schedule i : scheduleList) {
            logger.info(i.toString() + " Index: " + scheduleList.indexOf(i));
        }
        ObservableList<ScheduleCard> mappedList = EasyBind.map(
                scheduleList, (schedule) -> new ScheduleCard(schedule, scheduleList.indexOf(schedule) + 1));
        reminderSchedulesListView.setItems(mappedList);
        reminderSchedulesListView.setCellFactory(listView -> new ScheduleCardListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ScheduleCard}.
     */
    class ScheduleCardListViewCell extends ListCell<ScheduleCard> {

        @Override
        protected void updateItem(ScheduleCard schedule, boolean empty) {
            super.updateItem(schedule, empty);

            if (empty || schedule == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(schedule.getRoot());
            }
        }
    }

    /**
     * Shows the reminder window.
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
        logger.fine("Showing help page about the application.");
        // Set dimensions
        dialogStage.setWidth(620);
        dialogStage.setHeight(620);

        dialogStage.showAndWait();
    }
}

package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

//import com.google.common.eventbus.Subscribe;

//import javafx.application.Platform;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
//import seedu.address.commons.events.ui.JumpToScheduleListRequestEvent;
import seedu.address.commons.events.ui.SchedulePanelSelectionChangedEvent;
import seedu.address.model.schedule.ReadOnlySchedule;



/**
 * Panel containing the list of schedules.
 */
public class ScheduleListPanel extends UiPart<Region> {
    private static final String FXML = "ScheduleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ScheduleListPanel.class);

    @FXML
    private ListView<ScheduleCard> scheduleListView;

    public ScheduleListPanel(ObservableList<ReadOnlySchedule> scheduleList) {
        super(FXML);
        setConnections(scheduleList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlySchedule> scheduleList) {
        ObservableList<ScheduleCard> mappedList = EasyBind.map(
                scheduleList, (schedule) -> new ScheduleCard(schedule, scheduleList.indexOf(schedule) + 1));
        scheduleListView.setItems(mappedList);
        scheduleListView.setCellFactory(listView -> new scheduleListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        scheduleListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in schedule list panel changed to : '" + newValue + "'");
                        raise(new SchedulePanelSelectionChangedEvent(newValue));
                    }
                });
    }



    /**
     * Scrolls to the {@code ScheduleCard} at the {@code index} and selects it.
     */
    /* Both methods should be disabled until tests for this schedule list panel is done.
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            scheduleListView.scrollTo(index);
            scheduleListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToScheduleListRequestEvent(JumpToScheduleListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }
    */

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ScheduleCard}.
     */
    class scheduleListViewCell extends ListCell<ScheduleCard> {

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

}

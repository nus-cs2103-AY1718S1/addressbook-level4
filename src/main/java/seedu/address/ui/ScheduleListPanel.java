package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.event.ReadOnlyEvent;

//@@author reginleiff
/**
 * Panel containing the list of events in the schedule.
 *
 */
public class ScheduleListPanel extends UiPart<Region> {
    private static final String FXML = "ScheduleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ScheduleListPanel.class);

    @FXML
    private ListView<ScheduleListCard> scheduleListView;

    public ScheduleListPanel(ObservableList<ReadOnlyEvent> eventList) {
        super(FXML);
        setConnections(eventList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyEvent> eventList) {
        ObservableList<ScheduleListCard> mappedList = EasyBind.map(eventList, (event) -> new ScheduleListCard(event));
        scheduleListView.setItems(mappedList);
        scheduleListView.setCellFactory(listView -> new ScheduleListViewCell());
        scheduleListView.setOrientation(Orientation.HORIZONTAL);
        logger.info("UI ------ Got eventList with " + eventList.size() + " events.");
    }

    /**
     * Upon receiving an AddressBookChangedEvent, update the event list accordingly.
     */
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        ObservableList<ReadOnlyEvent> eventList = abce.data.getSchedule(abce.data.getCurrentDate());
        ObservableList<ScheduleListCard> mappedList = EasyBind.map(eventList, (event) -> new ScheduleListCard(event));
        scheduleListView.setItems(mappedList);
    }

    /**
     * Scrolls to the {@code ScheduleListCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            scheduleListView.scrollTo(index);
            scheduleListView.getSelectionModel().clearAndSelect(index);
        });
    }
    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ScheduleListCard}.
     */
    class ScheduleListViewCell extends ListCell<ScheduleListCard> {

        @Override
        protected void updateItem(ScheduleListCard event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(event.getRoot());
            }
        }
    }
}

package seedu.address.ui;

import java.util.logging.Logger;

import javax.smartcardio.Card;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import org.fxmisc.easybind.EasyBind;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.schedule.Schedule;

/**
 * Panel containing the list of persons.
 */
public class AgendaPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<ScheduleCard> scheduleCardListView;

    public PersonListPanel(ObservableList<Schedule> scheduleList) {
        super(FXML);
        setConnections(scheduleList);
        registerAsAnEventHandler(this);
    }

    /**
     * Creates a list of {@code ScheduleCard} from {@code scheduleList}, sets them to the {@code scheduleCardListView}
     * and adds listener to {@code scheduleCardListView} for selection change.
     */
    private void setConnections(ObservableList<Schedule> scheduleList) {
        ObservableList<Schedule> mappedList = EasyBind.map(
                scheduleList, (schedule) -> new ScheduleCard(schedule, scheduleList.indexOf(schedule) + 1));
        scheduleCardListView.setItems(mappedList);
        scheduleCardListView.setCellFactory(listView -> new AgendaPanel.ScheduleCardListViewCell());
        //addListenerForSelectionChangeEvent();
    }

    /*
    /**
     *
     * Adds a listener to {@code personListView} so that
     * selected item raises {@code PersonPanelSelectionChangedEvent}.
     */
    /*   private void addListenerForSelectionChangeEvent() {
        scheduleCardListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new AgendaPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    /*    private void scrollTo(int index) {
        Platform.runLater(() -> {
            scheduleCardListView.scrollTo(index);
            scheduleCardListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }
    */
    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ScheduleCard}.
     */
    class ScheduleCardListViewCell extends ListCell<Schedule> {

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

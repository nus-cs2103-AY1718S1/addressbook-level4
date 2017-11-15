package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

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
 * Panel containing the list of events in the timetable.
 *
 */
public class TimetableListPanel extends UiPart<Region> {
    private static final String FXML = "TimetableListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TimetableListPanel.class);

    @FXML
    private ListView<TimetableListCard> timetableListView;

    public TimetableListPanel(ObservableList<ReadOnlyEvent> eventList) {
        super(FXML);
        setConnections(eventList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyEvent> eventList) {
        ObservableList<TimetableListCard> mappedList = EasyBind.map(eventList, (event)
            -> new TimetableListCard(event).setWidth());
        timetableListView.setItems(mappedList);
        timetableListView.setCellFactory(listView -> new TimetableListViewCell());
        timetableListView.setOrientation(Orientation.HORIZONTAL);
        logger.info("UI ------ Got timetable with " + eventList.size() + " events.");
    }

    /**
     * Upon receiving an AddressBookChangedEvent, update the timetable accordingly.
     */
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        ObservableList<ReadOnlyEvent> eventList = abce.data.getTimetable(abce.data.getCurrentDate());
        ObservableList<TimetableListCard> mappedList = EasyBind.map(eventList, (event)
            -> new TimetableListCard(event).setWidth());
        timetableListView.setItems(mappedList);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TimetableListCard}.
     */
    class TimetableListViewCell extends ListCell<TimetableListCard> {

        @Override
        protected void updateItem(TimetableListCard event, boolean empty) {
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

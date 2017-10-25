package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.MeetingPanelSelectionChangedEvent;
import seedu.address.model.meeting.ReadOnlyMeeting;
/**
 * Panel containing the list of meetings.
 */
public class MeetingListPanel extends UiPart<Region> {
    private static final String FXML = "MeetingListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MeetingListPanel.class);

    @FXML
    private ListView<MeetingCard> meetingListView;

    public MeetingListPanel(ObservableList<ReadOnlyMeeting> meetingList) {
        super(FXML);
        setConnections(meetingList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyMeeting> meetingList) {
        ObservableList<MeetingCard> mappedMeetingList = EasyBind.map(
                meetingList, (meeting) -> new MeetingCard(meeting, meetingList.indexOf(meeting) + 1));
        meetingListView.setItems(mappedMeetingList);
        meetingListView.setCellFactory(listView -> new MeetingListPanel.MeetingListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        meetingListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in meeting list panel changed to : '" + newValue + "'");
                        raise(new MeetingPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code MeetingCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            meetingListView.scrollTo(index);
            meetingListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code MeetingCard}.
     */
    class MeetingListViewCell extends ListCell<MeetingCard> {

        @Override
        protected void updateItem(MeetingCard meeting, boolean empty) {
            super.updateItem(meeting, empty);

            if (empty || meeting == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(meeting.getRoot());
            }
        }
    }
}

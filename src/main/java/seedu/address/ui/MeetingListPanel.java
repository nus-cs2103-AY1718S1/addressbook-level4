package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.meeting.Meeting;

//@@author LimYangSheng
/**
 * Panel containing the list of meetings.
 */
public class MeetingListPanel extends UiPart<Region> {
    private static final String FXML = "MeetingListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MeetingListPanel.class);

    @FXML
    private ListView<MeetingCard> meetingListView;

    public MeetingListPanel(ObservableList<Meeting> meetingList) {
        super(FXML);
        setConnections(meetingList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Meeting> meetingList) {
        ObservableList<MeetingCard> mappedList = EasyBind.map(
                meetingList, (meeting) -> new MeetingCard(meeting, meetingList.indexOf(meeting) + 1));
        meetingListView.setItems(mappedList);
        meetingListView.setCellFactory(listView -> new MeetingListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
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

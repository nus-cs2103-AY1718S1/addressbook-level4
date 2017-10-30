package seedu.address.ui;

import java.util.logging.Logger;
import java.util.stream.Collectors;

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
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.UniqueMeetingList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

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

//        ObservableList<ReadOnlyPerson> mappedList = EasyBind.map(personList, (person) -> new Person(person));
//        ObservableList<Meeting> meetingList;
//        EasyBind.listBind(meetingList, getMeetingList(mappedList));
//        ObservableList<MeetingCard> displayList = EasyBind.map(
//                getMeetingList(mappedList), (meeting) ->
//                        new MeetingCard(meeting, getMeetingList(mappedList).indexOf(meeting) + 1));
//        meetingListView.setItems(displayList);
//        meetingListView.setCellFactory(listView -> new MeetingListViewCell());
    }

//    private void updateMeetingList(UniqueMeetingList meetingList) {
//
//        ObservableList<MeetingCard> displayList = meetingList.stream().map(meeting -> new MeetingCard(meeting, meetingList.indexOf(meeting) + 1)).collect(Collectors.toList());
//                forEach((meeting -> new MeetingCard(meeting, meetingList.indexOf(meeting) + 1)));
//    }

//    public UniqueMeetingList getMeetingList(ObservableList<ReadOnlyPerson> personList) {
//        UniqueMeetingList meetingList = new UniqueMeetingList();
//        for (ReadOnlyPerson person : personList) {
//            for (Meeting meeting : person.getMeetings()) {
//                try {
//                    meetingList.add(meeting);
//                } catch (UniqueMeetingList.DuplicateMeetingException e) {
//                    throw new AssertionError("Meetings should all be unique" + e);
//                }
//            }
//        }
//        return meetingList;
//    }

//    @Subscribe
//    private void handleUpdateMeetingListUIEvent(updateMeetingListUIEvent event) {
//        UniqueMeetingList meetingList = getMeetingList(event.getPersonList);
//        updateMeetingList(meetingList);
//    }

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

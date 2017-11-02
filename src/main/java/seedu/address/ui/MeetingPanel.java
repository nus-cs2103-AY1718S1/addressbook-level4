package seedu.address.ui;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;

/**
 *  UI component containing a listview to show list of meetings
 */
public class MeetingPanel extends UiPart<Region> {

    private static final String FXML = "MeetingPanel.fxml";

    private Logic logic;

    @FXML
    private ListView<MeetingPanelCard> meetingList;

    public MeetingPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        setConnections();
    }

    private void setConnections() {
        ObservableList<MeetingPanelCard> mappedMeetingList = EasyBind.map(
                logic.getMeetingList(), (item) -> new MeetingPanelCard(item, logic.getMeetingNames(item)));
        meetingList.setItems(mappedMeetingList);
        meetingList.setCellFactory(listView -> new MeetingListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code MeetingPanelCard}.
     */
    class MeetingListViewCell extends ListCell<MeetingPanelCard> {

        @Override
        protected void updateItem(MeetingPanelCard item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(item.getRoot());
            }
        }
    }
}

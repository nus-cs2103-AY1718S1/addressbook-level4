package guitests.guihandles;

import javafx.scene.control.ListView;
import seedu.address.ui.MeetingCard;

//@@author LimYangSheng
/**
 * Provides a handle for {@code MeetingListPanel} containing the list of {@code MeetingCard}.
 */
public class MeetingListPanelHandle extends NodeHandle<ListView<MeetingCard>> {
    public static final String MEETING_LIST_VIEW_ID = "#meetingListView";

    public MeetingListPanelHandle(ListView<MeetingCard> meetingListPanelNode) {
        super(meetingListPanelNode);
    }

}

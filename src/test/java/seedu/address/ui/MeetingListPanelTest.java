package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETING;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetings;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysMeeting;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.MeetingCardHandle;
import guitests.guihandles.MeetingListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.meeting.ReadOnlyMeeting;

public class MeetingListPanelTest extends GuiUnitTest {

    private static final ObservableList<ReadOnlyMeeting> TYPICAL_MEETINGS =
            FXCollections.observableList(getTypicalMeetings());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_MEETING);

    private MeetingListPanelHandle meetingListPanelHandle;

    @Before
    public void setUp() {
        MeetingListPanel meetingListPanel = new MeetingListPanel(TYPICAL_MEETINGS);
        uiPartRule.setUiPart(meetingListPanel);

        meetingListPanelHandle = new MeetingListPanelHandle(getChildNode(meetingListPanel.getRoot(),
                MeetingListPanelHandle.MEETING_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_MEETINGS.size(); i++) {
            meetingListPanelHandle.navigateToCard(TYPICAL_MEETINGS.get(i));
            ReadOnlyMeeting expectedMeeting = TYPICAL_MEETINGS.get(i);
            MeetingCardHandle actualCard = meetingListPanelHandle.getMeetingCardHandle(i);

            assertCardDisplaysMeeting(expectedMeeting, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        MeetingCardHandle expectedCard =
                meetingListPanelHandle.getMeetingCardHandle(INDEX_SECOND_MEETING.getZeroBased());
        MeetingCardHandle selectedCard = meetingListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}

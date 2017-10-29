package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalGroups.getTypicalGroups;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GROUP;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysGroup;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.GroupCardHandle;
import guitests.guihandles.GroupListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToGroupListRequestEvent;
import seedu.address.model.group.ReadOnlyGroup;

public class GroupListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyGroup> TYPICAL_GROUPS =
            FXCollections.observableList(getTypicalGroups());

    private static final JumpToGroupListRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToGroupListRequestEvent(INDEX_SECOND_GROUP);

    private GroupListPanelHandle groupListPanelHandle;

    @Before
    public void setUp() {
        GroupListPanel groupListPanel = new GroupListPanel(TYPICAL_GROUPS);
        uiPartRule.setUiPart(groupListPanel);

        groupListPanelHandle = new GroupListPanelHandle(getChildNode(groupListPanel.getRoot(),
                GroupListPanelHandle.GROUP_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_GROUPS.size(); i++) {
            groupListPanelHandle.navigateToCard(TYPICAL_GROUPS.get(i));
            ReadOnlyGroup expectedGroup = TYPICAL_GROUPS.get(i);
            GroupCardHandle actualCard = groupListPanelHandle.getGroupCardHandle(i);

            assertCardDisplaysGroup(expectedGroup, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToGroupListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        GroupCardHandle expectedCard = groupListPanelHandle.getGroupCardHandle(INDEX_SECOND_GROUP.getZeroBased());
        GroupCardHandle selectedCard = groupListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}

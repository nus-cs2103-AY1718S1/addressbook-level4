//@@author conantteo
package seedu.address.ui;

import static seedu.address.testutil.TypicalTags.getTypicalTags;
import static seedu.address.ui.testutil.GuiTestAssert.assertGroupLabelDisplayTag;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.GroupLabelHandle;
import guitests.guihandles.GroupListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.tag.Tag;

public class GroupListPanelTest extends GuiUnitTest {
    private static final ObservableList<Tag> TYPICAL_TAGS = FXCollections.observableList(getTypicalTags());
    private GroupListPanelHandle groupListPanelHandle;

    @Before
    public void setUp() {
        GroupListPanel groupListPanel = new GroupListPanel(TYPICAL_TAGS);
        uiPartRule.setUiPart(groupListPanel);

        groupListPanelHandle = new GroupListPanelHandle(getChildNode(groupListPanel.getRoot(),
                GroupListPanelHandle.GROUP_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_TAGS.size(); i++) {
            groupListPanelHandle.navigateToTag(TYPICAL_TAGS.get(i));
            Tag expectedTag = TYPICAL_TAGS.get(i);
            GroupLabelHandle actualTagBox = groupListPanelHandle.getGroupLabelHandle(i);

            assertGroupLabelDisplayTag(expectedTag, actualTagBox);
        }
    }
}

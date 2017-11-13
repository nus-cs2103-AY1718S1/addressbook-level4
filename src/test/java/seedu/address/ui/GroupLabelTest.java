//@@author conantteo
package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalTags.CLASSMATES;
import static seedu.address.testutil.TypicalTags.FACEBOOK;
import static seedu.address.testutil.TypicalTags.FRIENDS;
import static seedu.address.testutil.TypicalTags.GOOGLE;
import static seedu.address.testutil.TypicalTags.OWE_MONEY;
import static seedu.address.ui.testutil.GuiTestAssert.assertGroupLabelDisplayTag;

import org.junit.Test;

import guitests.guihandles.GroupLabelHandle;
import seedu.address.model.tag.Tag;

public class GroupLabelTest extends GuiUnitTest {

    private Tag testTag;
    private GroupLabel testGroupLabel;

    @Test
    public void display() {
        // Label with a tag
        testTag = GOOGLE;
        testGroupLabel = new GroupLabel(testTag);
        uiPartRule.setUiPart(testGroupLabel);

        assertTagBoxDisplay(testGroupLabel, testTag);

        // Changes label to reflect another new tag
        Tag anotherTag = FACEBOOK;
        testGroupLabel = new GroupLabel(anotherTag);
        uiPartRule.setUiPart(testGroupLabel);

        assertTagBoxDisplay(testGroupLabel, anotherTag);
    }

    @Test
    public void mouseEventHandler_tagBoxIsClicked() {
        testTag = OWE_MONEY;
        testGroupLabel = new GroupLabel(testTag);
        uiPartRule.setUiPart(testGroupLabel);

        GroupLabelHandle groupLabelHandle = new GroupLabelHandle(testGroupLabel.getRoot());
        groupLabelHandle.click();

        guiRobot.waitForEvent(groupLabelHandle::isClicked);

        assertTrue(groupLabelHandle.isClicked());
    }

    @Test
    public void equals() {
        testTag = FRIENDS;
        GroupLabel groupLabel = new GroupLabel(testTag);

        // same object -> returns true
        assertTrue(groupLabel.equals(groupLabel));

        // null -> returns false
        assertFalse(groupLabel.equals(null));

        // different types -> returns false
        Tag differentTag = CLASSMATES;
        assertFalse(groupLabel.equals(new GroupLabel(differentTag)));
    }

    /**
     * Asserts that {@code groupLabel} displays the {@code tag} correctly.
     */
    private void assertTagBoxDisplay(GroupLabel groupLabel, Tag tag) {
        guiRobot.pauseForHuman();

        GroupLabelHandle groupLabelHandle = new GroupLabelHandle(groupLabel.getRoot());

        assertGroupLabelDisplayTag(tag, groupLabelHandle);
    }
}

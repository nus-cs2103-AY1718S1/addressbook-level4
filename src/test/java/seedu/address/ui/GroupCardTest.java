package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalGroups.ALICE_GROUP;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysGroup;

import org.junit.Test;

import guitests.guihandles.GroupCardHandle;

import seedu.address.model.group.Group;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.testutil.GroupBuilder;


public class GroupCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Group group = new GroupBuilder().build();
        GroupCard groupCard = new GroupCard(group, 1);
        uiPartRule.setUiPart(groupCard);
        assertCardDisplay(groupCard, group, 1);

        Group group2 = new GroupBuilder().build();
        groupCard = new GroupCard(group2, 2);
        uiPartRule.setUiPart(groupCard);
        assertCardDisplay(groupCard, group2, 2);

        // changes made to Group reflects on card
        guiRobot.interact(() -> {
            group2.setGroupName(ALICE_GROUP.getName());
        });
        assertCardDisplay(groupCard, group2, 2);
    }

    @Test
    public void equals() {
        Group group = new GroupBuilder().build();
        GroupCard groupCard = new GroupCard(group, 0);

        // same group, same index -> returns true
        GroupCard copy = new GroupCard(group, 0);
        assertTrue(groupCard.equals(copy));

        // same object -> returns true
        assertTrue(groupCard.equals(groupCard));

        // null -> returns false
        assertFalse(groupCard.equals(null));

        // different types -> returns false
        assertFalse(groupCard.equals(0));

        // different group, same index -> returns false
        Group differentGroup = new GroupBuilder().withName("differentName").build();
        assertFalse(groupCard.equals(new GroupCard(differentGroup, 0)));

        // same group, different index -> returns false
        assertFalse(groupCard.equals(new GroupCard(group, 1)));
    }

    /**
     * Asserts that {@code groupCard} displays the details of {@code expectedGroup} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(GroupCard groupCard, ReadOnlyGroup expectedGroup, int expectedId) {
        guiRobot.pauseForHuman();

        GroupCardHandle groupCardHandle = new GroupCardHandle(groupCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", groupCardHandle.getId());

        // verify group details are displayed correctly
        assertCardDisplaysGroup(expectedGroup, groupCardHandle);
    }
}

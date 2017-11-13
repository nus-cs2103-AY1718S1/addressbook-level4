package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_BIRTHDAY;

import org.junit.Test;

import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.testutil.EditEventDescriptorBuilder;

//@@author royceljh
public class EditEventDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditEventDescriptor descriptorWithSameValues = new EditEventDescriptor(DESC_MEETING);
        assertTrue(DESC_MEETING.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_MEETING.equals(DESC_MEETING));

        // null -> returns false
        assertFalse(DESC_MEETING.equals(null));

        // different types -> returns false
        assertFalse(DESC_MEETING.equals(5));

        // different values -> returns false
        assertFalse(DESC_MEETING.equals(DESC_BIRTHDAY));

        // different header -> returns false
        EditEventDescriptor editedMeeting = new EditEventDescriptorBuilder(DESC_MEETING)
                .withHeader(VALID_HEADER_BIRTHDAY).build();
        assertFalse(DESC_MEETING.equals(editedMeeting));

        // different desc -> returns false
        editedMeeting = new EditEventDescriptorBuilder(DESC_MEETING).withDesc(VALID_DESC_BIRTHDAY).build();
        assertFalse(DESC_MEETING.equals(editedMeeting));

        // different eventDate -> returns false
        editedMeeting = new EditEventDescriptorBuilder(DESC_MEETING).withEventDate(VALID_EVENT_DATE_BIRTHDAY).build();
        assertFalse(DESC_MEETING.equals(editedMeeting));
    }
}

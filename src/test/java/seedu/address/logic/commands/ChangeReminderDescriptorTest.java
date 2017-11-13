package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAILS_MEETING;

import org.junit.Test;

import seedu.address.logic.commands.ChangeReminderCommand.ChangeReminderDescriptor;
import seedu.address.testutil.ChangeReminderDescriptorBuilder;
//@@author RonakLakhotia
public class ChangeReminderDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        ChangeReminderDescriptor descriptorWithSameValues = new ChangeReminderDescriptor(DESC_ASSIGNMENT);
        assertTrue(DESC_ASSIGNMENT.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ASSIGNMENT.equals(DESC_ASSIGNMENT));

        // null -> returns false
        assertFalse(DESC_ASSIGNMENT.equals(null));

        // different types -> returns false
        assertFalse(DESC_ASSIGNMENT.equals(5));

        // different values -> returns false
        assertFalse(DESC_ASSIGNMENT.equals(DESC_MEETING));

        // different details -> returns false
        ChangeReminderDescriptor editedAssignment = new ChangeReminderDescriptorBuilder(DESC_ASSIGNMENT)
                .withDetails(VALID_DETAILS_MEETING).build();
        assertFalse(DESC_ASSIGNMENT.equals(editedAssignment));



    }
}

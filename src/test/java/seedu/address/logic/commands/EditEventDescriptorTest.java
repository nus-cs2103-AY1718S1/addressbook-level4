package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EVENT_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EVENT_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESC_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TIME_SECOND;

import org.junit.Test;

import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.testutil.EventDescriptorBuilder;

// @@author Adoby7
/**
 * Test equality of EditEventDescriptorTest
 */
public class EditEventDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditEventDescriptor descriptorWithSameValues = new EditEventDescriptor(DESC_EVENT_FIRST);
        assertTrue(DESC_EVENT_FIRST.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_EVENT_FIRST.equals(DESC_EVENT_FIRST));

        // different types -> returns false
        assertFalse(DESC_EVENT_FIRST.equals(1));

        // different values -> returns false
        assertFalse(DESC_EVENT_FIRST.equals(DESC_EVENT_SECOND));

        // different name -> returns false
        EditEventDescriptor editedFirst = new EventDescriptorBuilder(DESC_EVENT_FIRST)
            .withName(VALID_EVENT_NAME_SECOND).build();
        assertFalse(DESC_EVENT_FIRST.equals(editedFirst));

        // different phone -> returns false
        editedFirst = new EventDescriptorBuilder(DESC_EVENT_FIRST).withDescription(VALID_EVENT_DESC_SECOND).build();
        assertFalse(DESC_EVENT_FIRST.equals(editedFirst));

        // different email -> returns false
        editedFirst = new EventDescriptorBuilder(DESC_EVENT_FIRST).withTime(VALID_EVENT_TIME_SECOND).build();
        assertFalse(DESC_EVENT_FIRST.equals(editedFirst));
    }
}

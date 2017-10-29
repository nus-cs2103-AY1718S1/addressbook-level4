package seedu.address.logic.commands.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_EVENT2;

import org.junit.Test;

import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditEventDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditEventCommand.EditEventDescriptor descriptorWithSameValues =
                new EditEventCommand.EditEventDescriptor(DESC_EVENT1);
        assertTrue(DESC_EVENT1.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_EVENT1.equals(DESC_EVENT1));

        // null -> returns false
        assertFalse(DESC_EVENT1.equals(null));

        // different types -> returns false
        assertFalse(DESC_EVENT1.equals(5));

        // different values -> returns false
        assertFalse(DESC_EVENT1.equals(DESC_EVENT2));

        // different name -> returns false
        EditEventCommand.EditEventDescriptor editedEvent1 =
                new EditEventDescriptorBuilder(DESC_EVENT1).withName(VALID_NAME_EVENT2).build();
        assertFalse(DESC_EVENT1.equals(editedEvent1));

        // different Time -> returns false
        editedEvent1 = new EditEventDescriptorBuilder(DESC_EVENT1).withTime(VALID_DATE_EVENT2).build();
        assertFalse(DESC_EVENT1.equals(editedEvent1));


        // different address -> returns false
        editedEvent1 = new EditEventDescriptorBuilder(DESC_EVENT1).withVenue(VALID_VENUE_EVENT2).build();
        assertFalse(DESC_EVENT1.equals(editedEvent1));
    }
}

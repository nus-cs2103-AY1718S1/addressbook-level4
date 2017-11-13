package seedu.address.logic.commands;

//@@author chernghann
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

public class AddEventCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null);
    }

    @Test
    public void equals() {
        Event zouk = new EventBuilder().withName("ZoukOut").build();
        AddEventCommand addZoukCommand = new AddEventCommand(zouk);

        // same object -> returns true
        assertTrue(addZoukCommand.equals(addZoukCommand));

        // different types -> returns false
        assertFalse(addZoukCommand.equals(1));

        // null -> returns false
        assertFalse(addZoukCommand.equals(null));
    }
}

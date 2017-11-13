package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DEEPAVALI;
import static seedu.address.logic.commands.CommandTestUtil.DESC_SPECTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_DEEPAVALI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_DEEPAVALI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_DEEPAVALI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_DEEPAVALI;

import org.junit.Test;

import seedu.address.testutil.EditEventDescriptorBuilder;

//@@author kaiyu92
public class EditEventDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditEventCommand.EditEventDescriptor descriptorWithSameValues =
                new EditEventCommand.EditEventDescriptor(DESC_SPECTRA);
        assertTrue(DESC_SPECTRA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_SPECTRA.equals(DESC_SPECTRA));

        // null -> returns false
        assertFalse(DESC_SPECTRA.equals(null));

        // different types -> returns false
        assertFalse(DESC_SPECTRA.equals(5));

        // different values -> returns false
        assertFalse(DESC_SPECTRA.equals(DESC_DEEPAVALI));

        // different title -> returns false
        EditEventCommand.EditEventDescriptor editedSpectra =
                new EditEventDescriptorBuilder(DESC_SPECTRA).withTitle(VALID_TITLE_DEEPAVALI).build();
        assertFalse(DESC_SPECTRA.equals(editedSpectra));

        // different description -> returns false
        editedSpectra =
                new EditEventDescriptorBuilder(DESC_SPECTRA).withDescription(VALID_DESCRIPTION_DEEPAVALI).build();
        assertFalse(DESC_SPECTRA.equals(editedSpectra));

        // different email -> returns false
        editedSpectra = new EditEventDescriptorBuilder(DESC_SPECTRA).withLocation(VALID_LOCATION_DEEPAVALI).build();
        assertFalse(DESC_SPECTRA.equals(editedSpectra));

        // different address -> returns false
        editedSpectra = new EditEventDescriptorBuilder(DESC_SPECTRA).withDatetime(VALID_DATETIME_DEEPAVALI).build();
        assertFalse(DESC_SPECTRA.equals(editedSpectra));
    }
}

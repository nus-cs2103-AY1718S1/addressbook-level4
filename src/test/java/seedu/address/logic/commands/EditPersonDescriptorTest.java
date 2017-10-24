package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LECTURER_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESLOT_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_CS2101;
import static seedu.address.logic.commands.EditCommand.EditLessonDescriptor;

import org.junit.Test;

import seedu.address.testutil.EditLessonDescriptorBuilder;

public class EditPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditLessonDescriptor descriptorWithSameValues = new EditLessonDescriptor(DESC_MA1101R);
        assertTrue(DESC_MA1101R.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_MA1101R.equals(DESC_MA1101R));

        // null -> returns false
        assertFalse(DESC_MA1101R.equals(null));

        // different types -> returns false
        assertFalse(DESC_MA1101R.equals(5));

        // different values -> returns false
        assertFalse(DESC_MA1101R.equals(DESC_CS2101));

        // different module code -> returns false
        EditLessonDescriptor editedAmy =
                new EditLessonDescriptorBuilder(DESC_MA1101R).withCode(VALID_CODE_CS2101).build();
        assertFalse(DESC_MA1101R.equals(editedAmy));

        // different class type -> returns false
        editedAmy = new EditLessonDescriptorBuilder(DESC_MA1101R).withCode(VALID_CODE_CS2101).build();
        assertFalse(DESC_MA1101R.equals(editedAmy));

        // different time slot -> returns false
        editedAmy = new EditLessonDescriptorBuilder(DESC_MA1101R).withTimeSlot(VALID_TIMESLOT_CS2101).build();
        assertFalse(DESC_MA1101R.equals(editedAmy));

        // different group -> returns false
        editedAmy = new EditLessonDescriptorBuilder(DESC_MA1101R).withGroup(VALID_GROUP_CS2101).build();
        assertFalse(DESC_MA1101R.equals(editedAmy));

        // different lecturer -> returns false
        editedAmy = new EditLessonDescriptorBuilder(DESC_MA1101R).withLecturers(VALID_LECTURER_CS2101).build();
        assertFalse(DESC_MA1101R.equals(editedAmy));

        // different location -> returns false
        editedAmy = new EditLessonDescriptorBuilder(DESC_MA1101R).withLocation(VALID_VENUE_CS2101).build();
        assertFalse(DESC_MA1101R.equals(editedAmy));
    }
}

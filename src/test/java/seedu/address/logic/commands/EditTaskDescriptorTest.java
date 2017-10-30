package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.UNQUOTED_DESCRIPTION_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NOT_URGENT;

import org.junit.Test;

import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditTaskCommand.EditTaskDescriptor descriptorWithSameValues =
                new EditTaskCommand.EditTaskDescriptor(DESC_INTERNSHIP);
        assertTrue(DESC_INTERNSHIP.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_INTERNSHIP.equals(DESC_INTERNSHIP));

        // null -> returns false
        assertFalse(DESC_INTERNSHIP.equals(null));

        // different types -> returns false
        assertFalse(DESC_INTERNSHIP.equals(5));

        // different values -> returns false
        assertFalse(DESC_INTERNSHIP.equals(DESC_PAPER));

        // different description -> returns false
        EditTaskCommand.EditTaskDescriptor editedInternship =
                new EditTaskDescriptorBuilder(DESC_INTERNSHIP).withDescription(UNQUOTED_DESCRIPTION_PAPER).build();
        assertFalse(DESC_INTERNSHIP.equals(editedInternship));

        // different start date -> returns false
        editedInternship = new EditTaskDescriptorBuilder(DESC_INTERNSHIP)
                .withStartDate(VALID_STARTDATE_GRAD_SCHOOL).build();
        assertFalse(DESC_INTERNSHIP.equals(editedInternship));

        // different deadline -> returns false
        editedInternship = new EditTaskDescriptorBuilder(DESC_INTERNSHIP).withDeadline(VALID_DEADLINE_GRAD_SCHOOL)
                .build();
        assertFalse(DESC_INTERNSHIP.equals(editedInternship));

        // different tags -> returns false
        editedInternship = new EditTaskDescriptorBuilder(DESC_INTERNSHIP).withTags(VALID_TAG_NOT_URGENT).build();
        assertFalse(DESC_INTERNSHIP.equals(editedInternship));
    }
}

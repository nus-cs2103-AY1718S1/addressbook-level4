//@@author srishag
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DESC_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_ASSIGNMENT;

import org.junit.Test;

import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskDescriptor descriptorWithSameValues = new EditTaskDescriptor(DESC_HOMEWORK);
        assertTrue(DESC_HOMEWORK.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_HOMEWORK.equals(DESC_HOMEWORK));

        // null -> returns false
        assertFalse(DESC_HOMEWORK.equals(null));

        // different types -> returns false
        assertFalse(DESC_HOMEWORK.equals(5));

        // different values -> returns false
        assertFalse(DESC_HOMEWORK.equals(DESC_BOB));

        // different name -> returns false
        EditTaskDescriptor editedHomework = new EditTaskDescriptorBuilder(DESC_HOMEWORK)
                .withHeader(VALID_HEADER_ASSIGNMENT).build();
        assertFalse(DESC_HOMEWORK.equals(editedHomework));

        // different phone -> returns false
        editedHomework = new EditTaskDescriptorBuilder(DESC_HOMEWORK).withDesc(VALID_DESC_ASSIGNMENT).build();
        assertFalse(DESC_HOMEWORK.equals(editedHomework));

        // different email -> returns false
        editedHomework = new EditTaskDescriptorBuilder(DESC_HOMEWORK).withDeadline(VALID_DEADLINE_ASSIGNMENT).build();
        assertFalse(DESC_HOMEWORK.equals(editedHomework));
    }
}

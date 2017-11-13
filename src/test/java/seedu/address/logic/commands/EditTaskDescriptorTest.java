package seedu.address.logic.commands;
//@@author Esilocke
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ADDRESS_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PAPER;

import org.junit.Test;

import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.testutil.EditTaskDescriptorBuilder;


public class EditTaskDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditTaskDescriptor descriptorWithSameValues = new EditTaskDescriptor(DESC_PENCIL);
        assertTrue(DESC_PENCIL.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_PENCIL.equals(DESC_PENCIL));

        // null -> returns false
        assertFalse(DESC_PENCIL.equals(null));

        // different types -> returns false
        assertFalse(DESC_PENCIL.equals(5));

        // different values -> returns false
        assertFalse(DESC_PENCIL.equals(DESC_PAPER));

        // different name -> returns false
        EditTaskDescriptor editedPencil = new EditTaskDescriptorBuilder(DESC_PENCIL)
                .withTaskName(VALID_TASK_NAME_PAPER).build();
        assertFalse(DESC_PENCIL.equals(editedPencil));

        // different description -> returns false
        editedPencil = new EditTaskDescriptorBuilder(DESC_PENCIL).withDescription(VALID_DESCRIPTION_PAPER).build();
        assertFalse(DESC_PENCIL.equals(editedPencil));

        // different deadline -> returns false
        editedPencil = new EditTaskDescriptorBuilder(DESC_PENCIL).withDeadline(VALID_DEADLINE_PAPER).build();
        assertFalse(DESC_PENCIL.equals(editedPencil));

        // different priority -> returns false
        editedPencil = new EditTaskDescriptorBuilder(DESC_PENCIL).withPriority(VALID_PRIORITY_PAPER).build();
        assertFalse(DESC_PENCIL.equals(editedPencil));

        // different task address -> returns false
        editedPencil = new EditTaskDescriptorBuilder(DESC_PENCIL).withTaskAddress(VALID_TASK_ADDRESS_PAPER).build();
        assertFalse(DESC_PENCIL.equals(editedPencil));
    }
}

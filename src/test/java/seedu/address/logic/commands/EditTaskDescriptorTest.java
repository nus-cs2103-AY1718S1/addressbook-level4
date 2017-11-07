package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.TaskTestUtil.TASK_AMY;
import static seedu.address.logic.commands.TaskTestUtil.TASK_BOB;
import static seedu.address.logic.commands.TaskTestUtil.VALID_DESC_BOB;
import static seedu.address.logic.commands.TaskTestUtil.VALID_DUEDATE_BOB;
import static seedu.address.logic.commands.TaskTestUtil.VALID_PRIORITY_BOB_STRING;

import org.junit.Test;

import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.testutil.EditTaskDescriptorBuilder;

//@@author JYL123
public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskDescriptor descriptorWithSameValues = new EditTaskDescriptor(TASK_AMY);
        assertTrue(TASK_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(TASK_AMY.equals(TASK_AMY));

        // null -> returns false
        assertFalse("".equals(TASK_AMY));

        // different types -> returns false
        assertFalse(TASK_AMY.equals(5));

        // different values -> returns false
        assertFalse(TASK_AMY.equals(TASK_BOB));

        // different description -> returns false
        EditTaskDescriptor editedAmy = new EditTaskDescriptorBuilder(TASK_AMY).withDescription(VALID_DESC_BOB).build();
        assertFalse(TASK_AMY.equals(editedAmy));

        // different priority -> returns false
        editedAmy = new EditTaskDescriptorBuilder(TASK_AMY).withPriority(VALID_PRIORITY_BOB_STRING).build();
        assertFalse(TASK_AMY.equals(editedAmy));

        // different duedate -> returns false
        editedAmy = new EditTaskDescriptorBuilder(TASK_AMY).withDueDate(VALID_DUEDATE_BOB).build();
        assertFalse(TASK_AMY.equals(editedAmy));
    }
}

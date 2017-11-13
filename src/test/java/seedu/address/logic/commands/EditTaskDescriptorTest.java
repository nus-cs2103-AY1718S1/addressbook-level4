//@@author yuzu1209
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.DESC_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.DESC_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HOTPOT;

import org.junit.Test;

import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskDescriptor descriptorWithSameValues = new EditTaskDescriptor(DESC_DEMO);
        assertTrue(DESC_DEMO.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_DEMO.equals(DESC_DEMO));

        // null -> returns false
        assertFalse(DESC_DEMO.equals(null));

        // different types -> returns false
        assertFalse(DESC_DEMO.equals(5));

        // different values -> returns false
        assertFalse(DESC_DEMO.equals(DESC_HOTPOT));

        // different name -> returns false
        EditTaskDescriptor editedDemo = new EditTaskDescriptorBuilder(DESC_DEMO).withName(VALID_NAME_HOTPOT).build();
        assertFalse(DESC_DEMO.equals(editedDemo));

        // different description -> returns false
        editedDemo = new EditTaskDescriptorBuilder(DESC_DEMO).withDescription(VALID_DESCRIPTION_HOTPOT).build();
        assertFalse(DESC_DEMO.equals(editedDemo));

        // different start time -> returns false
        editedDemo = new EditTaskDescriptorBuilder(DESC_DEMO).withStart(VALID_START_HOTPOT).build();
        assertFalse(DESC_DEMO.equals(editedDemo));

        // different end time -> returns false
        editedDemo = new EditTaskDescriptorBuilder(DESC_DEMO).withEnd(VALID_END_HOTPOT).build();
        assertFalse(DESC_DEMO.equals(editedDemo));

        // different tags -> returns false
        editedDemo = new EditTaskDescriptorBuilder(DESC_DEMO).withTags(VALID_TAG_HOTPOT).build();
        assertFalse(DESC_DEMO.equals(editedDemo));
    }
}

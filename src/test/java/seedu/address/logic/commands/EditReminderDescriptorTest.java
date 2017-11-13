package seedu.address.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_DATE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_TIME_ASSIGNMENT;

import org.junit.Test;

import seedu.address.logic.commands.EditReminderCommand.EditReminderDescriptor;
import seedu.address.testutil.EditReminderDescriptorBuilder;

//@@author justinpoh
public class EditReminderDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditReminderDescriptor descriptorWithSameValues = new EditReminderDescriptor(REMINDER_COFFEE);
        assertTrue(REMINDER_COFFEE.equals(descriptorWithSameValues));

        // same objects -> returns true
        assertTrue(REMINDER_COFFEE.equals(REMINDER_COFFEE));

        // null -> returns false
        assertFalse(REMINDER_COFFEE.equals(null));

        // different types -> returns false
        assertFalse(REMINDER_COFFEE.equals(5));

        // different values -> returns false
        assertFalse(REMINDER_COFFEE.equals(REMINDER_ASSIGNMENT));

        // different reminder -> returns false
        EditReminderDescriptor editedCoffee = new EditReminderDescriptorBuilder(REMINDER_COFFEE)
                .withReminder(VALID_REMINDER_ASSIGNMENT).build();
        assertFalse(REMINDER_COFFEE.equals(editedCoffee));

        // different date -> returns false;
        editedCoffee = new EditReminderDescriptorBuilder(REMINDER_COFFEE)
                .withDate(VALID_REMINDER_DATE_ASSIGNMENT).build();
        assertFalse(REMINDER_COFFEE.equals(editedCoffee));

        // different time -> returns false;
        editedCoffee = new EditReminderDescriptorBuilder(REMINDER_COFFEE)
                .withTime(VALID_REMINDER_TIME_ASSIGNMENT).build();
        assertFalse(REMINDER_COFFEE.equals(editedCoffee));
    }
}

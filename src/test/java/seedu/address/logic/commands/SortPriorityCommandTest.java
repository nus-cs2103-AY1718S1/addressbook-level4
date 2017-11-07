//@@author inGall
package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertSortSuccess;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.ReminderBuilder;

public class SortPriorityCommandTest {

    public static final int FIRST_REMINDER = 0;
    public static final int SECOND_REMINDER = 1;

    private Model model;
    private Model expectedModel;
    private SortPriorityCommand sortPriorityCommand;

    @Before
    public void setUp() {
        model = new ModelManager();
        expectedModel = new ModelManager();

        sortPriorityCommand = new SortPriorityCommand();
        sortPriorityCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_firstReminderAdded_unsorted() throws Exception {
        Reminder Breakfast = new ReminderBuilder().withTask("Breakfast").withPriority("Low").build();
        model.addReminder(Breakfast);
        assertTrue(model.getFilteredReminderList().get(FIRST_REMINDER).equals(Breakfast));
    }

    @Test
    public void execute_sameFirstReminderAfterAdded_unSorted() throws Exception {
        Reminder Breakfast = new ReminderBuilder().withTask("Breakfast").withPriority("Low").build();
        Reminder Lunch = new ReminderBuilder().withTask("Lunch").withPriority("High").build();
        model.addReminder(Breakfast);
        model.addReminder(Lunch);
        assertTrue(model.getFilteredReminderList().get(FIRST_REMINDER).equals(Breakfast));
        assertTrue(model.getFilteredReminderList().get(SECOND_REMINDER).equals(Lunch));
    }


    @Test
    public void execute_differentFirstReminderAfterSorted() throws Exception {
        Reminder Breakfast = new ReminderBuilder().withTask("Breakfast").withPriority("Low").build();
        Reminder Lunch = new ReminderBuilder().withTask("Lunch").withPriority("High").build();
        model.addReminder(Breakfast);
        model.addReminder(Lunch);
        assertSortSuccess(sortPriorityCommand, model, SortPriorityCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(model.getFilteredReminderList().get(FIRST_REMINDER).equals(Lunch));
        assertTrue(model.getFilteredReminderList().get(SECOND_REMINDER).equals(Breakfast));

    }

}

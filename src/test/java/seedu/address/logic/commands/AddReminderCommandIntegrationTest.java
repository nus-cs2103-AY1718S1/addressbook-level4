package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
import static seedu.address.testutil.TypicalReminders.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.ReminderBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddReminderCommand}.
 */
public class AddReminderCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());
    }

    @Test
    public void execute_newReminder_success() throws Exception {
        Reminder validReminder = new ReminderBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getDatabase(), new UserPrefs());
        expectedModel.addReminder(validReminder);

        assertCommandSuccess(prepareCommand(validReminder, model), model,
                String.format(AddReminderCommand.MESSAGE_SUCCESS, validReminder), expectedModel);
    }

    @Test
    public void execute_duplicateReminder_throwsCommandException() {
        Reminder reminderInList = new Reminder(model.getAddressBook().getReminderList().get(0));
        assertCommandFailure(prepareCommand(reminderInList, model), model,
                AddReminderCommand.MESSAGE_DUPLICATE_REMINDER);
    }

    /**
     * Generates a new {@code AddReminderCommand} which upon execution, adds {@code reminder} into the {@code model}.
     */
    private AddReminderCommand prepareCommand(Reminder reminder, Model model) {
        AddReminderCommand command = new AddReminderCommand(reminder);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}

package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

/**
 * Lists all persons in the address book to the user.
 */
public class ListReminderCommand extends Command {

    public static final String COMMAND_WORD = "listReminder";

    public static final String MESSAGE_SUCCESS = "Listed all reminders";


    @Override
    public CommandResult execute() {
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

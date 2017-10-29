//@@author inGall
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.reminder.ReadOnlyReminder;

/**
 * Sort reminders in order or priority.
 */
public class SortPriorityCommand extends Command {
    public static final String COMMAND_WORD = "sortPriority";

    public static final String MESSAGE_SUCCESS = "All contacts are sorted by priority. (High -> Medium -> Low)";
    public static final String MESSAGE_EMPTY_LIST = "Contact list is empty.";

    private ArrayList<ReadOnlyReminder> contactList;

    public SortPriorityCommand() {
        contactList = new ArrayList<>();
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        Boolean isEmpty = model.checkIfReminderListEmpty(contactList);
        if (!isEmpty) {
            model.sortListByPriority(contactList);
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(MESSAGE_EMPTY_LIST);
    }
}

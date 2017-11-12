package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class LinkedTasksCommand extends Command {

    public static final String COMMAND_WORD = "linkedTasks";
    public static final String COMMAND_ALIAS = "lts";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Show tasks linked the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LINKED_TASKS_SUCCESS = "Linked tasks of Person: %1$s";

    private final Index targetIndex;

    public LinkedTasksCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson targetPerson = lastShownList.get(targetIndex.getZeroBased());

        model.updateFilteredTaskList(task->task.getPeopleIds().contains(targetPerson.getId()));
        return new CommandResult(String.format(MESSAGE_LINKED_TASKS_SUCCESS, targetPerson.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LinkedTasksCommand // instanceof handles nulls
                && this.targetIndex.equals(((LinkedTasksCommand) other).targetIndex)); // state check
    }
}

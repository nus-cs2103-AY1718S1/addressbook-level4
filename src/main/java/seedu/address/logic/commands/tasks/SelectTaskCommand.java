package seedu.address.logic.commands.tasks;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.task.ReadOnlyTask;

//@@author tby1994
/**
 * Selects a task identified using it's last displayed index from the address book.
 */
public class SelectTaskCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Selects the task identified by the index number used in the last task listing.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    private final Index targetIndex;

    public SelectTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        String tag = model.getFilteredTaskList().get(targetIndex.getZeroBased()).getTags().toString()
                .replaceAll("[\\[\\](),{}]", "");
        conductSearch(tag);

        EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex.getOneBased()));

    }

    /**
     * find the tag of task in person list
     * If there are no tags or tags cannot be found in the person list, all persons are listed
     * @param tag
     */
    private void conductSearch(String tag) {
        if (!tag.isEmpty()) {
            String[] tagArray = tag.split("\\s+");
            model.updateFilteredPersonList(new PersonContainsKeywordsPredicate(Arrays.asList(tagArray)));
        } else if (tag.isEmpty() || model.getFilteredPersonList().size() < 1) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SelectTaskCommand // instanceof handles nulls
            && this.targetIndex.equals(((SelectTaskCommand) other).targetIndex)); // state check
    }
}


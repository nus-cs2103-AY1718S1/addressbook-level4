package seedu.address.logic.commands.tasks;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandUtil;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

//@@author raisa2010
/**
 * Tags multiple people in the address book.
 */
//@@author raisa2010
public class TagTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tags multiple tasks using the same tag(s) "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDICES (must be positive integers and may be one or more) "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1, 2, 3 "
            + PREFIX_TAG + "urgent";

    public static final String MESSAGE_TAG_TASKS_SUCCESS = "New tag added.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final Index[] indices;
    private final Set<Tag> newTags;

    /**
     * @param indices of the tasks in the filtered task list to tag
     * @param tagList list of tags to tag the task with
     */
    public TagTaskCommand(Index[] indices, Set<Tag> tagList) {
        requireNonNull(indices);
        requireNonNull(tagList);

        this.indices = indices;
        newTags = tagList;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        Index[] validIndices = CommandUtil.filterValidIndices(lastShownList.size(), indices);

        if (validIndices.length == 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        for (Index currentIndex : validIndices) {
            ReadOnlyTask taskToEdit = lastShownList.get(currentIndex.getZeroBased());

            try {
                model.updateTaskTags(taskToEdit, newTags);
            } catch (DuplicateTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            } catch (TaskNotFoundException pnfe) {
                throw new AssertionError("The target task cannot be missing");
            }
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_TAG_TASKS_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagTaskCommand)) {
            return false;
        }

        //state check
        for (int i = 0; i < indices.length; i++) {
            assert(indices.length == ((TagTaskCommand) other).indices.length);
            if (!indices[i].equals(((TagTaskCommand) other).indices[i])) {
                return false;
            }
        }
        return newTags.toString().equals(((TagTaskCommand) other).newTags.toString());
    }
}

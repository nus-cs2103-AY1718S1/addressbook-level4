package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Edits the details of an existing person in the address book.
 */
public class LinkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "link";
    public static final String COMMAND_ALIAS = "lk";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Link the task with the people specified "
            + "by the index number used in the last person listing.\n "
            + "Parameters: TaskIndex (must be a positive integer) "
            + PREFIX_PERSON + "personIndex "
            + "[" + PREFIX_PERSON + "personIndex]... \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PERSON + "2";


    public static final String MESSAGE_LINK_SUCCESS = "linked Task: %s with The following person(s): ";
    public static final String MESSAGE_PERSON_LINKED = "person %d already linked.";

    private final Index index;
    private final ArrayList<Index> personIndices;

    /**
     * @param index of the task in the filtered task list to edit
     * @param
     */
    public LinkCommand(Index index, ArrayList<Index> personIndices) {
        requireNonNull(index);
        requireNonNull(personIndices);

        this.index = index;
        this.personIndices = personIndices;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        int personId;

        List<ReadOnlyTask> lastShownTaskList = model.getSortedTaskList();

        ReadOnlyTask targetTask = chooseItem(lastShownTaskList, index);

        List<ReadOnlyPerson> lastShownPersonList = model.getFilteredPersonList();

        ArrayList<Integer> peopleIds = targetTask.getPeopleIds();

        String personNameList = "";

        for (Index index : personIndices) {
            personNameList += lastShownPersonList.get(index.getZeroBased()).getName() + " ";
            personId = chooseItem(lastShownPersonList, index).getId();
            if (peopleIds.contains(personId)) {
                throw new CommandException(String.format(MESSAGE_PERSON_LINKED, index.getOneBased()));
            }

            peopleIds.add(personId);
        }

        Task taskLinked = new Task(targetTask);
        taskLinked.setPeopleIds(peopleIds);

        try {
            model.updateTask(targetTask, taskLinked);
        } catch (DuplicateTaskException e) {
            throw new AssertionError("These people are already linked");
        } catch (TaskNotFoundException e) {
            throw new AssertionError("can never reach this");
        }
        return new CommandResult(String.format(MESSAGE_LINK_SUCCESS, taskLinked.getName())
                                    + personNameList);
    }

    /**
     * @param list to be chosen from
     * @param index of the item to choose
     * @return item chosen
     */
    private  static <E> E chooseItem(List<E> list, Index index) throws CommandException {
        if (index.getZeroBased() >= list.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return list.get(index.getZeroBased());
    }
}

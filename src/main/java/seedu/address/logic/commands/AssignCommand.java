package seedu.address.logic.commands;

//@@author Esilocke

import static seedu.address.logic.parser.CliSyntax.PREFIX_TARGET;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/** Assigns at least 1 person to a specified task in the Address Book**/
public class AssignCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "assign";
    public static final String COMMAND_ALIAS = "as";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns people to a task in the Address Book. "
            + "Parameters: "
            + "PERSON INDEXES... "
            + PREFIX_TARGET + "TASK ";

    public static final String MESSAGE_SUCCESS = "Assigned %1$s people to \n%2$s";
    public static final String MESSAGE_INVALID_TARGET_ARGS = "Only 1 task index should be specified";
    public static final String MESSAGE_INVALID_PERSONS_ARGS = "At least 1 person index must be specified";
    public static final String MESSAGE_NONE_ASSIGNED = "All the specified persons are already assigned to this task";

    private ArrayList<Index> personIndexes;
    private Index taskIndex;

    public AssignCommand(ArrayList<Index> personIndexes, Index taskIndex) {
        assert(personIndexes.size() > 0);
        this.personIndexes = personIndexes;
        this.taskIndex = taskIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyTask> tasksList = model.getFilteredTaskList();
        ArrayList<ReadOnlyPerson> personIndexes = createPersonsToAssign(this.personIndexes);

        if (taskIndex.getZeroBased() >= tasksList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        ReadOnlyTask assignedTask = tasksList.get(taskIndex.getZeroBased());
        try {
            model.assignToTask(personIndexes, assignedTask);
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("The specified task cannot be missing");
        } catch (DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_NONE_ASSIGNED);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, personIndexes.size(), assignedTask));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignCommand // instanceof handles nulls
                && this.personIndexes.equals(((AssignCommand) other).personIndexes)
                && this.taskIndex.equals(((AssignCommand) other).taskIndex)); // state check
    }

    /**
     * Creates a {@code ArrayList} that contains all the {@code ReadOnlyPerson} converted from the {@Code Index}
     * @throws CommandException if the specified Index is out of range
     */
    public ArrayList<ReadOnlyPerson> createPersonsToAssign (ArrayList<Index> indexes)  throws CommandException {
        HashSet<ReadOnlyPerson> addedPersons = new HashSet<>();
        ArrayList<ReadOnlyPerson> personsToAssign = new ArrayList<>();
        List<ReadOnlyPerson> personsList = model.getFilteredPersonList();
        try {
            for (Index i : personIndexes) {
                ReadOnlyPerson toAssign = personsList.get(i.getZeroBased());
                if (!addedPersons.contains(toAssign)) {
                    addedPersons.add(toAssign);
                    personsToAssign.add(toAssign);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return personsToAssign;
    }
}

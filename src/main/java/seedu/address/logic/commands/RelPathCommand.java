package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.graph.GraphWrapper;
import seedu.address.model.person.ReadOnlyPerson;

//@@author Xenonym
/**
 * Gets the shortest relationship path between two persons with the highest confidence.
 */
public class RelPathCommand extends Command {
    public static final String COMMAND_WORD = "relPath";
    public static final String COMMAND_ALIAS = "rp";

    public static final String COMMAND_PARAMETERS = "FROM_INDEX, TO_INDEX (must be positive integers)";

    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_PARAMETERS;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Gets the shortest relationship path with the highest confidence between the two persons specified by "
            + "the index numbers used in the last person listing.\n"
            + "Parameters: " + COMMAND_PARAMETERS + "\n"
            + "Example: " + COMMAND_WORD + "1 2";

    public static final String MESSAGE_PATH_FOUND = "Path found between %1$s and %2$s!";
    public static final String MESSAGE_NO_PATH = "There is no path between %1$s and %2$s.";

    private final Index from;
    private final Index to;

    public RelPathCommand(Index from, Index to) {
        requireAllNonNull(from, to);
        this.from = from;
        this.to = to;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (from.getZeroBased() >= lastShownList.size() || to.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson fromPerson = lastShownList.get(from.getZeroBased());
        ReadOnlyPerson toPerson = lastShownList.get(to.getZeroBased());
        int nodeCount = GraphWrapper.getInstance().highlightShortestPath(fromPerson, toPerson);

        if (nodeCount > 1) {
            return new CommandResult(String.format(MESSAGE_PATH_FOUND, fromPerson.getName(), toPerson.getName()));
        } else {
            return new CommandResult(String.format(MESSAGE_NO_PATH, fromPerson.getName(), toPerson.getName()));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RelPathCommand that = (RelPathCommand) o;

        return from.equals(that.from) && to.equals(that.to);
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }
}

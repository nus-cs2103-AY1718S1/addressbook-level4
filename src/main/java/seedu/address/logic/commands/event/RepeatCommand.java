//@@author shuangyang
package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Period;


/**
 * Change the repeat period of an event.
 */
public class RepeatCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "repeat";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the repeat period of the event identified "
            + "by the index number used in the last event listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[PERIOD OF REPEAT] "
            + "Example: " + COMMAND_WORD + " 1 " + " 7 ";

    public static final String MESSAGE_REPEAT_EVENT_SUCCESS = "Scheduled repeated Event: %1$s";
    public static final String MESSAGE_NOT_REPEATED = "Period of repetition must be provided.";
    public static final String MESSAGE_TIME_CLASH = "The repeated event has time clash with an existing event";

    private final Index index;
    private final Optional<Period> period;

    /**
     * @param index               of the event in the filtered event list to edit
     * @param period of repetition
     */
    public RepeatCommand(Index index, Optional<Period> period) {
        requireNonNull(index);
        requireNonNull(period);

        this.index = index;
        this.period = period;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        return new CommandResult(String.format(MESSAGE_REPEAT_EVENT_SUCCESS, period));
    }

}

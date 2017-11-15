//@@author shuang-yang
package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.Period;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.event.exceptions.EventTimeClashException;


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

    public static final String MESSAGE_REPEAT_EVENT_SUCCESS = "Scheduled event for future recurrence: %1$s";
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

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with new {@code period}.
     */
    private static Event createEditedEvent(ReadOnlyEvent eventToEdit, Optional<Period> period) {
        assert eventToEdit != null;

        Event editedEvent = new Event(eventToEdit);
        period.ifPresent(editedEvent::setPeriod);

        return editedEvent;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyEvent> lastShownList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        ReadOnlyEvent eventToEdit = lastShownList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, period);

        try {
            model.updateEvent(eventToEdit, editedEvent);
        } catch (EventNotFoundException enfe) {
            throw new AssertionError("The target event cannot be missing");
        } catch (EventTimeClashException etce) {
            throw new CommandException(MESSAGE_TIME_CLASH);
        }

        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        return new CommandResult(String.format(MESSAGE_REPEAT_EVENT_SUCCESS, editedEvent));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RepeatCommand)) {
            return false;
        }

        // state check
        RepeatCommand r = (RepeatCommand) other;
        return index.equals(r.index)
                && period.equals(r.period);
    }
}

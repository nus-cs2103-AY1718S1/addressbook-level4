package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.DeleteOnCascadeException;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.EventNotFoundException;

//@@author leonchowwenhao
/**
 * Deletes an event identified using it's last displayed index from the address book.
 */
public class DeleteEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteE";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the index number used in the last event listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";
    public static final String MESSAGE_EVENT_BEING_PARTICIPATED_FAIL = "Some person participates this event,"
            + "please disjoin all participated persons before deleting this event";

    private final Index targetIndex;
    private ReadOnlyEvent eventToDelete;

    public DeleteEventCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyEvent> lastShownList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        eventToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deleteEvent(eventToDelete);
        } catch (EventNotFoundException pnfe) {
            assert false : "The target event cannot be missing";
        } catch (DeleteOnCascadeException doce) {
            throw new CommandException(MESSAGE_EVENT_BEING_PARTICIPATED_FAIL);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEventCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteEventCommand) other).targetIndex)); // state check
    }

    @Override
    protected void undo() {
        requireAllNonNull(model, eventToDelete);
        model.addEvent(targetIndex.getZeroBased(), eventToDelete);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    protected void redo() {
        requireAllNonNull(model, eventToDelete);
        try {
            model.deleteEvent(eventToDelete);
        } catch (EventNotFoundException pnfe) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        } catch (DeleteOnCascadeException doce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
    }

    /**
     * Assign a typical event to delete
     * Can only be used for JUnit test
     */
    public void assignEvent(ReadOnlyEvent event) {
        this.eventToDelete = event;
    }
}

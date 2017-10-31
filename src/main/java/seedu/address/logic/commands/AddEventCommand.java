package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.DeleteOnCascadeException;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;

// @@author HuWanqing
/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a event to the event list. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "NAME "
            + PREFIX_EVENT_DESCRIPTION + "DESCRIPTION "
            + PREFIX_EVENT_TIME + "TIME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME + "Project Meeting "
            + PREFIX_EVENT_DESCRIPTION + "Discuss how to conduct software demo "
            + PREFIX_EVENT_TIME + "30/10/2017 ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the event list";

    private final Event toAdd;

    public AddEventCommand (ReadOnlyEvent event) {
        toAdd = new Event (event);
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addEvent(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateEventException e) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }

    @Override
    protected void undo() {
        requireNonNull(model);
        try {
            model.deleteEvent(toAdd);
        } catch (EventNotFoundException enfe) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        } catch (DeleteOnCascadeException doce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
    }

    @Override
    protected void redo() {
        requireNonNull(model);
        try {
            model.addEvent(toAdd);
        } catch (DuplicateEventException dee) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
    }
}

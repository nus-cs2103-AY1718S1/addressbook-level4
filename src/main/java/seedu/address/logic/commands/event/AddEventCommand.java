//@@author A0162268B
package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESLOT;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.EventTimeClashException;

/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "eventadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "TITLE "
            + PREFIX_TIMESLOT + "TIMING "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John's 21st Birthday "
            + PREFIX_TIMESLOT + "22/10/2017 1900-2200 "
            + PREFIX_DESCRIPTION + "johnd@example.com ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_TIME_CLASH = "This event has time clash with an existing event";

    private final Event toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code ReadOnlyEvent}
     */
    public AddEventCommand(ReadOnlyEvent event) {
        toAdd = new Event(event);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addEvent(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (EventTimeClashException e) {
            throw new CommandException(MESSAGE_TIME_CLASH);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}

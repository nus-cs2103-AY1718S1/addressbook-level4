package seedu.room.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.room.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.room.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.room.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.room.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.model.event.Event;
import seedu.room.model.event.ReadOnlyEvent;
import seedu.room.model.event.exceptions.DuplicateEventException;


//@@author sushinoya
/**
 * Adds a person to the resident book.
 */
public class AddEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addevent";
    public static final String COMMAND_ALIAS = "ae";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a event to the event book. \n"
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_LOCATION + "LOCATION "
            + "[" + PREFIX_DATETIME + "STARTTIME TO ENDTIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "End of Sem Dinner "
            + PREFIX_DESCRIPTION + "Organised by USC "
            + PREFIX_LOCATION + "Cinnamon College "
            + PREFIX_DATETIME + "25/11/2017 2030 to 2359";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the event book";
    private final Event toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyEvent}
     */
    public AddEventCommand(ReadOnlyEvent person) {
        toAdd = new Event(person);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
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
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

public class AddEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "Name "
            + PREFIX_EVENT_DESCRIPTION + "Description "
            + PREFIX_EVENT_TIME + "Time \n"
            + "Example: "
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
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }
    }

    @Override
    protected void undo() {

    }

    @Override
    protected void redo() {

    }
}

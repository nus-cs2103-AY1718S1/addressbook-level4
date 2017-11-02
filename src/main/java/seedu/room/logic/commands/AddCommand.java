package seedu.room.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.room.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.room.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.room.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.room.logic.parser.CliSyntax.PREFIX_ROOM;
import static seedu.room.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.room.logic.parser.CliSyntax.PREFIX_TEMPORARY;

import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.model.person.Person;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.exceptions.DuplicatePersonException;

/**
 * Adds a person to the resident book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the resident book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ROOM + "ROOM "
            + "[" + PREFIX_TEMPORARY + "DAY_TO_EXPIRY] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ROOM + "09-100 "
            + PREFIX_TEMPORARY + "1 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the resident book";
    public static final String MESSAGE_TEMPORARY_PERSON = "Warning: This contact will expire after your preset time";
    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public AddCommand(ReadOnlyPerson person) {
        toAdd = new Person(person);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addPerson(toAdd);
            if (toAdd.getTimestamp().getExpiryTime() != null) {
                String successMessage = String.format(MESSAGE_SUCCESS, toAdd);
                return new CommandResult(successMessage, MESSAGE_TEMPORARY_PERSON);
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}

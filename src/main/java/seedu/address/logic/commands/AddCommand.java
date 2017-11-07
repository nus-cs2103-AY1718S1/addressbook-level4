package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

//@@author LeeYingZheng
/**
 * Adds a person to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORDVAR_1 = "add";
    public static final String COMMAND_WORDVAR_2 = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + " OR "
            + COMMAND_WORDVAR_2
            + ": Adds a person to the address book. Command is case-insensitive. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_BIRTHDAY + "BIRTHDAY "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example 1: " + COMMAND_WORDVAR_1 + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_BIRTHDAY + "050595 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney \n"
            + "Example 2: " + COMMAND_WORDVAR_2.toUpperCase() + " "
            + PREFIX_NAME + "Kosaki Yamekuri "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "Yamekos@example.com "
            + PREFIX_ADDRESS + "255, Bedok Ave 2, #03-18 "
            + PREFIX_BIRTHDAY + "121287 "
            + PREFIX_TAG + "colleague "
            + PREFIX_TAG + "lunch-appointment";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    private static boolean requiresHandling;


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

        //resets AddCommand
        if (isWaitingforReply) {
            isWaitingforReply = false;
            requiresHandling = false;
        }

        /* Check if the person to add contains any duplicate fields.
         * If so, ReplyCommand to store the AddCommand to wait for further instructions.
         */
        checkDuplicateField(toAdd);

        if (isWaitingforReply) {
            requiresHandling = true;
            ReplyCommand.storeAddCommandParameter(toAdd);
            return result;

        } else {
            try {
                model.addPerson(toAdd);
                return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
            } catch (DuplicatePersonException e) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }
        }
    }

    public static boolean requiresHandling() {
        return requiresHandling;
    }

    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }

    public static void setHandlingFalse() {
        requiresHandling = false;
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.person.Address.DEFAULT_ADDRESS;
import static seedu.address.model.person.Email.DEFAULT_EMAIL;

import java.util.List;

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
    public static final String MESSAGE_DUPLICATE_FIELD = "This person's %1$s is already in use."
            + "Would you like to continue? YES or NO?";
    public static final String NAME_FIELD = "name";
    public static final String PHONE_FIELD = "phone";
    public static final String ADDRESS_FIELD = "address";
    public static final String EMAIL_FIELD = "email";

    private static boolean isWaitingforReply;
    private CommandResult result;

    private final Person toAdd;
    private String duplicateFields = "";

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public AddCommand(ReadOnlyPerson person) {
        toAdd = new Person(person);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        //resets AddCommand to eliminate unhandled Add and Reply Command
        if (isWaitingforReply) {
            isWaitingforReply = false;
        }

        /* Checks if the person to add contains any duplicate fields.
         * If so, ReplyCommand to store the AddCommand to wait for further instructions.
         */
        try {
            checkDuplicateField(toAdd);
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        /* checkDuplicateField method finds duplicate fields in the list of contacts, informs users and prompts them
         * if they wish to proceed with the add feature. ReplyCommand stores the person toAdd and wait for further
         * instructions.
         */
        if (isWaitingforReply) {
            ReplyCommand.storeAddCommandParameter(toAdd);
            return result;

        } else { //no duplicates found. AddCommand proceeds as normal.
            try {
                model.addPerson(toAdd);
                return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
            } catch (DuplicatePersonException e) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }
        }
    }

    /**
     * Checks for duplicate fields shared with {@code toAdd} in current UniCity contacts. Once duplicate(s) are found,
     * isWaitingforReply is set to true and a message is crafted to prompt users whether they want to continue
     * with the operation.
     */
    private void checkDuplicateField(Person toAdd) throws DuplicatePersonException {
        requireNonNull(model);
        List<ReadOnlyPerson> currentContacts = model.getFilteredPersonList();

        if (currentContacts.contains(toAdd)) {
            throw new DuplicatePersonException();
        }

        for (ReadOnlyPerson contact: currentContacts) {
            if (duplicateFields.contains(NAME_FIELD) && duplicateFields.contains(PHONE_FIELD)
                    && duplicateFields.contains(EMAIL_FIELD) && duplicateFields.contains(ADDRESS_FIELD)) {
                //method found duplicates for all fields, no further concatenation of message. break from loop.
                break;
            }

            /* The following conditional statements checks if duplicateFields already covers, or have yet to cover, any
             * of the fields. It will keep checking the fields which have not been listed as a duplicate until it hits
             * one or the loop ends.
             */

            if (!duplicateFields.contains(NAME_FIELD)) {
                checkDuplicateName(toAdd, contact);
            }

            if (!duplicateFields.contains(PHONE_FIELD)) {
                checkDuplicatePhone(toAdd, contact);

            }

            if (!duplicateFields.contains(ADDRESS_FIELD)) {
                checkDuplicateAddress(toAdd, contact);
            }

            if (!duplicateFields.contains(EMAIL_FIELD)) {
                checkDuplicateEmail(toAdd, contact);
            }

        }

        result = new CommandResult(String.format(MESSAGE_DUPLICATE_FIELD, duplicateFields.trim()));
    }

    /**
     * Checks for similarities between the email fields for {@code toAdd} and {@code contact}. Updates isWaitingforReply
     * and duplicateFields accordingly.
     */
    private void checkDuplicateEmail(Person toAdd, ReadOnlyPerson contact) {
        if ((toAdd.getEmail().toString().trim().equalsIgnoreCase(contact.getEmail().toString().trim()))
                && (!toAdd.getEmail().toString().trim().equals(DEFAULT_EMAIL))) {
            isWaitingforReply = true;
            if (!duplicateFields.equals("")) {
                duplicateFields += ", " + EMAIL_FIELD;
            } else {
                duplicateFields = EMAIL_FIELD;
            }
        }
    }

    /**
     * Checks for similarities between the address fields for {@code toAdd} and {@code contact}. Updates
     * isWaitingforReply and duplicateFields accordingly.
     */
    private void checkDuplicateAddress(Person toAdd, ReadOnlyPerson contact) {
        if ((toAdd.getAddress().toString().trim().equalsIgnoreCase(contact.getAddress().toString().trim()))
                && (!toAdd.getAddress().toString().trim().equals(DEFAULT_ADDRESS))) {
            isWaitingforReply = true;
            if (!duplicateFields.equals("")) {
                duplicateFields += ", " + ADDRESS_FIELD;
            } else {
                duplicateFields = ADDRESS_FIELD;
            }
        }
    }

    /**
     * Checks for similarities between the phone fields for {@code toAdd} and {@code contact}. Updates isWaitingforReply
     * and duplicateFields accordingly.
     */
    private void checkDuplicatePhone(Person toAdd, ReadOnlyPerson contact) {
        if (toAdd.getPhone().toString().trim().equalsIgnoreCase(contact.getPhone().toString().trim())) {
            isWaitingforReply = true;
            if (!duplicateFields.equals("")) {
                duplicateFields += ", " + PHONE_FIELD;
            } else {
                duplicateFields = PHONE_FIELD;
            }

        }
    }

    /**
     * Checks for similarities between the name fields for {@code toAdd} and {@code contact}. Updates isWaitingforReply
     * and duplicateFields accordingly.
     */
    private void checkDuplicateName(Person toAdd, ReadOnlyPerson contact) {
        if (toAdd.getName().toString().trim().equalsIgnoreCase(contact.getName().toString().trim())) {
            isWaitingforReply = true;
            if (!duplicateFields.equals("")) {
                duplicateFields += ", " + NAME_FIELD;
            } else {
                duplicateFields = NAME_FIELD;
            }
        }
    }

    /**
     * Checks if UniCity is awaiting for user's reply.
     */
    public static boolean isWaitingforReply() {
        return isWaitingforReply;
    }

    /**
     * Ensures continuation of AddCommand when user has replied.
     */
    public static void reply() {
        isWaitingforReply = false;
    }

    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}

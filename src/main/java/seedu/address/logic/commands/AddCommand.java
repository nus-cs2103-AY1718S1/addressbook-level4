package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String[] COMMAND_WORDS = {"add", "a", "+"};
    public static final String COMMAND_WORD = "add";

    //@@author OscarWang114
    public static final String MESSAGE_USAGE = concatenateCommandWords(COMMAND_WORDS)
            + ": Adds a person to Lisa. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            //@@author Pujitha97
            + "[" + PREFIX_DOB + "DATE OF BIRTH] "
            + "[" + PREFIX_GENDER + "GENDER] "
            //@@author
            //@@author OscarWang114
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_DOB + "20 01 1997 "
            + PREFIX_GENDER + "Male "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";
    //@@author

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in LISA";

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

    //@@author arnollim
    /**
     * Returns the Command String of that added this person into the addressbook
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(toAdd.getName())
                .append(" Phone: ")
                .append(toAdd.getPhone())
                .append("Email: ")
                .append(toAdd.getEmail())
                .append("Address: ")
                .append(toAdd.getAddress())
                .append("DateOfBirth: ")
                .append(toAdd.getDateOfBirth())
                .append("Gender: ")
                .append(toAdd.getGender())
                .append(" Tags: ");
        toAdd.getTags().forEach(builder::append);
        String person = builder.toString();
        return COMMAND_WORD + " " + person;
    }
    //@@author

    //@@author OscarWang114
    /**
     * Stores the optional details to add the person with. By default each field is an object
     * with value of empty String.
     */
    public static class AddPersonOptionalFieldDescriptor {
        private Phone phone;
        private Email email;
        private Address address;
        private DateOfBirth dateOfBirth;
        private Gender gender;

        public AddPersonOptionalFieldDescriptor() {
            this.phone = new Phone();
            this.email = new Email();
            this.address = new Address();
            this.dateOfBirth = new DateOfBirth();
            this.gender = new Gender();
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Phone getPhone() {
            return phone;
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Email getEmail() {
            return email;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Address getAddress() {
            return address;
        }
        //@@author Pujitha97
        public void setDateOfBirth(DateOfBirth dateofbirth) {
            this.dateOfBirth = dateofbirth;
        }

        public DateOfBirth getDateOfBirth() {
            return dateOfBirth;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Gender getGender() {
            return gender;
        }
        //@@author
        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AddCommand.AddPersonOptionalFieldDescriptor)) {
                return false;
            }

            // state check
            AddCommand.AddPersonOptionalFieldDescriptor a = (AddCommand.AddPersonOptionalFieldDescriptor) other;

            return getPhone().equals(a.getPhone())
                    && getEmail().equals(a.getEmail())
                    && getAddress().equals(a.getAddress())
                    && getDateOfBirth().equals(a.getDateOfBirth())
                    && getGender().equals(a.getGender());
        }
    }
}

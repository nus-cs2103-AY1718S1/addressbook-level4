package seedu.address.logic.commands;

import java.util.ArrayList;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import com.sun.org.apache.regexp.internal.RE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds a person to the address book.
 */
public class AddMultipleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "multiple";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds multiple person to the address book. "
            + "Parameters: "
            + "FILE_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + "personsToAdd.txt";
    
    public static final String MESSAGE_PERSON_FORMAT = "Person format in .txt file: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person(s) added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "The persons list contains person(s) that already exists in the address book.";
    public static final String DEFAULT_FOLDER_PATH = "./data";
    public static final String MESSAGE_INVALID_FILE = "Unable to open file '%1$s'";
    public static final String MESSAGE_ERROR_FILE = "Error reading file '%1$s'";
    
    private ArrayList<Person> toAdd;
    private ArrayList<ReadOnlyPerson> readOnlyPeople;
    
    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public AddMultipleCommand(ArrayList<ReadOnlyPerson> personsList) {
        readOnlyPeople = personsList;
        toAdd = new ArrayList<>();
        for(ReadOnlyPerson person: personsList) {
            toAdd.add(new Person(person));
        }
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        int numberOfPersonsAdded = 0;
        StringBuilder successMessage = new StringBuilder();
        requireNonNull(model);
        try {
            for(Person personToAdd: toAdd) {
                model.addPerson(personToAdd);
                successMessage.append(System.lineSeparator());
                successMessage.append(personToAdd);
                numberOfPersonsAdded++;
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, successMessage));
        } catch (DuplicatePersonException e) {
            try {
                for (int i = 0; i < numberOfPersonsAdded; i++) {
                    model.deletePerson(readOnlyPeople.get(i));
                }
            } catch(PersonNotFoundException pnfe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }
            
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMultipleCommand // instanceof handles nulls
                && toAdd.equals(((AddMultipleCommand) other).toAdd));
    }
}

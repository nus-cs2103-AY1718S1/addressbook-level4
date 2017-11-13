//@@author edwinghy
package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import java.io.IOException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.person.DisplayPicture;
import seedu.address.model.person.Person;
import seedu.address.model.person.PopularityCounter;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.XmlAddressBookStorage;

/**
 * Imports contacts to the address book.
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Import contacts to the address book."
            + "Parameters: "
            + PREFIX_PATH + "PATH";

    public static final String MESSAGE_SUCCESS = "%1$s person/s imported %2$s duplicate/s found";
    public static final String MESSAGE_INVALID_FILE = "Invalid file path %1$s.";

    private final String path;
    private final AddressBookStorage addressBookStorage;
    private AddressBook addressBook;
    private int numSuccess;
    private int numDuplicates;
    /**
     * Creates an ImportCommand to import the specified File
     */
    public ImportCommand(String path) {
        this.path = path;
        this.addressBookStorage = new XmlAddressBookStorage(path);
        numSuccess = 0;
        numDuplicates = 0;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            if (addressBookStorage.readAddressBook().isPresent()) {
                this.addressBook = new AddressBook(addressBookStorage.readAddressBook().get());
                for (ReadOnlyPerson person : this.addressBook.getPersonList()) {
                    Person personToAdd = new Person(person);
                    personToAdd.setPopularityCounter(new PopularityCounter());
                    personToAdd.setDisplayPicture(new DisplayPicture(""));
                    try {
                        model.clearSelection();
                        model.showDefaultPanel();
                        model.addPerson(personToAdd);
                        numSuccess++;
                    } catch (DuplicatePersonException e) {
                        numDuplicates++;
                    }
                }
            } else {
                throw new CommandException(String.format(MESSAGE_INVALID_FILE, path));
            }
        } catch (DataConversionException | IOException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_FILE, path));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, numSuccess, numDuplicates));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && path.equals(((ImportCommand) other).path));
    }
}
//@@author

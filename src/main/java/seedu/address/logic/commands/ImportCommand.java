//@@author PhuaJunJie
package seedu.address.logic.commands;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.api.services.people.v1.model.Person;

import seedu.address.commons.GoogleContactsBuilder;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.GoogleAuthException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.FacebookAddress;
import seedu.address.model.person.GoogleId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Imports contacts to from google contacts to addressbook
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_ALIAS = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Import contacts from google"
            + "process\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD;

    private List<Person> googleContactsList;

    /**
     * Constructor for ImportCommand (Gets the Google Contact List after successful authentication)
     */
    public ImportCommand() throws GoogleAuthException {
        try {
            GoogleContactsBuilder builder = new GoogleContactsBuilder();
            this.googleContactsList = builder.getPersonlist();
        } catch (IOException e) {
            throw new GoogleAuthException("Authentication Failed. Please login again.");
        }
    }

    /**
     * This constructor exists only for the sake of testing so as to pass through a dummy google contacts list.
     * Will not be used in the main implementation of the programme
     */
    public ImportCommand(List<Person> connections) {
        this.googleContactsList = connections;
    }



    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        String commandMessage;
        String namesNotImported = "";
        int contactsImportedCount = 0;
        int errorImportsCount = 0;

        List<ReadOnlyPerson> addressBookList = model.getAddressBook().getPersonList();

        if (this.googleContactsList == null) {
            throw new CommandException("No contacts found in Google Contacts");
        }

        for (Person googlePerson : googleContactsList) {
            if (!this.ifContactExists(addressBookList, googlePerson)) {
                try {
                    model.addPerson(this.newPerson(googlePerson));
                    contactsImportedCount++;
                } catch (IllegalValueException | NullPointerException e) {
                    namesNotImported += " " + googlePerson.getNames().get(0).getGivenName() + ", ";
                    errorImportsCount++;
                }
            }
        }

        commandMessage = setCommandMessage(namesNotImported, contactsImportedCount, errorImportsCount,
                googleContactsList.size(), namesNotImported);
        return new CommandResult(commandMessage);
    }

    /**
     * Check if a particular google contact already exists in the addressbook
     * Returns true if already exists. Otherwise, false.
     */
    public boolean ifContactExists(List<ReadOnlyPerson> list, Person person) {
        for (ReadOnlyPerson readOnlyPerson : list) {
            if (person.getResourceName().substring(8).equals(readOnlyPerson.getGoogleId().value)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Creates a person in addressBook based on the contact in google contact
     */
    public ReadOnlyPerson newPerson(Person person) throws IllegalValueException, NullPointerException {
        Name name = new Name(person.getNames().get(0).getGivenName());
        Email email = new Email(person.getEmailAddresses().get(0).getValue());
        Phone phone = new Phone(person.getPhoneNumbers().get(0).getValue().replace(" ", ""));
        Address address = new Address(person.getAddresses().get(0).getStreetAddress());
        GoogleId id = new GoogleId(person.getResourceName().substring(8));
        Birthday birthday = new Birthday("");
        FacebookAddress facebookAddress = new FacebookAddress("");

        Tag tag = new Tag("GoogleContact");
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);

        return new seedu.address.model.person.Person(name, phone, email, address, birthday, facebookAddress, tags, id);

    }
    /**
     * Creates a detailed message on the status of the import
     */
    public String setCommandMessage(String notImported, int contactsImported, int errorImports, int size,
                                    String namesNotImported) {
        int existedContacts = size - contactsImported - errorImports;
        String commandMessage;
        commandMessage = String.format(Messages.MESSAGE_IMPORT_CONTACT, contactsImported,
                size - contactsImported) + "\n";

        if (size > contactsImported) {
            commandMessage += String.format(Messages.MESSAGE_IMPORT_STATUS, existedContacts, errorImports) + "\n";
        }

        if (errorImports > 0) {
            commandMessage += "Please check the format of the following google contacts : "
                    + notImported.substring(0, namesNotImported.length() - 2);
        }
        return commandMessage;
    }
}


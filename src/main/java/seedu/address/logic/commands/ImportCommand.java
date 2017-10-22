package seedu.address.logic.commands;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.GoogleContactsBuilder;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.FacebookAddress;
import seedu.address.model.person.GoogleId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

import com.google.api.services.people.v1.model.Person;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_ALIAS = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Import contacts from google"
            + "process\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD;

    private List<Person> googleContactsList;


    private String commandMessage = "";
    private String namesNotImported = "";
    private int contactsImportedCount = 0;
    private int errorImportsCount = 0;

    /**
     * Constructor for ImportCommand (Gets the Google Contact List after successful authentication)
     */
    public ImportCommand() throws CommandException {
        try {
            GoogleContactsBuilder builder = new GoogleContactsBuilder();
            this.googleContactsList = builder.getPersonlist();
        } catch (IOException e) {
            throw new CommandException("Authentication Failed. Please login again.");
        }
        if (this.googleContactsList == null) {
            throw new CommandException("No contacts found in Google Contacts");
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
    public CommandResult execute() {
        List<ReadOnlyPerson> personList = model.getAddressBook().getPersonList();

        for (Person person : googleContactsList) {
            if (!this.ifContactExists(personList, person)) {
                try {
                    model.addPerson(this.newPerson(person));
                    contactsImportedCount++;
                } catch (IllegalValueException | NullPointerException e) {
                    namesNotImported += " " + person.getNames().get(0).getDisplayName() + ", ";
                    errorImportsCount++;
                }
            }
        }

        commandMessage = setCommandMessage(namesNotImported, contactsImportedCount, errorImportsCount,
                googleContactsList.size());
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
        Name name = new Name(person.getNames().get(0).getDisplayName());
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
    public String setCommandMessage(String notImported, int contactsImported, int errorImports, int size) {
        int existedContacts = size - contactsImported - errorImports;
        String commandMessage;
        commandMessage = String.format(Messages.MESSAGE_IMPORT_CONTACT, contactsImported,
                size - contactsImported) + "\n";

        if (size > contactsImported) {
               commandMessage += "Contacts already existed : " + String.valueOf(existedContacts)
                       + "     Contacts not in the correct format : " + String.valueOf(errorImports) + "\n";
        }
        if (errorImports > 0) {
               commandMessage += "Please check the format of the following google contacts : "
                       + notImported.substring(0, namesNotImported.length() - 2);
        }
        return commandMessage;
    }
}


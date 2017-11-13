//@@author PhuaJunJie
package seedu.address.logic.commands;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.api.services.people.v1.model.Person;

import javafx.collections.ObservableList;
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
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Syncs all existing google contacts in the addressbook with the contacts in google contacts
 * Contacts in google contacts takes higher precedence.
 */
public class SyncCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sync";
    public static final String COMMAND_ALIAS = "sync";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sync contacts from google"
            + "process\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD;
    private String commandMessage = "";
    private String namesNotSynced = "";
    private int contactsSyncedCount = 0;
    private int errorSyncedCount = 0;

    private List<Person> googleContactsList;

    /**
     * Constructor for SyncCommand (Gets the Google Contact List after successful authentication)
     */
    public SyncCommand() throws GoogleAuthException {
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
    public SyncCommand(List<Person> connections) {
        this.googleContactsList = connections;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        boolean exists = false;

        ObservableList<ReadOnlyPerson> personlist = model.getAddressBook().getPersonList();
        if (personlist.isEmpty()) {
            throw new CommandException("No contacts in addressbook to sync");
        }


        for (ReadOnlyPerson addressPerson : personlist) {
            if ((googleContactsList != null)) {
                for (Person googlePerson : googleContactsList) {
                    exists = updateAddressBook(googlePerson, addressPerson);
                    if (exists == true) {
                        break;
                    }
                }
            }

            //Removes google contact status from addressbook Contact if it does not exists in google contacts
            if ((addressPerson.getGoogleId().value != "not GoogleContact") && (!exists)) {
                try {
                    model.updatePerson(addressPerson, removeGoogleContactStatus(addressPerson));
                    contactsSyncedCount++;
                } catch (IllegalValueException | NullPointerException | PersonNotFoundException e) {
                    errorSyncedCount++;
                }
            }
        }

        commandMessage = setCommandMessage(namesNotSynced, contactsSyncedCount, errorSyncedCount);

        return new CommandResult(commandMessage);
    }

    /**
     * First, the method runs a check to see if the addressbook contact exists in google contacts
     * Updates the contact in addressbook with the new contact in google contact if they are found to be different
     * Returns true if contact cant be found in google contacts
     */
    public boolean updateAddressBook(Person googlePerson, ReadOnlyPerson addressPerson) {
        if (googlePerson.getResourceName().substring(8).equals(addressPerson.getGoogleId().value)) {
            try {
                if (!addressPerson.isSameStateAs(convertToAddress(googlePerson, addressPerson))) {
                    model.updatePerson(addressPerson, convertToAddress(googlePerson, addressPerson));
                    contactsSyncedCount++;
                }
            } catch (IllegalValueException | NullPointerException | PersonNotFoundException e) {
                errorSyncedCount++;
                namesNotSynced += googlePerson.getNames().get(0).getGivenName() + ", ";
            }
            return true;
        }
        return false;
    }

    /**
     * Creates a person in addressBook based on the contact in google contact
     */
    public ReadOnlyPerson convertToAddress(Person person, ReadOnlyPerson addressPerson)
            throws IllegalValueException, NullPointerException {

        Name name = new Name(person.getNames().get(0).getGivenName());
        Phone phone = new Phone(person.getPhoneNumbers().get(0).getValue().replace(" ", ""));
        Email email = new Email(person.getEmailAddresses().get(0).getValue());
        Address address = new Address(person.getAddresses().get(0).getStreetAddress());
        GoogleId id = new GoogleId(person.getResourceName().substring(8));
        Birthday birthday = new Birthday("");
        FacebookAddress facebookAddress = new FacebookAddress("");

        Set<Tag> tags = addressPerson.getTags();

        return new seedu.address.model.person.Person(name, phone, email, address, birthday, facebookAddress, tags, id);
    }

    /**
     * Creates a new person without its previous google contact status
     * This method is used if a person has a google contact status but cannot be found in google contacts
     */
    public ReadOnlyPerson removeGoogleContactStatus(ReadOnlyPerson contact)
            throws IllegalValueException, NullPointerException {

        Name name = new Name(contact.getName().fullName);
        Phone phone = new Phone(contact.getPhone().value);
        Email email = new Email(contact.getEmail().value);
        Address address = new Address(contact.getAddress().value);
        GoogleId id = new GoogleId("not GoogleContact");
        Set<Tag> tags = new HashSet<>();
        for (Tag existingTags : contact.getTags()) {
            if (!existingTags.getTagName().equals("GoogleContact")) {
                tags.add(existingTags);
            }
        }

        return new seedu.address.model.person.Person(name, phone, email, address,
                contact.getBirthday(), contact.getFacebookAddress(), tags, id);
    }


    /**
     * Creates a detailed message on the status of the sync
     */
    public String setCommandMessage(String namesNotSynced, int contactsSyncedCount, int errorSyncedCount) {
        String commandMessage;

        commandMessage = String.format(Messages.MESSAGE_SYNC_CONTACT, contactsSyncedCount, errorSyncedCount);
        if (errorSyncedCount > 0) {
            commandMessage += "\n" + "Please check the format of the following google contacts : "
                    + namesNotSynced.substring(0, namesNotSynced.length() - 2);
        }
        return commandMessage;
    }
}

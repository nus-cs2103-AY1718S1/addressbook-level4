package seedu.address.logic.commands;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.Address;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;
import com.google.api.services.people.v1.model.Source;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Email;
import seedu.address.model.person.Id;
import seedu.address.model.person.LastUpdated;
import seedu.address.model.person.Note;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

//@@author derrickchua
/**
 * Adds a person to the address book.
 */
public class SyncCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sync";
    public static final String COMMAND_ALIAS = "sy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Syncs the current addressbook with Google Contacts ";

    public static final String MESSAGE_SUCCESS = "Synchronising";
    public static final String MESSAGE_FAILURE = "Please login first";

    private static PeopleService client;

    private static HashSet<String> syncedIDs;

    private static final Logger logger = LogsCenter.getLogger(SyncCommand.class);


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        if (clientFuture == null || !clientFuture.isDone()) {
            throw new CommandException(MESSAGE_FAILURE);
        } else {
            syncedIDs =  (loadStatus() == null) ? new HashSet<String>() : (HashSet) loadStatus();
            try {
                client = clientFuture.get();
                List<ReadOnlyPerson> personList = model.getFilteredPersonList();
                exportContacts(personList);
                importContacts();
                saveStatus(syncedIDs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    /** Exports local contacts to Google Contacts
     *
     * @param personList
     * @throws IOException
     */

    public void exportContacts (List<ReadOnlyPerson> personList) throws Exception {
        for (ReadOnlyPerson person : personList) {
            if (person.getId().getValue().equals("")) {
                Person contactToCreate = convertAPerson(person);
                Person createdContact = client.people().createContact(contactToCreate).execute();

                String id = createdContact.getResourceName();

                seedu.address.model.person.Person updatedPerson = insertId(person, id);
                updatedPerson.setLastUpdated(new LastUpdated(getLastUpdated(createdContact)));

                updatePerson(person, updatedPerson);
                syncedIDs.add(id);
            }
        }
    }

    /**Pulls Google contacts and import new contacts, while checking for updates
     */

    private void importContacts () throws IOException {

        ListConnectionsResponse response = client.people().connections().list("people/me")
                .setPersonFields("metadata,names,emailAddresses,addresses,phoneNumbers")
                .execute();
        List<Person> connections = response.getConnections();

        for (Person person : connections) {
            try {
                seedu.address.model.person.Person aPerson = convertGooglePerson(person);
                String id = person.getResourceName();
                if (id == "") {
                    logger.warning("Google Contact has no retrievable ResourceName");
                } else if (syncedIDs.contains(id)) {
                    //checks for updating
                    updateContact(person);
                }  else {

                    model.addPerson(aPerson);
                    syncedIDs.add(id);
                }
            } catch (Exception e) {
                logger.severe(e.getMessage());
                e.printStackTrace();
            }
        }

    }

    /** Checks existing contacts, and picks the updated one to be synchronized
     *
     * @param person
     * @throws Exception
     */
    private void updateContact(Person person) throws Exception {
        List<seedu.address.model.person.ReadOnlyPerson> personList = model.getFilteredPersonList();

        for (seedu.address.model.person.ReadOnlyPerson aPerson : personList) {
            if (person.getResourceName().equals(aPerson.getId().getValue())) {
                String lastUpdated = person.getMetadata().getSources().get(0).getUpdateTime();
                Instant gTime = Instant.parse(lastUpdated);
                Instant aTime = Instant.parse(aPerson.getLastUpdated().getValue());
                Integer compare = gTime.compareTo(aTime);

                if (compare < 0) {
                    Person updatedPerson = convertAPerson(aPerson);
                    updatedPerson.setMetadata(person.getMetadata());

                    // The Google Contact is updated
                    Person updatedContact = client.people()
                            .updateContact(person.getResourceName(), updatedPerson)
                            .setUpdatePersonFields("names,emailAddresses,addresses,phoneNumbers")
                            .execute();

                    // Synchronize update time of both database entries to prevent looping
                    String newUpdated = updatedContact.getMetadata().getSources().get(0).getUpdateTime();
                    seedu.address.model.person.Person updatedAPerson = new seedu.address.model.person.Person(aPerson);
                    updatedAPerson.setLastUpdated(new LastUpdated(newUpdated));
                    model.updatePerson(aPerson, updatedAPerson);

                } else if (compare > 0) {

                    // The local contact is updated
                    seedu.address.model.person.Person updatedPerson = convertGooglePerson(person);
                    model.updatePerson(aPerson, updatedPerson);
                    break;
                } else {
                    break;
                }
            }
        }

    }

    /** Converts a Google Person to a local Person
     *
     * @param person
     * @return
     * @throws IllegalValueException
     */

    private seedu.address.model.person.Person convertGooglePerson (Person person)  throws IllegalValueException {
        seedu.address.model.person.Person aPerson = null;

        Name name = (person.getNames() == null)
                ? null
                : person.getNames().get(0);
        PhoneNumber phone = (person.getPhoneNumbers() == null)
                ? null
                : person.getPhoneNumbers().get(0);
        Address address = (person.getAddresses() == null)
                ? null
                : person.getAddresses().get(0);
        EmailAddress email = (person.getEmailAddresses() == null)
                ? null
                : person.getEmailAddresses().get(0);
        String id = person.getResourceName();
        String lastUpdated = getLastUpdated(person);

        if (name == null) {
            logger.warning("Google Contact has no retrievable name");
        } else {
            seedu.address.model.person.Name aName = new seedu.address.model.person.Name(name.getGivenName());
            Phone aPhone = (phone == null || !Phone.isValidPhone(phone.getValue()))
                    ? new Phone(null)
                    : new seedu.address.model.person.Phone(phone.getValue());
            seedu.address.model.person.Address aAddress = (address == null)
                    ? new seedu.address.model.person.Address(null)
                    : new seedu.address.model.person.Address(address.getStreetAddress());
            Email aEmail = (email == null)
                    ? new Email(null)
                    : new Email(email.getValue());
            aPerson = new seedu.address.model.person.Person(aName, aPhone, aEmail, aAddress,
                    new Note(""), new Id(id), new LastUpdated(lastUpdated),
                    new HashSet<Tag>(), new HashSet<Meeting>());
        }

        return aPerson;
    }

    /** Converts a local Person to a Google Person
     *
     * @param person
     * @return
     */
    private Person convertAPerson (ReadOnlyPerson person) {
        Person result = new Person();
        List<Name> name = new ArrayList<Name>();
        List<EmailAddress> email = new ArrayList<EmailAddress>();
        List<Address> address = new ArrayList<Address>();
        List<PhoneNumber> phone = new ArrayList<PhoneNumber>();
        name.add(new Name().setGivenName(person.getName().fullName));

        result.setNames(name);

        if (!person.getEmail().value.equals("No Email")) {
            email.add(new EmailAddress().setValue(person.getEmail().value));
            result.setEmailAddresses(email);
        }

        if (!person.getAddress().value.equals("No Address")) {
            address.add(new Address().setFormattedValue(person.getAddress().value));
            result.setAddresses(address);
        }

        if (!person.getPhone().value.equals("No Phone Number")) {
            phone.add(new PhoneNumber().setValue(person.getPhone().value));
            result.setPhoneNumbers(phone);
        }

        return result;
    }

    /**Updates the local model with the provided Google Person
     *
     * @param person
     * @param updatedPerson
     */
    public void updatePerson (ReadOnlyPerson person, seedu.address.model.person.Person updatedPerson) {
        try {
            model.updatePerson(person, updatedPerson);
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }

    }

    /**Creates a new seedu.address.model.person.Person,
     * and sets its id to the provided parameter
     *
     * @return a seedu.address.model.person.Person
     *
     */

    private seedu.address.model.person.Person insertId(ReadOnlyPerson person, String id) {
        seedu.address.model.person.Person updated = new seedu.address.model.person.Person(person);
        updated.setId(new Id(id));
        return updated;
    }

    /** Fetches the time a Person entry was last updated
     *
     * @param person
     * @return a String containing the time where the Person entry was last updated
     */
    private String getLastUpdated (Person person) {
        Source meta = person.getMetadata().getSources().get(0);
        return meta.getUpdateTime();
    }

    /** Saves the HashSet tracking synchronised entries
     *
     * @param object
     */
    private void saveStatus(Serializable object) {
        try {
            FileOutputStream saveFile = new FileOutputStream("syncedIDs.dat");
            ObjectOutputStream out = new ObjectOutputStream(saveFile);
            out.writeObject(object);
            out.close();
            saveFile.close();
        } catch (IOException e) {
            logger.fine(e.getMessage());
        }
    }

    /** Restores the saved HashSet
     *
     * @return an Object which is casted to its original type (HashSet in this case)
     */
    private Object loadStatus() {
        Object result = null;
        try {
            FileInputStream saveFile = new FileInputStream("syncedIDs.dat");
            ObjectInputStream in = new ObjectInputStream(saveFile);
            result = in.readObject();
            in.close();
            saveFile.close();
        } catch (Exception e) {
            logger.info("Initialising saved file");
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof SyncCommand; // instanceof handles null
    }
}

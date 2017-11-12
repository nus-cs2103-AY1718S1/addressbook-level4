package seedu.address.logic.commands;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
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
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

//@@author derrickchua
/**
 * Adds a person to the address book.
 */
public class SyncCommand extends Command {

    public static final String COMMAND_WORD = "sync";
    public static final String COMMAND_ALIAS = "sy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Syncs the current addressbook with Google Contacts ";

    public static final String MESSAGE_SUCCESS = "Synchronised";
    public static final String MESSAGE_FAILURE = "Please login first";
    public static final String MESSAGE_FAILURE_INTERNET =
            "Unable to connect to the Internet. Please check your internet and firewall settings";

    protected static PeopleService client;

    private static HashSet<String> syncedIDs;

    private static final Logger logger = LogsCenter.getLogger(SyncCommand.class);

    private HashMap<String, ReadOnlyPerson> hashId;

    private HashMap<String, ReadOnlyPerson> hashName;

    private List<Person> connections;

    private HashMap<String, Person> hashGoogleId;
    private HashMap<String, Person> hashGoogleName;


    @Override
    public CommandResult execute() throws CommandException {

        if (clientFuture == null || !clientFuture.isDone()) {
            throw new CommandException(MESSAGE_FAILURE);
        } else {

            syncedIDs =  (loadStatus() == null) ? new HashSet<String>() : (HashSet) loadStatus();

            try {
                List<ReadOnlyPerson> personList = model.getFilteredPersonList();

                initialise(personList);

                checkContacts(personList);
                updateContacts();
                exportContacts(personList);

                if (connections != null) {
                    importContacts();
                }

                saveStatus(syncedIDs);
            } catch (java.net.UnknownHostException e) {
                throw new CommandException(MESSAGE_FAILURE_INTERNET);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    /** Preprocessing for future methods (Initialises hashmaps for faster future access)
     *
     * @throws Exception
     */
    private void initialise(List<ReadOnlyPerson> personList) throws Exception {
        client = clientFuture.get();
        ListConnectionsResponse response = client.people().connections().list("people/me")
                .setPersonFields("metadata,names,emailAddresses,addresses,phoneNumbers")
                .execute();
        connections = response.getConnections();
        hashId = constructHashId(personList);
        hashName = constructHashName(personList);

        if (connections != null) {
            hashGoogleId = constructGoogleHashId();
            hashGoogleName = constructGoogleHashName();
        } else {
            hashGoogleId = new HashMap<String, Person>();
            hashGoogleName = new HashMap<String, Person>();
        }
    }

    /** Ensures that all Google Contacts have not been removed, and unlinks them if they are
     *
     * @throws Exception
     */
    private void checkContacts(List<ReadOnlyPerson> personList) throws Exception {
        List<ReadOnlyPerson> toDelete = new ArrayList<ReadOnlyPerson>();
        for (ReadOnlyPerson person : personList) {
            String id = person.getId().getValue();

            if (!id.equals("") && !hashGoogleId.containsKey(id)) {
                logger.info("Deleting local contact");
                toDelete.add(person);
                syncedIDs.remove(id);
                continue;
            } else if (!id.equals("") && !syncedIDs.contains(id)) {
                syncedIDs.add(id);
            }

        }

        for (ReadOnlyPerson dPerson: toDelete) {
            model.deletePerson(dPerson);
        }
    }

    /** Exports local contacts to Google Contacts
     *
     * @param personList
     * @throws IOException
     */

    private void exportContacts (List<ReadOnlyPerson> personList) throws Exception {
        for (ReadOnlyPerson person : personList) {
            if (person.getId().getValue().equals("")) {
                if (!hashGoogleName.containsKey(person.getName().fullName)) {
                    addGoogleContact(person);
                } else {
                    // We check if the person is identical, and link them if they are
                    Person gPerson = hashGoogleName.get(person.getName().fullName);
                    if (equalPerson(person, gPerson)) {
                        linkContacts(person, gPerson);
                    } else {
                        addGoogleContact(person);
                    }
                }
            }
        }
    }

    /**Pulls Google contacts and import new contacts, while checking for updates
     */

    private void importContacts () throws IOException {

        for (Person person : connections) {
            try {
                String id = person.getResourceName();
                String gName = retrieveFullGName(person);
                if (!syncedIDs.contains(id)) {
                    if (!hashName.containsKey(gName)) {
                        addAContact(person);
                    } else {
                        seedu.address.model.person.ReadOnlyPerson aPerson = hashName.get(gName);
                        if (equalPerson(aPerson, person)) {
                            linkContacts(aPerson, person);
                        } else {
                            addAContact(person);
                        }
                    }
                }
            } catch (DuplicatePersonException e) {
                logger.info("Not importing duplicate");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /** Update all contacts
     *
     * @throws Exception
     */
    private void updateContacts() throws Exception {
        List<String> toRemove = new ArrayList<String>();
        for (String id : syncedIDs) {
            seedu.address.model.person.ReadOnlyPerson aPerson;
            Person person;

            if (!hashId.containsKey(id)) {
                // Contact has been deleted locally. We update this remotely
                if (hashGoogleId.containsKey(id)) {
                    client.people().deleteContact(id).execute();
                }
                toRemove.add(id);
                logger.info("Removing id: " + id);
                continue;
            }

            aPerson = hashId.get(id);

            if (!hashGoogleId.containsKey(id)) {
                // Contact is no longer existent on Google servers
                seedu.address.model.person.Person updatedPerson = setId(aPerson, "");
                updatePerson(aPerson, updatedPerson);
                logger.info("Removing id: " + id);
                toRemove.add(id);
                continue;
            }

            person = hashGoogleId.get(id);

            String lastUpdated = person.getMetadata().getSources().get(0).getUpdateTime();
            Instant gTime = Instant.parse(lastUpdated);
            Instant aTime = Instant.parse(aPerson.getLastUpdated().getValue());
            Integer compare = gTime.compareTo(aTime);

            if (compare < 0) {
                Person updatedPerson = convertAPerson(aPerson);
                updatedPerson.setMetadata(person.getMetadata());
                checkNullFields(person, updatedPerson);

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
                seedu.address.model.person.Person updatedPerson = convertGooglePerson(person, aPerson);
                model.updatePerson(aPerson, updatedPerson);
            }
        }
        syncedIDs.removeAll(toRemove);

    }

    /** Links a ABC and Google contact
     *
     * @param aPerson
     * @param gPerson
     * @throws Exception
     */
    private void linkContacts(ReadOnlyPerson aPerson, Person gPerson) throws Exception {
        seedu.address.model.person.Person updatedPerson =
                new seedu.address.model.person.Person(aPerson);
        updatedPerson.setId(new Id(gPerson.getResourceName()));

        // We now set last update time to the Google one
        String updateTime = gPerson.getMetadata().getSources().get(0).getUpdateTime();
        updatedPerson.setLastUpdated(new LastUpdated(updateTime));
        updatePerson(aPerson, updatedPerson);

    }

    /** Adds a Google contact from the specified ABC contact
     *
     * @param person
     * @throws Exception
     */
    private void addGoogleContact (ReadOnlyPerson person) throws Exception {
        Person contactToCreate = convertAPerson(person);
        Person createdContact = client.people().createContact(contactToCreate).execute();

        String id = createdContact.getResourceName();

        seedu.address.model.person.Person updatedPerson = setId(person, id);
        updatedPerson.setLastUpdated(new LastUpdated(getLastUpdated(createdContact)));

        updatePerson(person, updatedPerson);
        syncedIDs.add(id);
    }

    /** Adds a ABC contact from the specified Google contact
     *
     * @param person
     * @throws Exception
     */
    private void addAContact (Person person) throws Exception {
        String id = person.getResourceName();
        seedu.address.model.person.Person convertedAPerson = convertGooglePerson(person);
        model.addPerson(convertedAPerson);
        syncedIDs.add(id);
    }

    /**Ensures that we do not override a Google Contact with null fields when updating
     *
     * @param person
     * @param updatedPerson
     */
    private void checkNullFields(Person person, Person updatedPerson) {
        if (updatedPerson.getPhoneNumbers() == null && person.getPhoneNumbers() != null) {
            updatedPerson.setPhoneNumbers(person.getPhoneNumbers());
        }
        if (updatedPerson.getAddresses() == null && person.getAddresses() != null) {
            updatedPerson.setAddresses(person.getAddresses());
        }
        if (updatedPerson.getEmailAddresses() == null && person.getEmailAddresses() != null) {
            updatedPerson.setEmailAddresses(person.getEmailAddresses());
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
            seedu.address.model.person.Name aName = new seedu.address.model.person.Name(retrieveFullGName(person));
            Phone aPhone = (phone == null || !Phone.isValidPhone(phone.getValue().replaceAll("\\s+", "")))
                    ? new Phone(null)
                    : new seedu.address.model.person.Phone(phone.getValue().replaceAll("\\s+", ""));
            seedu.address.model.person.Address aAddress = (
                    address == null || !seedu.address.model.person.Address.isValidAddress(address.getFormattedValue()))
                    ? new seedu.address.model.person.Address(null)
                    : new seedu.address.model.person.Address(address.getFormattedValue());
            Email aEmail = (email == null || !Email.isValidEmail(email.getValue()))
                    ? new Email(null)
                    : new Email(email.getValue());
            aPerson = new seedu.address.model.person.Person(aName, aPhone, aEmail, aAddress,
                    new Note(""), new Id(id), new LastUpdated(lastUpdated),
                    new HashSet<Tag>(), new HashSet<Meeting>());
        }

        return aPerson;
    }

    /** Converts a Google Person with an already existing entry to a local Person
     *
     * @param person
     * @param aOldPerson
     * @return
     * @throws IllegalValueException
     */

    private seedu.address.model.person.Person convertGooglePerson (Person person, ReadOnlyPerson aOldPerson)
            throws IllegalValueException {
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
            seedu.address.model.person.Name aName = new seedu.address.model.person.Name(retrieveFullGName(person));
            Phone aPhone = (phone == null || !Phone.isValidPhone(phone.getValue().replaceAll("\\s+", "")))
                    ? new Phone(null)
                    : new seedu.address.model.person.Phone(phone.getValue().replaceAll("\\s+", ""));
            seedu.address.model.person.Address aAddress = (
                    address == null || !seedu.address.model.person.Address.isValidAddress(address.getFormattedValue()))
                    ? new seedu.address.model.person.Address(null)
                    : new seedu.address.model.person.Address(address.getFormattedValue());
            Email aEmail = (email == null || !Email.isValidEmail(email.getValue()))
                    ? new Email(null)
                    : new Email(email.getValue());
            aPerson = new seedu.address.model.person.Person(aName, aPhone, aEmail, aAddress,
                    aOldPerson.getNote(), new Id(id), new LastUpdated(lastUpdated),
                    aOldPerson.getTags(), aOldPerson.getMeetings());
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
    private void updatePerson (ReadOnlyPerson person, seedu.address.model.person.Person updatedPerson) {
        try {
            model.updatePerson(person, updatedPerson);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /** Retrieves full name from a Google Contact
     *
     * @param person
     * @return
     */
    private String retrieveFullGName (Person person) {
        Name name = person.getNames().get(0);

        String result;

        if (name.getFamilyName() != null) {
            if (name.getMiddleName() != null) {
                result = name.getGivenName() + " " + name.getMiddleName() + " " + name.getFamilyName();
            } else {
                result = name.getGivenName() + " " + name.getFamilyName();
            }
        } else {
            result = name.getGivenName();
        }

        return result;
    }

    /**Creates a new seedu.address.model.person.Person,
     * and sets its id to the provided parameter
     *
     * @return a seedu.address.model.person.Person
     *
     */

    protected static seedu.address.model.person.Person setId(ReadOnlyPerson person, String id) {
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

    /**Constructs a HashMap of a Person's ID and itself
     *
     * @param personList
     * @return
     */

    private HashMap<String, ReadOnlyPerson> constructHashId (List<ReadOnlyPerson> personList) {
        HashMap<String, ReadOnlyPerson> result = new HashMap<>();

        personList.forEach(e -> {
            result.put(e.getId().getValue(), e);
        });

        return result;
    }

    /**Constructs a HashMap of a Person's Name and itself
     *
     * @param personList
     * @return
     */

    private HashMap<String, ReadOnlyPerson> constructHashName (List<ReadOnlyPerson> personList) {
        HashMap<String, ReadOnlyPerson> result = new HashMap<>();

        personList.forEach(e -> {
            result.put(e.getName().fullName, e);
        });

        return result;
    }


    /**Constructs a HashMap of Google ResourceName and their Person objects
     *
     * @return Hashmap
     */

    private HashMap<String, Person> constructGoogleHashId () {
        HashMap<String, Person> result = new HashMap<>();

        connections.forEach(e -> {
            result.put(e.getResourceName(), e);
        });

        return result;
    }

    /**Constructs a HashMap of Google ResourceName and their Person objects
     *
     * @return Hashmap
     */

    private HashMap<String, Person> constructGoogleHashName () {
        HashMap<String, Person> result = new HashMap<>();

        connections.forEach(e -> {
            String name = retrieveFullGName(e);
            if (!result.containsKey(name)) {
                result.put(name, e);
            } else {
                if (hashName.containsKey(name)) {
                    ReadOnlyPerson person = hashName.get(name);
                    if (equalPerson(person, e)) {
                        result.put(name, e);
                    }
                }
            }

        });

        return result;
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

    /** Checks whether a Google Person and an ABC Person are equal
     *
     * @param abcPerson
     * @param gPerson
     * @return
     */
    private boolean equalPerson (ReadOnlyPerson abcPerson, Person gPerson) {
        Name name = (gPerson.getNames() == null)
                ? null
                : gPerson.getNames().get(0);
        String abcName = abcPerson.getName().fullName;
        String gName;
        boolean equalName = false;
        if (name != null) {
            gName = retrieveFullGName(gPerson);
            equalName = gName.equals(abcName);
        }

        EmailAddress email = (gPerson.getEmailAddresses() == null)
                ? null
                : gPerson.getEmailAddresses().get(0);
        String abcEmail = abcPerson.getEmail().value;
        String gEmail;
        boolean equalEmail;

        if (email != null) {
            gEmail = email.getValue();
            equalEmail = gEmail.equals(abcEmail);
        } else {
            equalEmail = abcEmail.equals("No Email");
        }

        PhoneNumber phone = (gPerson.getPhoneNumbers() == null)
                ? null
                : gPerson.getPhoneNumbers().get(0);
        String abcPhone = abcPerson.getPhone().value;
        String gPhone = null;
        boolean equalPhone;

        if (phone != null) {
            gPhone = phone.getValue().replaceAll("\\s+", "");
            equalPhone = gPhone.equals(abcPhone);
        } else {
            equalPhone = abcPhone.equals("No Phone Number");
        }

        Address address = (gPerson.getAddresses() == null)
                ? null
                : gPerson.getAddresses().get(0);
        String abcAddress = abcPerson.getAddress().value;
        String gAddress;
        boolean equalAddress;

        if (address != null) {
            gAddress = address.getFormattedValue();
            equalAddress = gAddress.equals(abcAddress);
        } else {
            equalAddress = abcAddress.equals("No Address");
        }

//        System.out.println(abcPerson.getName().fullName);
//        if (!equalPhone) {
//            System.out.println(gPhone);
//            System.out.println(abcPhone);
//        }
        return equalName && equalPhone && equalAddress && equalEmail;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof SyncCommand; // instanceof handles null
    }
}

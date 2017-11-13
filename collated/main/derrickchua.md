# derrickchua
###### \java\seedu\address\logic\commands\Command.java
``` java
    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        this.model = model;
    }

    public void setOAuth (OAuth oauth) {
        this.oauth = oauth;
    }

    public void setExecutor (ExecutorService executor) {
        this.executor = executor;
    }

}
```
###### \java\seedu\address\logic\commands\LoginCommand.java
``` java
/**TODO: Throw prompt to open browser window as CommandResult
 * Adds a person to the address book.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_ALIAS = "li";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Login to Google Contacts ";

    public static final String MESSAGE_SUCCESS = "Logging in";
    public static final String MESSAGE_FAILURE = "Something has gone wrong...";

    @Override
    public CommandResult execute() throws CommandException {

        clientFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return oauth.execute();
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable t) {
                System.err.println(t.getStackTrace());
            }

            return null;
        }, executor);

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof LoginCommand; // instanceof handles null
    }
}
```
###### \java\seedu\address\logic\commands\LogoutCommand.java
``` java

/**
 * Adds a person to the address book.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";
    public static final String COMMAND_ALIAS = "lo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logout of Google Contacts ";

    public static final String MESSAGE_SUCCESS = "Logged out!";
    public static final String MESSAGE_FAILURE = "Failure logging out. Are you sure you are logged in?";

    /** Directory to store user credentials. */
    private final java.io.File dataStoreDir =
            new java.io.File("data/StoredCredential");

    private final java.io.File syncedIDs =
            new java.io.File("data/syncedIDs.dat");

    @Override
    public CommandResult execute() throws CommandException {
        SyncCommand.client = null;
        SyncCommand.clientFuture = null;
        syncedIDs.delete();
        try {
            resetIDs();
        } catch (Exception e) {
            assert false;
        }

        if (dataStoreDir.delete()) {
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } else {
            throw new CommandException(String.format(MESSAGE_FAILURE));
        }


    }

    /** Removes all IDs from linked contacts
     *
     * @throws DuplicatePersonException
     * @throws PersonNotFoundException
     */
    private void resetIDs () throws DuplicatePersonException, PersonNotFoundException {
        List<ReadOnlyPerson> personList = model.getFilteredPersonList();

        for (ReadOnlyPerson person : personList) {
            Person updated = SyncCommand.setId(person, "");
            model.updatePerson(person, updated);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof LogoutCommand; // instanceof handles null
    }
}
```
###### \java\seedu\address\logic\commands\NoteCommand.java
``` java
/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class NoteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "note";
    public static final String COMMAND_ALIAS = "n";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Modifies a note for the person identified by "
            + "the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + "NOTE]\n"
            + "Example: " + COMMAND_WORD + " 1 " + " Has 3 children.";

    public static final String MESSAGE_NOTE_SUCCESS = "Note Added: %1$s";

    private final Index targetIndex;
    private final Note note;

    public NoteCommand(Index targetIndex, Note note) {

        this.targetIndex = targetIndex;
        this.note = note;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToNote = lastShownList.get(targetIndex.getZeroBased());
        Name updatedName = personToNote.getName();
        Phone updatedPhone = personToNote.getPhone();
        Email updatedEmail = personToNote.getEmail();
        Address updatedAddress = personToNote.getAddress();
        Id updatedId = personToNote.getId();
        LastUpdated lastUpdated = personToNote.getLastUpdated();
        Set<Tag> updatedTags = personToNote.getTags();
        Set<Meeting> updatedMeetings = personToNote.getMeetings();

        ReadOnlyPerson updatedPerson =
                new Person (updatedName, updatedPhone, updatedEmail, updatedAddress,
                        this.note, updatedId, lastUpdated, updatedTags, updatedMeetings);

        try {
            model.updatePerson(personToNote, updatedPerson);
        }  catch (DuplicatePersonException dpe) {
            throw new CommandException("Person already exists in the AddressBook.");
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_NOTE_SUCCESS, updatedPerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NoteCommand // instanceof handles nulls
                && this.targetIndex.equals(((NoteCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\SyncCommand.java
``` java
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
    protected void linkContacts(ReadOnlyPerson aPerson, Person gPerson) throws Exception {
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
    protected void addGoogleContact (ReadOnlyPerson person) throws Exception {
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
    protected void addAContact (Person person) throws Exception {
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
    protected void checkNullFields(Person person, Person updatedPerson) {
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

    protected seedu.address.model.person.Person convertGooglePerson (Person person)  throws IllegalValueException {
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

    protected seedu.address.model.person.Person convertGooglePerson (Person person, ReadOnlyPerson aOldPerson)
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
    protected Person convertAPerson (ReadOnlyPerson person) {
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
    protected void updatePerson (ReadOnlyPerson person, seedu.address.model.person.Person updatedPerson) {
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
    protected String retrieveFullGName (Person person) {
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
    protected String getLastUpdated (Person person) {
        Source meta = person.getMetadata().getSources().get(0);
        return meta.getUpdateTime();
    }

    /**Constructs a HashMap of a Person's ID and itself
     *
     * @param personList
     * @return
     */

    protected HashMap<String, ReadOnlyPerson> constructHashId (List<ReadOnlyPerson> personList) {
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

    protected HashMap<String, ReadOnlyPerson> constructHashName (List<ReadOnlyPerson> personList) {
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

    protected HashMap<String, Person> constructGoogleHashId () {
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

    protected HashMap<String, Person> constructGoogleHashName () {
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
    protected void saveStatus(Serializable object) {
        try {
            FileOutputStream saveFile = new FileOutputStream("data/syncedIDs.dat");
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
    protected Object loadStatus() {
        Object result = null;
        try {
            FileInputStream saveFile = new FileInputStream("data/syncedIDs.dat");
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
    protected boolean equalPerson (ReadOnlyPerson abcPerson, Person gPerson) {
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

        return equalName && equalPhone && equalAddress && equalEmail;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof SyncCommand; // instanceof handles null
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();

            Optional<Phone> phoneOptional = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE));
            Phone phone = phoneOptional.isPresent() ? phoneOptional.get() : new Phone(null);
            Optional<Email> emailOptional = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL));
            Email email = emailOptional.isPresent() ? emailOptional.get() : new Email(null);
            Optional<Address> addressOptional = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS));
            Address address = addressOptional.isPresent() ? addressOptional.get() : new Address(null);
            Note note = new Note("");
            Id id = new Id("");
            Instant time = Instant.now();
            LastUpdated lastUpdated = new LastUpdated(time.toString());
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            Set<Meeting> meetingList = new HashSet<>();

            ReadOnlyPerson person = new Person(name, phone, email, address, note, id,
                    lastUpdated, tagList, meetingList);
            for (Meeting meeting : person.getMeetings()) {
                meeting.setPerson((Person) person);
            }

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\logic\parser\NoteCommandParser.java
``` java
/**
 * Parses input arguments and creates a new NoteCommand object
 */
public class NoteCommandParser implements Parser<NoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NoteCommand
     * and returns an NoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String[] parts = trimmedArgs.split("\\s+", 2);
        Index index;

        try {
            index = ParserUtil.parseIndex(parts[0]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
        }

        String note = (parts.length == 2) ? parts[1] : "";

        return new NoteCommand(index, new Note(note));
    }

}
```
###### \java\seedu\address\model\person\Id.java
``` java
/**
 * Represents a Person's note in the address book.
 */
public class Id {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Person id can take any values";
    public static final String EMPTY_ID = "";

    private String value;

    public Id(String value) {
        if (value == null) {
            value = EMPTY_ID;
        }
        this.value = value;
    }

    public static boolean isValidId(String test) {
        return test != null;
    }

    public String getValue() {
        return this.value;
    }


    public void setValue (String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return this.value;
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Id // instanceof handles nulls
                && this.value.equals(((Id) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\LastUpdated.java
``` java
/**
 * Represents a Person's note in the address book.
 */
public class LastUpdated {

    public static final String MESSAGE_LASTUPDATED_CONSTRAINTS =
            "Person update timings must fit UTC Zulu format, e.g. 2017-10-31T16:20:34.856Z, with 3 to 6 decimals";

    public static final String LASTUPDATED_VALIDATION_REGEX = "\\d{4}-(?:0[1-9]|1[0-2])-(?:0[1-9]|[1-2]\\d|3[0-1])T"
            + "(?:[0-1]\\d|2[0-3]):[0-5]\\d:[0-5]\\d.\\d{3,6}Z";

    private String value;

    public LastUpdated(String value) throws IllegalValueException {
        if (value == null) {
            throw new IllegalValueException(MESSAGE_LASTUPDATED_CONSTRAINTS);
        }
        if (!isValidLastUpdated(value)) {
            throw new IllegalValueException(MESSAGE_LASTUPDATED_CONSTRAINTS);
        }
        this.value = value;
    }

    public static boolean isValidLastUpdated(String test) {
        return test.matches(LASTUPDATED_VALIDATION_REGEX);
    }

    public String getValue() {
        return this.value;
    }


    public void setValue (String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return this.value;
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LastUpdated // instanceof handles nulls
                && this.value.equals(((LastUpdated) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Note.java
``` java
/**
 * Represents a Person's note in the address book.
 */
public class Note {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Person notes can take any values";
    public static final String EMPTY_NOTE = "";

    private final String value;

    public Note(String value) {
        if (value == null) {
            value = EMPTY_NOTE;
        }
        this.value = value;
    }

    public static boolean isValidNote(String test) {
        return test != null;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Note // instanceof handles nulls
                && this.value.equals(((Note) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```

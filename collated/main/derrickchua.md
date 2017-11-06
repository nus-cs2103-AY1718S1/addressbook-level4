# derrickchua
###### /java/seedu/address/logic/commands/SyncCommand.java
``` java
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
```
###### /java/seedu/address/logic/commands/LoginCommand.java
``` java
/**
 * Adds a person to the address book.
 */
public class LoginCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_ALIAS = "lg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Login to Google Contacts ";

    public static final String MESSAGE_SUCCESS = "Logging in";
    public static final String MESSAGE_FAILURE = "Something has gone wrong...";

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        clientFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return oauth.execute();
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
###### /java/seedu/address/logic/commands/NoteCommand.java
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
            + "[" + PREFIX_NOTE + "NOTE]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_NOTE + " Has 3 children.";

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

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

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
###### /java/seedu/address/logic/commands/Command.java
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
###### /java/seedu/address/logic/parser/NoteCommandParser.java
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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NOTE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
        }

        String note = argMultimap.getValue(PREFIX_NOTE).orElse("");

        return new NoteCommand(index, new Note(note));
    }

}
```
###### /java/seedu/address/logic/parser/AddCommandParser.java
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
                        PREFIX_ADDRESS, PREFIX_TAG, PREFIX_MEETING);

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
            Set<Meeting> meetingList = ParserUtil.parseMeetings(argMultimap.getAllValues(PREFIX_MEETING));

            ReadOnlyPerson person = new Person(name, phone, email, address, note, id,
                    lastUpdated, tagList, meetingList);

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
###### /java/seedu/address/model/person/Id.java
``` java
/**
 * Represents a Person's note in the address book.
 */
public class Id {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Person id can take any values";


    private String value;

    public Id(String value) {
        requireNonNull(value);
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
###### /java/seedu/address/model/person/LastUpdated.java
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
        requireNonNull(value);
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
###### /java/seedu/address/model/person/Note.java
``` java
/**
 * Represents a Person's note in the address book.
 */
public class Note {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Person notes can take any values";

    private final String value;

    public Note(String value) {
        requireNonNull(value);
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

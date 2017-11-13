# derrickchua
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

```
###### \java\seedu\address\logic\commands\LoginCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code LoginCommand}.
 */
public class LoginCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addLogin_success() throws Exception {
        LoginCommand loginCommand = prepareCommand();

        String expectedMessage = String.format(LoginCommand.MESSAGE_SUCCESS);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(loginCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void equals() {
        LoginCommand loginFirstCommand = new LoginCommand();
        LoginCommand loginSecondCommand = new LoginCommand();

        // same object -> returns true
        assertTrue(loginFirstCommand.equals(loginFirstCommand));

        // different types -> returns false
        assertFalse(loginFirstCommand.equals(1));

        // null -> returns false
        assertFalse(loginFirstCommand.equals(null));

        // returns true
        assertTrue(loginFirstCommand.equals(loginSecondCommand));
    }

    /**
     * Returns a {@code LoginCommand} with the parameter {@code index}.
     */
    private LoginCommand prepareCommand() {
        LoginCommand logincommand = new LoginCommand();
        logincommand.setData(model, new CommandHistory(), new UndoRedoStack());
        logincommand.setExecutor(Executors.newFixedThreadPool(4));
        return logincommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
```
###### \java\seedu\address\logic\commands\LogoutCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code LogoutCommand}.
 */
public class LogoutCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addLogout_success() throws Exception {

        LogoutCommand logoutCommand = prepareCommand();
        java.io.File filetoDelete =
                new java.io.File("data/StoredCredential");
        filetoDelete.mkdirs();
        filetoDelete.createNewFile();
        String expectedMessage = String.format(LogoutCommand.MESSAGE_SUCCESS);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(logoutCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addLogout_failure() throws Exception {

        LogoutCommand logoutCommand = prepareCommand();

        java.io.File filetoDelete =
                new java.io.File("data/StoredCredential");
        filetoDelete.delete();
        String expectedMessage = String.format(LogoutCommand.MESSAGE_FAILURE);
        assertCommandFailure(logoutCommand, model, expectedMessage);
    }


    @Test
    public void equals() {
        LogoutCommand logoutFirstCommand = new LogoutCommand();
        LogoutCommand logoutSecondCommand = new LogoutCommand();

        // same object -> returns true
        assertTrue(logoutFirstCommand.equals(logoutFirstCommand));

        // different types -> returns false
        assertFalse(logoutFirstCommand.equals(1));

        // null -> returns false
        assertFalse(logoutFirstCommand.equals(null));

        // returns true
        assertTrue(logoutFirstCommand.equals(logoutSecondCommand));
    }

    /**
     * Returns a {@code LogoutCommand} with the parameter {@code index}.
     */
    private LogoutCommand prepareCommand() {
        LogoutCommand logoutcommand = new LogoutCommand();
        logoutcommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return logoutcommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
```
###### \java\seedu\address\logic\commands\NoteCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code NoteCommand}.
 */
public class NoteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addNote_success() throws Exception {
        ReadOnlyPerson personToNote = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToNote).withNote(VALID_NOTE_AMY).build();
        NoteCommand noteCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getNote());

        String expectedMessage = String.format(NoteCommand.MESSAGE_NOTE_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToNote = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToNote).withNote(VALID_NOTE_AMY).build();
        NoteCommand noteCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getNote());

        String expectedMessage = String.format(NoteCommand.MESSAGE_NOTE_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToNote, editedPerson);
        showFirstPersonOnly(expectedModel);
        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        NoteCommand noteCommand = prepareCommand(outOfBoundIndex, new Note(VALID_NOTE_BOB));

        assertCommandFailure(noteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        NoteCommand noteFirstCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(VALID_NOTE_AMY));
        NoteCommand noteSecondCommand = new NoteCommand(INDEX_SECOND_PERSON, new Note(VALID_NOTE_AMY));

        // same object -> returns true
        assertTrue(noteFirstCommand.equals(noteFirstCommand));

        // same values -> returns true
        NoteCommand noteFirstCommandCopy = new NoteCommand(INDEX_FIRST_PERSON, new Note(VALID_NOTE_AMY));
        assertTrue(noteFirstCommand.equals(noteFirstCommandCopy));

        // different types -> returns false
        assertFalse(noteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(noteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(noteFirstCommand.equals(noteSecondCommand));
    }

    /**
     * Returns a {@code NoteCommand} with the parameter {@code index}.
     */
    private NoteCommand prepareCommand(Index index, Note note) {
        NoteCommand notecommand = new NoteCommand(index, note);
        notecommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return notecommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
```
###### \java\seedu\address\logic\commands\SyncCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code SyncCommand}.
 */
public class SyncCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addSync_failure() throws Exception {
        SyncCommand syncCommand = prepareCommand();

        String expectedMessage = String.format(SyncCommand.MESSAGE_FAILURE);
        assertCommandFailure(syncCommand, model, expectedMessage);
    }

    @Test
    public void check_equalPerson() {
        showFirstPersonOnly(model);

        SyncCommand syncCommand = prepareCommand();
        Person aliceGoogle = prepareAliceGoogle();

        assertTrue(syncCommand.equalPerson(model.getFilteredPersonList().get(0), aliceGoogle));
    }

    @Test
    public void check_convertAPerson() {
        showFirstPersonOnly(model);

        SyncCommand syncCommand = prepareCommand();
        ReadOnlyPerson aliceAbc = model.getFilteredPersonList().get(0);
        Person person = syncCommand.convertAPerson(aliceAbc);

        assertTrue(syncCommand.equalPerson(aliceAbc, person));
    }

    @Test
    public void check_convertGPerson() throws Exception {
        showFirstPersonOnly(model);

        SyncCommand syncCommand = prepareCommand();
        ReadOnlyPerson aliceAbc = model.getFilteredPersonList().get(0);
        Person aliceGoogle = prepareAliceGoogle();
        seedu.address.model.person.Person converted = syncCommand.convertGooglePerson(aliceGoogle, aliceAbc);

        assertEquals(converted, aliceAbc);
    }

    @Test
    public void test_getLastUpdated() {
        showFirstPersonOnly(model);

        SyncCommand syncCommand = prepareCommand();
        Person aliceGoogle = prepareAliceGoogle();

        assertTrue(syncCommand.getLastUpdated(aliceGoogle).equals("2017-11-12T16:29:49.398001Z"));
    }

    @Test
    public void check_linkedContact() throws Exception {
        showFirstPersonOnly(model);

        SyncCommand syncCommand = prepareCommand();
        ReadOnlyPerson aliceAbc =  model.getFilteredPersonList().get(0);
        Person aliceGoogle = prepareAliceGoogle();

        syncCommand.linkContacts(aliceAbc, aliceGoogle);

        aliceAbc =  model.getFilteredPersonList().get(0);

        assertTrue(aliceAbc.getId().getValue().equals("alice")
                && aliceAbc.getLastUpdated().getValue().equals("2017-11-12T16:29:49.398001Z"));
    }

    @Test
    public void equals() {
        SyncCommand syncFirstCommand = new SyncCommand();
        SyncCommand syncSecondCommand = new SyncCommand();

        // same object -> returns true
        assertTrue(syncFirstCommand.equals(syncFirstCommand));

        // different types -> returns false
        assertFalse(syncFirstCommand.equals(1));

        // null -> returns false
        assertFalse(syncFirstCommand.equals(null));

        // returns true
        assertTrue(syncFirstCommand.equals(syncSecondCommand));
    }

    /**
     * Returns a {@code SyncCommand}
     */
    private SyncCommand prepareCommand() {
        SyncCommand synccommand = new SyncCommand();
        synccommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return synccommand;
    }

    /** Prepares a Google Person which is the equivalent of the ABC Person ALICE for testing
     *
     * @return
     */
    private Person prepareAliceGoogle() {
        Person result  = new Person();
        PersonMetadata metadata = new PersonMetadata();
        List<Name>  name = new ArrayList<>();
        List<Address> address = new ArrayList<Address>();
        List<EmailAddress> email = new ArrayList<>();
        List<PhoneNumber> phone = new ArrayList<>();
        List<Source> source = new ArrayList<>();


        name.add(new Name().setGivenName("Alice Pauline"));
        address.add(new Address().setFormattedValue("123, Jurong West Ave 6, #08-111"));
        email.add(new EmailAddress().setValue("alice@example.com"));
        phone.add(new PhoneNumber().setValue("85355255"));
        source.add(new Source().setUpdateTime("2017-11-12T16:29:49.398001Z"));
        metadata.setSources(source);

        result.setEmailAddresses(email)
                .setNames(name)
                .setPhoneNumbers(phone)
                .setAddresses(address)
                .setResourceName("alice")
                .setMetadata(metadata);

        return result;
    }


}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY, new AddCommand(expectedPerson));

        // no phone number
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(NO_PHONE_SET)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY, new AddCommand(expectedPerson));

        // no email address
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(NO_EMAIL_SET).withAddress(VALID_ADDRESS_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + ADDRESS_DESC_AMY, new AddCommand(expectedPerson));

        // no phone number
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(NO_ADDRESS_SET).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY, new AddCommand(expectedPerson));

        // only has name
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(NO_PHONE_SET)
                .withEmail(NO_EMAIL_SET).withAddress(NO_ADDRESS_SET).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY, new AddCommand(expectedPerson));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_EMAIL_BOB + VALID_ADDRESS_BOB, expectedMessage);
    }

```
###### \java\seedu\address\logic\parser\NoteCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the NoteCommand code.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class NoteCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE);

    private NoteCommandParser parser = new NoteCommandParser();

    @Test
    public void parse_validArgs_returnsNoteCommand() {

        String userInputNotes = INDEX_FIRST_PERSON.getOneBased() + " " + "remark";
        String userInputNoNotes = INDEX_FIRST_PERSON.getOneBased() + " ";
        String userInputNotesAmy = INDEX_FIRST_PERSON.getOneBased() + " " + VALID_NOTE_AMY;

        assertParseSuccess(parser, userInputNotes, new NoteCommand(INDEX_FIRST_PERSON, new Note("remark")));
        assertParseSuccess(parser, userInputNoNotes, new NoteCommand(INDEX_FIRST_PERSON, new Note("")));
        assertParseSuccess(parser, userInputNotesAmy, new NoteCommand(INDEX_FIRST_PERSON, new Note(VALID_NOTE_AMY)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", MESSAGE_INVALID_FORMAT);
        // no index and no tag specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_NOTE_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_NOTE_AMY, MESSAGE_INVALID_FORMAT);
    }
}
```
###### \java\seedu\address\model\person\IdTest.java
``` java

public class IdTest {

    @Test
    public void isValidId() {
        // invalid note
        assertFalse(Id.isValidId(null));

        //// valid note
        assertTrue(Id.isValidId("")); // Any string
        assertTrue(Id.isValidId("123abc!@#")); // Any string
    }
}


```
###### \java\seedu\address\model\person\LastUpdatedTest.java
``` java
public class LastUpdatedTest {

    @Test
    public void isValidLastUpdated() {
        // invalid lastUpdated
        assertFalse(LastUpdated.isValidLastUpdated("")); // Any string
        assertFalse(LastUpdated.isValidLastUpdated("123abc!@#")); // Any string
        assertFalse(LastUpdated.isValidLastUpdated("2017-10-31T16:20:34Z"));
        assertFalse(LastUpdated.isValidLastUpdated("2017-10-31T16:20:34.9891211Z"));

        //valid lastUpdated
        assertTrue(LastUpdated.isValidLastUpdated("2017-10-31T16:20:34.989Z"));
        assertTrue(LastUpdated.isValidLastUpdated("2017-10-31T16:20:34.989121Z"));
    }
}


```
###### \java\seedu\address\model\person\NoteTest.java
``` java
public class NoteTest {

    @Test
    public void isValidNote() {
        // invalid note
        assertFalse(Note.isValidNote(null));

        //// valid note
        assertTrue(Note.isValidNote("")); // Any string
        assertTrue(Note.isValidNote("123abc!@#")); // Any string
    }
}


```

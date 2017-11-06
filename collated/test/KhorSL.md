# KhorSL
###### \java\seedu\address\logic\commands\AddMultipleCommandTest.java
``` java
public class AddMultipleCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddMultipleCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        ArrayList<ReadOnlyPerson> validPersonArrayList = new ArrayList<>();
        Person validPerson1 = new PersonBuilder().withName("Alice").build();
        Person validPerson2 = new PersonBuilder().withName("Bob").build();
        validPersonArrayList.add(validPerson1);
        validPersonArrayList.add(validPerson2);

        CommandResult commandResult = getAddMultipleCommandForPerson(validPersonArrayList, modelStub).execute();

        StringBuilder successMessage = new StringBuilder();
        for(ReadOnlyPerson personToAdd: validPersonArrayList) {
            successMessage.append(System.lineSeparator());
            successMessage.append(personToAdd);
        }

        assertEquals(String.format(AddMultipleCommand.MESSAGE_SUCCESS, successMessage), commandResult.feedbackToUser);
        assertEquals(validPersonArrayList, modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePersonException();
        ArrayList<ReadOnlyPerson> validPersonArrayList = new ArrayList<>();
        Person validPerson1 = new PersonBuilder().withName("Alice").build();
        Person validPerson2 = new PersonBuilder().withName("Bob").build();
        validPersonArrayList.add(validPerson1);
        validPersonArrayList.add(validPerson2);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddMultipleCommand.MESSAGE_DUPLICATE_PERSON);

        getAddMultipleCommandForPerson(validPersonArrayList, modelStub).execute();
    }

    @Test
    public void equals() {
        ArrayList<ReadOnlyPerson> personArrayList1 = new ArrayList<>();
        ArrayList<ReadOnlyPerson> personArrayList2 = new ArrayList<>();

        ReadOnlyPerson alice = new PersonBuilder().withName("Alice").build();
        ReadOnlyPerson bob = new PersonBuilder().withName("Bob").build();
        ReadOnlyPerson mary = new PersonBuilder().withName("Mary").build();
        ReadOnlyPerson jane = new PersonBuilder().withName("Jane").build();

        personArrayList1.add(alice);
        personArrayList1.add(bob);
        personArrayList2.add(mary);
        personArrayList2.add(jane);

        AddMultipleCommand addPersonArrayList1 = new AddMultipleCommand(personArrayList1);
        AddMultipleCommand addPersonArrayList2 = new AddMultipleCommand(personArrayList2);

        // same object -> returns true
        assertTrue(addPersonArrayList1.equals(addPersonArrayList1));

        // same values -> returns true
        AddMultipleCommand addPersonArrayList1Copy = new AddMultipleCommand(personArrayList1);
        assertTrue(addPersonArrayList1.equals(addPersonArrayList1Copy));

        // different types -> returns false
        assertFalse(addPersonArrayList1.equals(1));

        // null -> returns false
        assertFalse(addPersonArrayList1.equals(null));

        // different person -> returns false
        assertFalse(addPersonArrayList1.equals(addPersonArrayList2));
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private AddMultipleCommand getAddMultipleCommandForPerson(ArrayList<ReadOnlyPerson> personList, Model model) {
        AddMultipleCommand command = new AddMultipleCommand(personList);
        command.setData(model, new CommandHistory(), new UndoRedoStack(), null);
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredListToShowAll() {
            fail("This method should not be called.");
        }

        @Override
        public void mergeAddressBook(ObservableList<ReadOnlyPerson> newFilePersonList) {
            fail("This method should not be called.");
        }

    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            personsAdded.add(new Person(person));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        HashMap<String, List<String>> firstPredicateMap = new HashMap<>();
        HashMap<String, List<String>> secondPredicateMap = new HashMap<>();
        firstPredicateMap.put("first", Collections.singletonList("first"));
        secondPredicateMap.put("second", Collections.singletonList("second"));

        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(firstPredicateMap);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(secondPredicateMap);

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand(" n/Kurz Elle Kunz r/dummy e/@dummy.com");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String userInput) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL);

        String trimmedArgsName;
        String trimmedArgsTag;
        String trimmedArgsEmail;
        String[] keywordNameList;
        String[] keywordTagList;
        String[] keywordEmailList;
        HashMap<String, List<String>> mapKeywords = new HashMap<>();

        try {
            if (argumentMultimap.getValue(PREFIX_NAME).isPresent()) {
                trimmedArgsName = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_NAME)).get().trim();
                if (trimmedArgsName.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
                keywordNameList = trimmedArgsName.split("\\s+");
                mapKeywords.put(PREFIX_NAME.toString(), Arrays.asList(keywordNameList));
            }

            if (argumentMultimap.getValue(PREFIX_TAG).isPresent()) {
                trimmedArgsTag = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_TAG)).get().trim();
                if (trimmedArgsTag.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
                keywordTagList = trimmedArgsTag.split("\\s+");
                mapKeywords.put(PREFIX_TAG.toString(), Arrays.asList(keywordTagList));
            }

            if (argumentMultimap.getValue(PREFIX_EMAIL).isPresent()) {
                trimmedArgsEmail = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_EMAIL)).get().trim();
                if (trimmedArgsEmail.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
                keywordEmailList = trimmedArgsEmail.split("\\s+");
                mapKeywords.put(PREFIX_EMAIL.toString(), Arrays.asList(keywordEmailList));
            }

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        FindCommand command =
                new FindCommand(new PersonContainsKeywordsPredicate(mapKeywords));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), null);
        return command;
    }
```
###### \java\seedu\address\logic\commands\MergeCommandTest.java
``` java
/**
 * Contains integration test (interaction with Model) for {@code MergeCommand}
 */
public class MergeCommandTest {
    private final String TEST_DATA_ERROR_FILE_PATH = "./src/test/data/XmlAddressBookStorageTest/DataConversionError.xml";
    private final String TEST_NEW_FILE_PATH = "./src/test/data/XmlAddressBookStorageTest/TestNewFile.xml";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager();
    private Email emailManager = new EmailManager();
    private Logic logic = new LogicManager(model, emailManager);

    @Test
    public void equals() {
        MergeCommand mergeCommandFirst = new MergeCommand("./dummy/path/file1.txt");
        MergeCommand mergeCommandSecond = new MergeCommand("./dummy/path/file2.txt");

        // same object -> returns true
        assertTrue(mergeCommandFirst.equals(mergeCommandFirst));

        // same values -> returns true
        MergeCommand mergeCommandFirstCopy = new MergeCommand("./dummy/path/file1.txt");
        assertTrue(mergeCommandFirst.equals(mergeCommandFirstCopy));

        // different types -> returns false
        assertFalse(mergeCommandFirst.equals(1));

        // null -> returns false
        assertFalse(mergeCommandFirst.equals(null));

        // different file path -> returns false
        assertFalse(mergeCommandFirst.equals(mergeCommandSecond));
    }

    @Test
    public void merge_success() {
        // uses model and logic stubs to ensure testing files do not merge into actual data
        ModelStubAcceptingMergePath modelStub = new ModelStubAcceptingMergePath();
        Logic logicStub = new LogicManager(modelStub, emailManager);

        String mergeCommand = MergeCommand.COMMAND_WORD + " " + TEST_NEW_FILE_PATH;
        assertCommandSuccess(mergeCommand, MergeCommand.MESSAGE_SUCCESS, logicStub);
    }

    @Test
    public void merge_fileNotFound_failure() {
        String mergeCommand = MergeCommand.COMMAND_WORD + " " + "./dummy/path/file.xml";
        assertCommandFailure(mergeCommand, CommandException.class, MergeCommand.MESSAGE_FILE_NOT_FOUND, logic);
    }

    @Test
    public void merge_dataConversionError_failure() {
        String mergeCommand = MergeCommand.COMMAND_WORD + " " + TEST_DATA_ERROR_FILE_PATH;
        assertCommandFailure(mergeCommand, CommandException.class, MergeCommand.MESSAGE_DATA_CONVERSION_ERROR, logic);
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedModel} is as specified.
     *
     * @see #assertCommandBehavior(Class, String, String, Logic)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, Logic expectedLogic) {
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedLogic);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandBehavior(Class, String, String, Logic)
     */
    private void assertCommandFailure(String inputCommand, Class<?> expectedException, String expectedMessage, Logic expectedLogic) {
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedLogic);
    }

    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
                                       String expectedMessage, Logic expectedLogic) {

        try {
            CommandResult result = expectedLogic.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredListToShowAll() {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
        }

        @Override
        public void mergeAddressBook(ObservableList<ReadOnlyPerson> newFilePersonList) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accept the merge path given.
     */
    private class ModelStubAcceptingMergePath extends MergeCommandTest.ModelStub {
        ObservableList<ReadOnlyPerson> mergeFilePersonList;

        @Override
        public void mergeAddressBook(ObservableList<ReadOnlyPerson> newFilePersonList) {
            mergeFilePersonList = newFilePersonList;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### \java\seedu\address\logic\parser\MergeCommandParserTest.java
``` java
public class MergeCommandParserTest {
    private MergeCommandParser parser = new MergeCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MergeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsMergeCommand() {
        // no leading and trailing whitespaces
        MergeCommand expectedMergeCommand = new MergeCommand("./some/data/path/file.xml");
        // leading and trailing whitespaces is not part of user input, but to accommodate for tokenizer
        assertParseSuccess(parser, "  \n    \t  ./some/data/path/file.xml   \n  \t",
                expectedMergeCommand);
    }

}
```
###### \java\systemtests\AddMultipleCommandSystemTest.java
``` java
public class AddMultipleCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();

        /* Case: add from a file that contains duplicated persons --> rejected */
        final String DUPLICATE_PERSONS_FILEPATH = "./src/test/data/AddMultipleCommandSystemTest/duplicatePersons.txt";
        String command = AddMultipleCommand.COMMAND_WORD + " " + DUPLICATE_PERSONS_FILEPATH;
        String expectedResultMessage = AddMultipleCommand.MESSAGE_DUPLICATE_PERSON;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: add from a file that contains missing field name */
        final String MISSING_FIELD_NAME_FILEPATH = "./src/test/data/AddMultipleCommandSystemTest/missingPrefix_name.txt";
        command = AddMultipleCommand.COMMAND_WORD + " " + MISSING_FIELD_NAME_FILEPATH;
        expectedResultMessage = String.format(MESSAGE_INVALID_PERSON_FORMAT, AddMultipleCommand.MESSAGE_PERSON_FORMAT);
        assertCommandFailure(command, expectedResultMessage);

        /* Case: add from a file that does not exist in the data folder --> rejected */
        String NOT_EXISTS_FILE = "doesNotExist.txt";
        command = AddMultipleCommand.COMMAND_WORD + "  " + NOT_EXISTS_FILE;
        expectedResultMessage = String.format(AddMultipleCommand.MESSAGE_INVALID_FILE, NOT_EXISTS_FILE);
        assertCommandFailure(command, expectedResultMessage);

       /* Case add from a file containing valid persons --> added */
        String VALID_PERSONS_FILEPATH = "./src/test/data/AddMultipleCommandSystemTest/validPersons_missingOptionalFields.txt";
        ArrayList<ReadOnlyPerson> personList = new ArrayList<>();
        ReadOnlyPerson amy = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withTags().build();
        ReadOnlyPerson bob = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags().build();
        personList.add(amy);
        personList.add(bob);
        command = AddMultipleCommand.COMMAND_WORD + " " + VALID_PERSONS_FILEPATH;
        assertCommandSuccess(command, personList);

         /* Case: undo adding persons to the list -> persons deleted */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding persons to the list -> persons added again */
        command = RedoCommand.COMMAND_WORD;
        try {
            for (ReadOnlyPerson person : personList) {
                model.addPerson(person);
            }
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);
    }

    /**
     * Executes the {@code AddMultipleCommand} that adds {@code toAdd} to the model and verifies that the command box displays
     * an empty string, the result display box displays the success message of executing {@code AddMultipleCommand} with the
     * details of {@code toAdd}, and the model related components equal to the current model added with {@code toAdd}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, ArrayList<ReadOnlyPerson> toAdd) {
        Model expectedModel = getModel();
        try {
            for (ReadOnlyPerson person : toAdd) {
                expectedModel.addPerson(person);
            }
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }

        StringBuilder successMessage = new StringBuilder();
        for (ReadOnlyPerson personToAdd: toAdd) {
            successMessage.append(System.lineSeparator());
            successMessage.append(personToAdd);
        }
        String expectedResultMessage = String.format(AddMultipleCommand.MESSAGE_SUCCESS, successMessage);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }
```
###### \java\systemtests\MergeCommandSystemTest.java
``` java
public class MergeCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void merge_success() throws Exception {
        Model expectedModel = getModel();

        /* Case: Merge a new file into the default address book data -> merged **/
        final String TEST_NEW_FILE_PATH = "./src/test/data/XmlAddressBookStorageTest/TestNewFile.xml";
        String command = MergeCommand.COMMAND_WORD + " " + TEST_NEW_FILE_PATH;
        assertCommandSuccess(command, TEST_NEW_FILE_PATH);

        /* Case: Undo the previous merge -> address book data back to previous state **/
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: Merge the same file twice into the default address book -> merged **/
        command = MergeCommand.COMMAND_WORD + " " + TEST_NEW_FILE_PATH;
        assertCommandSuccess(command, TEST_NEW_FILE_PATH);

        command = MergeCommand.COMMAND_WORD + " " + TEST_NEW_FILE_PATH;
        assertCommandSuccess(command, TEST_NEW_FILE_PATH);

        /* Case: Merge the new file into an empty address book -> merged **/
        command = ClearCommand.COMMAND_WORD;
        expectedResultMessage = ClearCommand.MESSAGE_SUCCESS;
        expectedModel.resetData(new AddressBook());
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        command = MergeCommand.COMMAND_WORD + " " + TEST_NEW_FILE_PATH;
        assertCommandSuccess(command, TEST_NEW_FILE_PATH);
    }

    @Test
    public void merge_failure() throws Exception {
        String command = MergeCommand.COMMAND_WORD + " " + "./empty/file/path/file.xml";
        assertCommandFailure(command, MergeCommand.MESSAGE_FILE_NOT_FOUND);

        command = MergeCommand.COMMAND_WORD + " " + "./src/test/data/XmlAddressBookStorageTest/DataConversionError.xml";
        assertCommandFailure(command, MergeCommand.MESSAGE_DATA_CONVERSION_ERROR);
    }

    /**
     * Executes the {@code MergeCommand} that adds {@code toAdd} to the model and verifies that the command box displays
     * an empty string, the result display box displays the success message of executing {@code MergeCommand} with the
     * details from {@code newFilePath}, and the model related components equal to the current model added with {@code newFilePath}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String newFilePath) {
        Model expectedModel = getModel();
        String expectedResultMessage = MergeCommand.MESSAGE_SUCCESS;

        File newFile = new File(newFilePath);
        XmlSerializableAddressBook xmlSerializableAddressBook = new XmlSerializableAddressBook();
        ObservableList<ReadOnlyPerson> newPersons;
        try {
            xmlSerializableAddressBook = XmlFileStorage.loadDataFromSaveFile(newFile);
        } catch (FileNotFoundException fnfe) {
            expectedResultMessage = MergeCommand.MESSAGE_FILE_NOT_FOUND;
        } catch (DataConversionException dce) {
            expectedResultMessage = MergeCommand.MESSAGE_DATA_CONVERSION_ERROR;
        }
        newPersons = xmlSerializableAddressBook.getPersonList();

        for (ReadOnlyPerson rop : newPersons) {
            try {
                expectedModel.addPerson(rop);
            } catch (DuplicatePersonException dpe) {
                continue; // simulates merge command to ignore duplicated person
            }
        }

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }
```

# KhorSL
###### /src/main/java/seedu/address/model/person/PersonContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} or {@code Tag} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final HashMap<String, List<String>> keywords;

    public PersonContainsKeywordsPredicate(HashMap<String, List<String>> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean result = false;
        if (keywords.containsKey(PREFIX_NAME.toString()) && !keywords.get(PREFIX_NAME.toString()).isEmpty()) {
            result = keywords.get(PREFIX_NAME.toString()).stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));

        }

        if (keywords.containsKey(PREFIX_TAG.toString()) && !keywords.get(PREFIX_TAG.toString()).isEmpty()) {
            result = result || person.containTags(keywords.get(PREFIX_TAG.toString()));
        }

        if (keywords.containsKey(PREFIX_EMAIL.toString()) && !keywords.get(PREFIX_EMAIL.toString()).isEmpty()) {
            result = result || keywords.get(PREFIX_EMAIL.toString()).stream()
                    .anyMatch(keyword -> person.getEmail().value.contains(keyword));
        }

        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /src/main/java/seedu/address/model/Model.java
``` java
    /** Merges new file data {@code newFilePersonList} to default addressbook storage **/
    void mergeAddressBook(ObservableList<ReadOnlyPerson> newFilePersonList);
```
###### /src/main/java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void mergeAddressBook(ObservableList<ReadOnlyPerson> newFilePersonList) {
        Boolean isAddressBookChanged = false;
        ObservableList<ReadOnlyPerson> defaultFilePersonList = addressBook.getPersonList();

        for (ReadOnlyPerson newDataPerson : newFilePersonList) {
            boolean isSamePerson = false;
            for (ReadOnlyPerson defaultDataPerson : defaultFilePersonList) {
                if (defaultDataPerson.equals(newDataPerson)) {
                    isSamePerson = true;
                    break;
                }
            }
            if (!isSamePerson) {
                try {
                    addressBook.addPerson(new Person(newDataPerson));
                } catch (DuplicatePersonException dpe) {
                    assert false : "Unexpected exception " + dpe.getMessage();
                }
                isAddressBookChanged = true;
            }
        }

        if (isAddressBookChanged) {
            updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            indicateAddressBookChanged();
        }
    }
```
###### /src/main/java/seedu/address/logic/commands/AddMultipleCommand.java
``` java
/**
 * Adds a person to the address book.
 */
public class AddMultipleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "multiple";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds multiple person to the address book. "
            + "Parameters: "
            + "FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " "
            + "./data/personsToAdd.txt";

    public static final String MESSAGE_PERSON_FORMAT = "Person format in .txt file: "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_AVATAR + "AVATAR IMAGE FILE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_AVATAR + "john_doe.png "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person(s) added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "The persons list contains person(s) that already exists in the address book.";
    public static final String MESSAGE_INVALID_FILE = "Unable to open file '%1$s'";
    public static final String MESSAGE_ERROR_FILE = "Error reading file '%1$s'";

    private ArrayList<Person> toAdd;
    private ArrayList<ReadOnlyPerson> readOnlyPeople;

    /**
     * Creates an AddMultipleCommand to add the specified {@code ReadOnlyPerson}
     */
    public AddMultipleCommand(ArrayList<ReadOnlyPerson> personsList) {
        readOnlyPeople = personsList;
        toAdd = new ArrayList<>();
        for (ReadOnlyPerson person : personsList) {
            toAdd.add(new Person(person));
        }
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        int numberOfPersonsAdded = 0;
        StringBuilder successMessage = new StringBuilder();
        requireNonNull(model);
        try {
            for (Person personToAdd : toAdd) {
                model.addPerson(personToAdd);
                successMessage.append(System.lineSeparator());
                successMessage.append(personToAdd);
                numberOfPersonsAdded++;
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, successMessage));
        } catch (DuplicatePersonException e) {
            try {
                for (int i = 0; i < numberOfPersonsAdded; i++) {
                    model.deletePerson(readOnlyPeople.get(i));
                }
            } catch (PersonNotFoundException pnfe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMultipleCommand // instanceof handles nulls
                && toAdd.equals(((AddMultipleCommand) other).toAdd));
    }
}
```
###### /src/main/java/seedu/address/logic/commands/FindCommand.java
``` java
    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_ALIAS
            + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: PREFIX_PERSON_ATTRIBUTE/KEYWORD [MORE_KEYWORDS]... [MORE_PARAMETERS]...\n"
            + "Examples: " + System.lineSeparator()
            + "1) " + COMMAND_WORD + " " + PREFIX_NAME.toString() + "alice bob charlie" + System.lineSeparator()
            + "2) " + COMMAND_WORD + " " + PREFIX_TAG.toString() + "family friends" + System.lineSeparator()
            + "3) " + COMMAND_WORD + " " + PREFIX_NAME.toString() + "alice bob charlie"
            + " " + PREFIX_TAG.toString() + "family friends";

    private final PersonContainsKeywordsPredicate predicate;

    public FindCommand(PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }
```
###### /src/main/java/seedu/address/logic/commands/MergeCommand.java
``` java
/**
 * Merge the file given with the default storage file
 */
public class MergeCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "merge";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": merge the file given with the default storage file\n"
            + "Parameters: "
            + "XML_FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " ./data/newFile.xml";
    public static final String MESSAGE_SUCCESS = "File merged successfully.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File not found.";
    public static final String MESSAGE_DATA_CONVERSION_ERROR = "Unable to convert file data.";

    private final String newFilePath;

    public MergeCommand(String newFilePath) {
        this.newFilePath = newFilePath;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        XmlSerializableAddressBook newFileData;
        try {
            newFileData = XmlFileStorage.loadDataFromSaveFile(new File(newFilePath));
        } catch (FileNotFoundException fne) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        } catch (DataConversionException dce) {
            throw new CommandException(MESSAGE_DATA_CONVERSION_ERROR);
        }

        ObservableList<ReadOnlyPerson> newFilePersonList = newFileData.getPersonList();
        model.mergeAddressBook(newFilePersonList);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack, Email emailManager) {
        this.model = model;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MergeCommand // instanceof handles nulls
                && this.newFilePath.equals(((MergeCommand) other).newFilePath)); // state check
    }
}
```
###### /src/main/java/seedu/address/logic/commands/MergeCommand.java
``` java

```
###### /src/main/java/seedu/address/logic/parser/AddMultipleCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddMultipleCommand object
 */
public class AddMultipleCommandParser implements Parser<AddMultipleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMultipleCommand
     * then parse data from file name given arguments if it exists
     * and returns an AddMultipleCommand object for execution.
     *
     * @param args arguments
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMultipleCommand parse(String args) throws ParseException {
        String filePath = args.trim();
        if (filePath.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMultipleCommand.MESSAGE_USAGE));
        }

        ArrayList<ReadOnlyPerson> personsList = new ArrayList<>();
        File fileToRead = new File(filePath);
        String data;

        if (!FileUtil.isFileExists(fileToRead)) {
            throw new ParseException(String.format(AddMultipleCommand.MESSAGE_INVALID_FILE, filePath));
        }

        try {
            data = FileUtil.readFromFile(fileToRead);
        } catch (IOException ie) {
            throw new ParseException(String.format(AddMultipleCommand.MESSAGE_ERROR_FILE, filePath));
        }

        String[] lines = data.split(System.lineSeparator());

        for (String eachLine : lines) {
            String toAdd = " " + eachLine;
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(toAdd, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                            PREFIX_TAG);
            if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)) {
                throw new ParseException(String.format(MESSAGE_INVALID_PERSON_FORMAT,
                        AddMultipleCommand.MESSAGE_PERSON_FORMAT));
            }
            try {
                Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
                Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
                Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
                Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
                Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
                Comment comment = new Comment(""); // add command does not allow adding comments straight away
                Appoint appoint = new Appoint("");
                ReadOnlyPerson person = new Person(name, phone, email, address, comment, appoint, tagList);

                personsList.add(person);
            } catch (IllegalValueException ive) {
                throw new ParseException(ive.getMessage(), ive);
            }
        }

        return new AddMultipleCommand(personsList);
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
###### /src/main/java/seedu/address/logic/parser/FindCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL);

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

        if (mapKeywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new PersonContainsKeywordsPredicate(mapKeywords));
    }

}
```
###### /src/main/java/seedu/address/logic/parser/MergeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class MergeCommandParser implements Parser<MergeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MergeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MergeCommand.MESSAGE_USAGE));
        }

        return new MergeCommand(trimmedArgs);
    }

}
```
###### /src/test/java/seedu/address/logic/commands/FindCommandTest.java
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
###### /src/test/java/seedu/address/logic/commands/AddMultipleCommandTest.java
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
        for (ReadOnlyPerson personToAdd: validPersonArrayList) {
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
###### /src/test/java/seedu/address/logic/commands/MergeCommandTest.java
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
###### /src/test/java/seedu/address/logic/parser/MergeCommandParserTest.java
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
###### /src/test/java/systemtests/MergeCommandSystemTest.java
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
###### /src/test/java/systemtests/AddMultipleCommandSystemTest.java
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
# KhorSL
###### \java\seedu\address\commons\util\StringUtilTest.java
``` java
    //---------------- Tests for containsWordIgnoreCaseAndCharacters --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordIgnoreCaseAndCharacters_nullWord_throwsNullPointerException() {
        assertExceptionThrownForIgnoreCaseAndCharacters(NullPointerException.class, "typical sentence",
                null, Optional.empty());
    }

    private void assertExceptionThrownForIgnoreCaseAndCharacters(Class<? extends Throwable> exceptionClass,
                                                        String sentence, String word, Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsWordIgnoreCaseAndCharacters(sentence, word);
    }

    @Test
    public void containsWordIgnoreCaseAndCharacters_emptyWord_throwsIllegalArgumentException() {
        assertExceptionThrownForIgnoreCaseAndCharacters(IllegalArgumentException.class, "typical sentence", "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsWordIgnoreCaseAndCharacters_multipleWords_throwsIllegalArgumentException() {
        assertExceptionThrownForIgnoreCaseAndCharacters(IllegalArgumentException.class, "typical sentence", "aaa BBB",
                Optional.of("Word parameter should be a single word"));
    }

    @Test
    public void containsWordIgnoreCaseAndCharacters_nullSentence_throwsNullPointerException() {
        assertExceptionThrownForIgnoreCaseAndCharacters(NullPointerException.class, null, "abc", Optional.empty());
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *
     * Possible scenarios returning false:
     *   - query word matches part of a sentence word
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsWordIgnoreCaseAndCharacters_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsWordIgnoreCaseAndCharacters("", "abc")); // Boundary case
        assertFalse(StringUtil.containsWordIgnoreCaseAndCharacters("    ", "123"));

        // Matches a partial word only
        assertFalse(StringUtil.containsWordIgnoreCaseAndCharacters("aaa bbb ccc", "bb"));
        assertFalse(StringUtil.containsWordIgnoreCaseAndCharacters("aaa bbb ccc", "bbbb"));

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters("aaa bBb ccc", "Aaa")); // First word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters("aaa. bBb ccc", "Aaa")); // boundary case
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters("aaa bBb ccc1", "CCc1")); // Last word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters("aaa bBb ccc_1", "CCc_1")); // boundary case
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters("  AAA   bBb   ccc  ", "aaa"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters("Aaa", "aaa")); // boundary case
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters(",Aaa", "aaa")); // boundary case
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters("AAA bBb ccc  bbb", "bbB"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters("AAA b_Bb ccc  bb_b", "bbB"));
    }

    //---------------- Tests for containsDate --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsDate_nullWord_throwsNullPointerException() {
        assertExceptionThrownForDate(NullPointerException.class, "typical sentence", null, Optional.empty());
    }

    private void assertExceptionThrownForDate(Class<? extends Throwable> exceptionClass, String sentence, String word,
                                       Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsDate(sentence, word);
    }

    @Test
    public void containsDate_emptyWord_throwsIllegalArgumentException() {
        assertExceptionThrownForDate(IllegalArgumentException.class, "typical sentence", "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsDate_multipleWords_throwsIllegalArgumentException() {
        assertExceptionThrownForDate(IllegalArgumentException.class, "typical sentence", "aaa BBB",
                Optional.of("Word parameter should be a single word"));
    }

    @Test
    public void containsDate_nullSentence_throwsNullPointerException() {
        assertExceptionThrownForDate(NullPointerException.class, null, "abc", Optional.empty());
    }

    /*
     * Valid equivalence partitions for date:
     *   - any date with valid format
     *   - date with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one date
     *   - multiple words with date
     *   - sentence with extra spaces
     *   - sentence with wrong format date
     *
     * Possible scenarios returning true:
     *   - matches first date in sentence
     *   - last date in sentence
     *   - middle date in sentence
     *   - matches multiple dates
     *
     * Possible scenarios returning false:
     *   - query date matches part of a sentence date
     *   - sentence date matches part of the query date
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsDate_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsDate("", "20/10/2017")); // Boundary case
        assertFalse(StringUtil.containsDate("    ", "20/10/2017"));

        // Matches a partial date only
        assertFalse(StringUtil.containsDate("20/10/2017 05:30", "20/10/2018"));
        assertFalse(StringUtil.containsDate("20/10/17 05:30", "20/10/2017")); // Query word bigger than sentence word

        // Sentence with wrong format date
        assertFalse(StringUtil.containsDate("20/10/17", "20/10/17"));

        // Matches word in the date sentence
        assertTrue(StringUtil.containsDate("20/10/2017 bBb ccc", "20/10/2017")); // First word (boundary case)
        assertTrue(StringUtil.containsDate("aaa bBb 20/10/2017", "20/10/2017")); // Last word (boundary case)
        assertTrue(StringUtil.containsDate("  AAA   20/10/2017   ccc  ", "20/10/2017")); // Sentence has extra spaces
        assertTrue(StringUtil.containsDate("20/10/2017", "20/10/2017")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsDate("aaa bbb 20/10/2017", "  20/10/2017  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsDate("AAA 20/10/2017 ccc  20/10/2017", "20/10/2017"));
    }

    //---------------- Tests for containsTime --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsTime_nullWord_throwsNullPointerException() {
        assertExceptionThrownForTime(NullPointerException.class, "typical sentence", null, Optional.empty());
    }

    private void assertExceptionThrownForTime(Class<? extends Throwable> exceptionClass, String sentence, String word,
                                              Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsDate(sentence, word);
    }

    @Test
    public void containsTime_emptyWord_throwsIllegalArgumentException() {
        assertExceptionThrownForTime(IllegalArgumentException.class, "typical sentence", "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsTime_multipleWords_throwsIllegalArgumentException() {
        assertExceptionThrownForTime(IllegalArgumentException.class, "typical sentence", "aaa BBB",
                Optional.of("Word parameter should be a single word"));
    }

    @Test
    public void containsTime_nullSentence_throwsNullPointerException() {
        assertExceptionThrownForTime(NullPointerException.class, null, "abc", Optional.empty());
    }

    /*
     * Valid equivalence partitions for time:
     *   - any time with valid format
     *   - time with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one time
     *   - multiple words with time
     *   - sentence with extra spaces
     *   - sentence with wrong format time
     *
     * Possible scenarios returning true:
     *   - matches first time in sentence
     *   - last time in sentence
     *   - middle time in sentence
     *   - matches multiple times
     *
     * Possible scenarios returning false:
     *   - query date matches part of a sentence time
     *   - sentence date matches part of the query time
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsTime_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsTime("", "10:50")); // Boundary case
        assertFalse(StringUtil.containsTime("    ", "10:50"));

        // Matches a partial time only
        assertFalse(StringUtil.containsTime("20/10/2017 05:30", "05:40"));

        // Sentence with wrong format time
        assertFalse(StringUtil.containsTime("5:30", "5:30"));

        // Matches word in the date sentence
        assertTrue(StringUtil.containsTime("05:30 bBb ccc", "05:30")); // First word (boundary case)
        assertTrue(StringUtil.containsTime("aaa bBb 05:30", "05:30")); // Last word (boundary case)
        assertTrue(StringUtil.containsTime("  AAA   05:30   ccc  ", "05:30")); // Sentence has extra spaces
        assertTrue(StringUtil.containsTime("05:30", "05:30")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsTime("aaa bbb 05:30", "  05:30  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsTime("AAA 05:30 ccc  05:30", "05:30"));
    }

    //---------------- Tests for extractDates --------------------------------------

    /*
     * Equivalence Partitions:
     *   - empty sentence
     *   - multiple dates
     *   - single dates
     *   - sentence containing other words other that date
     *
     * Possible scenario returning dates:
     *    - There is date is the first word of the sentence
     *    - date is the middle word of the sentence
     *    - date is the last word of the sentence
     *    - matches multiple date in the sentence
     *    - matches multiple date in a sentence containing other words
     *
     * Possible scenario returning empty ArrayList<String>:
     *     - sentence do not contain any dates
     *     - sentence do not contain date with correct format
     */

    @Test
    public void extractDates_validInputs_correctResults() {
        ArrayList<String> expectedSingleDateList = new ArrayList<>();
        expectedSingleDateList.add("20/10/2017");

        ArrayList<String> expectedMultipleDatesList1 = new ArrayList<>();
        expectedMultipleDatesList1.add("20/10/2017");
        expectedMultipleDatesList1.add("20/10/2018");
        expectedMultipleDatesList1.add("20/10/2019");

        ArrayList<String> expectedMultipleDatesList2 = new ArrayList<>();
        expectedMultipleDatesList2.add("20/10/2017");
        expectedMultipleDatesList2.add("20/10/2017");

        // Empty sentence
        assertCorrectDateResult("", new ArrayList<>());
        assertCorrectDateResult("    ", new ArrayList<>());

        // Sentence with wrong format date
        assertCorrectDateResult("20/10/17", new ArrayList<>());
        assertCorrectDateResult("01/13/2017", new ArrayList<>()); // date wrong format with mm/dd/yyyy
        assertCorrectDateResult("01-01-2017", new ArrayList<>()); // date dd-mm-yyyy

        // single date
        assertCorrectDateResult("20/10/2017", expectedSingleDateList);

        // multiple dates, dates as the first, middle and last word
        assertCorrectDateResult("20/10/2017 20/10/2018 20/10/2019", expectedMultipleDatesList1);
        assertCorrectDateResult("20/10/2017 20/10/2017", expectedMultipleDatesList2); // multiple same dates
        assertCorrectDateResult("20/10/2017 10:50", expectedSingleDateList);
    }

    /**
     * Assert true if {@code sentence} contains dates in {@code expected}
     *
     * @param sentence should not be null
     * @param expected should not be null
     */
    public void assertCorrectDateResult(String sentence, ArrayList<String> expected) {
        ArrayList<String> actual = StringUtil.extractDates(sentence);
        assertTrue(actual.equals(expected));
    }

    //---------------- Tests for extractTimes --------------------------------------

    /*
     * Equivalence Partitions:
     *   - empty sentence
     *   - multiple times
     *   - single times
     *   - sentence containing other words other that time
     *
     * Possible scenario returning times:
     *    - There is time is the first word of the sentence
     *    - date is the middle time of the sentence
     *    - date is the last time of the sentence
     *    - matches multiple time in the sentence
     *    - matches multiple time in a sentence containing other words
     *
     * Possible scenario returning empty ArrayList<String>:
     *     - sentence do not contain any times
     *     - sentence do not contain time with correct format
     */

    @Test
    public void extractTimes_validInputs_correctResults() {
        ArrayList<String> expectedSingleTimeList = new ArrayList<>();
        expectedSingleTimeList.add("05:30");

        ArrayList<String> expectedMultipleTimesList1 = new ArrayList<>();
        expectedMultipleTimesList1.add("00:00");
        expectedMultipleTimesList1.add("10:40");
        expectedMultipleTimesList1.add("23:59");

        ArrayList<String> expectedMultipleTimesList2 = new ArrayList<>();
        expectedMultipleTimesList2.add("10:30");
        expectedMultipleTimesList2.add("10:30");

        // Empty sentence
        assertCorrectTimeResult("", new ArrayList<>());
        assertCorrectTimeResult("    ", new ArrayList<>());

        // Sentence with wrong format time
        assertCorrectTimeResult("0:00", new ArrayList<>());
        assertCorrectTimeResult("5:30", new ArrayList<>());
        assertCorrectTimeResult("24:59", new ArrayList<>());
        assertCorrectTimeResult("05-30", new ArrayList<>());

        // single date
        assertCorrectTimeResult("05:30", expectedSingleTimeList);

        // multiple dates, dates as the first, middle and last word
        assertCorrectTimeResult("00:00 10:40 23:59", expectedMultipleTimesList1);
        assertCorrectTimeResult("10:30 10:30", expectedMultipleTimesList2); // multiple same dates
        assertCorrectTimeResult("20/10/2017 05:30", expectedSingleTimeList);
    }

    /**
     * Assert true if {@code sentence} contains times in {@code expected}
     *
     * @param sentence should not be null
     * @param expected should not be null
     */
    public void assertCorrectTimeResult(String sentence, ArrayList<String> expected) {
        ArrayList<String> actual = StringUtil.extractTimes(sentence);
        assertTrue(actual.equals(expected));
    }
```
###### \java\seedu\address\logic\commands\AddMultipleCommandIntegrationTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code AddMultipleCommand}.
 */
public class AddMultipleCommandIntegrationTest extends GuiUnitTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() throws Exception {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        ArrayList<ReadOnlyPerson> validPersonArrayList = new ArrayList<>();
        Person validPerson1 = new PersonBuilder().withName("Alice").build();
        Person validPerson2 = new PersonBuilder().withName("Bob").build();
        Person validPerson3 = new PersonBuilder().withName("Casey").build();
        StringBuilder successMessage = new StringBuilder();

        // single person
        validPersonArrayList.add(validPerson1);

        for (ReadOnlyPerson validPerson : validPersonArrayList) {
            expectedModel.addPerson(validPerson);
        }

        for (ReadOnlyPerson personToAdd: validPersonArrayList) {
            successMessage.append(System.lineSeparator());
            successMessage.append(personToAdd);
        }

        assertCommandSuccess(prepareCommand(validPersonArrayList, model), model,
                String.format(AddMultipleCommand.MESSAGE_SUCCESS, successMessage), expectedModel);

        // multiple persons
        validPersonArrayList = new ArrayList<>();
        validPersonArrayList.add(validPerson3);
        validPersonArrayList.add(validPerson2);

        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        for (ReadOnlyPerson validPerson : validPersonArrayList) {
            expectedModel.addPerson(validPerson);
        }

        successMessage = new StringBuilder();
        for (ReadOnlyPerson personToAdd: validPersonArrayList) {
            successMessage.append(System.lineSeparator());
            successMessage.append(personToAdd);
        }

        assertCommandSuccess(prepareCommand(validPersonArrayList, model), model,
                String.format(AddMultipleCommand.MESSAGE_SUCCESS, successMessage), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        // 1 duplicate person (boundary case)
        Person firstPersonInList = new Person(model.getAddressBook().getPersonList().get(0));
        ArrayList<ReadOnlyPerson> personArrayList = new ArrayList<>();
        personArrayList.add(firstPersonInList);
        assertCommandFailure(prepareCommand(personArrayList, model), model,
                AddMultipleCommand.MESSAGE_DUPLICATE_PERSON);

        // more than 1 duplicate person
        Person secondPersonInList = new Person(model.getAddressBook().getPersonList().get(1));
        personArrayList = new ArrayList<>();
        personArrayList.add(firstPersonInList);
        personArrayList.add(secondPersonInList);
        assertCommandFailure(prepareCommand(personArrayList, model), model,
                AddMultipleCommand.MESSAGE_DUPLICATE_PERSON);

        // first person not duplicate, second person is duplicate
        Person validPerson = new PersonBuilder().withName("Alice").build();
        personArrayList = new ArrayList<>();
        personArrayList.add(validPerson);
        personArrayList.add(firstPersonInList);
        assertCommandFailure(prepareCommand(personArrayList, model), model,
                AddMultipleCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_emptyPersonList() {
        ArrayList<ReadOnlyPerson> personArrayList = new ArrayList<>();
        try {
            prepareCommand(personArrayList, model);
        } catch (AssertionError e) {
            return; // short circuit test here as assertion error should be caught
        }
        Assert.fail("Fails to catch Assertion Error");
    }

    /**
     * Generates a new {@code AddMultipleCommand} which upon execution,
     * adds persons in {@code personArrayList} into the {@code model}.
     */
    private AddMultipleCommand prepareCommand(ArrayList<ReadOnlyPerson> personArrayList, Model model) {
        AddMultipleCommand command = new AddMultipleCommand(personArrayList);
        command.setData(model, new CommandHistory(), new UndoRedoStack(), null);
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\AddMultipleCommandTest.java
``` java
public class AddMultipleCommandTest extends GuiUnitTest {

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
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest extends GuiUnitTest {
    private Model model;
    private Email emailManager = new EmailManager();
    private Logic logic = new LogicManager(model, emailManager);

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

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
        String command = FindCommand.COMMAND_WORD + " ";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure(ParseException.class, command, expectedMessage, logic);
    }

    @Test
    public void execute_singleKeyword() throws ParseException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand(" n/Kurz");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(CARL));

        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        command = prepareCommand(" e/lydia");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(FIONA));

        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        command = prepareCommand(" p/9482");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ELLE, FIONA, GEORGE));

        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        command = prepareCommand(" ap/12:12");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());

        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        command = prepareCommand(" a/wall");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(CARL));

        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        command = prepareCommand(" c/tetris");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(GEORGE));

        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        command = prepareCommand(" r/friend");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand(" n/Kurz Elle Kunz r/dummy e/@dummy.com");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand(" e/lydia a/wall p/224");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand(" e/lydia werner a/tokyo wall c/swim ap/10:30");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String userInput) throws ParseException {
        FindCommandParser parser = new FindCommandParser();
        FindCommand command = parser.parse(userInput);
        command.setData(model, new CommandHistory(), new UndoRedoStack(), null);
        return command;
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @param expectedException expected exception
     * @param inputCommand input command
     * @param expectedMessage expected message
     * @param expectedLogic expected logic
     */
    private void assertCommandFailure(Class<?> expectedException, String inputCommand,
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
```
###### \java\seedu\address\logic\commands\MergeCommandTest.java
``` java

/**
 * Contains integration test (interaction with Model) for {@code MergeCommand}
 */
public class MergeCommandTest extends GuiUnitTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final String testDataErrorFilePath =
            "./src/test/data/XmlAddressBookStorageTest/DataConversionError.xml";
    private final String testNewFilePath = "./src/test/data/XmlAddressBookStorageTest/TestNewFile.xml";

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

        String mergeCommand = MergeCommand.COMMAND_WORD + " " + testNewFilePath;
        assertCommandSuccess(mergeCommand, MergeCommand.MESSAGE_SUCCESS, logicStub);
    }

    @Test
    public void merge_fileNotFound_failure() {
        String mergeCommand = MergeCommand.COMMAND_WORD + " " + "./dummy/path/file.xml";
        assertCommandFailure(mergeCommand, CommandException.class, MergeCommand.MESSAGE_FILE_NOT_FOUND, logic);
    }

    @Test
    public void merge_dataConversionError_failure() {
        String mergeCommand = MergeCommand.COMMAND_WORD + " " + testDataErrorFilePath;
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
    private void assertCommandFailure(String inputCommand, Class<?> expectedException, String expectedMessage,
                                      Logic expectedLogic) {
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedLogic);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @param expectedException expected exception
     * @param inputCommand input command
     * @param expectedMessage expected message
     * @param expectedLogic expected logic
     */
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
        private ObservableList<ReadOnlyPerson> mergeFilePersonList;

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
###### \java\seedu\address\logic\parser\FindCommandParserTest.java
``` java
public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPrefix_throwsParseException() {
        assertParseFailure(parser, "alex john 91234567",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyFields_throwsParseException() {
        assertParseFailure(parser, " n/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " a/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " r/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " c/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " ap/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " p/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " e/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {

        // single user input
        HashMap<String, List<String>> expectedSingleFindCmdMap = new HashMap<>();
        expectedSingleFindCmdMap.put(PREFIX_NAME.toString(), Collections.singletonList("Alice"));
        FindCommand expectedSingleFindCmd =
                new FindCommand(new PersonContainsKeywordsPredicate(expectedSingleFindCmdMap));
        assertParseSuccess(parser, " n/Alice", expectedSingleFindCmd);

        expectedSingleFindCmdMap = new HashMap<>();
        expectedSingleFindCmdMap.put(PREFIX_TAG.toString(), Collections.singletonList("friends"));
        expectedSingleFindCmd = new FindCommand(new PersonContainsKeywordsPredicate(expectedSingleFindCmdMap));
        assertParseSuccess(parser, " r/friends", expectedSingleFindCmd);

        expectedSingleFindCmdMap = new HashMap<>();
        expectedSingleFindCmdMap.put(PREFIX_EMAIL.toString(), Collections.singletonList("friends@gmail.com"));
        expectedSingleFindCmd = new FindCommand(new PersonContainsKeywordsPredicate(expectedSingleFindCmdMap));
        assertParseSuccess(parser, " e/friends@gmail.com", expectedSingleFindCmd);

        expectedSingleFindCmdMap = new HashMap<>();
        expectedSingleFindCmdMap.put(PREFIX_PHONE.toString(), Collections.singletonList("91234567"));
        expectedSingleFindCmd = new FindCommand(new PersonContainsKeywordsPredicate(expectedSingleFindCmdMap));
        assertParseSuccess(parser, " p/91234567", expectedSingleFindCmd);

        expectedSingleFindCmdMap = new HashMap<>();
        expectedSingleFindCmdMap.put(PREFIX_ADDRESS.toString(), Arrays.asList("Felix", "Road", "23", "#12-12"));
        expectedSingleFindCmd = new FindCommand(new PersonContainsKeywordsPredicate(expectedSingleFindCmdMap));
        assertParseSuccess(parser, " a/Felix Road 23 #12-12", expectedSingleFindCmd);

        expectedSingleFindCmdMap = new HashMap<>();
        expectedSingleFindCmdMap.put(PREFIX_APPOINT.toString(), Arrays.asList("01/01/2017", "10:30"));
        expectedSingleFindCmd = new FindCommand(new PersonContainsKeywordsPredicate(expectedSingleFindCmdMap));
        assertParseSuccess(parser, " ap/01/01/2017 10:30", expectedSingleFindCmd);

        expectedSingleFindCmdMap = new HashMap<>();
        expectedSingleFindCmdMap.put(PREFIX_COMMENT.toString(), Collections.singletonList("funny"));
        expectedSingleFindCmd = new FindCommand(new PersonContainsKeywordsPredicate(expectedSingleFindCmdMap));
        assertParseSuccess(parser, " c/funny", expectedSingleFindCmd);

        // no leading and trailing whitespaces
        HashMap<String, List<String>> expectedFindCmdMap = new HashMap<>();
        expectedFindCmdMap.put(PREFIX_NAME.toString(), Arrays.asList("Alice", "Bob"));
        expectedFindCmdMap.put(PREFIX_TAG.toString(), Arrays.asList("friends", "family"));
        expectedFindCmdMap.put(PREFIX_EMAIL.toString(), Arrays.asList("@gmail.com", "@hotmail.com"));
        expectedFindCmdMap.put(PREFIX_ADDRESS.toString(), Arrays.asList("Felix", "Road", "23", "#12-12"));
        expectedFindCmdMap.put(PREFIX_APPOINT.toString(), Arrays.asList("01/01/2017", "10:30"));
        expectedFindCmdMap.put(PREFIX_COMMENT.toString(), Arrays.asList("funny", "swim"));
        expectedFindCmdMap.put(PREFIX_PHONE.toString(), Arrays.asList("91234567", "81234567"));

        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(expectedFindCmdMap));
        // leading whitespaces is not part of user input, but to accommodate for tokenizer
        assertParseSuccess(parser, " n/Alice Bob r/friends family e/@gmail.com @hotmail.com a/Felix Road 23 #12-12 "
                        + "ap/01/01/2017 10:30 c/funny swim p/91234567 81234567", expectedFindCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, "  n/ \n Alice \n \t Bob  \t  r/ \n friends \t family   "
                + "\t e/ @gmail.com \n @hotmail.com a/Felix Road \n 23 #12-12 ap/01/01/2017 \t  "
                + "\n 10:30 c/funny \n swim p/91234567 81234567", expectedFindCommand);
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
###### \java\seedu\address\model\person\PersonContainsKeywordsPredicateTest.java
``` java
public class PersonContainsKeywordsPredicateTest extends GuiUnitTest {

    @Test
    public void equals() {
        HashMap<String, List<String>> firstPredicateKeywordHashMap = new HashMap<>();
        firstPredicateKeywordHashMap.put("T", Collections.singletonList("first"));
        HashMap<String, List<String>> secondPredicateKeywordHashMap = new HashMap<>();
        secondPredicateKeywordHashMap.put("T", Arrays.asList("first", "second"));

        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(firstPredicateKeywordHashMap);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(secondPredicateKeywordHashMap);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy =
                new PersonContainsKeywordsPredicate(firstPredicateKeywordHashMap);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personContainsKeywords_returnsTrue() {
        // One keyword
        HashMap<String, List<String>> expectedHashMap = new HashMap<>();
        expectedHashMap.put(PREFIX_NAME.toString(), Collections.singletonList("Alice"));
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        expectedHashMap.put(PREFIX_NAME.toString(), Arrays.asList("Alice", "Bob"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        expectedHashMap.put(PREFIX_NAME.toString(), Arrays.asList("Bob", "Carol"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        expectedHashMap.put(PREFIX_NAME.toString(), Arrays.asList("aLIce", "bOB"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Single prefix
        expectedHashMap.put(PREFIX_NAME.toString(), Collections.singletonList("Alice"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        expectedHashMap.put(PREFIX_TAG.toString(), Collections.singletonList("friends"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        expectedHashMap.put(PREFIX_PHONE.toString(), Collections.singletonList("12345"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345").build()));

        expectedHashMap.put(PREFIX_EMAIL.toString(), Collections.singletonList("@gmail.com"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withEmail("test@gmail.com").build()));

        expectedHashMap.put(PREFIX_ADDRESS.toString(), Collections.singletonList("Wall"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withAddress("Wall Street").build()));

        expectedHashMap.put(PREFIX_COMMENT.toString(), Collections.singletonList("funny"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withComment("funny").build()));

        // Multiple prefixes
        expectedHashMap = new HashMap<>();
        expectedHashMap.put(PREFIX_PHONE.toString(), Collections.singletonList("12345"));
        expectedHashMap.put(PREFIX_EMAIL.toString(), Collections.singletonList("alice@gmail.com"));
        expectedHashMap.put(PREFIX_ADDRESS.toString(), Arrays.asList("Main", "Street"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        HashMap<String, List<String>> expectedHashMap = new HashMap<>();
        expectedHashMap.put(PREFIX_NAME.toString(), Collections.emptyList());
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        expectedHashMap.put(PREFIX_NAME.toString(), Collections.singletonList("Carol"));
        predicate = new PersonContainsKeywordsPredicate(expectedHashMap);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
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
        final String duplicatePersonsFilepath = "./src/test/data/AddMultipleCommandSystemTest/duplicatePersons.txt";
        String command = AddMultipleCommand.COMMAND_WORD + " " + duplicatePersonsFilepath;
        String expectedResultMessage = AddMultipleCommand.MESSAGE_DUPLICATE_PERSON;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: add from a file that contains missing field name */
        final String missingFieldNameFilepath = "./src/test/data/AddMultipleCommandSystemTest/missingPrefix_name.txt";
        command = AddMultipleCommand.COMMAND_WORD + " " + missingFieldNameFilepath;
        expectedResultMessage = String.format(MESSAGE_INVALID_PERSON_FORMAT, AddMultipleCommand.MESSAGE_PERSON_FORMAT);
        assertCommandFailure(command, expectedResultMessage);

        /* Case: add from a file that does not exist in the data folder --> rejected */
        String notExistsFile = "doesNotExist.txt";
        command = AddMultipleCommand.COMMAND_WORD + "  " + notExistsFile;
        expectedResultMessage = String.format(AddMultipleCommand.MESSAGE_INVALID_FILE, notExistsFile);
        assertCommandFailure(command, expectedResultMessage);

        /* Case add from a file containing valid persons --> added */
        String validPersonsFilepath =
                "./src/test/data/AddMultipleCommandSystemTest/validPersons_missingOptionalFields.txt";
        ArrayList<ReadOnlyPerson> personList = new ArrayList<>();
        ReadOnlyPerson amy = new PersonBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withTags().build();
        ReadOnlyPerson bob = new PersonBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags().build();
        personList.add(amy);
        personList.add(bob);
        command = AddMultipleCommand.COMMAND_WORD + " " + validPersonsFilepath;
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
     * Executes the {@code AddMultipleCommand} that adds {@code toAdd} to the model and verifies that the command box
     * displays an empty string, the result display box displays the success message of executing
     * {@code AddMultipleCommand} with the details of {@code toAdd}, and the model related components equal to the
     * current model added with {@code toAdd}.
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
        final String testNewFilePath = "./src/test/data/XmlAddressBookStorageTest/TestNewFile.xml";
        String command = MergeCommand.COMMAND_WORD + " " + testNewFilePath;
        assertCommandSuccess(command, testNewFilePath);

        /* Case: Undo the previous merge -> address book data back to previous state **/
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: Merge the same file twice into the default address book -> merged **/
        command = MergeCommand.COMMAND_WORD + " " + testNewFilePath;
        assertCommandSuccess(command, testNewFilePath);

        command = MergeCommand.COMMAND_WORD + " " + testNewFilePath;
        assertCommandSuccess(command, testNewFilePath);

        /* Case: Merge the new file into an empty address book -> merged **/
        command = ClearCommand.COMMAND_WORD;
        expectedResultMessage = ClearCommand.MESSAGE_SUCCESS;
        expectedModel.resetData(new AddressBook());
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        command = MergeCommand.COMMAND_WORD + " " + testNewFilePath;
        assertCommandSuccess(command, testNewFilePath);
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
     * details from {@code newFilePath}, and the model related components equal to the current model added with
     * {@code newFilePath}.
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

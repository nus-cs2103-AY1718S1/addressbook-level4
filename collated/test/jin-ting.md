# jin-ting
###### \java\guitests\CalendarWindowTest.java
``` java
public class CalendarWindowTest extends AddressBookGuiTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openCalendarWindow() {

        //using incorrect command
        runCommand(HelpCommand.COMMAND_ALIAS);
        assertCalendarWindowOpen();


        runCommand(AddCommand.COMMAND_ALIAS);
        assertCalendarWindowOpen();

        runCommand(CalendarCommand.COMMAND_ALIAS);
        assertCalendarWindowOpen();

    }


    /**
     * Asserts if calendar window is open.
     */


    private void assertCalendarWindowOpen() {

        assertFalse(ERROR_MESSAGE, CalendarWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();


    }

}

```
###### \java\guitests\EmailWindowTest.java
``` java
public class EmailWindowTest extends AddressBookGuiTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openEmailWindow() {

        //using incorrect command
        runCommand(CalendarCommand.COMMAND_ALIAS);
        assertEmailWindowOpen();

        runCommand(ClearCommand.COMMAND_ALIAS);
        assertEmailWindowOpen();

        runCommand(EmailCommand.COMMAND_ALIAS);
        assertEmailWindowOpen();
    }

    /**
     * Asserts if email window is open.
     */


    private void assertEmailWindowOpen() {
        assertFalse(ERROR_MESSAGE, EmailWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

    }


}

```
###### \java\guitests\guihandles\CalendarWindowHandle.java
``` java
/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class CalendarWindowHandle extends StageHandle {

    public static final String CALENDAR_WINDOW_TITLE = "Calendar";


    private static final String CALENDAR_WINDOW_BROWSER_ID = "#browser";

    public CalendarWindowHandle(Stage stage) {
        super(stage);
    }

    /**
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(CALENDAR_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(CALENDAR_WINDOW_BROWSER_ID));
    }
}
```
###### \java\guitests\guihandles\EmailWindowHandle.java
``` java
/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class EmailWindowHandle extends StageHandle {

    public static final String EMAIL_WINDOW_TITLE = "Email";

    private static final String EMAIL_WINDOW_BROWSER_ID = "#browser";

    public EmailWindowHandle(Stage stage) {
        super(stage);
    }

    /**
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(EMAIL_WINDOW_TITLE);
    }


    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(EMAIL_WINDOW_BROWSER_ID));
    }
}
```
###### \java\seedu\address\commons\util\PredicateUtilTest.java
``` java
public class PredicateUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    //----------- Tests for containsNameIgnoreCase and containsEmailIgnoreCase --------------
    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsIgnoreCase_nullWord_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, "typical sentence", null, Optional.empty());
    }

    @Test
    public void containsEmailIgnoreCase_nullWord_throwsNullPointerException() {
        Set<Email> emailSet = new HashSet<>();
        try {
            emailSet.add(new Email("dumb@gmail.com"));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("emailSet should contain emails.");
        }
        assertExceptionThrownEmail(NullPointerException.class, emailSet, null, Optional.empty());
    }

    @Test
    public void containsTagIgnoreCase_nullWord_throwsNullPointerException() {
        Set<Tag> tagSet = new HashSet<Tag>();
        try {
            tagSet.add(new Tag("typicaltags"));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagSet should contain tags.");
        }
        assertExceptionThrownTag(NullPointerException.class, tagSet, null, Optional.empty());
    }

    /**
     * assertExceptionThrown
     */
    private void assertExceptionThrown(Class<? extends Throwable> exceptionClass, String sentence, String word,
                                       Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        PredicateUtil.containsAttributeIgnoreCase(sentence, word);
        PredicateUtil.containsPhoneIgnoreCase(sentence, word);
        PredicateUtil.containsAddressIgnoreCase(sentence, word);
    }

    private void assertExceptionThrownEmail(Class<? extends Throwable> exceptionClass, Set<Email> emailSet, String word,
                                            Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        PredicateUtil.containsEmailIgnoreCase(emailSet, word);
    }

    private void assertExceptionThrownTag(Class<? extends Throwable> exceptionClass, Set<Tag> tagSet, String word,
                                          Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        PredicateUtil.containsTagIgnoreCase(tagSet, word);
    }

    @Test
    public void containsIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertExceptionThrown(IllegalArgumentException.class, "typical sentence", "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertExceptionThrown(IllegalArgumentException.class, "typical sentence", "aaa BBB",
                Optional.of("Word parameter should be a single word"));
    }

    @Test
    public void containsIgnoreCase_nullSentence_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, null, "abc", Optional.empty());
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
    public void containsNameIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(PredicateUtil.containsAttributeIgnoreCase("", "abc")); // Boundary case
        assertFalse(PredicateUtil.containsAttributeIgnoreCase("    ", "123"));

        // Matches a partial word only
        assertFalse(PredicateUtil.containsAttributeIgnoreCase("aaa bbb", "bb")); // Sentence word bigger than query word
        assertFalse(PredicateUtil.containsAttributeIgnoreCase("bbb c", "bbbb")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("aaa bBb ccc", "Bbb")); // First word (boundary case)
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("aaa ccc@1", "CCc@1")); // Last word (boundary case)
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("  AAA   cc  ", "aaa")); // Sentence has extra spaces
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("Aa", "aa")); // Only one word in sentence (boundary case)
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("AAA bBb ccc  bbb", "bbB"));
    }

    @Test
    public void containsCountryIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(PredicateUtil.containsAttributeIgnoreCase("", "china")); // Boundary case
        assertFalse(PredicateUtil.containsAttributeIgnoreCase("    ", "vietnam"));

        // Matches a partial word only
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("hong kong", "kong"));
        assertFalse(PredicateUtil.containsAttributeIgnoreCase("paris", "par")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("Africa", "AFrica")); // case insensitive
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("China London", "LONDOn")); // Last word (boundary case)
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("  Paris  China  ", "China")); // Sentence has extra spaces

        //Full word match
        assertFalse(PredicateUtil.containsAttributeIgnoreCase("Brazi l", " Brazil")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("Paris Britian", "paris"));
    }

    @Test
    public void containsEmailIgnoreCase_validInputs_correctResult() {
        Set<Email> emailSet = new HashSet<>();
        try {
            emailSet.add(new Email("abc@example.com"));
            emailSet.add(new Email("abc@123.com"));
            emailSet.add(new Email("abc@example.com.sg"));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("emailSet should contain emails.");
        }

        //Matches word in the sentence, different upper/lower case letters
        assertTrue(PredicateUtil.containsEmailIgnoreCase(emailSet, "ExaMPLE")); // Case insensitive

        //Matches email of numeric domain
        assertTrue(PredicateUtil.containsEmailIgnoreCase(emailSet, "123")); // number email domain

        //Match email with multiple domain
        assertTrue(PredicateUtil.containsEmailIgnoreCase(emailSet, "example")); // number email domain

        //Match for non exact word
        assertFalse(PredicateUtil.containsEmailIgnoreCase(emailSet, "example.com")); //email end with .com domain
        assertFalse(PredicateUtil.containsEmailIgnoreCase(emailSet, "exam")); // Match partial word

        //Match for email with no '@"
        assertFalse(PredicateUtil.containsEmailIgnoreCase(emailSet, "gmail")); //email without @
    }


    @Test
    public void containsAddressIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(PredicateUtil.containsAddressIgnoreCase("", "abc")); // Boundary case

        //Matches any address field
        assertTrue(PredicateUtil.containsAddressIgnoreCase("123, Lorong Ave 6, #08-111", "123"));
        assertTrue(PredicateUtil.containsAddressIgnoreCase("123, Lorong Ave 6, #08-111", "Lorong"));
        assertTrue(PredicateUtil.containsAddressIgnoreCase("123, Lorong Ave 6, #08-111", "#08-111"));
        assertTrue(PredicateUtil.containsAddressIgnoreCase("123, Lorong Ave 6, #08-111", "6"));

        //Case insensitive
        assertTrue(PredicateUtil.containsAddressIgnoreCase("123, Bishan Ave 6, #08-111", "biSHan")); //case insensitive

        //Non exact word match
        assertFalse(PredicateUtil.containsAddressIgnoreCase("12, Jurong Ave 6, #08-11", "Juron")); //case insensitive
        assertFalse(PredicateUtil.containsAddressIgnoreCase("123, Jurong Ave 6, #08-111", "100")); //case insensitive
    }


    @Test
    public void containsPhoneIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(PredicateUtil.containsPhoneIgnoreCase("", "98989898")); // Boundary case

        //Matches phone number that has strictly 4 or 8 digits
        assertTrue(PredicateUtil.containsPhoneIgnoreCase("98984554", "4554"));
        assertTrue(PredicateUtil.containsPhoneIgnoreCase("98984554", "9898"));
        assertFalse(PredicateUtil.containsPhoneIgnoreCase("98984554", "455"));
        assertFalse(PredicateUtil.containsPhoneIgnoreCase("98989898", "98989898")); //search word must be 4 digits

    }

    @Test
    public void containsTagsIgnoreCase_validInputs_correctResult() {

        Set<Tag> tagSet = new HashSet<Tag>();
        try {
            tagSet.add(new Tag("friends"));
            tagSet.add(new Tag("neighbours"));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagSet should contain tags.");
        }

        //Exact word match
        assertTrue(PredicateUtil.containsTagIgnoreCase(tagSet, "friends"));
        assertTrue(PredicateUtil.containsTagIgnoreCase(tagSet, "neighbours"));

        //case insensitive
        assertTrue(PredicateUtil.containsTagIgnoreCase(tagSet, "frIENds"));
        assertTrue(PredicateUtil.containsTagIgnoreCase(tagSet, "neIGHBOurs"));

        //Non exact word match
        assertFalse(PredicateUtil.containsTagIgnoreCase(tagSet, "frIENd"));
        assertFalse(PredicateUtil.containsTagIgnoreCase(tagSet, "BOurs"));

    }


}
```
###### \java\seedu\address\logic\commands\CalendarCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code LocateCommand}.
 */
public class CalendarCommandTest {


    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();


    @Test
    public void assertExecutionSuccess() throws CommandException {
        CalendarCommand command = new CalendarCommand();

        try {
            CommandResult commandResult = command.execute();
            assertEquals(MESSAGE_DISPLAY_CALENDAR_SUCCESS, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

    }

}


```
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) for {@code EmailCommand}.
 */
public class EmailCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_PERSON);
        EmailCommand emailAliceCommand = new EmailCommand(indices);
        Set<Index> indicesBob = new HashSet<>();
        indicesBob.add(INDEX_SECOND_PERSON);
        EmailCommand emailBobCommand = new EmailCommand(indicesBob);

        // same object -> returns true
        assertTrue(emailAliceCommand.equals(emailAliceCommand));

        // same values -> returns true
        EmailCommand emailAliceCommandCopy = new EmailCommand(indices);
        assertTrue(emailAliceCommand.equals(emailAliceCommandCopy));


        // different types -> returns false
        assertFalse(emailAliceCommand.equals(1));

        // null -> returns false
        assertFalse(emailAliceCommand.equals(null));

    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToEmail = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EmailCommand emailCommand = prepareCommand(INDEX_FIRST_PERSON);
        Set<String> recipientSet = new HashSet<>();
        for (Email email : personToEmail.getEmails()) {
            recipientSet.add(email.toString());
        }

        String emailList = String.join(",", recipientSet);
        String expectedMessage = String.format(EmailCommand.MESSAGE_DISPLAY_EMAIL_SUCCESS, emailList);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EmailCommand emailCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(emailCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EmailCommand emailCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(emailCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    /**
     * Returns a {@code EmailCommand} with the parameter {@code index}.
     */
    private EmailCommand prepareCommand(Index index) {
        Set<Index> indices = new HashSet<>();
        indices.add(index);

        UserPrefs prefs = new UserPrefs();
        EmailCommand emailCommand = new EmailCommand(indices);
        emailCommand.setData(model, prefs, new CommandHistory(), new UndoRedoStack());
        return emailCommand;
    }


}
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
    @Test
    public void equalsPhone() {
        PhoneContainsKeywordsPredicate firstPredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("98989898"));
        PhoneContainsKeywordsPredicate secondPredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("98741444"));

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
    public void equalsEmail() {
        EmailContainsKeywordsPredicate firstPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("abc@example.com"));
        EmailContainsKeywordsPredicate secondPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("abc@gg.com"));

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
    public void equalsAddress() {
        AddressContainsKeywordsPredicate firstPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("1,Bishan Street 10,#10-00 ,555665"));
        AddressContainsKeywordsPredicate secondPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("8,Bishan Street 20,#09-09 ,545665"));

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
    public void equalsSchedule() {
        ScheduleContainsKeywordsPredicate firstPredicate =
                new  ScheduleContainsKeywordsPredicate(Collections.singletonList("Party,Interview"));
        ScheduleContainsKeywordsPredicate secondPredicate =
                new  ScheduleContainsKeywordsPredicate(Collections.singletonList("Concert"));

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
    public void equalsTags() {
        TagContainsKeywordsPredicate firstPredicate =
                new  TagContainsKeywordsPredicate(Collections.singletonList("schoolmates,friends"));
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("teacher"));

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
    public void equalsCountry() {
        CountryContainsKeywordsPredicate firstPredicate =
                new   CountryContainsKeywordsPredicate (Collections.singletonList("China,USA"));
        CountryContainsKeywordsPredicate  secondPredicate =
                new  CountryContainsKeywordsPredicate (Collections.singletonList("Hong Kong"));

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

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_findName() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "n/" + keywords.stream().collect(Collectors.joining(" ")));
        FindCommand commandUsingAlias = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + "n/" + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), commandUsingAlias);
    }

    @Test
    public void parseCommand_findEmail() throws Exception {
        List<String> keywords = Arrays.asList("yahoo", "gmail", "google");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "e/" + keywords.stream().collect(Collectors.joining(" ")));
        FindCommand commandUsingAlias = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + "e/" + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new EmailContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindCommand(new EmailContainsKeywordsPredicate(keywords)), commandUsingAlias);
    }

    @Test
    public void parseCommand_findAddress() throws Exception {
        List<String> keywords = Arrays.asList("123", "Bishan", "Ave", "6", "#08-111");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "a/123 Bishan Ave 6 #08-111");
        FindCommand commandUsingAlias = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "a/123 Bishan Ave 6 #08-111");
        assertEquals(new FindCommand(new AddressContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindCommand(new AddressContainsKeywordsPredicate(keywords)), commandUsingAlias);
    }

    @Test
    public void parseCommand_findPhone() throws Exception {
        List<String> keywords = Arrays.asList("9898", "4554", "0145");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "p/98984554 0145");
        FindCommand commandUsingAlias = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "p/98984554 0145");
        assertEquals(new FindCommand(new PhoneContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindCommand(new PhoneContainsKeywordsPredicate(keywords)), commandUsingAlias);
    }


    @Test
    public void parseCommand_findCountry() throws Exception {
        List<String> keywords = Arrays.asList("Paris", "China");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "c/Paris China");
        FindCommand commandUsingAlias = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "c/Paris China");
        assertEquals(new FindCommand(new CountryContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindCommand(new CountryContainsKeywordsPredicate(keywords)), commandUsingAlias);
    }


    @Test
    public void parseCommand_findTag() throws Exception {
        List<String> keywords = Arrays.asList("friends", "teachers", "schoolmates");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "t/ " + keywords.stream().collect(Collectors.joining(" ")));
        FindCommand commandUsingAlias = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + "t/ " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new TagContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindCommand(new TagContainsKeywordsPredicate(keywords)), commandUsingAlias);
    }


    @Test
    public void parseCommand_findSchedule() throws Exception {
        List<String> keywords = Arrays.asList("interview", "meeting", "party");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "act/ " + keywords.stream().collect(Collectors.joining(" ")));
        FindCommand commandUsingAlias = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + "act/ " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new ScheduleContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindCommand(new ScheduleContainsKeywordsPredicate(keywords)), commandUsingAlias);
    }

    @Test
    public void parseCommand_email() throws Exception {
        EmailCommand command = (EmailCommand) parser.parseCommand(
                EmailCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        EmailCommand commandUsingAlias = (EmailCommand) parser.parseCommand(
                EmailCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_PERSON);
        assertEquals(new EmailCommand(indices), command);
        assertEquals(new EmailCommand(indices), commandUsingAlias);
    }

    @Test
    public void parseCommand_copy() throws Exception {
        CopyCommand command = (CopyCommand) parser.parseCommand(
                CopyCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        CopyCommand commandUsingAlias = (CopyCommand) parser.parseCommand(
                CopyCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);

        assertEquals(new CopyCommand(indices), command);
        assertEquals(new CopyCommand(indices), commandUsingAlias);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_calendar() throws Exception {
        assertTrue(parser.parseCommand(CalendarCommand.COMMAND_WORD) instanceof CalendarCommand);
    }

```
###### \java\seedu\address\logic\parser\EmailCommandParserTest.java
``` java
public class EmailCommandParserTest {

    private EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parse_validArgs_returnsEmailCommand() {
        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_PERSON);
        indices.add(INDEX_SECOND_PERSON);
        assertParseSuccess(parser, "1 2", new EmailCommand(indices));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParserTest.java
``` java
public class FindCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommandName() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommandName =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "n/Alice Bob", expectedFindCommandName);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "n/ Alice    Bob", expectedFindCommandName);
    }


    @Test
    public void parse_validArgs_returnsFindCommandPhone() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommandPhone =
                new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("9898", "4553")));
        assertParseSuccess(parser, " " + "p/98984553", expectedFindCommandPhone);

        // digits not exactly 4 or 8
        assertParseFailure(parser, "p/988754445", PhoneContainsKeywordsPredicate.MESSAGE_PHONE_VALIDATION);
        assertParseFailure(parser, "p/988754445", PhoneContainsKeywordsPredicate.MESSAGE_PHONE_VALIDATION);
    }

    @Test
    public void parse_validArgs_returnsFindCommandCountry() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommandCountry =
                new FindCommand(new CountryContainsKeywordsPredicate(Arrays.asList("china", "paris")));
        assertParseSuccess(parser, "c/china paris", expectedFindCommandCountry);
    }

    @Test
    public void parse_validArgs_returnsFindCommandAddress() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommandAddress =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList("1", "Barn", "Ave", "6", "#08-10")));
        assertParseSuccess(parser, "a/1 Barn Ave 6 #08-10", expectedFindCommandAddress);
    }

    @Test
    public void parse_validArgs_returnsFindCommandEmail() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommandEmail =
                new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList("abc@yahoo.com.sg")));
        assertParseSuccess(parser, " " + "e/abc@yahoo.com.sg", expectedFindCommandEmail);

    }

    @Test
    public void parse_validArgs_returnsFindCommandTag() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommandTag =
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList("friends", "teachers")));
        assertParseSuccess(parser, " " + "t/friends teachers", expectedFindCommandTag);


        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + "t/friends      teachers", expectedFindCommandTag);

    }

    @Test
    public void parse_validArgs_returnsFindCommandSchedule() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommandSchedule =
                new FindCommand(new ScheduleContainsKeywordsPredicate(Arrays.asList("party", "interview")));
        assertParseSuccess(parser, " " + "act/party interview", expectedFindCommandSchedule);


        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + "act/party      interview", expectedFindCommandSchedule);

    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "/abc@example.com", MESSAGE_INVALID_FORMAT);


        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "i/ string", MESSAGE_UNKNOWN_FINDCOMMAND);
    }

    @Test
    public void checkValidPhoneNumbers() {
        String[] testPhoneNumbersValid = new String[]{"9898", "98874577"};
        String[] testPhoneNumbersNotValid = new String[]{"989545"};

        // valid phone numbers
        assertTrue(FindCommandParser.validPhoneNumbers(testPhoneNumbersValid)); // exactly 4 or 8 digits

        //Invalid phone numbers
        assertFalse(FindCommandParser.validPhoneNumbers(testPhoneNumbersNotValid)); // Not exactly 4 or 8 digits


    }
}
```
###### \java\seedu\address\model\person\AddressContainsKeywordsPredicateTest.java
``` java
public class AddressContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("123, Jurong West Ave 6, #08-111");
        List<String> secondPredicateKeywordList = Arrays.asList("123, Jurong West Ave 6, #08-111", "1, Bishan, #01-11");

        Predicate<ReadOnlyPerson> firstPredicate = new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        Predicate<ReadOnlyPerson> secondPredicate = new AddressContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        Predicate<ReadOnlyPerson> firstPredicateCopy = new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // match any address field
        Predicate<ReadOnlyPerson> predicate = new AddressContainsKeywordsPredicate(Collections.singletonList("Jurong"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111, SG").build()));

        // match any address variables as long as there is one valid match
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("#08-111", "5"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111, SG").build()));

        // Mixed-case keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("JuRONG", "WESt"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111, SG").build()));

    }


    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero address parameter
        Predicate<ReadOnlyPerson> predicate = new AddressContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111, SG").build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("#08-110"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111, SG").build()));

        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("100"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111, SG").build()));

        // Keywords match phone, name and email, but does not match address
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("12345", "Alice", "alice@gmail.com"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("123, Jurong West Ave 6, #08-111, SG").build()));
    }
}
```
###### \java\seedu\address\model\person\CountryContainsKeywordsPredicateTest.java
``` java
public class CountryContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Singapore");
        List<String> secondPredicateKeywordList = Arrays.asList("Malaysia", "Singapore");

        Predicate<ReadOnlyPerson> firstPredicate = new CountryContainsKeywordsPredicate(firstPredicateKeywordList);
        Predicate<ReadOnlyPerson> secondPredicate = new CountryContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        Predicate<ReadOnlyPerson> firstPredicateCopy = new CountryContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }


    @Test
    public void test_countryDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CountryContainsKeywordsPredicate predicate = new CountryContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withCountry("Singapore").build()));

        // Non-matching keyword
        predicate = new CountryContainsKeywordsPredicate(Arrays.asList("China"));
        assertFalse(predicate.test(new PersonBuilder().withCountry("Singapore").build()));

        // Keywords match phone, email and address, but does not match country
        predicate = new CountryContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withCountry("65").withPhone("12345")
                .withEmail("alice@email.com").withAddress("123, Main Street, #08-111, Singapore 409999").build()));
    }
}
```
###### \java\seedu\address\model\person\EmailContainsKeywordsPredicateTest.java
``` java
public class EmailContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("gmail");
        List<String> secondPredicateKeywordList = Arrays.asList("gmail", "yahoo");

        Predicate<ReadOnlyPerson> firstPredicate = new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        Predicate<ReadOnlyPerson>  secondPredicate = new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        Predicate<ReadOnlyPerson>  firstPredicateCopy = new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One email domain
        Predicate<ReadOnlyPerson> predicate = new EmailContainsKeywordsPredicate(Collections.singletonList("gmail"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@gmail.com").build()));

        // Multiple email domains
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("gmail", "yahoo"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@gmail.com").build()));

        // Mixed-case keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("GmAil", "YAhoo"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@gmail.com").build()));

    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero email domain
        Predicate<ReadOnlyPerson>  predicate = new EmailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@gmail.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("yahoo"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@gmail.com").build()));

        // Keywords match phone, name and address, but does not match email
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("12345", "Alice", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("123, Main Street, #08-111, Singapore 409999").build()));
    }
}
```
###### \java\seedu\address\model\person\NameContainsKeywordsPredicateTest.java
``` java
public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        Predicate<ReadOnlyPerson> firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        Predicate<ReadOnlyPerson>  secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        Predicate<ReadOnlyPerson>  firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        Predicate<ReadOnlyPerson>  predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        Predicate<ReadOnlyPerson>  predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("123, Main Street, #08-111, Singapore 409999").build()));
    }
}
```
###### \java\seedu\address\model\person\PhoneContainsKeywordsPredicateTest.java
``` java
public class PhoneContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("85355255");
        List<String> secondPredicateKeywordList = Arrays.asList("85355255", "99995255");

        Predicate<ReadOnlyPerson> firstPredicate = new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        Predicate<ReadOnlyPerson> secondPredicate = new PhoneContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        Predicate<ReadOnlyPerson> firstPredicateCopy = new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One phone number of 4 digits
        Predicate<ReadOnlyPerson> predicate = new PhoneContainsKeywordsPredicate(Collections.singletonList("8535"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("85355255").build()));

        //Searching multiple phone numbers with varying digits
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("8535", "8989"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("85355255").build()));

        // Searching phone number using 4 digits
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("8535", "5255"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("85355255").build()));

    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero phone numbers
        Predicate<ReadOnlyPerson> predicate = new PhoneContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("85355255").build()));

        // phone with 3 digits
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("853"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("85355255").build()));

        // Keywords match email, name and address, but does not match phone
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("alice@gmail.com", "Alice", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("123, Main Street, #08-111, Singapore 409999").build()));
    }
}
```
###### \java\seedu\address\model\person\TagContainsKeywordsPredicateTest.java
``` java
public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("friends");
        List<String> secondPredicateKeywordList = Arrays.asList("neighbours");

        Predicate<ReadOnlyPerson> firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        Predicate<ReadOnlyPerson> secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        Predicate<ReadOnlyPerson> firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // exact match
        Predicate<ReadOnlyPerson> predicate = new TagContainsKeywordsPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Multiple tags
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("friends", "teachers"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("frIEnds"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero email domain
        Predicate<ReadOnlyPerson> predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("friEND"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Keywords match phone, name and address, but does not match tag
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("12345", "Alice", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withTags("friends").withAddress("123, Main Street, SG 409999").build()));
    }
}
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Displays all persons with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showPersonsWithName(String keyword) {
        executeCommand(FindCommand.COMMAND_WORD + " n/" + keyword);
        assert getModel().getFilteredPersonList().size() < getModel().getAddressBook().getPersonList().size();
    }

    /**
     * Selects the person at {@code index} of the displayed list.
     */
    protected void selectPerson(Index index) {
        executeCommand(LocateCommand.COMMAND_WORD + " " + index.getOneBased());
        assert getPersonListPanel().getSelectedCardIndex() == index.getZeroBased();
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the model and storage contains the same person objects as {@code expectedModel}
     * and the person list panel displays the persons in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
                                                     Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(expectedModel, getModel());
        assertEquals(expectedModel.getAddressBook(), testApp.readStorageAddressBook());
        assertListMatching(getPersonListPanel(), expectedModel.getFilteredPersonList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code PersonListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getPersonListPanel().rememberSelectedPersonCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url remains displaying the details
     * of the previously selected person.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getPersonListPanel().isAnyCardSelected());
    }

```
###### \java\systemtests\FindCommandSystemTest.java
``` java
public class FindCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {
        /* Case: find multiple persons in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " n/" + " " + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " n/" + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person where person list is not displaying the person we are finding -> 1 person found */
        command = FindCommand.COMMAND_WORD + " n/" + " Carl";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " n/" + "Benson Daniel";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords in reversed order -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " n/ " + " Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords with 1 repeat -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " n/" + " Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " n/" + " Daniel Benson NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons in address book after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getModel().getAddressBook().getPersonList().contains(BENSON);
        command = FindCommand.COMMAND_WORD + " n/" + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is same as name but of different case -> 1 person found */
        command = FindCommand.COMMAND_WORD + " n/" + " MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is substring of name -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " n/" + " Mei";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, name is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " n/" + " Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " n/" + " Mark";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " a/" + DANIEL.getAddress().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " e/ " + "google";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " p/ " + DANIEL.getPhone().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of person in address book -> 1 persons found */
        List<Tag> tags = new ArrayList<>(BENSON.getTags());
        command = FindCommand.COMMAND_WORD + " t/" + tags.get(0).tagName;
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        command = FindCommand.COMMAND_WORD + " n/ " + "Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindCommand.COMMAND_WORD + " n/" + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

```

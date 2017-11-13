# zameschua
###### \java\seedu\address\externals\TwilioApiHelperTest.java
``` java
/**
 * Contains tests for {@link TwilioApiHelper}
 * Note that test case for valid phone number is done via exploratory testing
 * Because it sends an actual SMS via Twilio's API
 */
public class TwilioApiHelperTest {
    private static final String STUB_MESSAGE = "This is a stub message";
    private static final String STUB_PHONE_NUMBER_INVALID = "1";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_invalidPhoneNumber_throwsAssertionException() {
        thrown.expect(AssertionError.class);
        TwilioApiHelper.init();
        TwilioApiHelper.sendSms(STUB_MESSAGE, STUB_PHONE_NUMBER_INVALID);
    }
}
```
###### \java\seedu\address\logic\commands\SmsCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for SmsCommand.
 */
public class SmsCommandTest {
    private static final String STUB_USER_INPUT_ALL = "all";
    private static final String STUB_USER_INPUT_VALID = "owesMoney";
    private static final String STUB_USER_INPUT_INVALID = "invalid";

    private Model model;
    private Model originalModel;

    @Test
    public void execute_allTag_success() throws Exception {
        setupModel();
        int expectedCount = originalModel.getAddressBook().getPersonList().size();
        SmsCommand command = prepareCommand(STUB_USER_INPUT_ALL);

        model.updateFilteredPersonList(command.getPredicate());
        int actualCount = model.getFilteredPersonList().size();

        System.out.println("expected persons: " + expectedCount + ", actual persons: " + actualCount);
        assertListSize(expectedCount, actualCount);
        assertEqualList(originalModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    // one valid tag
    public void  execute_oneValidTag_success() throws Exception {
        setupModel();
        ArrayList<String> tagList = new ArrayList<String>();
        tagList.add(STUB_USER_INPUT_VALID);
        TagMatchingPredicate predicate = new TagMatchingPredicate(tagList);
        ObservableList expectedPersons =  originalModel.getAddressBook().getPersonList().filtered(predicate);
        int expectedCount = expectedPersons.size();

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SmsCommand command = prepareCommand(STUB_USER_INPUT_VALID);
        model.updateFilteredPersonList(command.getPredicate());
        int actualCount = model.getFilteredPersonList().size();

        System.out.println("expected persons: " + expectedCount + ", actual persons: " + actualCount);
        assertListSize(expectedCount, actualCount);
        assertEqualList(expectedPersons, model.getFilteredPersonList());
    }

    @Test
    // no vaild tag
    public void  execute_oneInvalidTag_error() throws Exception {
        setupModel();
        ArrayList<String> tagList = new ArrayList<String>();
        tagList.add(STUB_USER_INPUT_INVALID);
        TagMatchingPredicate predicate = new TagMatchingPredicate(tagList);
        ObservableList expectedPersons =  originalModel.getAddressBook().getPersonList().filtered(predicate);
        int expectedCount = expectedPersons.size();

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SmsCommand command = prepareCommand(STUB_USER_INPUT_INVALID);
        model.updateFilteredPersonList(command.getPredicate());
        int actualCount = model.getFilteredPersonList().size();

        System.out.println("expected persons: " + expectedCount + ", actual persons: " + actualCount);
        assertListSize(expectedCount, actualCount);
        assert(model.getFilteredPersonList().isEmpty());
    }

    @Test
    // 1 vaild tag and 1 invalid tag
    public void  execute_mixedValidInvalidTag_error() throws Exception {
        setupModel();
        ArrayList<String> tagList = new ArrayList<String>();
        tagList.add(STUB_USER_INPUT_VALID);
        tagList.add(STUB_USER_INPUT_INVALID);
        TagMatchingPredicate predicate = new TagMatchingPredicate(tagList);
        ObservableList expectedPersons =  originalModel.getAddressBook().getPersonList().filtered(predicate);
        int expectedCount = expectedPersons.size();

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SmsCommand command = prepareCommand(STUB_USER_INPUT_INVALID + " " + STUB_USER_INPUT_VALID);
        model.updateFilteredPersonList(command.getPredicate());
        int actualCount = model.getFilteredPersonList().size();

        System.out.println("expected persons: " + expectedCount + ", actual persons: " + actualCount);
        assertListSize(expectedCount, actualCount);
        assert(!model.getFilteredPersonList().isEmpty());
        assertEqualList(expectedPersons, model.getFilteredPersonList());
    }

    /**
     * Helper method to setup a model {@code AddressBook}
     */
    private void setupModel() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        originalModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    }

    /**
     * Parses {@code userInput} into a {@code SmsCommand}.
     */
    private SmsCommand prepareCommand(String userInput) {
        SmsCommand command =
                new SmsCommand(new TagMatchingPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    private void assertListSize(int ex, int act) {
        assert (ex == act);
    }

    private void assertEqualList(ObservableList<ReadOnlyPerson> ex, ObservableList<ReadOnlyPerson> act) {
        assert (act.containsAll(ex));
    }
}
```
###### \java\seedu\address\logic\parser\SmsCommandParserTest.java
``` java
public class SmsCommandParserTest {

    private static final String STUB_TAG_VALID_FIRST = "owesMoney";
    private static final String STUB_TAG_VALID_SECOND = "classA";

    private SmsCommandParser parser = new SmsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SmsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSmsCommand() {
        SmsCommand expectedSmsCommand =
                new SmsCommand(new TagMatchingPredicate((Arrays.asList(STUB_TAG_VALID_FIRST, STUB_TAG_VALID_SECOND))));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, STUB_TAG_VALID_FIRST + " " + STUB_TAG_VALID_SECOND, expectedSmsCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser,
                " \n " + STUB_TAG_VALID_FIRST + " \n \t " + STUB_TAG_VALID_SECOND + "  \t",
                expectedSmsCommand);
    }
}
```

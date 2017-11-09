# mzxc152
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void sortList(String toSort) {
            fail("There is no person in Contags to sort.");
        }
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java

public class SortCommandTest {
    private static final String NAME_SORT = "name";
    private static final String EMAIL_SORT = "email";
    private static final String PHONE_SORT = "phone";
    private static final String ADDRESS_SORT = "address";
    private static final String TAG_SORT = "tag";

    private Model model;
    private Model expectedModel;


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void executeNameSortSuccess() {
        SortCommand sortCommand = prepareCommand(NAME_SORT);
        expectedModel.sortList(NAME_SORT);
        assertCommandSuccess(sortCommand, model, sortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeEmailSortSuccess() {
        SortCommand sortCommand = prepareCommand(EMAIL_SORT);
        expectedModel.sortList(EMAIL_SORT);
        assertCommandSuccess(sortCommand, model, sortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executePhoneSortSuccess() {
        SortCommand sortCommand = prepareCommand(PHONE_SORT);
        expectedModel.sortList(PHONE_SORT);
        assertCommandSuccess(sortCommand, model, sortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeAddressSortSuccess() {
        SortCommand sortCommand = prepareCommand(ADDRESS_SORT);
        expectedModel.sortList(ADDRESS_SORT);
        assertCommandSuccess(sortCommand, model, sortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeTagSortSuccess() {
        SortCommand sortCommand = prepareCommand(TAG_SORT);
        expectedModel.sortList(TAG_SORT);
        assertCommandSuccess(sortCommand, model, sortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     *  Parses {@code toSort} into a {@code SortCommand}.
     *  This sorts Contags according to the type of attribute parsed into SortCommand.
     */
    private SortCommand prepareCommand(String toSort) {
        SortCommand command = new SortCommand(toSort);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "birthday",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "!@#$`2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
```

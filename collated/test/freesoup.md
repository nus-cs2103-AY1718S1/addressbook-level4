# freesoup
###### \java\seedu\address\logic\commands\RemoveTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for RemoveTagCommand.
 */
public class RemoveTagCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_emptyArg_throwsParseException() {
        RemoveTagCommandParser parser = new RemoveTagCommandParser();
        assertParseFailure(parser, "     ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void execute_removeTag_success() throws IllegalValueException, PersonNotFoundException {
        String expectedMessage = MESSAGE_TAG_REMOVED;

        RemoveTagCommand command = prepareCommand("friends");
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(new Tag("friends"));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeSingleTag_success() throws IllegalValueException, PersonNotFoundException {
        String expectedMessage = MESSAGE_TAG_REMOVED;

        RemoveTagCommand command = prepareCommand(5, "owesMoney");
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(Index.fromOneBased(5), new Tag("owesMoney"));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Generates a new {@code RemoveTagCommand} which upon execution, removes all tag
     * matching given Tag in {@code model}.
     */
    private RemoveTagCommand prepareCommand(String tag) throws IllegalValueException {
        RemoveTagCommand command = new RemoveTagCommand(new Tag(tag));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new {@code RemoveTagCommand} which upon execution, removes tag
     * corresponding to given Tag and Index in {@code model}.
     */
    private RemoveTagCommand prepareCommand(int index, String tag) throws IllegalValueException {
        RemoveTagCommand command = new RemoveTagCommand(Index.fromZeroBased(index), new Tag(tag));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\parser\RemoveTagCommandParserTest.java
``` java
public class RemoveTagCommandParserTest {

    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleArg_throwsParseException() {
        assertParseFailure(parser, "friends owesMoney", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsRemoveTagCommand() throws IllegalValueException {
        // no leading and trailing whitespaces
        RemoveTagCommand expectedCommand = new RemoveTagCommand (new Tag("friends"));
        assertTrue(parser.parse("friends") instanceof RemoveTagCommand);
        assertParseSuccess(parser, "friends", expectedCommand);

        // no leading and trailing whitespaces but with Index.
        RemoveTagCommand expectedCommand2 = new RemoveTagCommand (Index.fromZeroBased(0), new Tag(
                "enemy"));
        assertTrue(parser.parse("1 enemy") instanceof RemoveTagCommand);
        assertParseSuccess(parser, " 1 enemy", expectedCommand2);
    }

}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // empty args
        assertParseFailure(parser, "     ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //only FIELD
        assertParseFailure(parser, "name", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //Only ORDER
        assertParseFailure(parser, "asc", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() throws ParseException {
        // name ascending
        SortCommand expectedCommand = new SortCommand(ReadOnlyPerson.NAMESORTASC);
        assertTrue(parser.parse("name asc") instanceof SortCommand);
        assertParseSuccess(parser, "name asc", expectedCommand);

        // phone descending
        SortCommand expectedCommand2 = new SortCommand(ReadOnlyPerson.PHONESORTDSC);
        assertTrue(parser.parse("phone dsc") instanceof SortCommand);
        assertParseSuccess(parser, "phone dsc", expectedCommand2);

        // name descending
        SortCommand expectedCommand3 = new SortCommand(ReadOnlyPerson.NAMESORTDSC);
        assertTrue(parser.parse("name dsc") instanceof SortCommand);
        assertParseSuccess(parser, "name dsc", expectedCommand3);

        // email descending
        SortCommand expectedCommand4 = new SortCommand(ReadOnlyPerson.EMAILSORTDSC);
        assertTrue(parser.parse("phone dsc") instanceof SortCommand);
        assertParseSuccess(parser, "email dsc", expectedCommand4);

        // phone ascending
        SortCommand expectedCommand5 = new SortCommand(ReadOnlyPerson.PHONESORTASC);
        assertTrue(parser.parse("phone dsc") instanceof SortCommand);
        assertParseSuccess(parser, "phone asc", expectedCommand5);

        // email ascending
        SortCommand expectedCommand6 = new SortCommand(ReadOnlyPerson.EMAILSORTASC);
        assertTrue(parser.parse("phone dsc") instanceof SortCommand);
        assertParseSuccess(parser, "email asc", expectedCommand6);
    }
}
```

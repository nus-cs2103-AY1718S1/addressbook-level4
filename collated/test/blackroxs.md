# blackroxs
###### /java/seedu/room/logic/parser/ImportCommandParserTest.java
``` java
public class ImportCommandParserTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/room/logic/parser/RemoveTagParserTest.java
``` java
public class RemoveTagParserTest {

    private static final String MESSAGE_EMPTY_TAG_INPUT = "";
    private static final String MESSAGE_EMPTY_STRING_INPUT = "     ";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE);

    private RemoveTagParser parser = new RemoveTagParser();

    @Test
    public void parse_missingParts_failure() {
        // no tagName specified
        assertParseFailure(parser, MESSAGE_EMPTY_TAG_INPUT, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_emptyString_failure() {
        // only empty string
        assertParseFailure(parser, MESSAGE_EMPTY_STRING_INPUT, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validFields_success() {
        String userInput = VALID_TAG_FRIEND;
        RemoveTagCommand expectedCommand = new RemoveTagCommand(userInput);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### /java/seedu/room/logic/commands/RemoveTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for RemoveTagCommand.
 */
public class RemoveTagCommandTest {
    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());

    @Test
    public void execute_tagNameValid_success() throws Exception {
        RemoveTagCommand removeTagCommand = prepareCommand(VALID_TAG_FRIEND);
        String expectedMessage = RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());
        expectedModel.removeTag(new Tag(VALID_TAG_FRIEND));

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tagNameInvalid() throws Exception {
        RemoveTagCommand removeTagCommand = prepareCommand(INVALID_TAG);
        String expectedMessage = RemoveTagCommand.MESSAGE_REMOVE_TAG_NOT_EXIST;

        assertCommandFailure(removeTagCommand, model, expectedMessage);
    }

    /**
     * Returns an {@code RemoveTagCommand} with parameters {@code tagName}
     */
    private RemoveTagCommand prepareCommand(String tagName) {
        RemoveTagCommand command = new RemoveTagCommand(tagName);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```

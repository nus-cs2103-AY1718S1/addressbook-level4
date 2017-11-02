# Xenonym
###### \java\seedu\address\logic\commands\ColourTagCommandTest.java
``` java
public class ColourTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_changeColour_success() throws Exception {
        ColourTagCommand command = new ColourTagCommand(new Tag("test"), "red");
        command.setData(model, null, null, null);

        String expectedMessage = String.format(ColourTagCommand.MESSAGE_COLOUR_TAG_SUCCESS, "test", "red");
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Map<Tag, String> colours = new HashMap<>(expectedModel.getUserPrefs().getGuiSettings().getTagColours());
        colours.put(new Tag("test"), "red");
        expectedModel.getUserPrefs().getGuiSettings().setTagColours(colours);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_clearhistory() throws Exception {
        assertTrue(parser.parseCommand(ClearHistoryCommand.COMMAND_WORD) instanceof ClearHistoryCommand);
        assertTrue(parser.parseCommand(ClearHistoryCommand.COMMAND_WORD + " 3") instanceof ClearHistoryCommand);
    }

    @Test
    public void parseCommand_colourtag() throws Exception {
        final Tag tag = new Tag("test");
        final String colour = "red";
        ColourTagCommand command = (ColourTagCommand) parser.parseCommand(ColourTagCommand.COMMAND_WORD
                + " test red");
        assertEquals(new ColourTagCommand(tag, colour), command);
    }
```
###### \java\seedu\address\logic\parser\ColourTagCommandParserTest.java
``` java
public class ColourTagCommandParserTest {

    private ColourTagCommandParser parser = new ColourTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ColourTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "onlyonearg", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ColourTagCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "&&@&C@B invalidtag", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ColourTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsColourTagCommand() throws Exception {
        assertParseSuccess(parser, "friends red", new ColourTagCommand(new Tag("friends"), "red"));
    }
}
```

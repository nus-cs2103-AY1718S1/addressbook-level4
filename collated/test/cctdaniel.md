# cctdaniel
###### /java/seedu/address/logic/commands/CustomiseCommandTest.java
``` java
public class CustomiseCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_fontSizeChangedToXsmall_success() throws Exception {
        CustomiseCommand customiseCommand = prepareCommand(new FontSize(FONT_SIZE_XSMALL));

        String expectedMessage = MESSAGE_SUCCESS + FONT_SIZE_XSMALL + ".";

        assertEquals(expectedMessage, customiseCommand.execute().feedbackToUser);
    }

    @Test
    public void execute_fontSizeChangedToSmall_success() throws Exception {
        CustomiseCommand customiseCommand = prepareCommand(new FontSize(FONT_SIZE_SMALL));

        String expectedMessage = MESSAGE_SUCCESS + FONT_SIZE_SMALL + ".";

        assertEquals(expectedMessage, customiseCommand.execute().feedbackToUser);
    }

    @Test
    public void execute_fontSizeChangedToNormal_success() throws Exception {
        CustomiseCommand customiseCommand = prepareCommand(new FontSize(FONT_SIZE_NORMAL));

        String expectedMessage = MESSAGE_SUCCESS + FONT_SIZE_NORMAL + ".";

        assertEquals(expectedMessage, customiseCommand.execute().feedbackToUser);
    }

    @Test
    public void execute_fontSizeChangedToLarge_success() throws Exception {
        CustomiseCommand customiseCommand = prepareCommand(new FontSize(FONT_SIZE_LARGE));

        String expectedMessage = MESSAGE_SUCCESS + FONT_SIZE_LARGE + ".";

        assertEquals(expectedMessage, customiseCommand.execute().feedbackToUser);
    }

    @Test
    public void execute_fontSizeChangedToXLarge_success() throws Exception {
        CustomiseCommand customiseCommand = prepareCommand(new FontSize(FONT_SIZE_XLARGE));

        String expectedMessage = MESSAGE_SUCCESS + FONT_SIZE_XLARGE + ".";

        assertEquals(expectedMessage, customiseCommand.execute().feedbackToUser);
    }

    @Test
    public void equals() {
        try {
            FontSize fontSizeXsmall = new FontSize(FONT_SIZE_XSMALL);
            FontSize fontSizeXlarge = new FontSize(FONT_SIZE_XLARGE);

            final CustomiseCommand customiseCommand = new CustomiseCommand(fontSizeXsmall);

            // same object -> returns true
            assertTrue(customiseCommand.equals(customiseCommand));

            // same values -> returns true
            CustomiseCommand commandWithSameValues = new CustomiseCommand(fontSizeXsmall);
            assertTrue(customiseCommand.equals(commandWithSameValues));

            // different types -> returns false
            assertFalse(customiseCommand.equals(1));

            // null -> returns false
            assertFalse(customiseCommand.equals(null));

            // different Customise command -> returns false
            CustomiseCommand commandWithDifferentValues = new CustomiseCommand(fontSizeXlarge);
            assertFalse(customiseCommand.equals(commandWithDifferentValues));
        } catch (IllegalValueException ile) {
            throw new AssertionError(MESSAGE_FONT_SIZE_CONSTRAINTS);
        }
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
    private CustomiseCommand prepareCommand(FontSize fontSize) {
        CustomiseCommand customiseCommand = new CustomiseCommand(fontSize);
        customiseCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return customiseCommand;
    }

}
```
###### /java/seedu/address/logic/parser/CustomiseCommandParserTest.java
``` java
public class CustomiseCommandParserTest {

    private CustomiseCommandParser parser = new CustomiseCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        try {
            FontSize fontSize = new FontSize(VALID_FONT_SIZE_SMALL);

            // multiple font sizes - last font size accepted
            assertParseSuccess(parser, CustomiseCommand.COMMAND_WORD + FONT_SIZE_DESC_XSMALL
                    + FONT_SIZE_DESC_SMALL, new CustomiseCommand(fontSize));

        } catch (IllegalValueException ile) {
            throw new AssertionError(MESSAGE_FONT_SIZE_CONSTRAINTS);
        }
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomiseCommand.MESSAGE_USAGE);

        // missing font size prefix
        assertParseFailure(parser, CustomiseCommand.COMMAND_WORD + VALID_FONT_SIZE_XSMALL, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid font size
        assertParseFailure(parser, CustomiseCommand.COMMAND_WORD
                + INVALID_FONT_SIZE_DESC, FontSize.MESSAGE_FONT_SIZE_CONSTRAINTS);
    }

}
```

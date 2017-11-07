# ChenXiaoman
###### \java\seedu\address\logic\commands\ChangeFontSizeCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class ChangeFontSizeCommandTest {
    private UserPrefs userPrefs = new UserPrefs();
    private Model model = new ModelManager(getTypicalAddressBook(), userPrefs);
    private GuiSettings guiSettings = userPrefs.getGuiSettings();

    @Test
    public void executeChangeFontSizeCommandSuccess() throws Exception {
        ChangeFontSizeCommand changeFontSizeCommand = prepareCommand("xl");

        String expectedMessage = ChangeFontSizeCommand.MESSAGE_SUCCESS + "xl.";

        UserPrefs expectedUserPrefs = new UserPrefs();
        expectedUserPrefs.setGuiSettings(
                guiSettings.getWindowHeight(),
                guiSettings.getWindowWidth(),
                guiSettings.getWindowCoordinates().x,
                guiSettings.getWindowCoordinates().y,
                "xl", guiSettings.getTheme());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), expectedUserPrefs);

        assertCommandSuccess(changeFontSizeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeChangeFontSizeCommandInvalidFontSizeNameFailure() throws Exception {
        ChangeFontSizeCommand changeFontSizeCommand = prepareCommand(INVALID_FONT_SIZE);

        String expectedMessage = String.format(FontSize.MESSAGE_FONT_SIZE_CONSTRAINTS);

        assertCommandFailure(changeFontSizeCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        ChangeFontSizeCommand fontSizeFirstCommand = new ChangeFontSizeCommand(FontSize.FONT_SIZE_L_LABEL);
        ChangeFontSizeCommand fontSizeSecondCommand = new ChangeFontSizeCommand(FontSize.FONT_SIZE_XL_LABEL);

        // same object -> returns true
        assertTrue(fontSizeFirstCommand.equals(fontSizeFirstCommand));

        // same values -> returns true
        ChangeFontSizeCommand fontSizeFirstCommandCopy = new ChangeFontSizeCommand(FontSize.FONT_SIZE_L_LABEL);
        assertTrue(fontSizeFirstCommand.equals(fontSizeFirstCommandCopy));

        // different types -> returns false
        assertFalse(fontSizeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(fontSizeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(fontSizeFirstCommand.equals(fontSizeSecondCommand));
    }

    /**
     * Returns an {@code ChangeFontSizeCommand} with parameters {@code theme}
     */
    private ChangeFontSizeCommand prepareCommand(String fontSize) {
        ChangeFontSizeCommand changeFontSizeCommand = new ChangeFontSizeCommand(fontSize);
        changeFontSizeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return changeFontSizeCommand;
    }

}
```
###### \java\seedu\address\logic\commands\ChangeTagColorCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class ChangeTagColorCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeChangeTagColorOfATagListSuccess() throws Exception {
        TagColor tagColor = new TagColor(VALID_TAG_COLOR_NAME_RED);
        ChangeTagColorCommand changeTagColorCommand = prepareCommand(VALID_TAGLIST, VALID_TAG_COLOR_NAME_RED);

        String expectedMessage = String.format(ChangeTagColorCommand.MESSAGE_CHANGE_TAG_COLOR_SUCCESS,
                VALID_TAGLIST, VALID_TAG_COLOR_NAME_RED);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTagColorPair(VALID_TAGLIST, tagColor);

        assertCommandSuccess(changeTagColorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeChangeTagColorOfAnInvalidTagListFailure() throws Exception {
        ChangeTagColorCommand changeTagColorCommand = prepareCommand(INVALID_TAGLIST, VALID_TAG_COLOR_NAME_RED);

        String expectedMessage = String.format(ChangeTagColorCommand.MESSAGE_NOT_EXISTING_TAGS, INVALID_TAGLIST);

        assertCommandFailure(changeTagColorCommand, model, expectedMessage);
    }

    @Test
    public void equals() throws Exception {
        final TagColor tagColor = new TagColor("red");
        final Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("colleagues"));

        final TagColor tagColor2 = new TagColor("blue");
        final Set<Tag> tags2 = new HashSet<>();
        tags2.add(new Tag("friend"));

        final ChangeTagColorCommand standardCommand = new ChangeTagColorCommand(tags, tagColor);

        // same values -> returns true
        ChangeTagColorCommand commandWithSameValues = new ChangeTagColorCommand(tags, tagColor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ChangeTagColorCommand(tags2, tagColor)));

        // different remarks -> returns false
        assertFalse(standardCommand.equals(new ChangeTagColorCommand(tags, tagColor2)));
    }

    /**
     * Returns an {@code ChangeTagColorCommand} with parameters {@code tags} and {@code color}
     */
    private ChangeTagColorCommand prepareCommand(Set<Tag> tags, String tagColor) {
        ChangeTagColorCommand changeTagColorCommand = null;
        try {
            changeTagColorCommand = new ChangeTagColorCommand(tags, new TagColor(tagColor));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        changeTagColorCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return changeTagColorCommand;
    }
}
```
###### \java\seedu\address\logic\commands\ChangeThemeCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class ChangeThemeCommandTest {

    private UserPrefs userPrefs;
    private Model model;
    private GuiSettings guiSettings;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        userPrefs = new UserPrefs();
        guiSettings = userPrefs.getGuiSettings();
    }

    @Test
    public void executeChangeThemeCommandSuccess() throws Exception {
        ChangeThemeCommand changeThemeCommand = prepareCommand(DARK_THEME);

        String expectedMessage = String.format(ChangeThemeCommand.MESSAGE_CHANGE_THEME_SUCCESS, DARK_THEME);

        UserPrefs expectedUserPrefs = new UserPrefs();
        expectedUserPrefs.setGuiSettings(
                guiSettings.getWindowHeight(),
                guiSettings.getWindowWidth(),
                guiSettings.getWindowCoordinates().x,
                guiSettings.getWindowCoordinates().y,
                guiSettings.getFontSize(), DARK_THEME);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), expectedUserPrefs);
        assertCommandSuccess(changeThemeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeChangeThemeCommandInvalidThemeNameFailure() throws Exception {
        ChangeThemeCommand changeThemeCommand = prepareCommand(INVALID_THEME_NAME);

        String expectedMessage = String.format(ChangeThemeCommand.MESSAGE_INVALID_THEME_NAME, INVALID_THEME_NAME);

        assertCommandFailure(changeThemeCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        ChangeThemeCommand themeFirstCommand = new ChangeThemeCommand(Theme.DARK_THEME);
        ChangeThemeCommand themeSecondCommand = new ChangeThemeCommand(Theme.BRIGHT_THEME);

        // same object -> returns true
        assertTrue(themeFirstCommand.equals(themeFirstCommand));

        // same values -> returns true
        ChangeThemeCommand themeFirstCommandCopy = new ChangeThemeCommand(Theme.DARK_THEME);
        assertTrue(themeFirstCommand.equals(themeFirstCommandCopy));

        // different types -> returns false
        assertFalse(themeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(themeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(themeFirstCommand.equals(themeSecondCommand));
    }

    /**
     * Returns an {@code ChangeThemeCommand} with parameters {@code theme}
     */
    private ChangeThemeCommand prepareCommand(String theme) {
        ChangeThemeCommand changeThemeCommand = new ChangeThemeCommand(theme);
        changeThemeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return changeThemeCommand;
    }

}
```
###### \java\seedu\address\logic\commands\MapCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class MapCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MapCommand mapCommand = prepareCommand(outOfBoundsIndex);
        assertCommandFailure(mapCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MapCommand mapFirstCommand = new MapCommand(INDEX_FIRST_PERSON);
        MapCommand mapSecondCommand = new MapCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(mapFirstCommand.equals(mapFirstCommand));

        // same values -> returns true
        MapCommand mapFirstCommandCopy = new MapCommand(INDEX_FIRST_PERSON);
        assertTrue(mapFirstCommand.equals(mapFirstCommandCopy));

        // different types -> returns false
        assertFalse(mapFirstCommand.equals(1));

        // null -> returns false
        assertFalse(mapFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(mapFirstCommand.equals(mapSecondCommand));
    }

    /**
     * Returns a {@code MapCommand} with parameters {@code index}.
     */
    private MapCommand prepareCommand(Index index) {
        MapCommand mapCommand = new MapCommand(index);
        mapCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return mapCommand;
    }


}
```
###### \java\seedu\address\logic\parser\ChangeFontSizeCommandParserTest.java
``` java
public class ChangeFontSizeCommandParserTest {
    private ChangeFontSizeCommandParser parser = new ChangeFontSizeCommandParser();

    @Test
    public void parseSuccess() throws Exception {
        ChangeFontSizeCommand changeFontSizeCommand = new ChangeFontSizeCommand(FONT_SIZE_L_LABEL);
        String userInput = ChangeFontSizeCommand.COMMAND_WORD + " " + FONT_SIZE_L_LABEL;
        assertParseSuccess(parser, userInput, changeFontSizeCommand);
    }

    @Test
    public void parseInvalidFontSizeFailure() throws Exception {
        String userInput = ChangeFontSizeCommand.COMMAND_WORD + " " + INVALID_FONT_SIZE;
        assertParseFailure(parser, userInput, FontSize.MESSAGE_FONT_SIZE_CONSTRAINTS);
    }

    @Test
    public void parseIncreaseFailure() throws Exception {
        FontSize.setCurrentFontSizeLabel(FONT_SIZE_XL_LABEL);
        String userInput = ChangeFontSizeCommand.COMMAND_WORD + " " + INCREASE_FONT_SIZE_COMMAND;
        assertParseFailure(parser, userInput, FontSize.MESSAGE_FONT_SIZE_IS_LARGEST);
    }

    @Test
    public void parseDecreaseFailure() throws Exception {
        FontSize.setCurrentFontSizeLabel(FONT_SIZE_XS_LABEL);
        String userInput = ChangeFontSizeCommand.COMMAND_WORD + " " + DECREASE_FONT_SIZE_COMMAND;
        assertParseFailure(parser, userInput, FontSize.MESSAGE_FONT_SIZE_IS_SMALLEST);
    }

}
```
###### \java\seedu\address\logic\parser\ChangeTagColorCommandParserTest.java
``` java
public class ChangeTagColorCommandParserTest {
    private ChangeTagColorCommandParser parser = new ChangeTagColorCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final TagColor tagColor = new TagColor("red");
        final Set<Tag> tags = new HashSet<>();
        Tag tag1 = new Tag("family");
        tags.add(tag1);

        // one tag
        String userInput = PREFIX_TAG.toString() + tag1.tagName + " " + PREFIX_COLOR.toString() + tagColor.tagColorName;
        ChangeTagColorCommand expectedCommand = new ChangeTagColorCommand(tags, tagColor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // more than one tag
        Tag tag2 = new Tag("friend");
        tags.add(tag2);
        userInput += " " + PREFIX_TAG.toString() + tag2.tagName;
        expectedCommand = new ChangeTagColorCommand(tags, tagColor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeTagColorCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, ChangeTagColorCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\ChangeThemeCommandParserTest.java
``` java
public class ChangeThemeCommandParserTest {

    private ChangeThemeCommandParser parser = new ChangeThemeCommandParser();

    @Test
    public void parse_validArgs_returnsChangeThemeCommand() throws Exception {
        // no leading and trailing whitespaces
        ChangeThemeCommand expectedChangeThemeCommand = new ChangeThemeCommand("dark");
        assertParseSuccess(parser, "dark", expectedChangeThemeCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "  \t \n dark \n \t ", expectedChangeThemeCommand);
    }

    @Test
    public void parse_emptyArg_throwsParseException() throws Exception {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeThemeCommand.MESSAGE_USAGE));

    }

}
```
###### \java\seedu\address\logic\parser\MapCommandParserTest.java
``` java
public class MapCommandParserTest {
    private MapCommandParser parser = new MapCommandParser();

    @Test
    public void parse_validArgs_returnsMapCommand() {
        assertParseSuccess(parser, "1", new MapCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\font\FontSizeTest.java
``` java
    @Test
    public void isValidFontSize() throws Exception {
        assertTrue(FontSize.isValidFontSize(FontSize.FONT_SIZE_L_LABEL));
        assertFalse(FontSize.isValidFontSize(INVALID_FONT_SIZE));
        assertFalse(FontSize.isValidFontSize(""));
    }

}
```
###### \java\seedu\address\model\tag\TagColorTest.java
``` java
public class TagColorTest {

    @Test
    public void isValidTagColor() {
        // invalid tag color names
        assertFalse(TagColor.isValidTagColorName("")); // empty string
        assertFalse(TagColor.isValidTagColorName("a")); // random words

        // valid tag color names
        assertTrue(TagColor.isValidTagColorName("red"));
        assertTrue(TagColor.isValidTagColorName("blue"));
        assertTrue(TagColor.isValidTagColorName("yellow"));
    }

}
```
###### \java\seedu\address\model\theme\ThemeTest.java
``` java
public class ThemeTest {
    @Test
    public void isValidThemeName() throws Exception {
        // invalid theme names
        assertFalse(Theme.isValidThemeName("")); // empty string
        assertFalse(Theme.isValidThemeName(null)); // null value
        assertFalse(Theme.isValidThemeName("a")); // random words

        // valid theme names
        assertTrue(Theme.isValidThemeName("dark"));
        assertTrue(Theme.isValidThemeName("bright"));
    }


}
```

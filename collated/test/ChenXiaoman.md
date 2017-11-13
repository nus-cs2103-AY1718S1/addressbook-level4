# ChenXiaoman
###### \java\seedu\address\logic\commands\ChangeFontSizeCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FONT_SIZE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.font.FontSize;

/**
 * Contains integration tests (interaction with the Model) for {@code ChangeFontSizeCommand}.
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
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAGLIST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAGLIST_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_NAME_RED;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColor;

/**
 * Contains integration tests (interaction with the Model) for {@code ChangeTagColorCommand}.
 */
public class ChangeTagColorCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeChangeTagColorOfATagListSuccess() throws Exception {
        TagColor tagColor = new TagColor(VALID_TAG_COLOR_NAME_RED);
        ChangeTagColorCommand changeTagColorCommand = prepareCommand(VALID_TAGLIST_1, VALID_TAG_COLOR_NAME_RED);

        String expectedMessage = String.format(ChangeTagColorCommand.MESSAGE_CHANGE_TAG_COLOR_SUCCESS,
                VALID_TAGLIST_1, VALID_TAG_COLOR_NAME_RED);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTagColorPair(VALID_TAGLIST_1, tagColor);

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
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_THEME_NAME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.theme.Theme.DARK_THEME;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.theme.Theme;

/**
 * Contains integration tests (interaction with the Model) for {@code ChangeThemeCommand}.
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
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code MapCommand}.
 */
public class MapCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MapCommand mapCommand = prepareCommand(outOfBoundsIndex);
        assertCommandFailure(mapCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstPersonOnly(model);

        assertExecutionSuccess(INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        MapCommand mapCommand = prepareCommand(outOfBoundsIndex);

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

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
     * Executes a {@code MapCommand} with the given {@code index}, and checks message showing is expected message
     */
    private void assertExecutionSuccess(Index index) {
        MapCommand mapCommand = prepareCommand(index);
        ReadOnlyPerson readOnlyPerson = model.getFilteredPersonList()
                .get(index.getZeroBased());
        String expectedResultMessage = String.format(
                MapCommand.MESSAGE_SELECT_PERSON_SUCCESS, readOnlyPerson.getName());
        assertCommandSuccess(mapCommand, model, expectedResultMessage, model);
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
package seedu.address.logic.parser;

import static seedu.address.logic.commands.ChangeFontSizeCommand.DECREASE_FONT_SIZE_COMMAND;
import static seedu.address.logic.commands.ChangeFontSizeCommand.INCREASE_FONT_SIZE_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FONT_SIZE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.font.FontSize.FONT_SIZE_L_LABEL;
import static seedu.address.model.font.FontSize.FONT_SIZE_XL_LABEL;
import static seedu.address.model.font.FontSize.FONT_SIZE_XS_LABEL;

import org.junit.Test;

import seedu.address.logic.commands.ChangeFontSizeCommand;
import seedu.address.model.font.FontSize;

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
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColor;

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
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ChangeThemeCommand;

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
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.MapCommand;

public class MapCommandParserTest {
    private MapCommandParser parser = new MapCommandParser();

    @Test
    public void parse_validArgs_returnsMapCommand() {
        assertParseSuccess(parser, "1", new MapCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MapCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MapCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\font\FontSizeTest.java
``` java
package seedu.address.model.font;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FONT_SIZE;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class FontSizeTest {

    @Test
    public void isValidFontSize() throws Exception {
        assertTrue(FontSize.isValidFontSize(FontSize.FONT_SIZE_L_LABEL));
        assertFalse(FontSize.isValidFontSize(INVALID_FONT_SIZE));
        assertFalse(FontSize.isValidFontSize(""));
    }

    @Test
    public void equals() throws IllegalValueException {

        FontSize size = new FontSize("+");
        FontSize sizeCopy = new FontSize("+");
        FontSize newSize = new FontSize("-");

        assertTrue(size.equals(size));
        assertFalse(size.equals(newSize));
        assertTrue(size.equals(sizeCopy));
    }

}
```
###### \java\seedu\address\model\tag\TagColorTest.java
``` java
package seedu.address.model.tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
package seedu.address.model.theme;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
###### \java\systemtests\ChangeFontSizeCommandSystemTest.java
``` java
package systemtests;

import org.junit.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.ChangeFontSizeCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.font.FontSize;

public class ChangeFontSizeCommandSystemTest extends AddressBookSystemTest {
    private Model modelXs;
    private Model modelS;
    private Model modelM;
    private Model modelL;
    private Model modelXl;

    @Test
    public void changeFontSize() throws Exception {
        prepareExpectedModels();

        /* Case: change the font size to L -> font size changed */
        String changeFontSizeCommand = ChangeFontSizeCommand.COMMAND_WORD + " l";
        String expectedMessage = ChangeFontSizeCommand.MESSAGE_SUCCESS + "l.";
        assertCommandSuccess(changeFontSizeCommand, modelL, expectedMessage);

        /* Case: undo changing font size  -> font size changes back to default M */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelM, expectedResultMessage);

        /* Case: undo changing font size  -> font size changes back to L again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelL, expectedResultMessage);

        /* Case: increase font size -> font size increases to XL */
        changeFontSizeCommand = ChangeFontSizeCommand.COMMAND_WORD + " +";
        expectedMessage = ChangeFontSizeCommand.MESSAGE_SUCCESS + "xl.";
        assertCommandSuccess(changeFontSizeCommand, modelXl, expectedMessage);

        /* Case: increase font size when the current font size is the largest one -> rejected */
        changeFontSizeCommand = ChangeFontSizeCommand.COMMAND_WORD + " +";
        assertCommandFailure(changeFontSizeCommand, FontSize.MESSAGE_FONT_SIZE_IS_LARGEST);

        /* Case: change the font size to S -> font size changed */
        changeFontSizeCommand = ChangeFontSizeCommand.COMMAND_WORD + " s";
        expectedMessage = ChangeFontSizeCommand.MESSAGE_SUCCESS + "s.";
        assertCommandSuccess(changeFontSizeCommand, modelS, expectedMessage);

        /* Case: decrease font size -> font size decreases to XS */
        changeFontSizeCommand = ChangeFontSizeCommand.COMMAND_WORD + " -";
        expectedMessage = ChangeFontSizeCommand.MESSAGE_SUCCESS + "xs.";
        assertCommandSuccess(changeFontSizeCommand, modelXs, expectedMessage);

        /* Case: decrease font size when the current font size is the smallest one -> rejected */
        changeFontSizeCommand = ChangeFontSizeCommand.COMMAND_WORD + " -";
        assertCommandFailure(changeFontSizeCommand, FontSize.MESSAGE_FONT_SIZE_IS_SMALLEST);

        /* Case: input font size name is invalid -> rejected */
        changeFontSizeCommand = ChangeFontSizeCommand.COMMAND_WORD + " " + "invalid_font_size_name";
        assertCommandFailure(changeFontSizeCommand, FontSize.MESSAGE_FONT_SIZE_CONSTRAINTS);

        /* Case: input font size name is null -> rejected */
        changeFontSizeCommand = ChangeFontSizeCommand.COMMAND_WORD + " ";
        assertCommandFailure(changeFontSizeCommand, FontSize.MESSAGE_FONT_SIZE_CONSTRAINTS);

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Generates five different models with five font sizes
     */
    private void prepareExpectedModels() {

        // Generate five models with five font size symbols
        modelXs = prepareExpectedModelGivenFontSize("xs");
        modelS = prepareExpectedModelGivenFontSize("s");
        modelM = prepareExpectedModelGivenFontSize("m");
        modelL = prepareExpectedModelGivenFontSize("l");
        modelXl = prepareExpectedModelGivenFontSize("xl");

    }

    /**
     * Generate models with different font size
     * @param fontSize
     * @return new model with given font size
     */
    private Model prepareExpectedModelGivenFontSize(String fontSize) {
        assert (FontSize.isValidFontSize(fontSize));

        // Generate new user preference with given font size
        UserPrefs newUserPrefs = new UserPrefs();
        GuiSettings guiSettings = newUserPrefs.getGuiSettings();
        newUserPrefs.setGuiSettings(
                guiSettings.getWindowHeight(),
                guiSettings.getWindowWidth(),
                guiSettings.getWindowCoordinates().x,
                guiSettings.getWindowCoordinates().y,
                fontSize, guiSettings.getTheme());

        return new ModelManager(new AddressBook(getModel().getAddressBook()), newUserPrefs);
    }

}
```
###### \java\systemtests\ChangeTagColorCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ChangeTagColorCommand.MESSAGE_CHANGE_TAG_COLOR_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAGLIST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAGLIST_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_COLOR_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAGLIST_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAGLIST_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_NAME_RED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_NAME_YELLOW;

import org.junit.Test;

import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColor;

public class ChangeTagColorCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void changeTagColor() throws Exception {
        Model model = getModel();

        /* Case: change the tag color of one tag to one color -> tag color changed */
        String changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " t/friends c/red";
        String expectedMessage = String.format(MESSAGE_CHANGE_TAG_COLOR_SUCCESS, VALID_TAGLIST_1,
                VALID_TAG_COLOR_NAME_RED);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTagColorPair(VALID_TAGLIST_1, new TagColor(VALID_TAG_COLOR_NAME_RED));
        assertCommandSuccess(changeTagColorCommand, expectedModel, expectedMessage);

        /* Case: undo changing tag color  -> tag color changes back to default orange */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, getModel(), expectedResultMessage);

        /* Case: undo changing tag color  -> tag color changes back to red again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: change the tag color of more than one tag to one color -> tag color changed */
        changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " t/friends t/family c/yellow";
        expectedMessage = String.format(MESSAGE_CHANGE_TAG_COLOR_SUCCESS, VALID_TAGLIST_2,
                VALID_TAG_COLOR_NAME_YELLOW);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTagColorPair(VALID_TAGLIST_2, new TagColor(VALID_TAG_COLOR_NAME_YELLOW));
        assertCommandSuccess(changeTagColorCommand, expectedModel, expectedMessage);

        /* Case: change the tag color of a tag with invalid tag name-> rejected */
        changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " t/invalid_tag_name c/yellow";
        expectedMessage = Tag.MESSAGE_TAG_CONSTRAINTS;
        assertCommandFailure(changeTagColorCommand, expectedMessage);

        /* Case: change the tag color of a tag with non-existing tag name-> rejected */
        changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " c/yellow" + " t/" + INVALID_TAG;
        expectedMessage = String.format(ChangeTagColorCommand.MESSAGE_NOT_EXISTING_TAGS, INVALID_TAGLIST);
        assertCommandFailure(changeTagColorCommand, expectedMessage);

        /* Case: change the tag color of a list of tags consist of non-existing tag name-> rejected */
        changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " c/yellow" + " t/" + INVALID_TAG
                + " t/" + INVALID_TAG_2;
        expectedMessage = String.format(ChangeTagColorCommand.MESSAGE_NOT_EXISTING_TAGS, INVALID_TAGLIST_2);
        assertCommandFailure(changeTagColorCommand, expectedMessage);

        /* Case: input tag color name is not valid-> rejected */
        changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " t/friends" + " c/" + INVALID_TAG_COLOR_NAME;
        expectedMessage = TagColor.MESSAGE_TAG_COLOR_CONSTRAINTS;
        assertCommandFailure(changeTagColorCommand, expectedMessage);

        /* Case: missing tag color field-> rejected */
        changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " t/friends";
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeTagColorCommand.MESSAGE_USAGE);
        assertCommandFailure(changeTagColorCommand, expectedMessage);

        /* Case: missing tag field-> rejected */
        changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " c/red";
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeTagColorCommand.MESSAGE_USAGE);
        assertCommandFailure(changeTagColorCommand, expectedMessage);

        /* Case: tag name is null-> rejected */
        changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " c/red t/";
        expectedMessage = Tag.MESSAGE_TAG_CONSTRAINTS;
        assertCommandFailure(changeTagColorCommand, expectedMessage);

        /* Case: color is null-> rejected */
        changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " t/friend c/";
        expectedMessage = TagColor.MESSAGE_TAG_COLOR_CONSTRAINTS;
        assertCommandFailure(changeTagColorCommand, expectedMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\ChangeThemeCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.theme.Theme;

public class ChangeThemeCommandSystemTest extends AddressBookSystemTest {
    private Model modelBrightTheme;
    private Model modelDarkTheme;

    @Test
    public void changeTheme() throws Exception {
        prepareExpectedModels();

        /* Case: change the theme to Dark -> them changed to dark */
        String changeThemeCommand = ChangeThemeCommand.COMMAND_WORD + " " + Theme.DARK_THEME;
        String expectedMessage = String.format(ChangeThemeCommand.MESSAGE_CHANGE_THEME_SUCCESS, Theme.DARK_THEME);
        assertCommandSuccess(changeThemeCommand, modelDarkTheme, expectedMessage);

        /* Case: undo changing theme  -> theme changes back to bright */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBrightTheme, expectedResultMessage);

        /* Case: undo changing theme  -> theme changes back to dark again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelDarkTheme, expectedResultMessage);

        /* Case: change the theme to bright -> theme changed */
        changeThemeCommand = ChangeThemeCommand.COMMAND_WORD + " " + Theme.BRIGHT_THEME;
        expectedMessage = String.format(ChangeThemeCommand.MESSAGE_CHANGE_THEME_SUCCESS, Theme.BRIGHT_THEME);
        assertCommandSuccess(changeThemeCommand, modelBrightTheme, expectedMessage);

        /* Case: input theme name is invalid -> rejected */
        changeThemeCommand = ChangeThemeCommand.COMMAND_WORD + " " + "invalid_theme_name";
        assertCommandFailure(changeThemeCommand,
                String.format(ChangeThemeCommand.MESSAGE_INVALID_THEME_NAME, "invalid_theme_name"));

        /* Case: input theme name is null -> rejected */
        changeThemeCommand = ChangeThemeCommand.COMMAND_WORD + "  ";
        assertCommandFailure(changeThemeCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Generates different models with different themes
     */
    private void prepareExpectedModels() {

        modelBrightTheme = prepareExpectedModelGivenThemeName(Theme.BRIGHT_THEME);
        modelDarkTheme = prepareExpectedModelGivenThemeName(Theme.DARK_THEME);

    }

    /**
     * Generate models with different theme
     * @param theme
     * @return new model with given theme
     */
    private Model prepareExpectedModelGivenThemeName(String theme) {
        assert (Theme.isValidThemeName(theme));

        // Generate new user preference with given theme
        UserPrefs newUserPrefs = new UserPrefs();
        GuiSettings guiSettings = newUserPrefs.getGuiSettings();
        newUserPrefs.setGuiSettings(
                guiSettings.getWindowHeight(),
                guiSettings.getWindowWidth(),
                guiSettings.getWindowCoordinates().x,
                guiSettings.getWindowCoordinates().y,
                guiSettings.getFontSize(), theme);

        return new ModelManager(new AddressBook(getModel().getAddressBook()), newUserPrefs);
    }

}
```
###### \java\systemtests\MapCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import org.junit.Ignore;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.MapCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

public class MapCommandSystemTest extends AddressBookSystemTest {
    @Test
    @Ignore
    public void map() {
        /* Case: show the map for the first card in the person list, command with leading spaces and trailing spaces
         * -> map showed
         */
        String command = "   " + MapCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + "   ";
        assertCommandSuccess(command, INDEX_FIRST_PERSON);

        /* Case: show the map of person's address of the last card in the person list -> map showed */
        Index personCount = Index.fromOneBased(getTypicalPersons().size());
        command = MapCommand.COMMAND_WORD + " " + personCount.getOneBased();
        assertCommandSuccess(command, personCount);

        /* Case: undo previous map showing -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo showing the map of person's address -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: show the map of person's address of the middle card in the person list -> showed */
        Index middleIndex = Index.fromOneBased(personCount.getOneBased() / 2);
        command = MapCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertCommandSuccess(command, middleIndex);

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredPersonList().size() + 1;
        command = MapCommand.COMMAND_WORD + " " + invalidIndex;
        expectedResultMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: filtered person list, show map within bounds of whole contact list but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        invalidIndex = getModel().getAddressBook().getPersonList().size();
        assertCommandFailure(MapCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: filtered person list, show map within bounds of whole contact list and person list -> showed */
        Index validIndex = Index.fromOneBased(1);
        assert validIndex.getZeroBased() < getModel().getFilteredPersonList().size();
        command = MapCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccess(command, validIndex);

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(MapCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(MapCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(MapCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(MapCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> success */
        command = "mAp 1";
        assertCommandSuccess(command, INDEX_FIRST_PERSON);

        /* Case: select from empty contact list -> rejected */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        assertCommandFailure(MapCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays the success message of executing map command with the {@code expectedSelectedCardIndex}
     * of the selected person, and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar remain unchanged. The
     * resulting
     * browser url and selected card will be verified if the current selected card and the card at
     * {@code expectedSelectedCardIndex} are different.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        ReadOnlyPerson readOnlyPerson = expectedModel.getFilteredPersonList()
                .get(expectedSelectedCardIndex.getZeroBased());
        String expectedResultMessage = String.format(
                MapCommand.MESSAGE_SELECT_PERSON_SUCCESS, readOnlyPerson.getName());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```

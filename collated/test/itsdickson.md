# itsdickson
###### \java\guitests\guihandles\ThemesWindowHandle.java
``` java

import java.net.URL;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code ThemesWindow} of the application.
 */
public class ThemesWindowHandle extends StageHandle {

    public static final String THEMES_WINDOW_TITLE = "Themes";

    private static final String THEMES_WINDOW_BROWSER_ID = "#browser";

    public ThemesWindowHandle(Stage themesWindowStage) {
        super(themesWindowStage);
    }

    /**
     * Returns true if a themes window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(THEMES_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(THEMES_WINDOW_BROWSER_ID));
    }
}
```
###### \java\guitests\ThemesWindowTest.java
``` java

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.ThemesWindowHandle;
import seedu.address.logic.commands.ThemeListCommand;

public class ThemesWindowTest extends AddressBookGuiTest {

    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openThemesWindow() {
        //use command box
        runCommand(ThemeListCommand.COMMAND_WORD);
        assertThemesWindowOpen();
    }

    /**
     * Asserts that the themes window is open, and closes it after checking.
     */
    private void assertThemesWindowOpen() {
        assertTrue(ERROR_MESSAGE, ThemesWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new ThemesWindowHandle(guiRobot.getStage(ThemesWindowHandle.THEMES_WINDOW_TITLE)).close();
        mainWindowHandle.focus();
    }
}
```
###### \java\seedu\address\logic\commands\FavouriteCommandTest.java
``` java

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.favouriteFirstPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

public class FavouriteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexValidPerson_success() throws Exception {
        ReadOnlyPerson personToFavourite = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FavouriteCommand favouriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FavouriteCommand.MESSAGE_FAVOURITE_PERSON_SUCCESS, personToFavourite);
        CommandResult commandResult = favouriteCommand.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    @Test
    public void execute_validIndexInvalidPerson_success() throws Exception {
        favouriteFirstPerson(model);
        FavouriteCommand favouriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        try {
            favouriteCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(FavouriteCommand.MESSAGE_FAVOURITE_PERSON_FAIL, e.getMessage());
        }
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FavouriteCommand favouriteCommand = prepareCommand(outOfBoundIndex);

        try {
            favouriteCommand.execute();
            fail("The expected CommandException was not thrown");
        } catch (CommandException e) {
            assertEquals(Messages.MESSAGE_INVALID_DISPLAYED_INDEX, e.getMessage());
        }
    }

    @Test
    public void equals() {
        FavouriteCommand favouriteFirstCommand = new FavouriteCommand(INDEX_FIRST_PERSON);
        FavouriteCommand favouriteSecondCommand = new FavouriteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(favouriteFirstCommand.equals(favouriteFirstCommand));

        // same values -> returns true
        FavouriteCommand deleteFirstCommandCopy = new FavouriteCommand(INDEX_FIRST_PERSON);
        assertTrue(favouriteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(favouriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(favouriteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(favouriteFirstCommand.equals(favouriteSecondCommand));
    }

    /**
     * Returns a {@code FavouriteCommand} with the parameter {@code index}.
     */
    private FavouriteCommand prepareCommand(Index index) {
        FavouriteCommand favouriteCommand = new FavouriteCommand(index);
        favouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favouriteCommand;
    }
}
```
###### \java\seedu\address\logic\commands\FavouriteListCommandTest.java
``` java

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.favouriteFirstPerson;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class FavouriteListCommandTest {

    private Model model;
    private Model expectedModel;
    private Model emptyModel;
    private FavouriteListCommand favouriteListCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        emptyModel = new ModelManager(new AddressBook(), new UserPrefs());

        favouriteListCommand = new FavouriteListCommand();
        favouriteListCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_favouriteListEmpty_showsNothing() {
        CommandResult result = favouriteListCommand.execute();
        assertEquals(result.feedbackToUser, FavouriteListCommand.MESSAGE_SUCCESS);
        assertEquals(model.getFilteredPersonList(), emptyModel.getFilteredPersonList());
    }

    @Test
    public void execute_favouriteListNotEmpty_showsPerson() {
        favouriteFirstPerson(model);
        favouriteFirstPerson(expectedModel);
        assertEquals(model, expectedModel);

        CommandResult result = favouriteListCommand.execute();
        assertEquals(result.feedbackToUser, FavouriteListCommand.MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\FindTagCommandTest.java
``` java

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class FindTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("first"));
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("second"));

        FindTagCommand findTagFirstCommand = new FindTagCommand(firstPredicate);
        FindTagCommand findTagSecondCommand = new FindTagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findTagFirstCommand.equals(findTagFirstCommand));

        // same values -> returns true
        FindTagCommand findTagFirstCommandCopy = new FindTagCommand(firstPredicate);
        assertTrue(findTagFirstCommand.equals(findTagFirstCommandCopy));

        // different types -> returns false
        assertFalse(findTagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findTagFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findTagFirstCommand.equals(findTagSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindTagCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        FindTagCommand command = prepareCommand("colleagues owesMoney");
        System.out.println("he=" + command.model.getFilteredPersonList());
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON, CARL, DANIEL, FIONA));
    }

    /**
     * Parses {@code userInput} into a {@code FindTagCommand}.
     */
    private FindTagCommand prepareCommand(String userInput) {
        FindTagCommand command =
                new FindTagCommand(new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindTagCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\SwitchThemeCommandTest.java
``` java

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SwitchThemeCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexInvalidSwitch_throwsCommandException() throws Exception {
        SwitchThemeCommand switchThemeCommand = prepareCommand(INDEX_FIRST_PERSON);

        try {
            switchThemeCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(Messages.MESSAGE_INVALID_SWITCH, e.getMessage());
        }
    }

    @Test
    public void execute_validIndexValidSwitch_success() throws Exception {
        SwitchThemeCommand switchThemeCommand = prepareCommand(INDEX_SECOND_PERSON);

        String expectedMessage = SwitchThemeCommand.MESSAGE_LIGHT_THEME_SUCCESS;
        CommandResult commandResult = switchThemeCommand.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getThemesList().size() + 1);
        SwitchThemeCommand switchThemeCommand = prepareCommand(outOfBoundIndex);

        try {
            switchThemeCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(Messages.MESSAGE_INVALID_DISPLAYED_INDEX, e.getMessage());
        }
    }

    @Test
    public void equals() {
        SwitchThemeCommand switchThemeFirstCommand = new SwitchThemeCommand(INDEX_FIRST_PERSON);
        SwitchThemeCommand switchThemeSecondCommand = new SwitchThemeCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(switchThemeFirstCommand.equals(switchThemeFirstCommand));

        // same values -> returns true
        SwitchThemeCommand switchThemeFirstCommandCopy = new SwitchThemeCommand(INDEX_FIRST_PERSON);
        assertTrue(switchThemeFirstCommand.equals(switchThemeFirstCommandCopy));

        // different types -> returns false
        assertFalse(switchThemeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(switchThemeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(switchThemeFirstCommand.equals(switchThemeSecondCommand));
    }

    /**
     * Returns a {@code SwitchThemeCommand} with the parameter {@code index}.
     */
    private SwitchThemeCommand prepareCommand(Index index) {
        SwitchThemeCommand switchThemeCommand = new SwitchThemeCommand(index);
        switchThemeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return switchThemeCommand;
    }
}
```
###### \java\seedu\address\logic\commands\ThemeListCommandTest.java
``` java

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ThemeListCommand.MESSAGE_SUCCESS;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowThemeRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ThemeListCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_help_success() {
        CommandResult result = new ThemeListCommand().execute();
        assertEquals(MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowThemeRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### \java\seedu\address\logic\commands\UnfavouriteCommandTest.java
``` java

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.favouriteFirstPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

public class UnfavouriteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexValidPerson_success() throws Exception {
        favouriteFirstPerson(model);
        ReadOnlyPerson personToUnfavourite = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnfavouriteCommand unfavouriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnfavouriteCommand.MESSAGE_UNFAVOURITE_PERSON_SUCCESS,
                personToUnfavourite);
        CommandResult commandResult = unfavouriteCommand.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    @Test
    public void execute_validIndexInvalidPerson_success() throws Exception {
        UnfavouriteCommand unfavouriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        try {
            unfavouriteCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(UnfavouriteCommand.MESSAGE_UNFAVOURITE_PERSON_FAIL, e.getMessage());
        }
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnfavouriteCommand unfavouriteCommand = prepareCommand(outOfBoundIndex);

        try {
            unfavouriteCommand.execute();
            fail("The expected CommandException was not thrown");
        } catch (CommandException e) {
            assertEquals(Messages.MESSAGE_INVALID_DISPLAYED_INDEX, e.getMessage());
        }
    }

    @Test
    public void equals() {
        UnfavouriteCommand favouriteFirstCommand = new UnfavouriteCommand(INDEX_FIRST_PERSON);
        UnfavouriteCommand favouriteSecondCommand = new UnfavouriteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(favouriteFirstCommand.equals(favouriteFirstCommand));

        // same values -> returns true
        UnfavouriteCommand deleteFirstCommandCopy = new UnfavouriteCommand(INDEX_FIRST_PERSON);
        assertTrue(favouriteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(favouriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(favouriteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(favouriteFirstCommand.equals(favouriteSecondCommand));
    }

    /**
     * Returns a {@code UnfavouriteCommand} with the parameter {@code index}.
     */
    private UnfavouriteCommand prepareCommand(Index index) {
        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(index);
        unfavouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unfavouriteCommand;
    }
}
```
###### \java\seedu\address\logic\parser\FavouriteCommandParserTest.java
``` java

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.FavouriteCommand;

public class FavouriteCommandParserTest {

    private FavouriteCommandParser parser = new FavouriteCommandParser();

    @Test
    public void parse_validArgs_returnsFavouriteCommand() {
        assertParseSuccess(parser, "1", new FavouriteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\FindTagCommandParserTest.java
``` java

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindTagCommand;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class FindTagCommandParserTest {

    private FindTagCommandParser parser = new FindTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindTagCommand() {
        // no leading and trailing whitespaces
        FindTagCommand expectedFindTagCommand =
                new FindTagCommand(new TagContainsKeywordsPredicate(Arrays.asList("owesMoney", "colleagues")));
        assertParseSuccess(parser, "owesMoney colleagues", expectedFindTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n owesMoney \n \t colleagues  \t", expectedFindTagCommand);
    }
}
```
###### \java\seedu\address\logic\parser\SwitchThemeCommandParserTest.java
``` java

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.SwitchThemeCommand;

public class SwitchThemeCommandParserTest {

    private SwitchThemeCommandParser parser = new SwitchThemeCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new SwitchThemeCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchThemeCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\UnfavouriteCommandParserTest.java
``` java

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.UnfavouriteCommand;

public class UnfavouriteCommandParserTest {

    private UnfavouriteCommandParser parser = new UnfavouriteCommandParser();

    @Test
    public void parse_validArgs_returnsUnfavouriteCommand() {
        assertParseSuccess(parser, "1", new UnfavouriteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnfavouriteCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\person\NameContainsFavouritePredicateTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsFavouritePredicateTest {
    @Test
    public void equals() {
        NameContainsFavouritePredicate firstPredicate = new NameContainsFavouritePredicate();

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsFavouritePredicate firstPredicateCopy = new NameContainsFavouritePredicate();
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void test_personIsFavourited_returnsTrue() {
        NameContainsFavouritePredicate predicate = new NameContainsFavouritePredicate();
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withFavourite("true").build()));
    }

    @Test
    public void test_personIsNotFavourited_returnsFalse() {
        NameContainsFavouritePredicate predicate = new NameContainsFavouritePredicate();
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }
}
```
###### \java\seedu\address\model\person\TagContainsKeywordsPredicateTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
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
        // One keyword
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("colleagues"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("colleagues").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("colleagues", "friends"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friends", "colleagues").build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("friends", "neighbours"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").withTags("neighbours").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("NeiGhbOUrs", "FriEndS"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("neighbours", "friends").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("colleagues"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Bob").withTags("neighbours", "friends").build()));
    }
}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Favourite} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withFavourite(String favourite) {
        descriptor.setFavourite(new Boolean(favourite));
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Boolean} of the {@code Person} that we are building.
     */
    public PersonBuilder withFavourite(String favourite) {
        this.person.setFavourite(new Boolean(favourite));
        return this;
    }
```
###### \java\seedu\address\ui\ThemesWindowTest.java
``` java

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.ThemesWindow.THEMES_FILE_PATH;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.ThemesWindowHandle;
import javafx.stage.Stage;

public class ThemesWindowTest extends GuiUnitTest {

    private ThemesWindow themesWindow;
    private ThemesWindowHandle themesWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> themesWindow = new ThemesWindow());
        Stage themesWindowStage = FxToolkit.setupStage((stage) -> stage.setScene(themesWindow.getRoot().getScene()));
        FxToolkit.showStage();
        themesWindowHandle = new ThemesWindowHandle(themesWindowStage);
    }

    @Test
    public void display() {
        URL expectedThemesPage = ThemesWindow.class.getResource(THEMES_FILE_PATH);
        assertEquals(expectedThemesPage, themesWindowHandle.getLoadedUrl());
    }
}
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}. Executes {@code command} instead.
     * @see AddCommandSystemTest#assertCommandSuccess(ReadOnlyPerson)
     */
    private void assertCommandSuccess(String command, ReadOnlyPerson toAdd) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);
        try {
            expectedModel.addPerson(toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        int preExecutionSelectedCardIndex = getPersonListPanel().getSelectedCardIndex();
        executeCommand(command);
        int postExecutionSelectedCardIndex = getPersonListPanel().getSelectedCardIndex();
        if (preExecutionSelectedCardIndex == postExecutionSelectedCardIndex) {
            assertSelectedCardUnchanged();
        } else {
            assertSelectedCardChanged(Index.fromZeroBased(postExecutionSelectedCardIndex));
        }
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)}
     * except that the selected card is deselected.
     * @see AddCommandSystemTest#assertCommandSuccess(String, ReadOnlyPerson)
     */
    private void assertUndoCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardDeselected();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)}
     * except that the selected card changed.
     * @see AddCommandSystemTest#assertCommandSuccess(String, ReadOnlyPerson)
     */
    private void assertRedoCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardChanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }
```
###### \java\systemtests\FindTagCommandSystemTest.java
``` java

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.TAG_MATCHING_DANIEL;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class FindTagCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void findtag() {
        executeCommand("list");
        /* Case: find 1 person in address book, command with leading spaces and trailing spaces
         * -> 1 person found
         */
        String command = "   " + FindTagCommand.COMMAND_WORD + " " + TAG_MATCHING_DANIEL + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL); // Daniel has tag "colleagues"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the person we are finding
         * -> 1 person found
         */
        command = FindTagCommand.COMMAND_WORD + " " + TAG_MATCHING_DANIEL;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons where person list is not displaying the person we are finding
         * -> 2 persons found
         */
        command = FindTagCommand.COMMAND_WORD + " neighbours";
        ModelHelper.setFilteredList(expectedModel, ELLE, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords -> 3 persons found */
        command = FindTagCommand.COMMAND_WORD + " colleagues neighbours";
        ModelHelper.setFilteredList(expectedModel, DANIEL, ELLE, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords in reversed order -> 3 persons found */
        command = FindTagCommand.COMMAND_WORD + " neighbours colleagues";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords with 1 repeat -> 3 persons found */
        command = FindTagCommand.COMMAND_WORD + " colleagues neighbours colleagues";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 3 persons found
         */
        command = FindTagCommand.COMMAND_WORD + " colleagues neighbours NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous findtag command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous findtag command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons in address book after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 2");
        assert !getModel().getAddressBook().getPersonList().contains(ELLE);
        command = FindTagCommand.COMMAND_WORD + " neighbours";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is same as tag but of different case -> 1 person found */
        command = FindTagCommand.COMMAND_WORD + " NeIgHBoURs";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*Case: find person in address book, keyword is substring of tag -> 0 person found */
        command = FindTagCommand.COMMAND_WORD + " neigh";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find person in address book, tag is substring of keyword -> 0 person found */
        command = FindTagCommand.COMMAND_WORD + " Colleaguers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person not in address book -> 0 person found */
        command = FindTagCommand.COMMAND_WORD + " girlfriend";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in address book -> 0 person found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 0 person found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 0 person found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        command = FindTagCommand.COMMAND_WORD + " " + TAG_MATCHING_DANIEL;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty address book -> 0 person found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindTagCommand.COMMAND_WORD + " " + TAG_MATCHING_DANIEL;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNdTaG friends";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

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

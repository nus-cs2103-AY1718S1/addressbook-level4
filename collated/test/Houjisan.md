# Houjisan
###### \java\seedu\address\logic\commands\FavouriteCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.FavouriteStatus;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for FavouriteCommand.
 */
public class FavouriteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_favourite_success() throws Exception {
        Person favouritedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_SECOND_PERSON.getZeroBased())).withFavouriteStatus(true).build();
        FavouriteCommand favouriteCommand = prepareCommand(INDEX_SECOND_PERSON);
        String expectedMessage = String.format(FavouriteCommand.MESSAGE_FAVOURITED_PERSON, favouritedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), favouritedPerson);
        assertCommandSuccess(favouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfavourite_success() throws Exception {
        Person favouritedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        favouritedPerson.setFavouriteStatus(new FavouriteStatus(true));
        Person unfavouritedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        unfavouritedPerson.setFavouriteStatus(new FavouriteStatus(false));
        model.updatePerson(model.getFilteredPersonList().get(0), favouritedPerson);
        FavouriteCommand favouriteCommand = prepareCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(FavouriteCommand.MESSAGE_UNFAVOURITED_PERSON, unfavouritedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), unfavouritedPerson);
        assertCommandSuccess(favouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);
        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person favouritedPerson = new PersonBuilder(personInFilteredList)
                .withFavouriteStatus(true).build();
        FavouriteCommand favouriteCommand = prepareCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(FavouriteCommand.MESSAGE_FAVOURITED_PERSON, favouritedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), favouritedPerson);
        assertCommandSuccess(favouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FavouriteCommand favouriteCommand = prepareCommand(outOfBoundIndex);
        assertCommandFailure(favouriteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        FavouriteCommand favouriteCommand = prepareCommand(outOfBoundIndex);
        assertCommandFailure(favouriteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final FavouriteCommand standardCommand = new FavouriteCommand(INDEX_FIRST_PERSON);
        // same values -> returns true
        FavouriteCommand commandWithSameValues = new FavouriteCommand(INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new FavouriteCommand(INDEX_SECOND_PERSON)));
    }

    /**
     * Returns an {@code FavouriteCommand} with parameter {@code index}
     */
    private FavouriteCommand prepareCommand(Index index) {
        FavouriteCommand favouriteCommand = new FavouriteCommand(index);
        favouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favouriteCommand;
    }
}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private String nameField = "name";
    private String phoneField = "phone";
    private String emailField = "email";
    private String addressField = "address";
    private boolean favIgnored = true;
    private boolean favNotIgnored = false;
    private boolean notReverseOrder = false;
    private boolean reverseOrder = true;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFilteredSortsByName_sameList() {
        SortCommand sortCommand = prepareCommand(nameField, favNotIgnored, notReverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, nameField), expectedModel);
    }

    @Test
    public void execute_listIsFilteredSortsByName_sameList() {
        showFirstPersonOnly(model);
        showFirstPersonOnly(expectedModel);
        SortCommand sortCommand = prepareCommand(nameField, favNotIgnored, notReverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, nameField), expectedModel);
    }

    @Test
    public void execute_listIsNotFilteredSortsByPhone_sameList() {
        SortCommand sortCommand = prepareCommand(phoneField, favNotIgnored, notReverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, phoneField), expectedModel);
    }

    @Test
    public void execute_listIsFilteredSortsByPhone_sameList() {
        showFirstPersonOnly(model);
        showFirstPersonOnly(expectedModel);
        SortCommand sortCommand = prepareCommand(phoneField, favNotIgnored, notReverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, phoneField), expectedModel);
    }

    @Test
    public void execute_listIsNotFilteredSortsByEmail_sameList() {
        SortCommand sortCommand = prepareCommand(emailField, favNotIgnored, notReverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, emailField), expectedModel);
    }

    @Test
    public void execute_listIsFilteredSortsByEmail_sameList() {
        showFirstPersonOnly(model);
        showFirstPersonOnly(expectedModel);
        SortCommand sortCommand = prepareCommand(emailField, favNotIgnored, notReverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, emailField), expectedModel);
    }

    @Test
    public void execute_listIsNotFilteredSortsByAddress_sameList() {
        SortCommand sortCommand = prepareCommand(addressField, favNotIgnored, notReverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, addressField), expectedModel);
    }

    @Test
    public void execute_listIsFilteredSortsByAddress_sameList() {
        showFirstPersonOnly(model);
        showFirstPersonOnly(expectedModel);
        SortCommand sortCommand = prepareCommand(addressField, favNotIgnored, notReverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, addressField), expectedModel);
    }

    @Test
    public void execute_listIsNotFilteredSortsByNameIgnoreFav_sameList() {
        SortCommand sortCommand = prepareCommand(nameField, favIgnored, notReverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, nameField)
                        + " ignoring favourites", expectedModel);
    }

    @Test
    public void execute_listIsNotFilteredSortsByNameReverseOrder_sameList() {
        SortCommand sortCommand = prepareCommand(nameField, favNotIgnored, reverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, nameField)
                        + " in reverse order", expectedModel);
    }

    /**
     * Returns an {@code SortCommand} with parameter {@code datafield}
     */
    private SortCommand prepareCommand(String datafield, boolean isFavIgnored, boolean isReverseOrder) {
        SortCommand sortCommand = new SortCommand(datafield, isFavIgnored, isReverseOrder);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
```
###### \java\seedu\address\logic\commands\TagsCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class TagsCommandTest {

    private Model model;
    private Model expectedModel;
    private TagsCommand tagsCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        tagsCommand = new TagsCommand();
        tagsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_showTags_success() {
        assertCommandSuccess(tagsCommand, model, TagsCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### \java\seedu\address\logic\parser\FavouriteCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FavouriteCommand;

public class FavouriteCommandParserTest {
    private FavouriteCommandParser parser = new FavouriteCommandParser();

    @Test
    public void parse_indexSpecified_success() throws Exception {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + "";
        FavouriteCommand expectedCommand = new FavouriteCommand(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, FavouriteCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_name_success() throws Exception {
        String userInput = "name";
        SortCommand expectedCommand = new SortCommand("name", false, false);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nameIgnoreFav_success() throws Exception {
        String userInput = "name -ignorefav";
        SortCommand expectedCommand = new SortCommand("name", true, false);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nameReverseOrder_success() throws Exception {
        String userInput = "name -reverse";
        SortCommand expectedCommand = new SortCommand("name", false, true);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nameIgnoreFavReverseOrder_success() throws Exception {
        String userInput = "name -ignorefav -reverse";
        SortCommand expectedCommand = new SortCommand("name", true, true);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, SortCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\model\person\FavouriteStatusTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FavouriteStatusTest {

    @Test
    public void equals() {
        FavouriteStatus favouriteStatus = new FavouriteStatus(false);

        // same object -> returns true
        assertTrue(favouriteStatus.equals(favouriteStatus));

        // same values -> returns true
        FavouriteStatus favouriteStatusCopy = new FavouriteStatus(favouriteStatus.isFavourite);
        assertTrue(favouriteStatus.equals(favouriteStatusCopy));

        // different types -> returns false
        assertFalse(favouriteStatus.equals(1));

        // null -> returns false
        assertFalse(favouriteStatus.equals(null));

        // different favourite status -> returns false
        FavouriteStatus differentFavouriteStatus = new FavouriteStatus(true);
        assertFalse(favouriteStatus.equals(differentFavouriteStatus));
    }
}
```

# giang
###### /java/seedu/address/logic/commands/FavoriteCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Favorite;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code FavoriteCommand}.
 */
public class FavoriteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToFavorite = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person favoritedPerson = new Person(personToFavorite);
        favoritedPerson.setFavorite(new Favorite(!personToFavorite.getFavorite().favorite));

        FavoriteCommand favoriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FavoriteCommand.MESSAGE_FAVORITE_PERSON_SUCCESS, favoritedPerson);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.favoritePerson(personToFavorite);

        assertCommandSuccess(favoriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FavoriteCommand favoriteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(favoriteCommand, model, MESSAGE_EXECUTION_FAILURE
                + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToFavorite = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person favoritedPerson = new Person(personToFavorite);
        favoritedPerson.setFavorite(new Favorite(!personToFavorite.getFavorite().favorite));

        FavoriteCommand favoriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FavoriteCommand.MESSAGE_FAVORITE_PERSON_SUCCESS, favoritedPerson);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.favoritePerson(personToFavorite);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(favoriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        FavoriteCommand favoriteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(favoriteCommand, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        FavoriteCommand favoriteFirstCommand = new FavoriteCommand(INDEX_FIRST_PERSON);
        FavoriteCommand favoriteSecondCommand = new FavoriteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(favoriteFirstCommand.equals(favoriteFirstCommand));

        // same values -> returns true
        FavoriteCommand favoriteFirstCommandCopy = new FavoriteCommand(INDEX_FIRST_PERSON);
        assertTrue(favoriteFirstCommand.equals(favoriteFirstCommandCopy));

        // different types -> returns false
        assertFalse(favoriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(favoriteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(favoriteFirstCommand.equals(favoriteSecondCommand));
    }

    /**
     * Returns a {@code FavoriteCommand} with the parameter {@code index}.
     */
    private FavoriteCommand prepareCommand(Index index) {
        FavoriteCommand favoriteCommand = new FavoriteCommand(index);
        favoriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favoriteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
```
###### /java/seedu/address/logic/commands/RemarkCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
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
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;

public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private String remarkString = "";

    @Test
    public void testExecuteUndoableCommand() throws Exception {
    }

    @Test
    public void testEqual() throws Exception {
        RemarkCommand remark1 = new RemarkCommand(Index.fromOneBased(1), new Remark("haha"));
        RemarkCommand remark2 = new RemarkCommand(Index.fromOneBased(1), new Remark("haha"));
        assertTrue(assertRemarkCommandEqual(remark1, remark2));
    }



    @Test
    public void execute_unfilteredList_success() {
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, new Remark(remarkString));
        ReadOnlyPerson remarkedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_REMARK_PERSON_SUCCESS, remarkedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }


    /**
     * Check if two RemarkCommand objects are equal
     */
    private boolean assertRemarkCommandEqual(RemarkCommand remark1, RemarkCommand remark2) {
        if (remark1.equals(remark2)) {
            return true;
        }
        return false;
    }

    /**
     * Remark filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex,
                new Remark("Nice"));

        assertCommandFailure(remarkCommand, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private RemarkCommand prepareCommand(Index index, Remark remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, remark);
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}
```
###### /java/seedu/address/logic/parser/FavoriteCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.FavoriteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class FavoriteCommandParserTest {

    private FavoriteCommandParser parser = new FavoriteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new FavoriteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                MESSAGE_INVALID_COMMAND_FORMAT + FavoriteCommand.MESSAGE_USAGE);
    }
}
```

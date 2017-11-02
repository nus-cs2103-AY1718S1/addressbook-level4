//@@author A0143832J
package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TestUtil.getPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import java.util.Collections;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FavoriteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Favorite;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;


public class FavoriteCommandSystemTest extends AddressBookSystemTest {

    // this message is used to match message in result display, which should be empty for any failed execution
    private static final String MESSAGE_EXECUTION_FAILURE_EMPTY = "";

    private static final String MESSAGE_INVALID_FAVORITE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FavoriteCommand.MESSAGE_USAGE);

    @Test
    public void favorite() {
        /* ----------- Performing favorite operation while an unfiltered list is being shown -------------------- */

        /* Case: favorite the first person in the list, command with leading spaces and trailing spaces -> favorited */
        Model expectedModel = getModel();
        String command = FavoriteCommand.COMMAND_WORD
                + " " + INDEX_FIRST_PERSON.getOneBased() + "       ";
        ReadOnlyPerson personToFavorite = favoritePerson(expectedModel, INDEX_FIRST_PERSON);

        String expectedResultMessage = String.format(
                FavoriteCommand.MESSAGE_FAVORITE_PERSON_SUCCESS, personToFavorite);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);


        /* Case: favorite the last person in the list -> favorited */
        Model modelBeforeFavoritingLast = getModel();
        Index lastPersonIndex = getLastIndex(modelBeforeFavoritingLast);
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo favoriting the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeFavoritingLast, expectedResultMessage);

        /* Case: redo favoriting the last person in the list -> last person deleted again */
        command = RedoCommand.COMMAND_WORD;
        favoritePerson(modelBeforeFavoritingLast, lastPersonIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeFavoritingLast, expectedResultMessage);

        /* Case: favorite the middle person in the list -> deleted */
        Index middlePersonIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* ----------- Performing favorite operation while a filtered list is being shown ------------------- */

        /* Case: filtered person list, favorite index within bounds of address book and person list -> favorited */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        assertCommandSuccess(index, INDEX_SECOND_PERSON);

        /* Case: filtered person list, favorite index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = FavoriteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* --------------- Performing favorite operation while a person card is selected ------------------- */

        /* Case: favorite the selected person -> person list panel selects the same person*/
        showAllPersons();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = INDEX_SECOND_PERSON;
        selectPerson(selectedIndex);
        command = FavoriteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        personToFavorite = favoritePerson(expectedModel, selectedIndex);

        expectedResultMessage = String.format(FavoriteCommand.MESSAGE_FAVORITE_PERSON_SUCCESS, personToFavorite);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------- Performing invalid favorite operation ---------------------------- */

        /* Case: invalid index (0) -> rejected */
        command = FavoriteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* Case: invalid index (-1) -> rejected */
        command = FavoriteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = FavoriteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(FavoriteCommand.COMMAND_WORD + " abc", MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(FavoriteCommand.COMMAND_WORD + " 1 abc", MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("FaVORIte 1",
                MESSAGE_EXECUTION_FAILURE_EMPTY);
    }

    /**
     * Removes the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     * @return the favorited person
     */
    private ReadOnlyPerson favoritePerson(Model model, Index index) {
        ReadOnlyPerson targetPerson = getPerson(model, index);
        try {
            model.favoritePerson(targetPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        }
        Person favoritedPerson = new Person(targetPerson);
        favoritedPerson.setFavorite(
                new Favorite(!targetPerson.getFavorite().favorite));
        return favoritedPerson;
    }

    /**
     * Favorite the person at {@code toFavorite} by creating a default {@code FavoriteCommand} using
     * {@code toFavorite} and performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see FavoriteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toFavorite) {
        Model expectedModel = getModel();
        ReadOnlyPerson personToFavorite = favoritePerson(expectedModel, toFavorite);
        String expectedResultMessage = String.format(FavoriteCommand.MESSAGE_FAVORITE_PERSON_SUCCESS, personToFavorite);
        assertCommandSuccess(
                FavoriteCommand.COMMAND_WORD + " "
                        + toFavorite.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Favorite the person at {@code toFavorite} in a filtered list which is equivalent to a person at
     * {@code defaultIndex} in the normal Model, by creating a default {@code FavoriteCommand} using {@code toFavorite}
     * and performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see FavoriteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toFavorite, Index defaultIndex) {
        Model expectedModel = getModel();

        ReadOnlyPerson personToFavorite = favoritePerson(expectedModel, defaultIndex);
        String expectedResultMessage = String.format(FavoriteCommand.MESSAGE_FAVORITE_PERSON_SUCCESS, personToFavorite);
        expectedModel.updateFilteredPersonList(
                new NameContainsKeywordsPredicate(Collections.singletonList(KEYWORD_MATCHING_MEIER)));
        assertCommandSuccess(
                FavoriteCommand.COMMAND_WORD + " "
                        + toFavorite.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see FavoriteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command, false);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command, true);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
//@@author

package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.RemoveTagCommand.MESSAGE_DELETE_TAG_SUCCESS;
import static seedu.address.logic.commands.RemoveTagCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author sidhmads
public class RemoveTagCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_REMOVE_TAG_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE);

    @Test
    public void removeTag() {
        /* --------------- Performing removeTag operation while an unfiltered list is being shown ------------------ */
        /* the same tags will be used */
        Set<Tag> tags = tagList("friends");
        /* Case: removeTag from the first person in the list
         * command with leading spaces and trailing spaces -> removedTag
         */
        Model expectedModel = getModel();
        String command = "     " + RemoveTagCommand.COMMAND_WORD + " rm/    "
                + INDEX_FIRST_PERSON.getOneBased() + "  t/friends    ";
        ObservableList<ReadOnlyPerson> personToRemoveTag = removePersonTag(expectedModel,
                personList(String.valueOf(INDEX_FIRST_PERSON.getOneBased())), tags);
        String expectedResultMessage = String.format(MESSAGE_DELETE_TAG_SUCCESS,
                tags.toString()) + String.format("from %s", personToRemoveTag.toString());
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: removeTag from the last person in the list -> removedTag */
        Model modelBeforeDeletingLast = getModel();
        ArrayList<Index> indexes = personList(String.valueOf(getLastIndex(modelBeforeDeletingLast).getOneBased()));
        assertCommandSuccess(indexes, tags);

        /* Case: undo deleting the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo removeTag the last person in the list -> last person's tag removed again */
        command = RedoCommand.COMMAND_WORD;
        removePersonTag(modelBeforeDeletingLast, indexes, tags);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: removeTag from the middle person in the list -> removedTag */
        indexes.clear();
        indexes = personList(String.valueOf(getMidIndex(getModel()).getOneBased()));
        assertCommandSuccess(indexes, tags);

        /* -------------Performing removeTag operation while a filtered list is being shown --------------- */

        /* Case: filtered person list, removeTag's person's index
         * within bounds of address book and person list -> removedTag
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        indexes.clear();
        indexes = personList(String.valueOf(INDEX_FIRST_PERSON.getOneBased()));
        assertTrue(INDEX_FIRST_PERSON.getZeroBased() < getModel().getFilteredPersonList().size());
        assertCommandSuccess(indexes, tags);

        /* Case: filtered person list, removeTag person's index
         * within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = RemoveTagCommand.COMMAND_WORD + " rm/"
                + invalidIndex + " t/friends";
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* ---------------- Performing removeTag operation while a person card is selected ------------------- */

        /* Case: removeTag the selected person
         * -> person list panel selects the person before the deleted person
         */
        showAllPersons();
        expectedModel = getModel();
        Index selectedIndex = Index.fromOneBased(getLastIndex(expectedModel).getOneBased() - 1);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased());
        selectPerson(selectedIndex);
        indexes.clear();
        indexes =  personList(String.valueOf(selectedIndex.getOneBased()));
        command = RemoveTagCommand.COMMAND_WORD + " rm/ "
                + selectedIndex.getOneBased() + "  t/friends ";
        personToRemoveTag.clear();
        personToRemoveTag = removePersonTag(expectedModel, indexes, tags);
        expectedResultMessage = String.format(MESSAGE_DELETE_TAG_SUCCESS,
                tags.toString()) + String.format("from %s", personToRemoveTag.toString());
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* -------------------------- Performing invalid delete operation ----------------------------- */

        /* Case: invalid index (0) -> rejected */
        command = RemoveTagCommand.COMMAND_WORD + " rm/ 0 " + "  t/friends ";
        assertCommandFailure(command, MESSAGE_INVALID_REMOVE_TAG_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = RemoveTagCommand.COMMAND_WORD + " rm/ -1 " + "  t/friends ";
        assertCommandFailure(command, MESSAGE_INVALID_REMOVE_TAG_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = RemoveTagCommand.COMMAND_WORD + " rm/ " + outOfBoundsIndex.getOneBased() + "  t/friends ";
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(RemoveTagCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_REMOVE_TAG_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(RemoveTagCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_REMOVE_TAG_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("REmoveTAg 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code tags} at the specified {@code indexes} in {@code model}'s address book.
     * @return the removed tags
     */
    private ObservableList<ReadOnlyPerson> removePersonTag(Model model, ArrayList<Index> indexes, Set<Tag> tags) {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        ObservableList<ReadOnlyPerson> personToRemoveTag = FXCollections.observableArrayList();
        for (Index i : indexes) {
            personToRemoveTag.add(lastShownList.get(i.getZeroBased()));
        }

        try {
            model.removeTag(personToRemoveTag, tags);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError(MESSAGE_DUPLICATE_PERSON);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Tag is invalid");
        }

        return personToRemoveTag;
    }

    /**
     * RemovesTags from the person at {@code removePersonTag} by
     * creating a default {@code RemoveTagCommand} using {@code removePersonTag} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see RemoveTagCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(ArrayList<Index> indexes, Set<Tag> tags) {
        Model expectedModel = getModel();
        ObservableList<ReadOnlyPerson> personToRemoveTag = removePersonTag(expectedModel, indexes, tags);
        String expectedResultMessage = (indexes.isEmpty())
                ? String.format(MESSAGE_DELETE_TAG_SUCCESS, tags.toString())
                : String.format(MESSAGE_DELETE_TAG_SUCCESS,
                tags.toString()) + String.format("from %s", personToRemoveTag.toString());
        String tagInitial = "";
        for (Tag tag : tags) {
            tagInitial += tag.tagName;
            tagInitial += " ";
        }
        String indexInitial = "";
        for (Index index : indexes) {
            indexInitial += String.valueOf(index.getOneBased());
            indexInitial += " ";
        }
        if (indexes.isEmpty()) {
            assertCommandSuccess(
                    RemoveTagCommand.COMMAND_WORD + "  t/"
                            + tagInitial, expectedModel, expectedResultMessage);
        } else {
            assertCommandSuccess(
                    RemoveTagCommand.COMMAND_WORD + " rm/"
                            + indexInitial + "t/" + tagInitial, expectedModel, expectedResultMessage);
        }
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
    //@@author

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see RemoveTagCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
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

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    //@@author sidhmads
    /**
     * Creates a ArrayList of Index
     * To be used for the RemoveTagCommand, as it is the input type
     */
    private ArrayList<Index> personList (String index) {
        String[] indexes = index.split("//s");
        ArrayList<Index> personList = new ArrayList<>();
        for (String number : indexes) {
            personList.add(Index.fromOneBased(Integer.parseInt(number)));
        }
        return personList;
    }

    /**
     * Creates a Set of Tags
     * To be used for the RemoveTagCommand, as it is the input type
     */
    private Set<Tag> tagList (String tags) {
        String[] tagNames = tags.split("//s");
        Set<Tag> tagSet = new HashSet<>();
        for (String tag : tagNames) {
            try {
                tagSet.add(new Tag(tag));
            } catch (IllegalValueException ive) {
                throw new AssertionError("tag is invalid");
            }

        }
        return tagSet;
    }
    //@@author
}

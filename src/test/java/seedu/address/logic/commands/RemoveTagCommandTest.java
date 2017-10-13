package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemoveTagCommand.
 */
public class RemoveTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_zeroKeywords_noTagsRemoved() throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_TAG_NOT_REMOVED);
        RemoveTagCommand command = prepareCommand(prepareTagList(" "));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
//        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
//        expectedModel.removeTagCommand (prepareTagList(" "));
        //  assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleKeywords_multipleTagsRemoved() throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS);
//        List<ReadOnlyPerson> expectedModel = model.getFilteredPersonList();
        RemoveTagCommand command = prepareCommand(prepareTagList("colleagues criminal"));
        assertCommandSuccess(command, model, expectedMessage,expectedModel);
    }

    @Test
    public void execute_tagsNotExist_noTagsRemoved() throws Exception {
        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_TAG_NOT_REMOVED);
        RemoveTagCommand command = prepareCommand(prepareTagList("nothing"));
//        List<ReadOnlyPerson> expectedModel = model.getFilteredPersonList();
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        RemoveTagCommand firstCommand = new RemoveTagCommand(prepareTagList(VALID_TAG_FRIEND));
        RemoveTagCommand secondCommand = new RemoveTagCommand(prepareTagList(VALID_TAG_HUSBAND));

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        RemoveTagCommand firstCommandCopy = new RemoveTagCommand(prepareTagList(VALID_TAG_FRIEND));
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different person -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    /**
     * Returns an {@code ArrayList<Tag>}
     */
    private ArrayList<Tag> prepareTagList(String userInput) throws Exception {
        ArrayList<Tag> tagToRemove = new ArrayList<>();
        String[] tagKeywords = userInput.split("\\s+");

        for (int i = 0; i < tagKeywords.length; i++) {
            tagToRemove.add(new Tag(tagKeywords[i]));
        }

        return tagToRemove;
    }

    /**
     * Returns an {@code RemoveTagCommand} with parameters {@code arraylist of tags}
     */
    private RemoveTagCommand prepareCommand(ArrayList<Tag> tagToRemove) {
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(tagToRemove);
        removeTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeTagCommand;
    }

//    /**
//     * Asserts that {@code command} is successfully executed, and<br>
//     * - the command feedback is equal to {@code expectedMessage}<br>
//     * - the {@code FilteredList<ReadOnlyPerson>} is not equal to {@code expectedList}<br>
//     * - the {@code AddressBook} in model is different after executing the {@code command}
//     */
//    private void assertCommandSuccess(RemoveTagCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList) throws Exception {
//        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
//        CommandResult commandResult = command.execute();
//
//        assertEquals(expectedMessage, commandResult.feedbackToUser);
//        assertNotEquals(expectedList, model.getFilteredPersonList());
//        assertNotEquals(expectedAddressBook, model.getAddressBook());
//    }
//
//    /**
//     * Asserts that {@code command} is not executed, and<br>
//     * - the command feedback is equal to {@code expectedMessage}<br>
//     * - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
//     * - the {@code AddressBook} in model remains the same after executing the {@code command}
//     */
//    private void assertCommandFailure(RemoveTagCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList) throws Exception {
//        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
//        CommandResult commandResult = command.execute();
//
//        assertEquals(expectedMessage, commandResult.feedbackToUser);
//        assertEquals(expectedList, model.getFilteredPersonList());
//        assertEquals(expectedAddressBook, model.getAddressBook());
//    }
}

package seedu.address.logic.commands;
//@@author Esilocke
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLLEAGUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EditTagCommand.MESSAGE_EDIT_TAG_SUCCESS;
import static seedu.address.logic.commands.EditTagCommand.MESSAGE_TAG_NOT_FOUND;
import static seedu.address.testutil.TypicalPersons.getTaglessAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;


public class EditTagCommandTest {
    private Model model = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());
    @Test
    public void noTagsPresent_throwsCommandException() throws IllegalValueException {
        Model taglessModel = new ModelManager(getTaglessAddressBook(), new UserPrefs());
        Model blankModel = new ModelManager(new AddressBook(), new UserPrefs());
        String absentTag = "notInAddressBook";
        EditTagCommand noPersonCommand = prepareCommand(VALID_TAG_FRIEND, absentTag, blankModel);
        EditTagCommand noTagsCommand = prepareCommand(VALID_TAG_FRIEND, absentTag, taglessModel);
        EditTagCommand absentTagCommand = prepareCommand(VALID_TAG_FRIEND, absentTag, model);

        // No people are in this address book
        assertCommandFailure(noPersonCommand, taglessModel, MESSAGE_TAG_NOT_FOUND);
        // All persons do not have tags
        assertCommandFailure(noTagsCommand, taglessModel, MESSAGE_TAG_NOT_FOUND);
        // No persons in address book has the required tag
        assertCommandFailure(absentTagCommand, model, MESSAGE_TAG_NOT_FOUND);
    }

    @Test
    public void editTagSubset_success() throws IllegalValueException, PersonNotFoundException {
        AddressBook testBook = prepareAddressBook();
        Model testModel = new ModelManager(testBook, new UserPrefs());
        EditTagCommand tagChangeColleagueToHusband = prepareCommand(VALID_TAG_COLLEAGUE, VALID_TAG_HUSBAND, testModel);
        String expectedMessage = String.format(MESSAGE_EDIT_TAG_SUCCESS, VALID_TAG_COLLEAGUE, VALID_TAG_HUSBAND);
        Tag husbandTag = new Tag(VALID_TAG_HUSBAND);
        Tag colleagueTag = new Tag(VALID_TAG_COLLEAGUE);

        // Attempt to change some Person objects
        Model expectedModel = new ModelManager(testModel.getAddressBook(), new UserPrefs());
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(Index.fromZeroBased(0));
        indices.add(Index.fromZeroBased(2));
        expectedModel.editTag(colleagueTag, husbandTag, indices);
        assertCommandSuccess(tagChangeColleagueToHusband, testModel, expectedMessage, expectedModel);
    }
    @Test
    public void editTagAll_success() throws IllegalValueException, PersonNotFoundException {
        AddressBook testBook = prepareAddressBook();
        Model testModel = new ModelManager(testBook, new UserPrefs());
        EditTagCommand tagChangeFriendToHusband = prepareCommand(VALID_TAG_FRIEND, VALID_TAG_HUSBAND, testModel);
        String expectedMessage = String.format(MESSAGE_EDIT_TAG_SUCCESS, VALID_TAG_FRIEND, VALID_TAG_HUSBAND);
        Tag friendTag = new Tag(VALID_TAG_FRIEND);
        Tag husbandTag = new Tag(VALID_TAG_HUSBAND);

        // Attempt to change all Person objects
        Model expectedModel = new ModelManager(testModel.getAddressBook(), new UserPrefs());
        ArrayList<Index> indices = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            indices.add(Index.fromZeroBased(i));
        }
        expectedModel.editTag(friendTag, husbandTag, indices);
        assertCommandSuccess(tagChangeFriendToHusband, testModel, expectedMessage, expectedModel);
    }
    /** Returns a new EditTagCommand with the parameters */
    public EditTagCommand prepareCommand(String toChange, String newValue, Model model) throws IllegalValueException {
        Tag changedTag = new Tag(toChange);
        Tag newTag = new Tag(newValue);
        EditTagCommand editTagCommand = new EditTagCommand(changedTag, newTag);
        editTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editTagCommand;
    }
    /** Returns a pre-made Address Book for testing purposes */
    private AddressBook prepareAddressBook() throws DuplicatePersonException {
        ReadOnlyPerson alice = new PersonBuilder().withName("Alice Pauline")
                .withTags(VALID_TAG_FRIEND, VALID_TAG_COLLEAGUE).build();
        ReadOnlyPerson bernice = new PersonBuilder().withName("Bernice Applecut")
                .withTags(VALID_TAG_FRIEND).build();
        ReadOnlyPerson clarice = new PersonBuilder().withName("Clarice Fenderbunt")
                .withTags(VALID_TAG_FRIEND, VALID_TAG_COLLEAGUE).build();
        ReadOnlyPerson denise = new PersonBuilder().withName("Denise Lieselocke")
                .withTags(VALID_TAG_FRIEND).build();
        ArrayList<ReadOnlyPerson> toAdd = new ArrayList<>(Arrays.asList(alice, bernice, clarice, denise));
        AddressBook preparedBook = new AddressBook();
        for (ReadOnlyPerson r : toAdd) {
            preparedBook.addPerson(r);
        }
        return preparedBook;
    }
}

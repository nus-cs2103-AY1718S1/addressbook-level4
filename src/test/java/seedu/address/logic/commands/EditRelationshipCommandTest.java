package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithRelationships;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.relationship.ConfidenceEstimate;
import seedu.address.model.relationship.Relationship;
import seedu.address.testutil.StorageStub;
import seedu.address.testutil.TypicalIndexes;

//@@author joanneong
/**
 * Contains integration tests (interaction with the Model) and unit tests for EditRelationshipCommand.
 */
public class EditRelationshipCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookWithRelationships(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() throws Exception {
        try {
            int lengthOfPersonList = model.getFilteredPersonList().size();
            Index firstPerson = Index.fromOneBased(lengthOfPersonList - 1);
            Index secondPerson = Index.fromOneBased(lengthOfPersonList);

            Name newName = new Name("friends");
            ConfidenceEstimate newConfidenceEstimate = new ConfidenceEstimate(10);

            EditRelationshipCommand editRelationshipCommand = prepareCommand(firstPerson, secondPerson,
                    newName, newConfidenceEstimate);

            String expectedMessage = String.format(EditRelationshipCommand.MESSAGE_EDIT_RELATIONSHIP_SUCCESS,
                    firstPerson, secondPerson);

            ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.editRelationship(firstPerson, secondPerson, newName, newConfidenceEstimate);

            assertCommandSuccess(editRelationshipCommand, model, expectedMessage, expectedModel);
        } catch (IllegalValueException ive) {
            fail("This is not supposed to fail");
        }
    }

    @Test
    public void execute_nameFieldSpecified_success() throws Exception {
        try {
            int lengthOfPersonList = model.getFilteredPersonList().size();
            Index firstPerson = Index.fromOneBased(lengthOfPersonList - 1);
            Index secondPerson = Index.fromOneBased(lengthOfPersonList);

            Name newName = new Name("friends");
            ReadOnlyPerson fromPerson = model.getFilteredPersonList().get(firstPerson.getZeroBased());
            Relationship relationship = fromPerson.getRelationships().iterator().next();
            ConfidenceEstimate originalConfidenceEstimate = relationship.getConfidenceEstimate();

            EditRelationshipCommand editRelationshipCommand = prepareCommand(firstPerson, secondPerson,
                    newName, originalConfidenceEstimate);

            String expectedMessage = String.format(EditRelationshipCommand.MESSAGE_EDIT_RELATIONSHIP_SUCCESS,
                    firstPerson, secondPerson);

            ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.editRelationship(firstPerson, secondPerson, newName, originalConfidenceEstimate);

            assertCommandSuccess(editRelationshipCommand, model, expectedMessage, expectedModel);
        } catch (IllegalValueException ive) {
            fail("This is not supposed to fail");
        }
    }

    @Test
    public void execute_confidenceEstimateFieldSpecified_success() throws Exception {
        try {
            int lengthOfPersonList = model.getFilteredPersonList().size();
            Index firstPerson = Index.fromOneBased(lengthOfPersonList - 1);
            Index secondPerson = Index.fromOneBased(lengthOfPersonList);

            ConfidenceEstimate newConfidenceEstimate = new ConfidenceEstimate(90);
            ReadOnlyPerson fromPerson = model.getFilteredPersonList().get(firstPerson.getZeroBased());
            Relationship relationship = fromPerson.getRelationships().iterator().next();
            Name name = relationship.getName();

            EditRelationshipCommand editRelationshipCommand = prepareCommand(firstPerson, secondPerson,
                   name, newConfidenceEstimate);

            String expectedMessage = String.format(EditRelationshipCommand.MESSAGE_EDIT_RELATIONSHIP_SUCCESS,
                    firstPerson, secondPerson);

            ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.editRelationship(firstPerson, secondPerson, name, newConfidenceEstimate);

            assertCommandSuccess(editRelationshipCommand, model, expectedMessage, expectedModel);
        } catch (IllegalValueException ive) {
            fail("This is not supposed to fail");
        }
    }

    @Test
    public void execute_noFieldsSpecified_success() throws Exception {
        try {
            int lengthOfPersonList = model.getFilteredPersonList().size();
            Index firstPerson = Index.fromOneBased(lengthOfPersonList - 1);
            Index secondPerson = Index.fromOneBased(lengthOfPersonList);

            ReadOnlyPerson fromPerson = model.getFilteredPersonList().get(firstPerson.getZeroBased());
            Relationship relationship = fromPerson.getRelationships().iterator().next();
            Name name = relationship.getName();
            ConfidenceEstimate cE = relationship.getConfidenceEstimate();

            EditRelationshipCommand editRelationshipCommand = prepareCommand(firstPerson, secondPerson,
                    name, cE);

            String expectedMessage = String.format(EditRelationshipCommand.MESSAGE_EDIT_RELATIONSHIP_SUCCESS,
                    firstPerson, secondPerson);

            ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.editRelationship(firstPerson, secondPerson, name, cE);

            assertCommandSuccess(editRelationshipCommand, model, expectedMessage, expectedModel);
        } catch (IllegalValueException ive) {
            fail("This is not supposed to fail");
        }
    }

    @Test
    public void execute_invalidFromPersonIndex_throwsCommandException() throws Exception {
        try {
            int lengthOfPersonList = model.getFilteredPersonList().size();
            Index firstPerson = Index.fromOneBased(lengthOfPersonList - 1);
            Index outOfBoundIndex = Index.fromOneBased(lengthOfPersonList + 1);
            Name newName = new Name("friends");
            ConfidenceEstimate newConfidenceEstimate = new ConfidenceEstimate(90);

            EditRelationshipCommand editRelationshipCommand = prepareCommand(firstPerson, outOfBoundIndex,
                    newName, newConfidenceEstimate);

            assertCommandFailure(editRelationshipCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        } catch (IllegalValueException ive) {
            fail("This is not supposed to fail");
        }
    }

    @Test
    public void execute_invalidToPersonIndex_throwsCommandException() throws Exception {
        try {
            int lengthOfPersonList = model.getFilteredPersonList().size();
            Index outOfBoundIndex = Index.fromOneBased(lengthOfPersonList + 1);
            Index secondPerson = Index.fromOneBased(lengthOfPersonList - 1);
            Name newName = new Name("friends");
            ConfidenceEstimate newConfidenceEstimate = new ConfidenceEstimate(90);

            EditRelationshipCommand editRelationshipCommand = prepareCommand(outOfBoundIndex, secondPerson,
                    newName, newConfidenceEstimate);

            assertCommandFailure(editRelationshipCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        } catch (IllegalValueException ive) {
            fail("This is not supposed to fail");
        }
    }

    @Test
    public void execute_invalidRelationship_throwsCommandException() throws Exception {
        try {
            Name name = new Name("friends");
            ConfidenceEstimate confidenceEstimate = new ConfidenceEstimate(90);

            EditRelationshipCommand editRelationshipCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                    name, confidenceEstimate);

            assertCommandFailure(editRelationshipCommand, model, Messages.MESSAGE_RELATIONSHIP_NOT_FOUND);
        } catch (IllegalValueException ive) {
            fail("This is not supposed to fail");
        }
    }

    @Test
    public void equals() {
        EditRelationshipCommand editRelationshipFirstCommand =
                new EditRelationshipCommand(TypicalIndexes.INDEX_FIRST_PERSON, TypicalIndexes.INDEX_SECOND_PERSON,
                        Name.UNSPECIFIED, ConfidenceEstimate.UNSPECIFIED);
        EditRelationshipCommand editRelationshipSecondCommand =
                new EditRelationshipCommand(TypicalIndexes.INDEX_SECOND_PERSON, TypicalIndexes.INDEX_THIRD_PERSON,
                        Name.UNSPECIFIED, ConfidenceEstimate.UNSPECIFIED);

        // same object -> returns true
        assertTrue(editRelationshipFirstCommand.equals(editRelationshipFirstCommand));

        // same values -> returns true
        EditRelationshipCommand editRelationshipFirstCommandCopy =
                new EditRelationshipCommand(TypicalIndexes.INDEX_FIRST_PERSON, TypicalIndexes.INDEX_SECOND_PERSON,
                Name.UNSPECIFIED, ConfidenceEstimate.UNSPECIFIED);
        assertTrue(editRelationshipFirstCommand.equals(editRelationshipFirstCommandCopy));

        // different types -> returns false
        assertFalse(editRelationshipFirstCommand.equals(1));

        // null -> returns false
        assertFalse(editRelationshipFirstCommand.equals(null));

        // different from person and to person -> returns false
        assertFalse(editRelationshipFirstCommand.equals(editRelationshipSecondCommand));
    }

    /**
     * Returns an {@code EditRelationshipCommand} with the parameters fromPerson and toPerson indexes.
     */
    private EditRelationshipCommand prepareCommand(Index fromPerson, Index toPerson, Name name, ConfidenceEstimate ce) {
        EditRelationshipCommand editRelationshipCommand = new EditRelationshipCommand(fromPerson, toPerson, name, ce);
        editRelationshipCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return editRelationshipCommand;
    }
}

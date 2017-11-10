package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.INDEX_SECOND_PERSON;
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
import seedu.address.model.person.Name;
import seedu.address.model.relationship.ConfidenceEstimate;
import seedu.address.model.relationship.RelationshipDirection;
import seedu.address.testutil.StorageStub;

//@@author wenmogu
public class DeleteRelationshipCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private final AddRelationshipCommand addRelationshipCommand = new AddRelationshipCommand(INDEX_FIRST_PERSON,
            INDEX_SECOND_PERSON, RelationshipDirection.DIRECTED, Name.UNSPECIFIED, ConfidenceEstimate.UNSPECIFIED);
    private final AddRelationshipCommand addRelationshipCommandOnExpectedModel = new AddRelationshipCommand(
            INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, RelationshipDirection.DIRECTED, Name.UNSPECIFIED,
            ConfidenceEstimate.UNSPECIFIED);

    private final Index fromPerson = INDEX_FIRST_PERSON;
    private final Index toPerson = INDEX_SECOND_PERSON;

    @Before
    public void setUp() {
        addRelationshipCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        addRelationshipCommandOnExpectedModel.setData(expectedModel, new CommandHistory(), new UndoRedoStack(),
                new StorageStub());
    }

    @Test
    public void execute_validIndexNoRelationshipPreviously_success() throws Exception {
        DeleteRelationshipCommand deleteRelationshipCommand = prepareCommand(fromPerson, toPerson);

        String expectedMessage = String.format(DeleteRelationshipCommand.MESSAGE_DELETE_RELATIONSHIP_SUCCESS,
                fromPerson.toString(), toPerson.toString());

        assertCommandSuccess(deleteRelationshipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexNoRelationshipPreviously_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteRelationshipCommand deleteRelationshipCommand = prepareCommand(outOfBoundIndex, toPerson);

        assertCommandFailure(deleteRelationshipCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexHaveRelationshipIndexInOrder_success() throws Exception {
        DeleteRelationshipCommand deleteRelationshipCommand = prepareCommand(fromPerson, toPerson);
        String expectedMessage = String.format(DeleteRelationshipCommand.MESSAGE_DELETE_RELATIONSHIP_SUCCESS,
                fromPerson.toString(), toPerson.toString());

        addRelationshipCommand.execute();
        assertCommandSuccess(deleteRelationshipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexHaveRelationshipInputIndexNotInOrder_success() throws Exception {
        DeleteRelationshipCommand deleteRelationshipCommand = prepareCommand(toPerson, fromPerson);
        String expectedMessage = String.format(DeleteRelationshipCommand.MESSAGE_DELETE_RELATIONSHIP_SUCCESS,
                toPerson.toString(), fromPerson.toString());

        addRelationshipCommand.execute();
        assertCommandSuccess(deleteRelationshipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexRelationshipToBeDeletedDoesntExist_success() throws Exception {
        Index thirdPerson = Index.fromOneBased(3);

        DeleteRelationshipCommand deleteRelationshipCommand = prepareCommand(thirdPerson, fromPerson);
        String expectedMessage = String.format(DeleteRelationshipCommand.MESSAGE_DELETE_RELATIONSHIP_SUCCESS,
                thirdPerson.toString(), fromPerson.toString());

        addRelationshipCommand.execute();
        addRelationshipCommandOnExpectedModel.execute();
        assertCommandSuccess(deleteRelationshipCommand, model, expectedMessage, expectedModel);
    }

    private DeleteRelationshipCommand prepareCommand(Index firstIndex, Index secondIndex) {
        DeleteRelationshipCommand deleteRelationshipCommand = new DeleteRelationshipCommand(firstIndex, secondIndex);
        deleteRelationshipCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return deleteRelationshipCommand;
    }
}

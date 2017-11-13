package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
public class AddRelationshipCommandTest {
    private static final ConfidenceEstimate DEFAULT_CE = ConfidenceEstimate.UNSPECIFIED;
    private static final Name DEFAULT_NAME = Name.UNSPECIFIED;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        RelationshipDirection direction = RelationshipDirection.DIRECTED;

        AddRelationshipCommand addRelationshipCommand = prepareCommand(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, direction);

        String expectedMessage = String.format(addRelationshipCommand.MESSAGE_ADD_RELATIONSHIP_SUCCESS,
                INDEX_SECOND_PERSON.toString(), INDEX_FIRST_PERSON.toString(), direction.toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addRelationship(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, direction, DEFAULT_NAME, DEFAULT_CE);

        assertCommandSuccess(addRelationshipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddRelationshipCommand addRelationshipCommand = prepareCommand(outOfBoundIndex,
                INDEX_SECOND_PERSON, RelationshipDirection.UNDIRECTED);

        assertCommandFailure(addRelationshipCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateRelationship_throwsCommandException() throws Exception {
        RelationshipDirection direction = RelationshipDirection.DIRECTED;

        AddRelationshipCommand addRelationshipCommand = prepareCommand(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, direction);
        addRelationshipCommand.execute();

        assertCommandFailure(addRelationshipCommand, model, AddRelationshipCommand.MESSAGE_DUPLICATED_RELATIONSHIP);
    }

    @Test
    public void execute_addRelationshipOfaDifferentDirection_throwsCommandException() throws Exception {
        AddRelationshipCommand addRelationshipCommand = prepareCommand(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, RelationshipDirection.DIRECTED);
        addRelationshipCommand.execute();

        AddRelationshipCommand addRelationshipCommand1 = prepareCommand(INDEX_SECOND_PERSON,
                INDEX_FIRST_PERSON, RelationshipDirection.DIRECTED);
        AddRelationshipCommand addRelationshipCommand2 = prepareCommand(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, RelationshipDirection.UNDIRECTED);
        AddRelationshipCommand addRelationshipCommand3 = prepareCommand(INDEX_SECOND_PERSON,
                INDEX_FIRST_PERSON, RelationshipDirection.UNDIRECTED);

        assertCommandFailure(addRelationshipCommand1, model, AddRelationshipCommand.MESSAGE_DUPLICATED_RELATIONSHIP);
        assertCommandFailure(addRelationshipCommand2, model, AddRelationshipCommand.MESSAGE_DUPLICATED_RELATIONSHIP);
        assertCommandFailure(addRelationshipCommand3, model, AddRelationshipCommand.MESSAGE_DUPLICATED_RELATIONSHIP);
    }


    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private AddRelationshipCommand prepareCommand(Index fromPersonIndex, Index toPersonIndex,
                                                  RelationshipDirection direction) {
        AddRelationshipCommand command = new AddRelationshipCommand(fromPersonIndex, toPersonIndex, direction,
                DEFAULT_NAME, DEFAULT_CE);
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return command;
    }
}

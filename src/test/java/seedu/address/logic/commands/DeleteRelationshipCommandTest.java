package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.StorageStub;
import seedu.address.testutil.TypicalIndexes;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class DeleteRelationshipCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Index fromPerson = TypicalIndexes.INDEX_FIRST_PERSON;
        Index toPerson = TypicalIndexes.INDEX_SECOND_PERSON;
        DeleteRelationshipCommand deleteRelationshipCommand = prepareCommand(fromPerson, toPerson);

        String expectedMessage = String.format(DeleteRelationshipCommand.MESSAGE_DELETE_RELATIONSHIP_SUCCESS,
                fromPerson.toString(), toPerson.toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(deleteRelationshipCommand, model, expectedMessage, expectedModel);
    }

    private DeleteRelationshipCommand prepareCommand(Index firstIndex, Index secondIndex) {
        DeleteRelationshipCommand deleteRelationshipCommand = new DeleteRelationshipCommand(firstIndex, secondIndex);
        deleteRelationshipCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return deleteRelationshipCommand;
    }
}

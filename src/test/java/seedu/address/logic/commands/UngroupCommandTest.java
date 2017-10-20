package seedu.address.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_FAMILY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.UngroupCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UngroupCommand.
 */
public class UngroupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws Exception {
        final String group = "Some group";

        assertCommandFailure(prepareCommand(INDEX_FIRST_PERSON, group), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), group));
    }

    @Test
    public void equals() {
        final UngroupCommand standardCommand = new UngroupCommand(INDEX_FIRST_PERSON, VALID_GROUP_NAME_CS2103);

        // same values -> returns true
        UngroupCommand commandWithSameValues = new UngroupCommand(INDEX_FIRST_PERSON, VALID_GROUP_NAME_CS2103);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UngroupCommand(INDEX_SECOND_PERSON, VALID_GROUP_NAME_CS2103)));

        // different groups -> returns false
        assertFalse(standardCommand.equals(new UngroupCommand(INDEX_FIRST_PERSON, VALID_GROUP_NAME_FAMILY)));
    }

    /**
     * Returns an {@code UngroupCommand} with parameters {@code index} and {@code group}
     */
    private UngroupCommand prepareCommand(Index index, String group) {
        UngroupCommand ungroupCommand = new UngroupCommand(index, group);
        ungroupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return ungroupCommand;
    }
}
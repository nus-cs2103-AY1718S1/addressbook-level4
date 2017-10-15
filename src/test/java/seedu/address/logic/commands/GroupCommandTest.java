package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_FAMILY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.GroupCommand.MESSAGE_ARGUMENTS;
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
import seedu.address.model.group.Group;

/**
 * Contains integration tests (interaction with the Model) and unit tests for GroupCommand.
 */
public class GroupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws Exception {
        final String groupName = "Some group name";

        assertCommandFailure(prepareCommand(INDEX_FIRST_PERSON, new Group(groupName)), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), groupName));
    }

    @Test
    public void equals() throws Exception {
        final GroupCommand standardCommand = new GroupCommand(INDEX_FIRST_PERSON, new Group(VALID_GROUP_NAME_FAMILY));

        // same values -> returns true
        GroupCommand commandWithSameValues = new GroupCommand(INDEX_FIRST_PERSON, new Group(VALID_GROUP_NAME_FAMILY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new GroupCommand(INDEX_SECOND_PERSON, new Group(VALID_GROUP_NAME_FAMILY))));

        // different remarks -> returns false
        assertFalse(standardCommand.equals(new GroupCommand(INDEX_FIRST_PERSON, new Group(VALID_GROUP_NAME_CS2103))));
    }

    /**
     * Returns an {@code GroupCommand} with parameters {@code index} and {@code group}
     */
    private GroupCommand prepareCommand(Index index, Group group) {
        GroupCommand groupCommand = new GroupCommand(index, group);
        groupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return groupCommand;
    }
}
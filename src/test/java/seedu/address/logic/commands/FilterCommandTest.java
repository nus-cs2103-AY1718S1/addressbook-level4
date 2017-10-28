package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Group;
import seedu.address.model.person.GroupContainsKeywordsPredicate;

public class FilterCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    //@@author grantcm
    @Test
    public void equals() {
        //Test same equality
        FilterGroupCommand filterCommand = new FilterGroupCommand("");
        assertTrue(filterCommand.equals(filterCommand));

        //Test value equality
        FilterGroupCommand copy = new FilterGroupCommand("");
        assertTrue(filterCommand.equals(copy));

        //Text unequal values
        assertFalse(filterCommand.equals(1));
        assertFalse(filterCommand.equals("False"));
    }

    @Test
    public void execute_filterCommand() throws Exception {
        FilterGroupCommand noGroup = new FilterGroupCommand("");
        noGroup.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult result = noGroup.executeUndoableCommand();
        assertEquals(result.feedbackToUser, FilterGroupCommand.MESSAGE_GROUP_DOESNT_EXIST);

        model.addGroup(new Group("None"));

        FilterGroupCommand groupExists = new FilterGroupCommand("None");
        groupExists.setData(model, new CommandHistory(), new UndoRedoStack());
        result = groupExists.executeUndoableCommand();

        assertEquals(result.feedbackToUser, model.getFilteredPersonList().size()+" persons listed!");
    }
    //@@author
}

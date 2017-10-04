package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Remark;

public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(),new UserPrefs());

    @Test
    public void execute() throws Exception {
    }


    private RemarkCommand prepareCommand(Index index, String remark){
        RemarkCommand remarkCommand = new RemarkCommand(index,new Remark(remark));
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}

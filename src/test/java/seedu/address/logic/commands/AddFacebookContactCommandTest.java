package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class AddFacebookContactCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute() throws Exception {
        //assertCommandFailure(prepareCommand(), model, MESSAGE_NOT_IMPLEMENTED_YET);
    }

    /**
     * Returns an {@code AddFacebookContactCommand}.
     */

    private AddFacebookContactCommand prepareCommand() {
        AddFacebookContactCommand addFacebookContactCommand = new AddFacebookContactCommand();
        addFacebookContactCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addFacebookContactCommand;
    }
}

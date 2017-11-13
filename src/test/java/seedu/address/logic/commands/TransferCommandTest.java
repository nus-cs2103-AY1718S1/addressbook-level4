//@@author keloysiusmak
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.TransferCommand.MESSAGE_TRANSFER_SUCCESS;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Config;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Logic;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.UiManager;


public class TransferCommandTest {

    private TransferCommand transferCommand;
    private CommandHistory history;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        history = new CommandHistory();
        transferCommand = new TransferCommand();
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        transferCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
    }

    @Test
    public void execute_transfer_success() {
        CommandResult result = transferCommand.execute();
        assertEquals(MESSAGE_TRANSFER_SUCCESS, result.feedbackToUser);
    }
}

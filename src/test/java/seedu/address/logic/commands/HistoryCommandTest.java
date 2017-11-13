package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.user.UserCreds;
import seedu.address.model.user.UserPrefs;

public class HistoryCommandTest {
    private HistoryCommand historyCommand;
    private CommandHistory history;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        model.getUserCreds().validateCurrentSession(); // validate user
        history = new CommandHistory();
        historyCommand = new HistoryCommand();
        historyCommand.setData(model, history, new UndoRedoStack());
    }

    //@@author zenghou-unused
    /** Initially used to test for invalid login since the check was done by command. However, this check will be
     * conducted by LogicManager now. As such, all commands will be executed.
     */
    public void execute_invalidUser_failure() throws Exception {
        String userNotLoggedInMessage = "Invalid session! Please log in first! \n"
                + LoginCommand.MESSAGE_USAGE;

        Model userCredsNotValidatedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UserCreds());

        HistoryCommand historyCommand = new HistoryCommand();

        historyCommand.setData(userCredsNotValidatedModel, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(historyCommand, userCredsNotValidatedModel,
                userNotLoggedInMessage);
    }
    //@@author

    @Test
    public void execute() {
        assertCommandResult(historyCommand, HistoryCommand.MESSAGE_NO_HISTORY);

        String command1 = "clear";
        history.add(command1);
        assertCommandResult(historyCommand, String.format(HistoryCommand.MESSAGE_SUCCESS, command1));

        String command2 = "randomCommand";
        String command3 = "select 1";
        history.add(command2);
        history.add(command3);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                String.join("\n", command3, command2, command1));

        assertCommandResult(historyCommand, expectedMessage);
    }

    /**
     * Asserts that the result message from the execution of {@code historyCommand} equals to {@code expectedMessage}
     */
    private void assertCommandResult(HistoryCommand historyCommand, String expectedMessage) {
        try {
            assertEquals(expectedMessage, historyCommand.execute().feedbackToUser);
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }
}

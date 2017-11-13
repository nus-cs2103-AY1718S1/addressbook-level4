package seedu.address.logic.commands;

//@@author quanle1994

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalUsers.getTypicalAccount;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Config;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Logic;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.User;
import seedu.address.ui.UiManager;

/**
 * Contains integration tests (interaction with the Model) for {@code LockCommand}.
 */
public class LockCommandIntegrationTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new EventBook(), new UserPrefs(), getTypicalAccount(),
                new Config());
    }

    @Test
    public void execute_duplicateUserException() throws Exception {
        User invalidUser = new User("a", "555555555555555555", "e");

        thrown.expect(CommandException.class);
        thrown.expectMessage(LockCommand.MESSAGE_EXISTING_USER);

        prepareCommand(invalidUser, model).execute();
    }

    @Test
    public void execute_newUser_success() throws Exception {
        User validUser = new User("e", "555555555555555555", "e");

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getEventBook(), new UserPrefs(),
                model.getAccount(), new Config());
        expectedModel.persistUserAccount(validUser);

        assertCommandSuccess(prepareCommand(validUser, model), model,
                String.format(LockCommand.MESSAGE_SUCCESS, validUser), expectedModel);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code person} into the {@code model}.
     */
    private LockCommand prepareCommand(User user, Model model) {
        LockCommand command = new LockCommand(user.getUserId(), user.getPassword());
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return command;
    }
}

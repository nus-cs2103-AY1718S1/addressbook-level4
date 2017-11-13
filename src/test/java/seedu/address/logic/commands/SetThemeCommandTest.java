//@@author keloysiusmak
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertConfigCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertConfigDiffCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Config;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Account;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class SetThemeCommandTest {

    private Config config;
    private Config expectedConfig;
    private SetThemeCommand setThemeCommand;
    private SetThemeCommand setThemeCommand2;
    private SetThemeCommand setThemeCommand3;
    private SetThemeCommand setThemeCommand4;
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(), new
            Account(), new Config());

    @Before
    public void setUp() {
        config = new Config();
        expectedConfig = new Config();

        setThemeCommand = new SetThemeCommand();

        UserPrefs userPrefs = new UserPrefs();
        Ui ui = null;
        Logic logic = new LogicManager(model, userPrefs, config, ui);
        ui = new UiManager(logic, config, userPrefs);
        logic.setUi(ui);
        setThemeCommand.setData(model, new CommandHistory(), new UndoRedoStack(), config, ui);
        setThemeCommand2 = new SetThemeCommand("nonsense");
        setThemeCommand2.setData(model, new CommandHistory(), new UndoRedoStack(), config, ui);
        setThemeCommand3 = new SetThemeCommand("winter");
        setThemeCommand3.setData(model, new CommandHistory(), new UndoRedoStack(), config, ui);
        setThemeCommand4 = new SetThemeCommand("summer");
        setThemeCommand4.setData(model, new CommandHistory(), new UndoRedoStack(), config, ui);
    }

    @Test
    public void execute_defaultTheme() {
        assertConfigCommandSuccess(setThemeCommand, config,
                String.format(SetThemeCommand.MESSAGE_CHANGED_THEME_SUCCESS, "summer"), expectedConfig);
    }

    @Test
    public void execute_sameTheme() {
        assertConfigCommandSuccess(setThemeCommand, config,
                String.format(SetThemeCommand.MESSAGE_CHANGED_THEME_SUCCESS, "summer"), expectedConfig);
    }

    @Test
    public void execute_winterTheme() throws Exception {
        assertConfigDiffCommandSuccess(setThemeCommand3, config,
                String.format(SetThemeCommand.MESSAGE_CHANGED_THEME_SUCCESS, "winter"), expectedConfig);
    }

    @Test
    public void execute_nonsenseTheme() {
        assertConfigCommandSuccess(setThemeCommand2, config,
                String.format(Messages.MESSAGE_WRONG_THEME, "nonsense"), expectedConfig);
    }
}

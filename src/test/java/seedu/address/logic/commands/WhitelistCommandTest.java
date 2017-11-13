package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstWhitelistedPersonOnly;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.ListObserver;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.CommandTest;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author jaivigneshvenugopal
public class WhitelistCommandTest extends CommandTest {

    private Model expectedModel;
    private WhitelistCommand whitelistCommand;

    @Override
    @Before
    public void setUp() {
        ListObserver.init(model);
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        whitelistCommand = new WhitelistCommand();
        whitelistCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        expectedModel.getFilteredWhitelistedPersonList();
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        model.setCurrentListName("whitelist");
        assertCommandSuccess(whitelistCommand, model,
                ListObserver.WHITELIST_NAME_DISPLAY_FORMAT + whitelistCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstWhitelistedPersonOnly(model);
        assertCommandSuccess(whitelistCommand, model,
                ListObserver.WHITELIST_NAME_DISPLAY_FORMAT + whitelistCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstWhitelistedPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class WhitelistCommandTest {

    private Model model;
    private Model expectedModel;
    private WhitelistCommand whitelistCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        whitelistCommand = new WhitelistCommand();
        whitelistCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        expectedModel.getFilteredWhitelistedPersonList();
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(whitelistCommand, model, whitelistCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstWhitelistedPersonOnly(model);
        assertCommandSuccess(whitelistCommand, model, whitelistCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

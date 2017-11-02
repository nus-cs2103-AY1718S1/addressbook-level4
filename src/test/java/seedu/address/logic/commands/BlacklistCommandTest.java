package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstBlacklistedPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.ListObserver;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class BlacklistCommandTest {

    private Model model;
    private Model expectedModel;
    private BlacklistCommand blacklistCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        blacklistCommand = new BlacklistCommand();
        blacklistCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        model.setCurrentListName("blacklist");
        assertCommandSuccess(blacklistCommand, model, ListObserver.BLACKLIST_NAME_DISPLAY_FORMAT
                + blacklistCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        model.setCurrentListName("blacklist");
        showFirstBlacklistedPersonOnly(model);
        assertCommandSuccess(blacklistCommand, model, ListObserver.BLACKLIST_NAME_DISPLAY_FORMAT
                + blacklistCommand.MESSAGE_SUCCESS, expectedModel);
    }
}


package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstBlacklistedPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
public class BlacklistCommandTest extends CommandTest {

    private Model expectedModel;
    private BlacklistCommand blacklistCommand;

    @Before
    @Override
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ListObserver.init(model);
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        blacklistCommand = new BlacklistCommand();
        blacklistCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        model.setCurrentListName("blacklist");
        assertCommandSuccess(blacklistCommand, model, ListObserver.BLACKLIST_NAME_DISPLAY_FORMAT
                + BlacklistCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        model.setCurrentListName("blacklist");
        showFirstBlacklistedPersonOnly(model);
        assertCommandSuccess(blacklistCommand, model, ListObserver.BLACKLIST_NAME_DISPLAY_FORMAT
                + BlacklistCommand.MESSAGE_SUCCESS, expectedModel);
    }
}


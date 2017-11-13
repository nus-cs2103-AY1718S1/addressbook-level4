package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_EXPORT_BOOK_SUCCESS;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Config;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Logic;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.exceptions.DuplicateUserException;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.XmlAccountStorage;
import seedu.address.storage.XmlAddressBookStorage;
import seedu.address.storage.XmlEventBookStorage;
import seedu.address.ui.UiManager;

//@@author kaiyu92
public class ExportCommandTest {

    private ExportCommand exportCommandForAddressBook;
    private ExportCommand exportCommandForEventBook;
    private CommandHistory history;

    @Before
    public void setUp() {

        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Storage userStorage = new StorageManager(
                new XmlAddressBookStorage(userPrefs.getAddressBookFilePath(), userPrefs.getAddressbookExportedPath(),
                        userPrefs.getAddressbookHeader()),
                new XmlEventBookStorage(userPrefs.getEventBookFilePath(), userPrefs.getEventbookExportedPath(),
                        userPrefs.getEventbookHeader()),
                new JsonUserPrefsStorage(config.getUserPrefsFilePath()),
                new XmlAccountStorage(userPrefs.getAccountFilePath()));
        Model model = new ModelManager();
        model.setUserStorage(userStorage);
        history = new CommandHistory();
        exportCommandForAddressBook = new ExportCommand("addressbook");
        exportCommandForEventBook = new ExportCommand("eventbook");


        Logic logic = null;
        exportCommandForAddressBook.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        exportCommandForEventBook.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
    }

    @Test
    public void execute_exportAddressBook_success() throws CommandException, DuplicateUserException {
        CommandResult result = exportCommandForAddressBook.execute();
        assertEquals(MESSAGE_EXPORT_BOOK_SUCCESS, result.feedbackToUser);
    }

    @Test
    public void execute_exportEventBook_success() throws CommandException, DuplicateUserException {
        CommandResult result = exportCommandForEventBook.execute();
        assertEquals(MESSAGE_EXPORT_BOOK_SUCCESS, result.feedbackToUser);
    }
}

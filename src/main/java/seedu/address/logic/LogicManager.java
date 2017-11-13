package seedu.address.logic;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.PasswordCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.storage.SecurityManager;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private final UndoRedoStack undoRedoStack;

    private boolean isLocked;

    public LogicManager(Model model) {
        this.model = model;
        this.history = new CommandHistory();
        this.addressBookParser = new AddressBookParser();
        this.undoRedoStack = new UndoRedoStack();
        this.isLocked = SecurityManager.passExists();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        if (isLocked) {
            logger.info("----------------[USER COMMAND][Password Entered]");
            tryUnlock(commandText);
            if (isLocked) {
                throw new CommandException(PasswordCommand.MESSAGE_WRONG_PASS);
            } else {
                return new CommandResult("Welcome");
            }
        }

        //makes sure password does not get leaked to the logs
        if (commandText.toLowerCase().contains(PasswordCommand.COMMAND_ALIAS)) {
            logger.info("----------------[USER COMMAND][" + PasswordCommand.class.getSimpleName() + "]");
        } else {
            logger.info("----------------[USER COMMAND][" + commandText + "]");
        }

        try {
            Command command = addressBookParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack);
            CommandResult result = command.execute();
            undoRedoStack.push(command);
            return result;
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public boolean getIsLocked() {
        return isLocked;
    }
    //@@author syy94

    /**
     * Takes in a password and checks with SecurityManager if the application should be unlocked.
     */
    private void tryUnlock(String commandText) throws CommandException {
        try {
            isLocked = !SecurityManager.unlock(commandText);
        } catch (IOException e) {
            throw new CommandException("Unable to open password file");
        } catch (NoSuchAlgorithmException e) {
            throw new CommandException(PasswordCommand.MESSAGE_NO_SUCH_ALGORITHM);
        }
    }
    //@@author

    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}

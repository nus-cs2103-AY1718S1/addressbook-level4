package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private final UndoRedoStack undoRedoStack;
    private boolean isLoggedIn = false;

    public LogicManager(Model model) {
        this.model = model;
        this.history = new CommandHistory();
        this.addressBookParser = new AddressBookParser();
        this.undoRedoStack = new UndoRedoStack();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        CommandResult result;
        if (!isLoggedIn) {
            if (isCorrectPassword(commandText)) {
                result = new CommandResult("Login successful! Welcome to H.M.U v1.2");
            } else {
                result = new CommandResult("Invalid Credentials! Please try again.");
            }
        } else {

            logger.info("----------------[USER COMMAND][" + commandText + "]");
            try {
                Command command = addressBookParser.parseCommand(commandText);
                command.setData(model, history, undoRedoStack);
                result = command.execute();
                undoRedoStack.push(command);
                return result;
            } finally {
                history.add(commandText);
            }
        }
        return result;
    }

    /**
     *
     * @param commandText
     * isLoggedIn = true if password is valid
     * isLoggedIn = false if password is invalid
     */
    private boolean isCorrectPassword(String commandText) {
        if (model.getUserPrefs().checkPassword(commandText)) {
            isLoggedIn = true;
            return true;
        } else {
            isLoggedIn = false;
            return false;
        }
    }

    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}

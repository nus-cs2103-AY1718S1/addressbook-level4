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
    //@@author derickjw
    private boolean isLoggedInUsername = false;
    private boolean isLoggedInPassword = false;
    //@@author

    public LogicManager(Model model) {
        this.model = model;
        this.history = new CommandHistory();
        this.addressBookParser = new AddressBookParser();
        this.undoRedoStack = new UndoRedoStack();
        //@@author derickjw
        isCorrectPassword("");
        isCorrectUsername("");
        //@@author
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        CommandResult result;

        //@@author derickjw
        if (!isLoggedInUsername) {
            if (isCorrectUsername(commandText)) {
                result = new CommandResult("Please enter your password");
                return result;
            } else {
                result = new CommandResult("Username does not exist. Please try again!");
                return result;
            }
        }

        if (!isLoggedInPassword) {
            if (isCorrectPassword(commandText)) {
                result = new CommandResult("Log in successful! Welcome to H.M.U v1.4!");
                return result;
            } else {
                result = new CommandResult("Invalid Credentials. Please try again!");
                return result;
            }
        //@@author
        }   else {
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
    }

    //@@author derickjw
    /**
     *
     * @param commandText
     * isLoggedInPassword = true if password is valid
     * isLoggedInPassword = false if password is invalid
     */
    private boolean isCorrectPassword(String commandText) {
        if (model.getUserPrefs().checkPassword(commandText)) {
            isLoggedInPassword = true;
            return true;
        } else {
            isLoggedInPassword = false;
            return false;
        }
    }

    /**
     *
     * @param commandText
     * isLoggedInUsername = true if username is valid
     * isLoggedInUsername = false if username is invalid
     */
    private boolean isCorrectUsername(String commandText) {
        if (model.getUserPrefs().checkUsername(commandText)) {
            isLoggedInUsername = true;
            return true;
        } else {
            isLoggedInUsername = false;
            return false;
        }
    }

    /**
     * Setter method for username
     */
    private void setDefaultUsername() {
        model.getUserPrefs().setDefaultUsername("admin");
    }

    /**
     * Setter method for password
     */
    private void setDefaultPassword() {
        model.getUserPrefs().setDefaultPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
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

package seedu.address.logic;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
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
    private boolean isLock = true;

    public LogicManager(Model model) {
        this.model = model;
        this.history = new CommandHistory();
        this.addressBookParser = new AddressBookParser();
        this.undoRedoStack = new UndoRedoStack();
        isPassword("");
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        CommandResult result;
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

    //@@author moomeowroar
    /**
     * Retrieves all available commands according to user input
     * @param commandText
     * @return output of available commands as a String
     */

    public String liveHelp(String commandText) throws IllegalValueException {
        String finalString = "";
        ArrayList<String> result = addressBookParser.filterCommand(commandText, false);
        for (String string: result) {
            finalString += string + "\n";
        }
        return finalString;
    }

    public String getCommandWord(String commandText) throws IllegalValueException {
        ArrayList<String> result = addressBookParser.filterCommand(commandText, true);
        if (result.isEmpty() || result.size() > 1) {
            return "nil";
        } else {
            return result.get(0);
        }
    }

    /**
     * Check if password is correct and set lock
     * @param commandText
     * @return
     */
    public boolean isPassword(String password) {
        if (model.getUserPrefs().checkPassword(password)) {
            isLock = false;
            return true;
        } else {
            isLock = true;
            return false;
        }
    }

    public boolean isAddressBookLock() {
        return isLock;
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

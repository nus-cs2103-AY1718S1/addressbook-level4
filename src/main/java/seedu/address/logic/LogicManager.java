package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.LoginCommand;
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

    public LogicManager(Model model) {
        this.model = model;
        this.history = new CommandHistory();
        this.addressBookParser = new AddressBookParser();
        this.undoRedoStack = new UndoRedoStack();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        try {
            Command command = addressBookParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack);
            CommandResult result = command.execute();
            undoRedoStack.push(command);
            return result;
        } finally {
            // prevent login details from being stored
            if (!commandText.contains(LoginCommand.COMMAND_WORD)) {
                logger.info("----------------[USER COMMAND][" + commandText + "]");
                history.add(commandText);
            }
        }

    }

    //@@author khooroko
    /**
     * Updates the selected person.
     * @param person the person that has been selected.
     */
    @Override
    public void updateSelectedPerson(ReadOnlyPerson person) {
        model.updateSelectedPerson(person);
    }

    /**
     * Resets the filteredPersonList to be a list of all persons.
     */
    @Override
    public void resetFilteredPersonList() {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public ObservableList<ReadOnlyPerson> getAllPersons() {
        return model.getAllPersons();
    }

    //@@author
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    //@@author jaivigneshvenugopal
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredBlacklistedPersonList() {
        return model.getFilteredBlacklistedPersonList();
    }

    @Override
    public ObservableList<ReadOnlyPerson> getFilteredWhitelistedPersonList() {
        return model.getFilteredWhitelistedPersonList();
    }
    //@@author

    @Override
    public ObservableList<ReadOnlyPerson> getFilteredOverduePersonList() {
        return model.getFilteredOverduePersonList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}

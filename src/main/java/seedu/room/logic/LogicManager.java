package seedu.room.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.room.commons.core.ComponentManager;
import seedu.room.commons.core.LogsCenter;
import seedu.room.logic.commands.Command;
import seedu.room.logic.commands.CommandResult;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.logic.parser.ResidentBookParser;
import seedu.room.logic.parser.exceptions.ParseException;
import seedu.room.model.Model;
import seedu.room.model.person.ReadOnlyPerson;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final ResidentBookParser residentBookParser;
    private final UndoRedoStack undoRedoStack;
    private AutoComplete autoCompleteList;

    public LogicManager(Model model) {
        this.model = model;
        this.history = new CommandHistory();
        this.residentBookParser = new ResidentBookParser();
        this.undoRedoStack = new UndoRedoStack();
        this.autoCompleteList = new AutoComplete(model);
    }

    @Override
    public void updateAutoCompleteList(String userInput) {
        autoCompleteList.updateAutoCompleteList(userInput);
    }

    @Override
    public String[] getAutoCompleteList() {
        return autoCompleteList.getAutoCompleteList();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = residentBookParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack);
            CommandResult result = command.execute();
            undoRedoStack.push(command);
            autoCompleteList.resetAutocompleteList();
            autoCompleteList.updatePersonsArray();
            return result;
        } finally {
            history.add(commandText);
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

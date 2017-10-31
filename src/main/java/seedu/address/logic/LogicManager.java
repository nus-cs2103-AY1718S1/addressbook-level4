package seedu.address.logic;

import java.util.HashMap;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ColorKeywordCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CustomiseCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.Remark;

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
        logger.info("----------------[USER COMMAND][" + commandText + "]");
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
    public ObservableList<ReadOnlyLesson> getFilteredLessonList() {
        return model.getFilteredLessonList();
    }

    @Override
    public ObservableList<Remark> getFilteredRemarkList() {
        return model.getFilteredRemarkList();
    }

    @Override
    public void setRemarkPredicate(Predicate predicate) {
        model.updateFilteredRemarkList(predicate);
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }


    @Override
    public HashMap<String, String> getCommandKeywordColorMap() {
        HashMap<String, String> keywordColorMap = new HashMap<>();
        keywordColorMap.put(AddCommand.COMMAND_WORD, "red");
        keywordColorMap.put(DeleteCommand.COMMAND_WORD, "red");
        keywordColorMap.put(EditCommand.COMMAND_WORD, "red");
        keywordColorMap.put(ExitCommand.COMMAND_WORD, "red");
        keywordColorMap.put(FindCommand.COMMAND_WORD, "red");
        keywordColorMap.put(HelpCommand.COMMAND_WORD, "red");
        keywordColorMap.put(ListCommand.COMMAND_WORD, "red");
        keywordColorMap.put(SelectCommand.COMMAND_WORD, "red");
        keywordColorMap.put(SortCommand.COMMAND_WORD, "red");
        keywordColorMap.put(ClearCommand.COMMAND_WORD, "red");
        keywordColorMap.put(UndoCommand.COMMAND_WORD, "red");
        keywordColorMap.put(RedoCommand.COMMAND_WORD, "red");
        keywordColorMap.put(CustomiseCommand.COMMAND_WORD, "red");
        keywordColorMap.put(HistoryCommand.COMMAND_WORD, "red");
        keywordColorMap.put(ViewCommand.COMMAND_WORD, "red");
        keywordColorMap.put(ColorKeywordCommand.COMMAND_WORD, "red");
        return keywordColorMap;
    }

}

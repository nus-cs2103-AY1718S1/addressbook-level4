package seedu.address.logic;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ListingUnit;
import seedu.address.model.Model;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.Remark;
import seedu.address.model.module.predicates.UniqueModuleCodePredicate;

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
            if (command instanceof ClearCommand
                    && !(ListingUnit.getCurrentPredicate() instanceof UniqueModuleCodePredicate)) {
                undoRedoStack.clearRedoStack();
                undoRedoStack.clearUndoStack();
            }
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

    //@@author junming403
    @Override
    public ObservableList<Remark> getFilteredRemarkList() {
        return model.getFilteredRemarkList();
    }

    @Override
    public void setRemarkPredicate(Predicate predicate) {
        model.updateFilteredRemarkList(predicate);
    }
    //@@author

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }


}

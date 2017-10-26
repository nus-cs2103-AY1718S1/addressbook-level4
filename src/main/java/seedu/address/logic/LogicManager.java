package seedu.address.logic;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListByMostSearchedCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.PrefCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
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
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<String> getCommandList() {
        List<String> commandList = Arrays.asList(
                AddCommand.COMMAND_WORD,
                ClearCommand.COMMAND_WORD,
                DeleteCommand.COMMAND_WORD,
                DeleteTagCommand.COMMAND_WORD,
                EditCommand.COMMAND_WORD,
                ExitCommand.COMMAND_WORD,
                FindCommand.COMMAND_WORD,
                HelpCommand.COMMAND_WORD,
                HistoryCommand.COMMAND_WORD,
                ListCommand.COMMAND_WORD,
                ListByMostSearchedCommand.COMMAND_WORD,
                RedoCommand.COMMAND_WORD,
                SelectCommand.COMMAND_WORD,
                UndoCommand.COMMAND_WORD,
                PrefCommand.COMMAND_WORD
        );
        return FXCollections.observableList(commandList);
    }

    @Override
    public List<String> getCommandTemplateList() {
        List<String> templateList = Arrays.asList(
                AddCommand.MESSAGE_TEMPLATE,
                ClearCommand.MESSAGE_TEMPLATE,
                DeleteCommand.MESSAGE_TEMPLATE,
                DeleteTagCommand.MESSAGE_TEMPLATE,
                EditCommand.MESSAGE_TEMPLATE,
                ExitCommand.MESSAGE_TEMPLATE,
                FindCommand.MESSAGE_TEMPLATE,
                HelpCommand.MESSAGE_TEMPLATE,
                HistoryCommand.MESSAGE_TEMPLATE,
                ListCommand.MESSAGE_TEMPLATE,
                ListByMostSearchedCommand.MESSAGE_TEMPLATE,
                RedoCommand.MESSAGE_TEMPLATE,
                SelectCommand.MESSAGE_TEMPLATE,
                UndoCommand.MESSAGE_TEMPLATE,
                PrefCommand.MESSAGE_TEMPLATE
        );
        return templateList;
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}

package seedu.address.logic;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AliasTokenChangedEvent;
import seedu.address.commons.events.model.ModelToggleEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.alias.AliasCommandParser;
import seedu.address.logic.parser.alias.UnaliasCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.person.AddCommandParser;
import seedu.address.logic.parser.person.DeleteCommandParser;
import seedu.address.logic.parser.person.EditCommandParser;
import seedu.address.logic.parser.person.FindCommandParser;
import seedu.address.logic.parser.person.HideCommandParser;
import seedu.address.logic.parser.person.PinCommandParser;
import seedu.address.logic.parser.person.RemarkCommandParser;
import seedu.address.logic.parser.person.SelectCommandParser;
import seedu.address.logic.parser.person.SortCommandParser;
import seedu.address.logic.parser.person.UnpinCommandParser;
import seedu.address.logic.parser.task.AddTaskCommandParser;
import seedu.address.logic.parser.task.DeleteTaskCommandParser;
import seedu.address.logic.parser.task.FindTaskCommandParser;
import seedu.address.logic.parser.task.MarkTaskCommandParser;
import seedu.address.logic.parser.task.RenameTaskCommandParser;
import seedu.address.logic.parser.task.RescheduleTaskCommandParser;
import seedu.address.logic.parser.task.UnmarkTaskCommandParser;
import seedu.address.model.Model;
import seedu.address.model.alias.ReadOnlyAliasToken;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.task.ReadOnlyTask;

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

        registerAllDefaultCommandParsers();
        registerAllOtherCommands();
        loadAllAliasTokens();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack);
            command.setLogic(this);
            CommandResult result = command.execute();
            undoRedoStack.push(command);
            return result;
        } finally {
            history.add(commandText);
        }
    }

    //@@author deep4k
    /**
     * Registers all commands with parsers into addressBookParser
     */
    private void registerAllDefaultCommandParsers() {
        addressBookParser.registerCommandParser(new AddCommandParser());
        addressBookParser.registerCommandParser(new DeleteCommandParser());
        addressBookParser.registerCommandParser(new FindCommandParser());
        addressBookParser.registerCommandParser(new EditCommandParser());
        addressBookParser.registerCommandParser(new AliasCommandParser());
        addressBookParser.registerCommandParser(new SortCommandParser());
        addressBookParser.registerCommandParser(new HideCommandParser());
        addressBookParser.registerCommandParser(new RemarkCommandParser());
        addressBookParser.registerCommandParser(new UnaliasCommandParser());
        addressBookParser.registerCommandParser(new SelectCommandParser());
        addressBookParser.registerCommandParser(new PinCommandParser());
        addressBookParser.registerCommandParser(new UnpinCommandParser());
        addressBookParser.registerCommandParser(new AddTaskCommandParser());
        addressBookParser.registerCommandParser(new DeleteTaskCommandParser());
        addressBookParser.registerCommandParser(new FindTaskCommandParser());
        addressBookParser.registerCommandParser(new MarkTaskCommandParser());
        addressBookParser.registerCommandParser(new UnmarkTaskCommandParser());
        addressBookParser.registerCommandParser(new RenameTaskCommandParser());
        addressBookParser.registerCommandParser(new RescheduleTaskCommandParser());
    }

    /**
     * Registers all other commands without parsers into addressBookParser
     */
    private void registerAllOtherCommands() {
        addressBookParser.registerOtherCommands();
    }

    /**
     * Loads existing aliases in model into addressBookParser
     */
    private void loadAllAliasTokens() {
        ObservableList<ReadOnlyAliasToken> allAliasTokens = model.getAddressBook().getAliasTokenList();
        for (ReadOnlyAliasToken token : allAliasTokens) {
            addressBookParser.addAliasToken(token);
        }
    }

    @Subscribe
    public void handleAliasTokenChangedEvent(AliasTokenChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(
                event, "Alias " + event.getAction().toString().toLowerCase()));
        if (event.getAction().equals(AliasTokenChangedEvent.Action.Added)) {
            logAliasChangeForParser(
                    addressBookParser.addAliasToken(event.getToken()),
                    event.getToken(),
                    "Successfully added AliasToken %s to main parser",
                    "Failed to add AliasToken '%s' to parser");
        } else if (event.getAction().equals(AliasTokenChangedEvent.Action.Removed)) {
            logAliasChangeForParser(
                    addressBookParser.removeAliasToken(event.getToken()),
                    event.getToken(),
                    "Successfully removed AliasToken '%s' from parser",
                    "Failed to removed AliasToken '%s' from parser");
        }
    }

    @Subscribe
    public void handleModeToggleEvent(ModelToggleEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(
                event, "Toggle: " + event.getToggle().toString()));
        if (event.getToggle().equals(ModelToggleEvent.Toggle.personEnabled)) {
            logModelToggelForParser(addressBookParser.enablePersonToggle(),
                    "Successfully toggled to Person Commands in main parser",
                    "Failed to toggle to Person Commands in main parser");
        } else if (event.getToggle().equals(ModelToggleEvent.Toggle.taskEnabled)) {
            logModelToggelForParser(
                    addressBookParser.enableTaskToggle(),
                    "Successfully toggled to Task Commands in main parser",
                    "Failed to toggle to Task Commands in main parser");
        }
    }

    /**
     * Enter result of changed AliasToken with the logger into the main parser.
     *
     * @param messageSuccessful The String to be input if the operation is a success
     * @param messageFailure    The String to be input if the operation is a failure
     */
    private void logAliasChangeForParser(boolean isSuccessful, ReadOnlyAliasToken tokenChanged,
                                         String messageSuccessful, String messageFailure) {
        if (isSuccessful) {
            logger.info(String.format(messageSuccessful, tokenChanged));
        } else {
            logger.warning(String.format(messageFailure, tokenChanged));
        }
    }

    /**
     * Enter result of changed ModelToggle with the logger into the main parser.
     *
     * @param messageSuccessful The String to be input if the operation is a success
     * @param messageFailure    The String to be input if the operation is a failure
     */
    private void logModelToggelForParser(boolean isSuccessful,
                                         String messageSuccessful, String messageFailure) {
        if (isSuccessful) {
            logger.info(messageSuccessful);
        } else {
            logger.warning(messageFailure);
        }
    }


    @Override
    public boolean isCommandWord(String keyword) {
        return addressBookParser.isCommandRegistered(keyword);
    }

    @Override
    public ObservableList<ReadOnlyAliasToken> getFilteredAliasTokenList() {
        return model.getFilteredAliasTokenList();
    }

    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}

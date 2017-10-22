package seedu.address.logic;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AliasTokenChangedEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddCommandParser;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.AliasCommandParser;
import seedu.address.logic.parser.DeleteCommandParser;
import seedu.address.logic.parser.EditCommandParser;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.HideCommandParser;
import seedu.address.logic.parser.PinCommandParser;
import seedu.address.logic.parser.RemarkCommandParser;
import seedu.address.logic.parser.SelectCommandParser;
import seedu.address.logic.parser.SortCommandParser;
import seedu.address.logic.parser.UnaliasCommandParser;
import seedu.address.logic.parser.UnpinCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.alias.ReadOnlyAliasToken;
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

    /**
     * Enter result of changed AliasToken with the logger into the main parser.
     *
     * @param messageSuccessful The String to be input if the operation is a success
     * @param messageFailure    The Stringto be input if the operation is a failure
     */
    private void logAliasChangeForParser(boolean isSuccessful, ReadOnlyAliasToken tokenChanged,
                                         String messageSuccessful, String messageFailure) {
        if (isSuccessful) {
            logger.info(String.format(messageSuccessful, tokenChanged));
        } else {
            logger.warning(String.format(messageFailure, tokenChanged));
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
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}

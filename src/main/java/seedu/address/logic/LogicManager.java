package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddCommandParser;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.AliasCommandParser;
import seedu.address.logic.parser.DeleteCommandParser;
import seedu.address.logic.parser.EditCommandParser;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.UnaliasCommandParser;
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
        loadAllAliasTokens();
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

    /**
     * Registers all commands with parsers into addressBookParser
     */
    private void registerAllDefaultCommandParsers() {
        addressBookParser.registerCommandParser(new AddCommandParser());
        addressBookParser.registerCommandParser(new DeleteCommandParser());
        addressBookParser.registerCommandParser(new FindCommandParser());
        addressBookParser.registerCommandParser(new EditCommandParser());
        addressBookParser.registerCommandParser(new AliasCommandParser());
        addressBookParser.registerCommandParser(new UnaliasCommandParser());
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

    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<ReadOnlyAliasToken> getFilteredAliasTokenList() {
        return model.getFilteredAliasTokenList();
    }

    @Override
    public boolean isCommandWord(String keyword) {
        return addressBookParser.isCommandParserRegistered(keyword);
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}

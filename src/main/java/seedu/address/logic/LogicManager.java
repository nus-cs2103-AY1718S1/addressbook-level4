package seedu.address.logic;

import static seedu.address.logic.parser.CliSyntax.PREFIXES_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.EmptyFieldException;
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
    //@@author Juxarius
    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack);
            CommandResult result = command.execute();
            undoRedoStack.push(command);
            return result;

        } catch (EmptyFieldException efe) {
            // index check was bypassed, this checks the index before filling empty prefix
            if (efe.getIndex().getOneBased() > model.getFilteredPersonList().size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            commandText = getAutoFilledCommand(commandText, efe.getIndex());
            throw efe;
        } finally {
            history.add(commandText);
        }
    }
    //@@author
    //@@author Juxarius
    /**
     * Replaces the given command text with filled command text
     * @param commandText original input command text
     * @param index index of person to edit
     * @return filled command
     */
    private String getAutoFilledCommand(String commandText, Index index) {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(index.getZeroBased());
        for (Prefix prefix : PREFIXES_PERSON) {
            String prefixInConcern = prefix.getPrefix();
            if (commandText.contains(prefixInConcern)) {
                String replacementText = prefixInConcern + person.getDetailByPrefix(prefix) + " ";
                commandText = commandText.replaceFirst(prefixInConcern, replacementText);
            }
        }
        if (commandText.contains(PREFIX_TAG.getPrefix())) {
            String formattedTags = PREFIX_TAG.getPrefix()
                    + person.getDetailByPrefix(PREFIX_TAG).replaceAll(" ", " t/") + " ";
            commandText = commandText.replaceFirst(PREFIX_TAG.getPrefix(), formattedTags);
        }
        if (commandText.contains(PREFIX_DELTAG.getPrefix())) {
            String formattedTags = PREFIX_DELTAG.getPrefix()
                    + person.getDetailByPrefix(PREFIX_DELTAG).replaceAll(" ", " t/") + " ";
            commandText = commandText.replaceFirst(PREFIX_DELTAG.getPrefix(), formattedTags);
        }
        return commandText.trim();
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

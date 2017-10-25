package seedu.address.logic;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
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
import seedu.address.model.tag.Tag;

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
        } catch (EmptyFieldException efe) {
            // index check was bypassed, this checks the index before filling empty prefix
            if (efe.getIndex().getOneBased() >= model.getFilteredPersonList().size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            commandText = getAutoFilledCommand(commandText, efe.getIndex(), efe.getEmptyFieldPrefix());
            throw efe;
        } finally {
            history.add(commandText);
        }
    }

    private String getAutoFilledCommand(String commandText, Index index, Prefix emptyFieldPrefix) {
        String filledPrefix = new String();
        if (emptyFieldPrefix.equals(PREFIX_ADDRESS)) {
            filledPrefix = emptyFieldPrefix.getPrefix()
                    + model.getAddressBook().getPersonList().get(index.getZeroBased()).getAddress().toString();
        } else if (emptyFieldPrefix.equals(PREFIX_PHONE)) {
            filledPrefix = emptyFieldPrefix.getPrefix()
                    + model.getAddressBook().getPersonList().get(index.getZeroBased()).getPhone().toString();
        } else if (emptyFieldPrefix.equals(PREFIX_EMAIL)) {
            filledPrefix = emptyFieldPrefix.getPrefix()
                    + model.getAddressBook().getPersonList().get(index.getZeroBased()).getEmail().toString();
        } else if (emptyFieldPrefix.equals(PREFIX_NAME)) {
            filledPrefix = emptyFieldPrefix.getPrefix()
                    + model.getAddressBook().getPersonList().get(index.getZeroBased()).getName().toString();
        }  else if (emptyFieldPrefix.equals(PREFIX_TAG)) {
            Set<Tag> tags = model.getAddressBook().getPersonList().get(index.getZeroBased()).getTags();
            for (Tag tag : tags) {
                filledPrefix += PREFIX_TAG + tag.getTagName() + " ";
            }
        } else if (emptyFieldPrefix.equals(PREFIX_DELTAG)) {
            Set<Tag> tags = model.getAddressBook().getPersonList().get(index.getZeroBased()).getTags();
            for (Tag tag : tags) {
                filledPrefix += PREFIX_DELTAG + tag.getTagName() + " ";
            }
        }
        return commandText.replaceFirst(emptyFieldPrefix.getPrefix(), filledPrefix).trim();
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

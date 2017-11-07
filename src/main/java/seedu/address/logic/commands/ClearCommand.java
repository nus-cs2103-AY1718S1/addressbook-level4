package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.AutoCorrectCommand;
import seedu.address.model.AddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_WORD_ALIAS = "c";
    public static final String AUTOCOMPLETE_FORMAT = COMMAND_WORD;
    public static final String MESSAGE_USAGE = "";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    private AutoCorrectCommand autoCorrectCommand = new AutoCorrectCommand();


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new AddressBook());

        if (autoCorrectCommand.getMessageToUser().equals("")) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(autoCorrectCommand.getMessageToUser()
                    + "\n"
                    + MESSAGE_SUCCESS);
        }
    }
}

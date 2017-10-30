package seedu.address.logic.commands;

import seedu.address.email.Email;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.AddressBookStorage;

public class GetEmailCommand extends Command {
    public static final String COMMAND_WORD = "email_address";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Get the current logged in email\n"
            + "Parameter: email_address";
    public static final String MESSAGE_NOT_LOGGED_IN = "Not logged in";

    public GetEmailCommand() {

    }

    @Override
    public CommandResult execute() throws CommandException {
        String email = emailManager.getEmail();
        if (email == null) {
            return new CommandResult(MESSAGE_NOT_LOGGED_IN);
        } else {
            return new CommandResult(email);
        }
    }

    /**
     * * Overridden as access to email manager is needed
     */
    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack, Email emailManager,
                        AddressBookStorage addressBookStorage) {
        this.emailManager = emailManager;
    }
}

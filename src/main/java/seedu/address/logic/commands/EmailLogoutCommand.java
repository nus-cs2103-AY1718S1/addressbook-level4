package seedu.address.logic.commands;

import seedu.address.email.Email;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.AddressBookStorage;

/**
 * Log out any email currently logged in
 */
public class EmailLogoutCommand extends Command {
    public static final String COMMAND_WORD = "email_logout";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Log out from any logged in email\n"
                                                + "Parameter: email_logout";
    public static final String MESSAGE_SUCCESS = "Logged out";

    public EmailLogoutCommand() {

    }

    @Override
    public CommandResult execute() throws CommandException {
        emailManager.logout();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Overridden as access to email manager is needed
     */
    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack, Email emailManager,
                        AddressBookStorage addressBookStorage) {
        this.emailManager = emailManager;
    }
}

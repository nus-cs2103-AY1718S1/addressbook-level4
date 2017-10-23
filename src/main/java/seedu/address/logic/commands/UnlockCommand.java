package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.address.commons.events.model.ReloadAddressBookEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.EncryptOrDecryptException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.security.Security;
import seedu.address.security.SecurityManager;

/**
 * Unlocks the address book.
 */
public class UnlockCommand extends Command {

    public static final String COMMAND_WORD = "unlock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Encrypts all contact with a input password."
            + "Parameters: "
            + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " mykey";

    public static final String MESSAGE_SUCCESS = "Address book is unlocked successfully.";
    public static final String MESSAGE_DUPLICATED_UNLOCK = "Address book is unlocked already. Cannot unlock again.";
    public static final String MESSAGE_ERROR_STORAGE_ERROR = "Meets errors during unlocking address book.";
    public static final String MESSAGE_ERROR_LOCK_PASSWORD = "Cannot unlock address book using current password.";

    private final String password;

    public UnlockCommand(String password) {
        this.password = password;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        Security security = SecurityManager.getInstance();
        try {
            if (!security.isEncrypted()) {
                return new CommandResult(MESSAGE_DUPLICATED_UNLOCK);
            }

            security.decryptAddressBook(password);
            security.raise(new ReloadAddressBookEvent());
            return new CommandResult(MESSAGE_SUCCESS);

        } catch (IOException e) {
            security.raise(new DataSavingExceptionEvent(e));
            return new CommandResult(MESSAGE_ERROR_STORAGE_ERROR);
        } catch (EncryptOrDecryptException e) {
            return new CommandResult(MESSAGE_ERROR_LOCK_PASSWORD);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnlockCommand // instanceof handles nulls
                && this.password.equals(((UnlockCommand) other).password)); // state check
    }

}

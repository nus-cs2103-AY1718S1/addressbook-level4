package seedu.address.logic.commands.commandmode;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.storage.SecurityManager;

//@@author syy94

/**
 * Represents the modes that PasswordCommand is able to do
 */
public abstract class PasswordMode {
    private String pass;

    public PasswordMode(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    protected boolean passExists() {
        return SecurityManager.passExists();
    }

    public abstract CommandResult execute() throws IOException, NoSuchAlgorithmException, CommandException;

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PasswordMode // instanceof handles nulls
                && this.pass.equals(((PasswordMode) other).pass)); // state check
    }
}


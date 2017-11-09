package seedu.address.model.asana;

//@@author Sri-vatsa

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Stores AccessToken hashed
 */
public class storeAccessToken extends Command {

    private final String mAccessToken;

    public storeAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }
    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult("");
    }
}

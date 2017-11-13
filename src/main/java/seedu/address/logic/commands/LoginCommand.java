//@@author zenghou
package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.user.UserCreds;

/**
 * Authenticates a user with username and password
 */
public class LoginCommand extends Command {
    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD  + ": Authenticates a user of the address book."
            + " Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD ";

    public static final String MESSAGE_SUCCESS = "Successfully logged in!";
    public static final String MESSAGE_FAILURE = "Please ensure that username and password are entered correctly!";

    private UserCreds userCreds;

    public LoginCommand(String username, String password) {
        this.userCreds = new UserCreds(username, password);

    }

    @Override
    public CommandResult execute() throws CommandException {
        UserCreds userCredsInModel = model.getUserCreds();
        boolean isVerifiedUser = UserCreds.isValidUser(userCreds, userCredsInModel);
        if (isVerifiedUser) {
            model.updateUserCreds();
            userCredsInModel.validateCurrentSession();
            return new CommandResult(MESSAGE_SUCCESS);
        }
        throw new CommandException(MESSAGE_FAILURE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && userCreds.equals(((LoginCommand) other).userCreds));
    }

}

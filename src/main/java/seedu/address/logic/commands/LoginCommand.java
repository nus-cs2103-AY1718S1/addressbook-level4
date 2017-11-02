package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.UserNotFoundException;
import seedu.address.logic.Password;
import seedu.address.logic.Username;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author jelneo
/**
 * Handles login of a user
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_LOGIN_ACKNOWLEDGEMENT = "Login successful";
    public static final String MESSAGE_LOGIN_UNSUCCESSFUL = "Unable to log into Address Book";
    public static final String MESSAGE_LOGIN_REQUEST = "Please log in first";
    public static final String MESSAGE_LOGIN_FORMAT = COMMAND_WORD + " USERNAME PASSWORD";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " USERNAME PASSWORD\nExample: "
            + COMMAND_WORD + " JohnDoe hiIAmJohnDoe123";
    private static boolean isLoggedIn = false;
    private final Username username;
    private final Password password;

    public LoginCommand(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    public static void setLoginStatus(boolean val) {
        isLoggedIn = val;
    }

    /**
     * Verifies if user is valid
     * @throws UserNotFoundException if user is not valid
     * @throws IllegalValueException if either username or password do not meet the username or password requirements
     */
    private void verifyUser() throws UserNotFoundException, IllegalValueException {
        model.authenticateUser(username, password);
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            verifyUser();
            return new CommandResult(MESSAGE_LOGIN_ACKNOWLEDGEMENT);
        } catch (UserNotFoundException unfe) {
            throw new CommandException(MESSAGE_LOGIN_UNSUCCESSFUL);
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && this.username.equals(((LoginCommand) other).username)
                && this.password.equals(((LoginCommand) other).password));
    }
}

package seedu.address.logic.commands;

import java.util.concurrent.CompletableFuture;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author derrickchua
/**TODO: Throw prompt to open browser window as CommandResult
 * Adds a person to the address book.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_ALIAS = "li";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Login to Google Contacts ";

    public static final String MESSAGE_SUCCESS = "Logging in";
    public static final String MESSAGE_FAILURE = "Something has gone wrong...";

    @Override
    public CommandResult execute() throws CommandException {

        clientFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return oauth.execute();
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable t) {
                System.err.println(t.getStackTrace());
            }

            return null;
        }, executor);

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof LoginCommand; // instanceof handles null
    }
}

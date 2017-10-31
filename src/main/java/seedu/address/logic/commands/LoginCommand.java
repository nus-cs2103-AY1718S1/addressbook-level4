package seedu.address.logic.commands;

import java.util.concurrent.CompletableFuture;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author derrickchua
/**
 * Adds a person to the address book.
 */
public class LoginCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_ALIAS = "lg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Login to Google Contacts ";

    public static final String MESSAGE_SUCCESS = "Logging in";
    public static final String MESSAGE_FAILURE = "Something has gone wrong...";

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        clientFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return oauth.execute();
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

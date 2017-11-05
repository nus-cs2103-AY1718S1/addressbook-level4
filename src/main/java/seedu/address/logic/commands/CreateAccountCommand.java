//@@author cqhchan
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.account.Account;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.exceptions.DuplicateAccountException;

/**
 *
 */
public class CreateAccountCommand extends Command {

    public static final String COMMAND_WORD = "create";
    public static final String COMMAND_ALIAS = "ca";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Account to the database. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD ";

    public static final String MESSAGE_SUCCESS = "New account added: %1$s";
    public static final String MESSAGE_DUPLICATE_ACCOUNT = "This account already exists in the address book";

    private final Account toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public CreateAccountCommand(ReadOnlyAccount account) {
        toAdd = new Account(account);
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.addAccount(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateAccountException e) {
            throw new CommandException(MESSAGE_DUPLICATE_ACCOUNT);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateAccountCommand // instanceof handles nulls
                && toAdd.equals(((CreateAccountCommand) other).toAdd));
    }
}

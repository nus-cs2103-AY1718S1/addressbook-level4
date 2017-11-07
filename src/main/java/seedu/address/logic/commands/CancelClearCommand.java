package seedu.address.logic.commands;
//@@author pjunwei95
import static java.util.Objects.requireNonNull;

/**
 * Clears the address book.
 */
public class CancelClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_FAILURE = "Address book has not been cleared!";


    @Override
    public CommandResult execute() {
        requireNonNull(model);
        return new CommandResult(MESSAGE_FAILURE);
    }
}

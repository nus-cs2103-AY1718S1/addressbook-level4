package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Change the current command mode
 */
public class ChangeModeCommand extends Command {

    public static final String COMMAND_WORD = "switchMode";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Change the current command mode.\n"
        + "Parameters: addressbook ab taskmanager tm\n"
        + "Example: " + COMMAND_WORD + " tm";

    public static final String MESSAGE_CHANGE_MODE_SUCCESS = "Command mode changed to : %1$s";
    public static final String MESSAGE_INVALID_MODE = "The mode can only be either addressbook (ab) "
        + "or taskmanager (tm)";

    private String mode;

    /**
     * Create an ChangeModeCommand to change current command mode
     */
    public ChangeModeCommand(String mode) {
        this.mode = mode;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.changeCommandMode(mode);
            return new CommandResult(String.format(MESSAGE_CHANGE_MODE_SUCCESS, paraphraseCurrentMode(mode)));
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_INVALID_MODE);
        }
    }

    /**
     * Paraphrase the input mode into either addressbook or taskmanager
     * @param mode
     */
    private String paraphraseCurrentMode(String mode) {
        if ("ab".equals(mode) || "addressbook".equals(mode)) {
            return "addressbook";
        } else {
            return "taskmanager";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ChangeModeCommand // instanceof handles nulls
            && mode.equals(((ChangeModeCommand) other).mode));
    }
}

package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author CindyTsai1
/**
 * Suggests a correct command
 */
public class SuggestCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Do you mean %1$s?";

    private final String possibleCommand;

    /**
     * Creates an SuggestCommand to suggest the specified {@code String} for command
     */
    public SuggestCommand(String command) {
        this.possibleCommand = command;
    }

    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(String.format(MESSAGE_SUCCESS, possibleCommand));
    }
}

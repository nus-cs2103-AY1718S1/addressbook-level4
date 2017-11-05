package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_PROMPT_COMMAND;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Suggests another command
 */
public class SuggestedCommand extends Command {

    public static final String COMMAND_WORD = "y";
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "yes", "ok", "yea", "yeah"));

    private final String commandWord;
    private final String arguments;
    private Command convertedCommand;

    public SuggestedCommand(String commandWord, String arguments) {
        this.commandWord = commandWord;
        this.arguments = arguments;
    }

    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Returns an instructional message that suggests the actual command to be executed.
     */
    public String getPromptMessage() {
        return String.format(MESSAGE_PROMPT_COMMAND, getCommandString());
    }

    /**
     * Returns a formatted command string in the format in which users would typically enter into the command box.
     */
    public String getCommandString() {
        return null;
    }
    
    /**
     * Returns {@code true} if the {@code commandWord} and
     * {@code arguments} can be successfully converted
     * into a {@code Command}, {@code false} otherwise.
     */
    public boolean isSuggestible() {
        return true;
    }
}

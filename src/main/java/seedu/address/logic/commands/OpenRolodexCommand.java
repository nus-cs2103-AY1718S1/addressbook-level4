package seedu.address.logic.commands;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.OpenRolodexRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Opens an existing Rolodex in a different directory.
 */
public class OpenRolodexCommand extends Command {

    public static final String COMMAND_WORD = "open";
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "o", "cd", "ls", "<"));
    public static final String COMMAND_HOTKEY = "Ctrl+O";
    public static final String FORMAT = "open FILEPATH";

    public static final String MESSAGE_OPENING = "Opening file: `%1$s`";
    public static final String MESSAGE_NOT_EXIST = "Unable to find `%1$s`. "
            + "Use the `" + NewRolodexCommand.COMMAND_WORD + "` command for creating a new file.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": "
            + "Reloads the application using the rolodex supplied at the given file path.\n"
            + "Parameters: [FILEPATH]\n"
            + "Example: open C:/Documents/MyRolodex.rldx";

    private final String filePath;

    public OpenRolodexCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (new File(filePath).exists()) {
            EventsCenter.getInstance().post(new OpenRolodexRequestEvent(filePath));
            return new CommandResult(String.format(MESSAGE_OPENING, filePath));
        } else {
            throw new CommandException(String.format(MESSAGE_NOT_EXIST, filePath));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OpenRolodexCommand // instanceof handles nulls
                && this.filePath.equals(((OpenRolodexCommand) other).filePath)); // state check
    }
}

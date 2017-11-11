package seedu.address.logic.commands;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.OpenRolodexRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Creates a new Rolodex at a specified directory.
 */
public class NewRolodexCommand extends Command {

    public static final String COMMAND_WORD = "new";
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "n", ">", "touch"));
    public static final String COMMAND_HOTKEY = "Ctrl+N";
    public static final String FORMAT = "new FILEPATH";

    public static final String MESSAGE_CREATING = "Creating new file: `%1$s`";
    public static final String MESSAGE_ALREADY_EXISTS = "`%1$s` already exists. "
            + "Use the `" + OpenRolodexCommand.COMMAND_WORD + "` command for opening an existing file.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": "
            + "Creates a new Rolodex file at the specified destination and "
            + "reloads the application using the rolodex supplied at the given file path.\n"
            + "Parameters: [FILEPATH]\n"
            + "Example: new data/new.rldx";

    private final String filePath;

    public NewRolodexCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (new File(filePath).exists()) {
            throw  new CommandException(String.format(MESSAGE_ALREADY_EXISTS, filePath));
        } else {
            EventsCenter.getInstance().post(new OpenRolodexRequestEvent(filePath));
            return new CommandResult(String.format(MESSAGE_CREATING, filePath));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NewRolodexCommand // instanceof handles nulls
                && this.filePath.equals(((NewRolodexCommand) other).filePath)); // state check
    }
}

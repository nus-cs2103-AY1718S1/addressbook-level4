package seedu.address.logic.commands;

import static seedu.address.commons.util.FileUtil.isFileExists;

import java.io.File;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.SwitchAddressBookRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author chrisboo
/**
 * Open Address Book
 */
public class NewCommand extends Command {

    public static final String COMMAND_SHORT = "n";
    public static final String COMMAND_WORD = "new";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Open a new DeathNote. \n"
        + "Paremeters: FILEPATH \n"
        + "Example: " + COMMAND_WORD + " C:\\Users\\crispy\\Downloads\\NewDeathNote.xml";

    public static final String MESSAGE_OPEN_DEATHNOTE_SUCCESS = "Opened DeathNote: %1$s";

    private final File file;

    public NewCommand(File file) {
        this.file = file;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (isFileExists(file)) {
            throw new CommandException(Messages.MESSAGE_EXISTING_FILE);
        }

        EventsCenter.getInstance().post(new SwitchAddressBookRequestEvent(file, true));
        return new CommandResult(String.format(MESSAGE_OPEN_DEATHNOTE_SUCCESS, file.getPath()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof NewCommand // instanceof handles nulls
            && this.file.equals(((NewCommand) other).file)); // state check
    }
}
//@@author

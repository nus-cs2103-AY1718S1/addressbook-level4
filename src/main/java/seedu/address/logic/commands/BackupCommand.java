package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.BackupEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * A command to create a backup of user's Tourist Book data.
 */
//@@author huyuanrong
public class BackupCommand extends Command {

    public static final String COMMAND_WORD = "backup";
    public static final String MESSAGE_SUCCESS = "A Tourist Book backup has been created!";

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        EventsCenter.getInstance().post(new BackupEvent(model.getAddressBook()));
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
//@@author

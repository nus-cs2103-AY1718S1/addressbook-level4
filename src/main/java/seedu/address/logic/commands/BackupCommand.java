package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.BackupRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Backs up current addressbook into a user input location.
 */
//@@author charlesgoh
public class BackupCommand extends Command {
    public static final String COMMAND_WORD = "backup";
    public static final String COMMAND_ALIAS = "bk";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Backs up data to a user input "
            + "location field [FILEPATH]\n"
            + "Parameter: KEYWORD [FILEPATH]\n"
            + "Example: " + COMMAND_WORD + "MyBackUpFile";

    public static final String MESSAGE_INVALID_INPUT = "Invalid Input.\n";

    public static final String MESSAGE_SUCCESS = "AddressBook++ data backed up successfully.";

    private String args;

    public BackupCommand(String trimmedArgs) {
        super();
        this.args = trimmedArgs;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        requireNonNull(model.getAddressBook());
        EventsCenter.getInstance().post(new BackupRequestEvent(model, args));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

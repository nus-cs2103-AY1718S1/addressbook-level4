package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Schedules an Activity with a person.
 */
public class ScheduleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "schedule";

    public static final String COMMAND_ALIAS = "sc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedules an Activity with a person. "
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_ACTIVITY + "ACTIVITY";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;
    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        return null;
    }
}

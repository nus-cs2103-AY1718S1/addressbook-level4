package seedu.address.logic.commands;
//@@author RonakLakhotia
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_PRIORITY;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;



/**
 * Adds a reminder to the address book.
 */
public class AddReminder extends UndoableCommand {

    public static final String COMMAND_WORD = "reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to the address book. "
            + "Parameters: "
            + PREFIX_REMINDER_DETAILS + "ABOUT "
            + PREFIX_REMINDER_PRIORITY + "HIGH "
            + PREFIX_REMINDER_DUE_DATE + "DATE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_REMINDER_DETAILS + "CS2103T Assignment "
            + PREFIX_REMINDER_PRIORITY + "High "
            + PREFIX_REMINDER_DUE_DATE + "12.05.2017 ";

    public static final String MESSAGE_SUCCESS = "New reminder added: %1$s";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists!";

    private static final Logger logger = LogsCenter.getLogger(AddReminder.class);
    private final Reminder toAdd;


    /**
     * Creates an AddReminder Command to add the specified {@code ReadOnlyReminder}
     */
    public AddReminder(ReadOnlyReminder reminder) {
        toAdd = new Reminder(reminder);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addReminder(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateReminderException e) {
            logger.severe(StringUtil.getDetails(e));
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReminder // instanceof handles nulls
                && toAdd.equals(((AddReminder) other).toAdd));
    }
}

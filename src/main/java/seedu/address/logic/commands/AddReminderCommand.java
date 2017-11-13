package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminders.ReadOnlyReminder;
import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.exceptions.DuplicateReminderException;

//@@author justinpoh
/**
 *  Adds a reminder to the program.
 */

public class AddReminderCommand extends Command {

    public static final String COMMAND_WORD = "addreminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder.\n"
            + "Parameters: "
            + PREFIX_REMINDER + "REMINDER "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_REMINDER + "Dinner with Family "
            + PREFIX_DATE + "22-11-2017 "
            + PREFIX_TIME + "17:00\n";

    public static final String MESSAGE_SUCCESS = "New reminder added.";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists in iContacts.";

    private Reminder toAdd;

    /**
     * Creates an AddReminderCommand to add the specified {@code ReadOnlyReminder}
     */
    public AddReminderCommand(ReadOnlyReminder toAdd) {
        this.toAdd = new Reminder(toAdd);
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.addReminder(toAdd);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (DuplicateReminderException dre) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReminderCommand // instanceof handles nulls
                && toAdd.equals(((AddReminderCommand) other).toAdd));
    }
}

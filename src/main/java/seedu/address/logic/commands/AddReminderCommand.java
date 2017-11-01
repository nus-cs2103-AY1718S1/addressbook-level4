//@@author inGall
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;

/**
 * Adds a reminder to the address book.
 */
public class AddReminderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to the address book. "
            + "Parameters: "
            + PREFIX_TASK + "NAME "
            + PREFIX_PRIORITY + "PRIORITY "
            + PREFIX_DATE + "DATE "
            + PREFIX_MESSAGE + "MESSAGE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK + "Proposal submission "
            + PREFIX_PRIORITY + "Low "
            + PREFIX_DATE + "25/12/2017 1500 "
            + PREFIX_MESSAGE + "Submit to manager "
            + PREFIX_TAG + "Work "
            + PREFIX_TAG + "John";

    public static final String MESSAGE_SUCCESS = "New reminder added: %1$s";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists in the address book";

    private final Reminder toAdd;

    /**
     * Creates an AddReminderCommand to add the specified {@code ReadOnlyReminder}
     */
    public AddReminderCommand(ReadOnlyReminder reminder) {
        toAdd = new Reminder(reminder);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addReminder(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateReminderException e) {
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

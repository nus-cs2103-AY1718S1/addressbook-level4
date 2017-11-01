package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;

/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addE";
    public static final String COMMAND_ALIAS = "aE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE_TIME + "DATE & TIME "
            + PREFIX_ADDRESS + "VENUE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe birthday "
            + PREFIX_DATE_TIME + "25122017 08:30 "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";

    private final Event toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code ReadOnlyEvent}
     */
    public AddEventCommand(ReadOnlyEvent event) {
        toAdd = new Event(event);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        String dateToString = toAdd.getTime().toString().substring(0, 8);
        LocalDate dateToCompare = LocalDate.parse(dateToString, formatter);
        LocalDate oneDayBefore = dateToCompare.minusDays(1);
        String text = oneDayBefore.format(formatter);
        LocalDate parsedDayBefore = LocalDate.parse(text, formatter);
        try {
            model.addEvent(toAdd);
            Reminder r = new Reminder(toAdd, parsedDayBefore.toString());
            toAdd.addReminder(r);
            model.addReminder(r);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateEventException | DuplicateReminderException e) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}

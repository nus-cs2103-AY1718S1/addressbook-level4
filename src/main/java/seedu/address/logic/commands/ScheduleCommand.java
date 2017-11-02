package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;

import java.util.Calendar;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.schedule.Schedule;

/**
 * Schedules a consultation timeslot with the person identified using it's last displayed index from the address book.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";
    public static final String COMMAND_ALIAS = "sche";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Schedules the selected indexed person to a consultation timeslot.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SCHEDULE + "today, 5.45pm";

    public static final String MESSAGE_SCHEDULE_PERSON_SUCCESS = "Scheduled Person: %1$s";
    public static final String PERSON_NOT_FOUND = "This person cannot be found";

    private final Index targetIndex;
    private final Calendar date;

    public ScheduleCommand(Index targetIndex, Calendar date) {
        this.targetIndex = targetIndex;
        this.date = date;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(targetIndex);
        requireNonNull(date);

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (date != null && !isDateValid()) {
            return new CommandResult("You have entered an invalid date.");
        }

        ReadOnlyPerson personAddedToSchedule = lastShownList.get(targetIndex.getZeroBased());
        Schedule schedule = new Schedule(personAddedToSchedule.getName().toString(), date);
        try {
            model.addSchedule(schedule);
        } catch (PersonNotFoundException e) {
            return new CommandResult(PERSON_NOT_FOUND);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult("Added " + personAddedToSchedule.getName().toString() + " to consultations schedule "
                + "on " + schedule.getDate().toString());

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleCommand // instanceof handles nulls
                && this.date.getTimeInMillis() == ((ScheduleCommand) other).date.getTimeInMillis())
                && this.targetIndex.getZeroBased() == ((ScheduleCommand) other).targetIndex.getZeroBased();
    }

    /**
     * Returns true if appointment date set to after current time
     */
    private boolean isDateValid() {
        requireNonNull(date);
        Calendar calendar = Calendar.getInstance();
        return !date.getTime().before(calendar.getTime());
    }
}

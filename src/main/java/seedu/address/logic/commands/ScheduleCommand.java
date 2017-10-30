package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.schedule.Activity;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleDate;

/**
 * Schedules an Activity with a person.
 */
public class ScheduleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "schedule";

    public static final String COMMAND_ALIAS = "sc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (alias: " + COMMAND_ALIAS + ")"
            + ": Schedules an Activity with a person.\n"
            + "Parameters: INDEX "
            + PREFIX_DATE + "DATE "
            + PREFIX_ACTIVITY + "ACTIVITY\n"
            + MESSAGE_GET_MORE_HELP;

    public static final String MESSAGE_SCHEDULE_SUCCESS = "Scheduled an activity with %1$d person(s)";

    private Set<Index> indices;
    private ScheduleDate date;
    private Activity activity;

    /**
     * Creates a ScheduleCommand to add the specified {@code Schedule}
     */
    public ScheduleCommand(Set<Index> indices, ScheduleDate date, Activity activity) {
        requireNonNull(indices);
        requireNonNull(date);
        requireNonNull(activity);
        this.indices = indices;
        this.date = date;
        this.activity = activity;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        for (Index index: indices) {

            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson schedulePerson = lastShownList.get(index.getZeroBased());
            Schedule schedule = new Schedule(date, activity, schedulePerson.getName());

            Set<Schedule> schedules = new HashSet<>(schedulePerson.getSchedules());

            if (!schedulePerson.getSchedules().contains(schedule)) {
                schedules.add(schedule);
            }

            Person scheduleAddedPerson = new Person(schedulePerson.getName(), schedulePerson.getPhone(),
                    schedulePerson.getCountry(), schedulePerson.getEmails(), schedulePerson.getAddress(), schedules,
                    schedulePerson.getTags());
            try {
                model.updatePerson(schedulePerson, scheduleAddedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        }
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SCHEDULE_SUCCESS, indices.size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleCommand // instanceof handles nulls
                && date.equals(((ScheduleCommand) other).date)
                && activity.equals(((ScheduleCommand) other).activity));
    }
}


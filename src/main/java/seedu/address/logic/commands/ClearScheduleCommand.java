package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;

import java.util.List;
import java.util.TreeSet;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.schedule.Day;
import seedu.address.model.schedule.Time;
import seedu.address.model.schedule.Slot;

/**
 * Clear a time span for a person identified using it's last displayed index from the address book.
 */
public class ClearScheduleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clearSchedule";
    public static final String COMMAND_ALIAS = "cs";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clear a specific time slot, during which a person is supposed to be free.\n"
            + "Parameters: INDEX (must be a positive integer); Day(From Monday to Saturday); start time "
            + "(Should be expressed in standard 24 hours time, no more accurate than 30 minutes and no earlier "
            + "than 0600 and no later than 2330\n"
            + "Example: "
            + COMMAND_WORD + " 1"
            + PREFIX_DAY + "Monday"
            + PREFIX_START_TIME + "0700"
            + PREFIX_END_TIME + "1430";

    public static final String MESSAGE_CLEAR_SCHEDULE_PERSON_SUCCESS = "Schedule successfully cleared";

    private final Index targetIndex;

    private Day day;
    private Time startTime;
    private Time endTime;
    private TreeSet<Integer> timeToClear;

    public ClearScheduleCommand(Index targetIndex, Day day, Time startTime, Time endTime)
            throws IllegalValueException {
        this.targetIndex = targetIndex;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        Slot slot = new Slot(day, startTime, endTime);
        timeToClear = slot.getBusyTime();
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToClearSchedule = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.clearScheduleForPerson(targetIndex.getZeroBased(), timeToClear);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_CLEAR_SCHEDULE_PERSON_SUCCESS, personToClearSchedule));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClearScheduleCommand // instanceof handles nulls
                && this.targetIndex.equals(((ClearScheduleCommand) other).targetIndex)
                && this.day.equals(((ClearScheduleCommand) other).day)
                && this.startTime.equals(((ClearScheduleCommand) other).startTime)
                && this.endTime.equals(((ClearScheduleCommand) other).endTime)); // state check
    }
}

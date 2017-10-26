package seedu.address.logic.commands;

import java.util.List;
import java.util.TreeSet;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.schedule.Day;
import seedu.address.model.schedule.Time;
import seedu.address.model.schedule.Slot;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class AddScheduleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addSchedule";
    public static final String COMMAND_ALIAS = "as";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add a specific time slot when a person is busy.\n"
            + "Parameters: INDEX (must be a positive integer); Day(From Monday to Saturday); start time " +
            "(Should be expressed in standard 24 hours time, no more accurate than 30 minutes and no earlier " +
            "than 0600 and no later than 2330\n"
            + "Example: " + COMMAND_WORD + " 1" + " Monday" + "0700" + "1430";

    public static final String MESSAGE_ADD_SCHEDULE_PERSON_SUCCESS = "Added Schedule: %1$s";

    private final Index targetIndex;

    private TreeSet<Integer> TimeToAdd;

    public AddScheduleCommand(Index targetIndex, Day day, Time startTime, Time endTime)
            throws Exception {
        if(!day.isValid()){
            throw new CommandException("Not a valid day!");
        }
        if(!startTime.isValid() || !endTime.isValid()){
            throw new CommandException("Start or end time is not in the proper format!");
        }
        if(startTime.getTime() >= endTime.getTime()){
            throw new CommandException("End time must be later than start Time!")
        }
        this.targetIndex = targetIndex;
        Slot slot = new Slot(day, startTime, endTime);
        TimeToAdd = slot.getBusyTime();
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToAddSchedule = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.addScheduleToPerson(targetIndex.getZeroBased(), TimeToAdd);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_ADD_SCHEDULE_PERSON_SUCCESS, personToAddSchedule));
    }

}

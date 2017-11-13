package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;


import java.util.List;
import java.util.TreeSet;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.schedule.Day;
import seedu.address.model.schedule.Slot;
import seedu.address.model.schedule.Time;
import seedu.address.model.tag.Tag;

/**
 * Adds a tag to persons who are in an event if the event can be scheduled.
 */
public class AddEventTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addEventTag";
    public static final String COMMAND_ALIAS = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": schedules an event in the time specified \n"
            + "Parameters: EventName StartTime EndTime Location "
            + "Indices separated by spaces (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "n/Meeting d/Monday st/1000 et/1300 loc/School persons/1 2 5 7 8";

    public static final String MESSAGE_ARRANGE_PERSON_SUCCESS = "Event Tag successfully added!";
    public static final String MESSAGE_ARRANGE_PERSON_FAILURE = "Not everyone can make it; "
            + "try using the 'arrange' command to look for common timings";

    private final Index[] listOfIndex;
    private final Day day;
    private final Time startTime;
    private final Time endTime;
    private TreeSet<Integer> timeToClear;
    private final Tag event;

    public AddEventTagCommand(Day day, Time startTime, Time endTime,
                              Index[] listOfIndex, Tag event) throws IllegalValueException {
        this.listOfIndex = listOfIndex;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        Slot slot = new Slot(day, startTime, endTime);
        timeToClear = slot.getBusyTime();
        this.event = event;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        boolean check = model.checkMeetingTime(listOfIndex, day.getDay(), startTime.getTime(), endTime.getTime());
        if (!check) {
            return new CommandResult(MESSAGE_ARRANGE_PERSON_FAILURE);
        } else {
            try {
                for (int i = 0; i < listOfIndex.length; i++) {
                    model.clearScheduleForPerson(listOfIndex[i].getZeroBased(), timeToClear);
                    model.addEventToPerson(listOfIndex[i].getZeroBased(), event);
                }
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
            return new CommandResult(MESSAGE_ARRANGE_PERSON_SUCCESS);

        }
    }
}


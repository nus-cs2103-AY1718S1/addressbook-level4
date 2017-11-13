package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.PossibleDays;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Time;
import seedu.address.ui.ScheduleTable;

//@@author YuchenHe98
/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class ArrangeCommand extends Command {

    public static final String COMMAND_WORD = "arrange";
    public static final String COMMAND_ALIAS = "ar";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": arrange meeting time for a list of indices of persons who appeared in the last list \n"
            + "Parameters: Indices separated by spaces (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 2 5 7 8";

    public static final String MESSAGE_ARRANGE_PERSON_SUCCESS = "meeting successfully arranged!";

    private final Index[] listOfIndex;

    public ArrangeCommand(int[] listOfIndex) {
        this.listOfIndex = new Index[listOfIndex.length];
        for (int i = 0; i < listOfIndex.length; i++) {
            this.listOfIndex[i] = Index.fromOneBased(listOfIndex[i]);
        }
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        for (int i = 0; i < listOfIndex.length; i++) {
            if (listOfIndex[i].getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
        TreeSet<Integer>[] timeSetArray = Schedule.splitScheduleToDays(model.generateMeetingTime(listOfIndex));

        ScheduleTable.generates(timeSetArray);
        String toShow = scheduleInfo();
        return new CommandResult(String.format(MESSAGE_ARRANGE_PERSON_SUCCESS) + toShow);

    }

    public int[] getSortedZeroBasedIndex() {
        int[] thisIndexList = new int[listOfIndex.length];
        for (int i = 0; i < listOfIndex.length; i++) {
            thisIndexList[i] = listOfIndex[i].getZeroBased();
        }
        Arrays.sort(thisIndexList);
        return thisIndexList;
    }

    /**
     * Returns the info of schedule to be shown to the user later.
     */
    public String scheduleInfo() {
        TreeSet<Integer>[] timeSetArray = Schedule.splitScheduleToDays(model.generateMeetingTime(listOfIndex));
        String toShow = "\nAll common free time: \n";
        for (int i = 0; i < timeSetArray.length; i++) {
            toShow = toShow + PossibleDays.DAY_TIME[i] + ":\n";
            for (Integer time : timeSetArray[i]) {
                toShow = toShow + Time.getTimeToString(time) + "--"
                        + Time.getTimeToString(Time.increaseTimeInteger(time)) + " ";
            }
            toShow += "\n";
        }
        return toShow;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ArrangeCommand // instanceof handles nulls
                && Arrays.equals(this.getSortedZeroBasedIndex(), ((ArrangeCommand) other).getSortedZeroBasedIndex()));
        // state check
    }
}

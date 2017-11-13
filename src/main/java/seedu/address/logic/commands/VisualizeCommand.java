package seedu.address.logic.commands;

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
public class VisualizeCommand extends Command {

    public static final String COMMAND_WORD = "visualize";
    public static final String COMMAND_ALIAS = "v";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Visualizes the person's schedule identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VISUALIZE_PERSON_SUCCESS = "Visualized Success! ";

    private final Index targetIndex;

    public VisualizeCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Schedule scheduleToBeShown =
                model.getAddressBook().getPersonList().get(targetIndex.getZeroBased()).getSchedule();

        ScheduleTable.generates(scheduleToBeShown);
        String toShow = scheduleInfo();
        return new CommandResult(String.format(MESSAGE_VISUALIZE_PERSON_SUCCESS + targetIndex.getOneBased() + toShow));

    }
    /**
     * Show schedule info as a message.
     */
    public String scheduleInfo() {

        Schedule scheduleToBeShown =
                model.getAddressBook().getPersonList().get(targetIndex.getZeroBased()).getSchedule();
        TreeSet<Integer>[] timeSetArray = scheduleToBeShown.splitScheduleToDays();
        String toShow = "\nAll free time: \n";
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
                || (other instanceof VisualizeCommand // instanceof handles nulls
                && this.targetIndex.equals(((VisualizeCommand) other).targetIndex)); // state check
    }
}

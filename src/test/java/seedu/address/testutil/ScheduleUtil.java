package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.model.schedule.Schedule;

/**
 * A utility class for Schedule.
 */
public class ScheduleUtil {
    /**
     * Returns a schedule command string for allocating the {@code schedule} with contact(s) of the specified
     * {@code indices}.
     */
    public static String getScheduleCommand(Schedule schedule, Index... indices) {
        StringBuilder scheduleCommand = new StringBuilder(ScheduleCommand.COMMAND_WORD + " ");
        for(Index index: indices) {
            scheduleCommand.append(index.getOneBased() + " ");
        }
        scheduleCommand.append(getScheduleDetails(schedule));

        return scheduleCommand.toString();
    }

    /**
     * Returns a schedule command string for allocating the {@code schedule} with contact(s) of the specified
     * {@code indices} using schedule command's alias.
     */
    public static String getScheduleCommandUsingAlias(Schedule schedule, Index... indices) {
        StringBuilder scheduleCommand = new StringBuilder(ScheduleCommand.COMMAND_ALIAS + " ");
        for(Index index: indices) {
            scheduleCommand.append(index.getOneBased() + " ");
        }
        scheduleCommand.append(getScheduleDetails(schedule));

        return scheduleCommand.toString();
    }

    /**
     * Returns the part of command string for the given {@code schedule}'s details.
     */
    public static String getScheduleDetails(Schedule schedule) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DATE + schedule.getScheduleDate().toString() + " " + PREFIX_ACTIVITY
                + schedule.getActivity().toString());
        return sb.toString();
    }
}

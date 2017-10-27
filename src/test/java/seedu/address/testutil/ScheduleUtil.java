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
     * Returns a schedule command string for allocating the {@code schedule} with contact of index {@code index}.
     */
    public static String getScheduleCommand(Index index, Schedule schedule) {
        return ScheduleCommand.COMMAND_WORD + " " + index.getOneBased() + " " + getScheduleDetails(schedule);
    }

    /**
     * Returns a schedule command string for allocating the {@code schedule} with contact of index {@code index}
     * using schedule command's alias.
     */
    public static String getScheduleCommandUsingAlias(Index index, Schedule schedule) {
        return ScheduleCommand.COMMAND_ALIAS + " " + index.getOneBased() + " " + getScheduleDetails(schedule);
    }

    /**
     * Returns the part of command string for the given {@code schedule}'s details.
     */
    public static String getScheduleDetails(Schedule schedule) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DATE + schedule.getScheduleDate().toString() + " ");
        sb.append(PREFIX_ACTIVITY + schedule.getActivity().toString());
        return sb.toString();
    }
}

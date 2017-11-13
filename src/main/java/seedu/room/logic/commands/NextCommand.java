package seedu.room.logic.commands;

import seedu.room.commons.core.EventsCenter;
import seedu.room.commons.events.ui.ChangeMonthRequestEvent;

//@@author Haozhe321
public class NextCommand extends Command{
    public static final String COMMAND_WORD = "next";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Go to the next month in the calendar\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SWITCH_TAB_SUCCESS = "Switched to next month on calendar";


    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ChangeMonthRequestEvent(1));
        return new CommandResult(MESSAGE_SWITCH_TAB_SUCCESS);
    }
}

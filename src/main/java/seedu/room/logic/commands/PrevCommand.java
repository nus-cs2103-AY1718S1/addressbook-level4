package seedu.room.logic.commands;


import seedu.room.commons.core.EventsCenter;
import seedu.room.commons.events.ui.ChangeMonthRequestEvent;

public class PrevCommand extends Command{
    public static final String COMMAND_WORD = "prev";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Go to the previous month in the calendar\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SWITCH_TAB_SUCCESS = "Switched to previous month on calendar";


    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ChangeMonthRequestEvent(0));
        return new CommandResult(MESSAGE_SWITCH_TAB_SUCCESS);
    }


}

package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Selection;
import seedu.address.commons.events.ui.JumpToBrowserListRequestEvent;
import seedu.address.commons.events.ui.ShowBrowserEvent;
import seedu.address.commons.events.ui.ShowMeetingEvent;
import seedu.address.logic.commands.exceptions.CommandException;


//@@author fongwz
/**
 * Chooses the display screen mode
 */
public class ChooseCommand extends Command {

    public static final String COMMAND_WORD = "choose";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : selects the type of display in the main browser window.\n"
            + "Parameters: <TYPE>\n"
            + "Example: choose linkedin";


    public static final String MESSAGE_SUCCESS = "Selected type ";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD + " TYPE";

    private final String targetDisplay;

    public ChooseCommand(String targetDisplay) {
        this.targetDisplay = targetDisplay;
    }

    @Override
    public CommandResult execute() throws CommandException {

        if (Selection.getSelectionStatus() == false && !targetDisplay.equals("meeting")) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_SELECTED);
        }

        if (targetDisplay.equals("meeting")) {
            EventsCenter.getInstance().post(new ShowMeetingEvent());
            EventsCenter.getInstance().post(new JumpToBrowserListRequestEvent(targetDisplay));


        } else if (targetDisplay.equals("linkedin") || targetDisplay.equals("google") || targetDisplay.equals("maps")) {
            EventsCenter.getInstance().post(new ShowBrowserEvent());
            EventsCenter.getInstance().post(new JumpToBrowserListRequestEvent(targetDisplay));

        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_BROWSER_INDEX);
        }

        return new CommandResult(MESSAGE_SUCCESS + targetDisplay);
    }

}

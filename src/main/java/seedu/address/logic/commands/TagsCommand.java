package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowTagListViewEvent;

/**
 * Lists all persons in the address book to the user.
 */
public class TagsCommand extends Command {

    public static final String COMMAND_WORD = "tags";
    public static final String COMMAND_ALIAS = "t";

    public static final String MESSAGE_SUCCESS = "Listed all tags";


    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowTagListViewEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

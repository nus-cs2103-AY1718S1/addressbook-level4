package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEETINGS;

/**
 * Lists all meetings in the address book to the user.
 */

public class ListMeetingCommand extends Command {

    public static final String COMMAND_WORD = "listmeeting";
    public static final String COMMAND_ALIAS = "lm";

    public static final String MESSAGE_SUCCESS = "Listed all meetings";


    @Override
    public CommandResult execute() {
        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

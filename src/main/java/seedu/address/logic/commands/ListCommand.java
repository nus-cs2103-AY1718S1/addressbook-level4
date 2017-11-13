package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PopulateBirthdayEvent;

/**
 * Lists all persons in UniCity to the user.
 */
public class ListCommand extends Command {

    //@@author LeeYingZheng
    public static final String COMMAND_WORDVAR_1 = "list";
    public static final String COMMAND_WORDVAR_2 = "l";
    //@@author

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        EventsCenter.getInstance().post(new PopulateBirthdayEvent(model.getFilteredPersonList()));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

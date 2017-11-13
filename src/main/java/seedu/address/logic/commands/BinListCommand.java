package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.commands.DisplayBinEvent;

/**
 * Lists all persons in the address book to the user.
 */
public class BinListCommand extends Command {

    public static final String COMMAND_WORD = "bin";

    public static final String MESSAGE_SUCCESS = "Listed all deleted persons";


    //@@author frozventus
    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new DisplayBinEvent());
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

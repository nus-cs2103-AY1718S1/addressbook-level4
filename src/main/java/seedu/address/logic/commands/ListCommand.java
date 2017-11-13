package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PLACES;

/**
 * Lists all places in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_WORD_ALIAS = "l";

    public static final String MESSAGE_SUCCESS = "Listed all places";


    @Override
    public CommandResult execute() {
        model.updateFilteredPlaceList(PREDICATE_SHOW_ALL_PLACES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

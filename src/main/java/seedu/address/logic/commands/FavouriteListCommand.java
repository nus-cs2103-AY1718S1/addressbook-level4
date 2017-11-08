package seedu.address.logic.commands;

//@@author itsdickson

import seedu.address.model.person.NameContainsFavouritePredicate;

/**
 * Lists all favourited persons in the address book to the user.
 */
public class FavouriteListCommand extends Command {

    public static final String COMMAND_WORD = "favouritelist";
    public static final String COMMAND_ALIAS = "favlist";

    public static final String MESSAGE_SUCCESS = "Listed all favourited persons";

    private static final NameContainsFavouritePredicate predicate = new NameContainsFavouritePredicate();

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
//@@author

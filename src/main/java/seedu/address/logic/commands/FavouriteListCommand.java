package seedu.address.logic.commands;

//@@author wangyiming1019
import seedu.address.model.person.NameContainsFavouritePredicate;

/**
 * Lists all favourited persons in the address book to the user.
 */
public class FavouriteListCommand extends Command {

    public static final String COMMAND_WORD = "showfavourite";
    public static final String COMMAND_ALIAS = "sfav";

    public static final String MESSAGE_SUCCESS = "Listed all favourited persons.";

    private static final NameContainsFavouritePredicate predicate = new NameContainsFavouritePredicate();

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";
    public static final String COMMAND_OPTION_FAV = FavoriteCommand.COMMAND_WORD;

    public static final String MESSAGE_SUCCESS_LIST_ALL = "Listed all persons";
    public static final String MESSAGE_SUCCESS_LIST_FAV = "Listed all favorite persons";

    private boolean hasOptionFav = false;

    public ListCommand(String args) {
        if (args.trim().equals(COMMAND_OPTION_FAV)) {
            hasOptionFav = true;
        }
    }

    @Override
    public CommandResult execute() {
        if (hasOptionFav) {
            model.updateFilteredPersonList(showAllFavoritePersons());
            return new CommandResult(MESSAGE_SUCCESS_LIST_FAV);
        } else {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS_LIST_ALL);
        }
    }

    /**
     * Returns a predicate consisting of all ReadOnlyPerson who has been favorited
     */
    public static Predicate<ReadOnlyPerson> showAllFavoritePersons() {
        return p -> p.getFavorite().isFavorite();
    }
}

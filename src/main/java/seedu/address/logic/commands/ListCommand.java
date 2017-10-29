package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALT = "l";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all persons in the address book.\n"
            + ": Specify prefix f/ to list all person(s) marked as 'Favourite'.\n"
            + "Parameters: [f/]\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all persons";
    public static final String MESSAGE_LIST_FAVOURITE_SUCCESS = "Listed all 'favourite' persons";

    private final boolean listFavourite;

    public ListCommand(boolean listFavourite) {
        this.listFavourite = listFavourite;
    }

    @Override
    public CommandResult execute() {
        if (listFavourite) {
            model.updateFilteredPersonList(isFavourite());
            return new CommandResult(MESSAGE_LIST_FAVOURITE_SUCCESS);
        } else {
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS);
        }

    }

    public static Predicate<ReadOnlyPerson> isFavourite() {
        return p ->
                p.getFavourite().getStatus();
    }
}

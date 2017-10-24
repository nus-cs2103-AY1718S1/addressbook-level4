package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Lists all favourite persons in the address book to the user.
 */
public class ListFaveCommand extends Command {
    public static final String COMMAND_WORD = "listFave";
    public static final String COMMAND_ALT = "lf";

    public static final String MESSAGE_SUCCESS = "Listed all favourite persons";


    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(isFavourite());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public static Predicate<ReadOnlyPerson> isFavourite() {
        return p ->
                p.getFavourite().getStatus();
    }
}

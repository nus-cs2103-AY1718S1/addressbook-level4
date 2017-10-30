package seedu.address.logic.commands;

import static seedu.address.logic.commands.SortCommand.MESSAGE_EMPTY_LIST;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.NoPersonsException;

/**
 * Lists all persons in the address book to the user with priority to favourites
 */
public class ModListCommand extends Command {
    public static final String COMMAND_WORD = "list2";
    public static final String COMMAND_ALT = "l2";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute() throws CommandException {
        Comparator<ReadOnlyPerson> faveComparator = getFaveComparator();
        try {
            model.sortPerson(faveComparator, false);
        } catch (NoPersonsException npe) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    private Comparator<ReadOnlyPerson> getFaveComparator() {
        return (f1, f2) -> Boolean.compare(f2.getFavourite().getStatus(), f1.getFavourite().getStatus());
    }
}

package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

//@@author limcel
/**
 * Sorts all contacts in alphabetical order by their names from the address book.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "st";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " OR "
            + COMMAND_ALIAS
            + ": Sort all contacts names in alphabetical order. ";

    public static final String MESSAGE_SUCCESS = "All contacts are sorted alphabetically by name.";

    public SortCommand() {
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.sortByPersonName();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

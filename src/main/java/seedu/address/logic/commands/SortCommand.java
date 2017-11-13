// @@author ahmadalkaff
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Sorts all persons in the address book in an alphabetical
 * order to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_SUCCESS = "Sorted all persons in the address book alphabetically.";

    @Override
    public CommandResult execute() {
        model.sortPersonListByName();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

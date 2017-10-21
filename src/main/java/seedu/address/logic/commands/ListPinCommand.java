package seedu.address.logic.commands;

import seedu.address.model.person.PersonIsPinnedPredicate;

/**
 * Lists all pinned persons in the address book to the user.
 */
public class ListPinCommand extends Command {

    public static final String COMMAND_WORD = "listpin";

    public static final String MESSAGE_SUCCESS = "Listed all pinned person";

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(new PersonIsPinnedPredicate());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

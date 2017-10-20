package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ONLY_PINNED;

/**
 * Lists all persons in the address book to the user.
 */
public class ListPinCommand extends Command {

    public static final String COMMAND_WORD = "listpin";

    public static final String MESSAGE_SUCCESS = "Listed all pinned person";

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ONLY_PINNED);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

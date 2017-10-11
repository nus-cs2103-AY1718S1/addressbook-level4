package seedu.address.logic.commands;

import seedu.address.model.person.NameIsPrivatePredicate;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    private NameIsPrivatePredicate predicate = new NameIsPrivatePredicate(true);

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

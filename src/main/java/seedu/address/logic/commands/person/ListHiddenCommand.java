package seedu.address.logic.commands.person;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all hidden persons in the addressbook to the user.
 */
public class ListHiddenCommand extends Command {

    public static final String COMMAND_WORD = "listhidden";

    public static final String MESSAGE_SUCCESS = "Listed all hidden persons";

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ONLY_HIDDEN);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

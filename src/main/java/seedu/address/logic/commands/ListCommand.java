package seedu.address.logic.commands;

import java.util.Set;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.tag.Tag;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: \n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + "friends";

    private final Set<Tag> toFind;

    public ListCommand() { toFind = null;}
    
    public ListCommand(Set<Tag> enteredTag) {
        toFind = enteredTag;
    }
    
    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

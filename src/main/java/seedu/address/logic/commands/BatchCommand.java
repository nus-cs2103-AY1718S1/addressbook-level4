package seedu.address.logic.commands;

import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class BatchCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "batch";
    public static final String COMMAND_ALIAS = "b"; // shorthand equivalent alias

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all persons with the given tags.\n"
            + COMMAND_ALIAS + ": Shorthand equivalent for batch delete. \n"
            + "Parameters: TAG (must be a tag that at least one person has)\n"
            + "Example 1: " + COMMAND_ALIAS + " friends \n"
            + "Example 2: " + COMMAND_WORD + " colleagues";

    public static final String MESSAGE_BATCH_DELETE_SUCCESS = "Deleted Persons with Tags: %1$s";

    private final Set<Tag> tagsToDelete;

    public BatchCommand(Set<Tag> tagsToDelete) {
        this.tagsToDelete = tagsToDelete;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.deletePersonsByTags(tagsToDelete);
        } catch (PersonNotFoundException e) {
            throw new CommandException("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_BATCH_DELETE_SUCCESS, tagsToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BatchCommand // instanceof handles nulls
                && this.tagsToDelete.equals(((BatchCommand) other).tagsToDelete)); // state check
    }
}

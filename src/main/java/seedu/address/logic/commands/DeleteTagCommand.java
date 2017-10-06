package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteTagCommand extends Command {
    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a tag from all records.\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";

    private final Tag tagToDelete;

    public DeleteTagCommand(Tag tagToDelete) {
        this.tagToDelete = tagToDelete;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.deleteTag(tagToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        } catch (DuplicatePersonException dpe) {
            assert false : "Update will cause two contacts to be the same";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, tagToDelete));
    }
}

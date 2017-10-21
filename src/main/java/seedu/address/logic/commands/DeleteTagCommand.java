package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * @author Sri-vatsa
 * Deletes all tags identified from the address book.
 */
public class DeleteTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteTag";
    public static final String COMMAND_ALIAS = "dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a particular tag from everyone.\n"
            + "Parameters: Tag(text)\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD + " TAG";

    private final String tagToDelete;

    public DeleteTagCommand(String tag) {
        this.tagToDelete = tag;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        try {
            Tag delTag = new Tag(tagToDelete);
            model.deleteTag(delTag);
        } catch (IllegalValueException ive) {
            assert false : "The tag is not a proper value";
        } catch (PersonNotFoundException pnfe) {
            assert  false: "The person associated with the tag cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, tagToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTagCommand // instanceof handles nulls
                && this.tagToDelete.equals(((DeleteTagCommand) other).tagToDelete)); // state check
    }
}

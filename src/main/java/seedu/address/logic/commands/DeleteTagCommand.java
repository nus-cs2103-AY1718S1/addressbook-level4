package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.TagNotFoundException;
import seedu.address.model.tag.Tag;

import java.util.List;

public class DeleteTagCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deletetag";
    public static final String COMMAND_ALIAS = "dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes all instances of a given tag.\n"
            + "Parameters: TAG NAME\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";

    private final Tag targetTag;

    public DeleteTagCommand(String targetTag) throws IllegalValueException {
        this.targetTag = new Tag(targetTag);
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        try {
            model.deleteTag(targetTag);
        } catch (DuplicatePersonException | PersonNotFoundException ex) {
            assert false: "Not creating a new person";
        } catch (TagNotFoundException tnfe) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG_DISPLAYED);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, targetTag.tagName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTagCommand // instanceof handles nulls
                && this.targetTag.equals(((DeleteTagCommand) other).targetTag)); // state check
    }
}

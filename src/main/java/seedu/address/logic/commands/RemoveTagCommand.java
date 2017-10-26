package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INPUT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.TagNotFoundException;


/**
 * Remove a tag from the tag lists of the address book and all persons in the address book
 */
public class RemoveTagCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "removeTag";

    public static final String COMMAND_PARAMETERS = "TAGNAME (must be alphanumeric)";

    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_PARAMETERS;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ":Removes the tag identified by the user input from the tag lists of the address book and all"
            + " persons in the address book.\n"
            + "Parameters: " + COMMAND_PARAMETERS + "\n"
            + "Example: " + COMMAND_WORD + " friend";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed Tag: %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag is not found.";


    private final String targetTag;

    public RemoveTagCommand(String targetTag) {
        this.targetTag = targetTag;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.removeTag(targetTag);
        } catch (IllegalValueException ive) {
            assert false : MESSAGE_INVALID_INPUT;
        } catch (TagNotFoundException tnfe) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }

        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS, targetTag));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && this.targetTag.equals(((RemoveTagCommand) other).targetTag)); // state check
    }
}

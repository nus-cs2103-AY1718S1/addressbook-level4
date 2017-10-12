package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INPUT;
import static seedu.address.commons.core.Messages.MESSAGE_TAG_NOT_FOUND;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.TagNotFoundException;


/**
 * Remove a tag from the tag lists of the address book and all persons in the address book
 */
public class RemoveTagCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ":Removes the tag identified by the user input from the tag lists of the address book and all"
            + " persons in the address book.\n"
            + "Parameters: TAG NAME (must be alphanumeric)\n"
            + "Example: " + COMMAND_WORD + " friend";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed Tag: %1$s";

    private final String targetTag;

    public RemoveTagCommand(String targetTag) {
        this.targetTag = targetTag;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.removeTag(targetTag);
        } catch (IllegalValueException ive) {
            throw new CommandException(MESSAGE_INVALID_INPUT);
        } catch (TagNotFoundException tnfe) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The person to be updated is not found.";
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

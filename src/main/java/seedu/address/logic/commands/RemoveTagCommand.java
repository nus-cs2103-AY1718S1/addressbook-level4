package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;

/**
 * Deletes a tag from the entire AddressBook
 */
//@@author justuswah
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removeTag";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the specified tag from all contacts in the AddressBook\n"
            + "Parameters: (TAG_NAME) \n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Tag Removed";
    private final Tag toRemove;

    public RemoveTagCommand(Tag toRemove) {
        this.toRemove = toRemove;
    }

    /**Searches the entire AddressBook for the {@Code toRemove Tag}
     *
     *
     */

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        if (!model.getAddressBook().getTagList().contains(toRemove)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG);
        } else {
            model.removeTag(toRemove);
            logger.log(Level.FINE, "Tag " + toRemove.tagName + " Removed");
            return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS));
        }
    }


}

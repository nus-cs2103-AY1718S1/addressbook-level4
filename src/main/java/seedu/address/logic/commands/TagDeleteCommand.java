//@@author tbhbhbh
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Deletes a tag identified using its tag name from the address book.
 */
public class TagDeleteCommand extends UndoableCommand {

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted tag: %1$s";
    public static final String COMMAND_WORD = "tagdelete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tag identified by the tag name in the address book.\n"
            + "Parameters: TAGNAME\n"
            + "Example: " + COMMAND_WORD + " friends";

    private final String tagName;
    private Tag tagToDelete;

    public TagDeleteCommand(String tagName) {
        requireNonNull(tagName);
        this.tagName = tagName.trim();

    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<Tag> tagList = model.getAddressBook().getTagList();

        try {
            System.out.println("" + Tag.MESSAGE_TAG_CONSTRAINTS);
            tagToDelete = new Tag(tagName);
            if (!tagList.contains(tagToDelete)) {
                throw new CommandException(Messages.MESSAGE_INVALID_TAG_NAME);
            }
            model.deleteTag(tagToDelete);
        } catch (IllegalValueException ive) {
            throw new CommandException(Tag.MESSAGE_TAG_CONSTRAINTS);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, tagName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagDeleteCommand // instanceof handles nulls
                && this.tagName.equals(((TagDeleteCommand) other).tagName)); // state check
    }
}

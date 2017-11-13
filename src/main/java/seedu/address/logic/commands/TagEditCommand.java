//@@author tbhbhbh
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;
import static seedu.address.model.tag.Tag.isValidTagName;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Edits a tag identified using its tag name from the address book and
 * replaces it with a new tag name provided by the user.
 */
public class TagEditCommand extends UndoableCommand {
    public static final String MESSAGE_EDIT_TAG_SUCCESS = "Edited tag: %1$s to %2$s";
    public static final String COMMAND_WORD = "tagedit";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the tag identified by the tag name in the address book to a new tag name provided by the user.\n"
            + "Parameters: TAGNAME NEWTAGNAME\n"
            + "Example: " + COMMAND_WORD + " groupmates " + "friends";

    private final String oldTagName;
    private final String newTagName;
    private Tag tagToEdit;
    private Tag newTag;

    public TagEditCommand(String oldTagName, String newTagName) {
        requireNonNull(oldTagName);
        requireNonNull(newTagName);

        this.oldTagName = oldTagName.trim();
        this.newTagName = newTagName.trim();
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<Tag> tagList = model.getAddressBook().getTagList();

        try {
            newTag = new Tag(newTagName);
            tagToEdit = new Tag(oldTagName);
            if (!isValidTagName(newTag.tagName)) {
                throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS);
            }
            if (newTag.tagName.isEmpty() || tagToEdit.tagName.isEmpty()) {
                throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
            }
            if (!tagList.contains(tagToEdit)) {
                throw new CommandException(Messages.MESSAGE_UNKNOWN_TAG_NAME);
            }
            model.editTag(tagToEdit, newTag);
        } catch (IllegalValueException ive) {
            throw new CommandException(Tag.MESSAGE_TAG_CONSTRAINTS);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(
                String.format(
                        MESSAGE_EDIT_TAG_SUCCESS,
                        tagToEdit.toString(),
                        newTag.toString()
                )
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagEditCommand // instanceof handles nulls
                && this.oldTagName.equals(((TagEditCommand) other).oldTagName)); // state check
    }
}

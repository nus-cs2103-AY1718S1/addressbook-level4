package seedu.address.logic.commands;
//@@author Esilocke
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_FULL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
/**
 * Renames and edits the specified tag in the address book.
 */

public class EditTagCommand extends EditCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_TAG_FULL
            + ": Edits the specified tag "
            + "and updates all existing contacts that shares this tag with the new value.\n"
            + "Parameters: TAGTOCHANGE (must be alphanumerical) "
            + "TAGNEWNAME (must be alphanumerical)\n"
            + "Example: " + COMMAND_WORD + " friends enemies";

    public static final String MESSAGE_EDIT_TAG_SUCCESS = "Replaced tag %1$s with %2$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "No such tag was found in the address book.";
    public static final String MESSAGE_INSUFFICIENT_ARGS = "Only 2 arguments should be provided!";
    public static final String MESSAGE_INVALID_TAG_NAME = "Tag names must be alphanumerical.";
    public static final String MESSAGE_DUPLICATE_TAGS = "The new name of the tag cannot be the same as the old name.";
    private final ArrayList<Index> affectedIndexes;
    private final Tag toEdit;
    private final Tag newTag;

    /**
     * @param toEdit The value of the tag to be changed
     * @param newTag The new value for the tag
     */
    public EditTagCommand(Tag toEdit, Tag newTag) {
        requireNonNull(toEdit);
        requireNonNull(newTag);
        this.toEdit = toEdit;
        this.newTag = newTag;
        this.affectedIndexes = new ArrayList<>();
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        ReadOnlyPerson currentlyViewed;
        Set<Tag> tagSet;
        boolean tagNotPresent = true;
        for (int i = 0; i < lastShownList.size(); i++) {
            currentlyViewed = lastShownList.get(i);
            tagSet = currentlyViewed.getTags();
            if (tagSet.contains(toEdit)) {
                tagNotPresent = false;
                affectedIndexes.add(Index.fromZeroBased(i));
            }
        }

        if (tagNotPresent) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }
        try {
            model.editTag(toEdit, newTag, affectedIndexes);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TAGS);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_TAG_SUCCESS, toEdit.tagName, newTag.tagName));
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTagCommand)) {
            return false;
        }

        // state check
        EditTagCommand e = (EditTagCommand) other;
        return toEdit.equals(e.toEdit)
                && newTag.equals(e.newTag);
    }
}

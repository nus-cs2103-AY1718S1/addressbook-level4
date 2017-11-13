package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author vivekscl
/**
 * Removes a tag from identified persons using the last displayed indexes from the address book.
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORDVAR_1 = "removetag";
    public static final String COMMAND_WORDVAR_2 = "rt";

    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + " OR "
            + COMMAND_WORDVAR_2
            + ": Removes the given tag from identified person by the list of index numbers used in the last person "
            + "listing."
            + " Command is case-sensitive. \n"
            + "Parameters: "
            + "[INDEX] [MORE INDEXES] (every index must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example 1: " + COMMAND_WORDVAR_1 + " 1 2 3 t/friends \n"
            + "Example 2: " + COMMAND_WORDVAR_2.toUpperCase() + " 2 5 t/classmate \n";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed Tag: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_NO_SUCH_TAG = "This tag does not exist in any of the given persons.";

    private final ArrayList<Index> targetIndexes;
    private final Tag tagToRemove;

    /**
     * @param targetIndexes of the persons in the filtered person list to edit
     * @param tagToRemove tag to remove from given target indexes
     */
    public RemoveTagCommand(ArrayList<Index> targetIndexes, Tag tagToRemove) {

        requireNonNull(targetIndexes);
        requireNonNull(tagToRemove);

        this.targetIndexes = targetIndexes;
        this.tagToRemove = tagToRemove;
    }

    /**
     * Removes tag to remove from each target person that has the given tag.
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        targetIndexesOutOfBoundsChecker(lastShownList);
        tagInTargetIndexesChecker(lastShownList);

        try {
            model.removeTag(this.targetIndexes, this.tagToRemove, COMMAND_WORDVAR_1);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS, tagToRemove));
    }

    /**
     * Checks if all target indexes are not out of bounds.
     */
    private void targetIndexesOutOfBoundsChecker(List<ReadOnlyPerson> lastShownList) throws CommandException {
        for (Index index : targetIndexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
    }

    /**
     * Checks if the tag exists among the given target indexes.
     */
    private void tagInTargetIndexesChecker(List<ReadOnlyPerson> lastShownList) throws CommandException {
        for (Index index : targetIndexes) {
            int targetIndex = index.getZeroBased();
            ReadOnlyPerson readOnlyPerson = lastShownList.get(targetIndex);
            if (readOnlyPerson.getTags().contains(tagToRemove)) {
                return;
            }
        }
        throw new CommandException(MESSAGE_NO_SUCH_TAG);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveTagCommand)) {
            return false;
        }

        // state check
        RemoveTagCommand e = (RemoveTagCommand) other;
        return targetIndexes.equals(e.targetIndexes)
                && tagToRemove.equals(e.tagToRemove);
    }
}

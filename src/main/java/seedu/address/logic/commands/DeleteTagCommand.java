package seedu.address.logic.commands;

//@@author wangyiming1019
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_FULL;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Deletes a tag from identified persons using the last displayed indexes from the address book.
 */
public class DeleteTagCommand extends DeleteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_TAG_FULL
            + ": Deletes the tag from the persons with the index numbers used in the last person list."
            + " Command is case-sensitive. \n"
            + "Parameters: "
            + "[INDEX] [MORE INDEXES] (index must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG]... \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG_FULL + " 1 2 t/friends \n";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_NONEXISTENT_TAG = "The target persons do not have input tags.";

    private final ArrayList<Index> targetIndexes;
    private final Tag toDelete;

    /**
     * @param targetIndexes of the persons in the filtered person list to edit
     * @param toDelete tag to delete from given target indexes
     */
    public DeleteTagCommand(Tag toDelete, ArrayList<Index> targetIndexes) {

        requireNonNull(targetIndexes);
        requireNonNull(toDelete);

        this.targetIndexes = targetIndexes;
        this.toDelete = toDelete;
    }
    /**
     * @param toDelete tag to delete from given target indexes
     */
    public DeleteTagCommand(Tag toDelete) {

        requireNonNull(toDelete);
        this.targetIndexes = new ArrayList<>();
        this.toDelete = toDelete;
    }
    /**
     * Check whether the index within the range then checks whether the tag exists among the specific persons.
     * If yes, delete the tag from the specific person in the person list.
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        boolean nonexistentTag = true;

        if (targetIndexes.size() == 0) {
            reinitlializeArray(lastShownList.size());
        }

        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
        // check any person have the tag
        for (int i = 0; i < targetIndexes.size(); i++) {
            int targetIndex = targetIndexes.get(i).getZeroBased();
            ReadOnlyPerson readOnlyPerson = lastShownList.get(targetIndex);
            if (readOnlyPerson.getTags().contains(toDelete)) {
                nonexistentTag = false;
            }
        }

        if (nonexistentTag) {
            throw  new CommandException(MESSAGE_NONEXISTENT_TAG);
        }

        try {
            model.deleteTag(this.toDelete, this.targetIndexes);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, toDelete));
    }

    private void reinitlializeArray(int size) {
        for (int i = 0; i < size; i++) {
            targetIndexes.add(Index.fromZeroBased(i));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        // state check
        DeleteTagCommand e = (DeleteTagCommand) other;
        return targetIndexes.equals(e.targetIndexes)
                && toDelete.equals(e.toDelete);
    }
}

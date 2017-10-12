package seedu.address.logic.commands;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Remove same tags from a list of people
 */
public class DetagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "detag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove tags from multiple people.";
    public static final String MESSAGE_DETAG_PERSONS_SUCCESS = "Old tags removed.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_MISSING_TAG = "One or more person(s) don't has this tag";
    private final Index[] indices;
    private final Tag tag;

    /**
     * Create a DetagCommand to delete the specific
     * @param indices
     * @param tag
     */
    public DetagCommand(Index[] indices, Tag tag) {
        requireNonNull(indices);
        requireNonNull(tag);
        this.indices = indices;
        this.tag = tag;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        for (Index targetIndex: indices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        try {
            model.deleteTag(indices, tag);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_DETAG_PERSONS_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DetagCommand // instanceof handles nulls
                && tag.equals(((DetagCommand) other).tag)); // state check
    }
}

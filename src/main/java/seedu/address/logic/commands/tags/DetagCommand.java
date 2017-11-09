package seedu.address.logic.commands.tags;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.TagNotFoundException;
import seedu.address.model.tag.Tag;

//@@author tpq95
/**
 * Remove same tags from a list of people
 */
public class DetagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "detag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove tags from multiple people. "
            + "Parameter: "
            + "INDEX, [INDEX]... "
            + PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_WORD + " "
            + "1, 2 "
            + PREFIX_TAG + "friends";

    public static final String MESSAGE_DETAG_PERSONS_SUCCESS = "Removed tag: %1$s";
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
        List<ReadOnlyPerson> indexToRemove = new ArrayList();

        for (Index targetIndex: indices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson personToEdit = lastShownList.get(targetIndex.getZeroBased());

            // Check whether person contains tag
            if (!personToEdit.getTags().contains(tag)) {
                throw new CommandException(MESSAGE_MISSING_TAG);
            }

            // Save the valid persons to list
            indexToRemove.add(personToEdit);
        }

        for (ReadOnlyPerson targetPerson: indexToRemove) {
            try {
                model.deleteTag(targetPerson, tag);
            } catch (TagNotFoundException dsd) {
                throw new CommandException(MESSAGE_MISSING_TAG);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DETAG_PERSONS_SUCCESS, tag.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DetagCommand // instanceof handles nulls
                && tag.equals(((DetagCommand) other).tag)); // state check
    }
}

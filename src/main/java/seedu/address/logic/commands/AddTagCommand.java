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
 * Adds a tag to the persons in the latest list from the address book.
 */
public class AddTagCommand extends AddCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_TAG_FULL
            + ": Adds the tag to the persons with the index numbers used in the last person list."
            + " Command is case-sensitive. \n"
            + "Parameters: "
            + "[INDEX] [MORE INDEXES] (index must be a positive integer)"
            + "[" + PREFIX_TAG + "TAG]... \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG_FULL + " 1 2 t/friends \n";


    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added Tag: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists in all "
            + "target persons in the current list.";

    private final ArrayList<Index> targetIndexes;
    private final Tag addTag;

    /**
     * @param targetIndexes of the persons in the filtered person list to edit
     * @param addTag tag to add to given target indexes
     */
    public AddTagCommand(Tag addTag, ArrayList<Index> targetIndexes) {

        requireNonNull(targetIndexes);
        requireNonNull(addTag);

        this.targetIndexes = targetIndexes;
        this.addTag = addTag;
    }
    /**
     * @param addTag tag to add to all entries in the address book
     */
    public AddTagCommand(Tag addTag) {

        requireNonNull(addTag);

        this.targetIndexes = new ArrayList<>();
        this.addTag = addTag;
    }
    /**
     * Check whether the index within the range then checks whether the specific persons have the tag.
     * If not, add the tag to the person that doesn't have the given tag.
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        boolean allPersonsContainGivenTag = true;
        if (targetIndexes.size() == 0) {
            reinitlializeArray(lastShownList.size());
        }
        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
        // check whether all persons have the given tag
        for (int i = 0; i < targetIndexes.size(); i++) {
            int personIndex = targetIndexes.get(i).getZeroBased();
            ReadOnlyPerson personToAddTag = lastShownList.get(personIndex);
            if (!personToAddTag.getTags().contains(addTag)) {
                allPersonsContainGivenTag = false;
            }
        }

        if (allPersonsContainGivenTag) {
            throw  new CommandException(MESSAGE_DUPLICATE_TAG);
        }

        try {
            model.addTag(this.addTag, this.targetIndexes);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, addTag));
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
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        // state check
        AddTagCommand e = (AddTagCommand) other;
        return targetIndexes.equals(e.targetIndexes)
                && addTag.equals(e.addTag);
    }
}

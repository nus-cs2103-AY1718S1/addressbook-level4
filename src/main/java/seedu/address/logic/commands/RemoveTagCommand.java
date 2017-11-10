package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.NoSuchTagException;
import seedu.address.model.tag.Tag;

//@@author freesoup
/**
 * Removes a tag from the address book.
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removetag";
    public static final String COMMAND_USAGE = COMMAND_WORD + " ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes specified tag\n"
            + "Parameters: RANGE (all OR INDEX(Index must be positive)) followed by TAG (must be a string)\n"
            + "Example: removetag 30 prospective OR removetag all colleagues";

    public static final String MESSAGE_SUCCESS = "Tag removed";
    public static final String MESSAGE_EXCEEDTAGNUM = "Please type one TAG to be removed";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag given does not exist in address book";
    public static final String MESSAGE_TAG_NOT_FOUND_IN = "Index %s does not have the given tag.";

    private final Tag toRemove;
    private final Optional<Index> index;

    /**
     * Creates an RemoveTagCommand to remove the specified {@code Tag} from a (@code Index) if given.
     */
    public RemoveTagCommand (Tag tag) {
        this.toRemove = tag;
        this.index = Optional.ofNullable(null);
    }

    public RemoveTagCommand (Index index, Tag tag) {
        this.toRemove = tag;
        this.index = Optional.of(index);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        if (index.orElse(null) == null) {
            removeAllTag(toRemove);
        } else {
            removeOneTag(index.get(), toRemove);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    /**
     * Ensures that the index given can be found within the given list.
     * @param index of the person in the filtered person list to remove tag from.
     * @throws CommandException if the index given is out of the bounds of the filtered list.
     */
    private void requireIndexValid(Index index) throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    /**
     * Removes a tag from a specified person.
     * @param index of the person in the filtered person list to remove tag from.
     * @param toRemove is the tag to be removed from the person
     * @throws CommandException if the tag to be removed does not exist in the person.
     */
    private void removeOneTag(Index index, Tag toRemove) throws CommandException {
        requireIndexValid(index);
        try {
            model.removeTag(index, toRemove);
        } catch (NoSuchTagException nste) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND_IN, index.getOneBased()));
        }
    }

    /**
     * Removes a tag from the whole addressbook.
     * @param toRemove is the tag to be removed from the addressbook.
     * @throws CommandException if tag does not exist in the addressbook.
     */
    private void removeAllTag(Tag toRemove) throws CommandException {
        try {
            model.removeTag(toRemove);
        } catch (NoSuchTagException nste) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && this.toRemove.equals(((RemoveTagCommand) other).toRemove) // state check
                && this.index.equals(((RemoveTagCommand) other).index)); // state check
    }

}

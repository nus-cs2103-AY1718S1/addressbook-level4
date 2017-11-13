package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Deletes a tag from every contact if index not stated. Else, only deletes
 * the tag from person's identified using it's last displayed index from the address book.
 */
//@@author sidhmads
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removeTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tag identified from all person if no index stated."
            + "Else, removes only from those selected indexes.\n"
            + "Example with index: " + COMMAND_WORD + " rm/1 2 t/friends\n"
            + "Example without index: " + COMMAND_WORD + " t/friends";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final ArrayList<Index> indexes;
    private final Set<Tag> tags;

    public RemoveTagCommand(ArrayList<Index> indexes, Set<Tag> tags) {

        this.indexes = indexes;
        this.tags = tags;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        ObservableList<ReadOnlyPerson> personToRemoveTag = FXCollections.observableArrayList();
        for (Index i : this.indexes) {
            if (i.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personToRemoveTag.add(lastShownList.get(i.getZeroBased()));
        }
        try {
            model.removeTag(personToRemoveTag, this.tags);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target Tag cannot be missing";
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (IllegalValueException ive) {
            throw new CommandException("The Tag is invalid!");
        }
        if (this.indexes.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, this.tags.toString()));
        } else {
            return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS,
                    this.tags.toString()) + String.format("from %s", personToRemoveTag.toString()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && this.tags.equals(((RemoveTagCommand) other).tags))
                && this.indexes.equals(((RemoveTagCommand) other).indexes); // state check
    }
}

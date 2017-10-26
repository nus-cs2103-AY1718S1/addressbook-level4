package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;


/**
 * Add the specified tag to the address book
 */
public class AddTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtag";
    public static final String COMMAND_ALIAS = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add the tag to a person by the index number used "
            + "in the last person listing.\n"
            + "Parameters: [TAG]... [INDEX]...(INDEX must be positive integer)\n"
            + "Example: " + COMMAND_WORD + " friends 1";

    public static final String MESSAGE_ADDED_SUCCESS = "Added Tag: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "Tag: %1$s already exist in";

    private final Set<Tag> tag;
    private final Set<Index> index;
    private final String indexDisplay;

    /**
     *
     * @param tag to be added to address book
     * @param index of the person in the filtered list to remove tag
     */
    public AddTagCommand(Set<Tag> tag, Set<Index> index, String indexDisplay)  {
        this.tag = tag;
        this.index = index;
        this.indexDisplay = indexDisplay;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        String successMessage;
        String duplicate;

        for (Index i : index) {
            if (i.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
        successMessage = String.format(MESSAGE_ADDED_SUCCESS + " to index " + indexDisplay + ".", tag);
        duplicate = String.format(MESSAGE_DUPLICATE_TAG + " index: " + indexDisplay + ".", tag);

        try {
            model.addTag(tag, index);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(
                    String.format
                            (MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(duplicate);
        }
        return new CommandResult(successMessage);
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

        boolean check1 = checkEqual(tag, e.tag);
        boolean check2 = checkEqual(index, e.index);
        return check1 && check2;
    }

    /**
     *
     * @param set1
     * @param set2
     * @param <T>
     * @return true if set1 and set2 are identical
     */
    public <T> boolean checkEqual(Set<T> set1, Set<T> set2) {
        Iterator<T> it1 = set1.iterator();
        Iterator<T> it2 = set2.iterator();

        Boolean check = false;

        while (it1.hasNext()) {
            T item = it1.next();
            while (it2.hasNext()) {
                T item2 = it2.next();
                if (item.equals(item2)) {
                    check = true;
                }
            }
        }
        return check;
    }
}


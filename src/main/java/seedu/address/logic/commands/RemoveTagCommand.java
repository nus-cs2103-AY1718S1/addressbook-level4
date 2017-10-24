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
 * Remove the specified tag from address book
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removetag";
    public static final String COMMAND_ALIAS = "rt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Remove the tag from a person by the index number used "
            + "in the last person listing.\n"
            + "Remove the specified tag in the whole address book by excluding the [INDEX] parameter.\n"
            + "Parameters: [TAG]... [INDEX]...(INDEX must be positive integer)\n"
            + "Example: " + COMMAND_WORD + " friends 1";

    public static final String MESSAGE_REMOVE_SUCCESS = "Removed Tag: %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag: %1$s does not exist in";

    private final Set<Tag> tag;
    private final Set<Index> index;

    /**
     *
     * @param tag to be removed from address book
     * @param index of the person in the filtered list to remove tag
     */
    public RemoveTagCommand(Set<Tag> tag, Set<Index> index)  {
        this.tag = tag;
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        String successMessage;
        String notFound;
        List<String> listIndex = new ArrayList<>();
        Iterator<Index> it = index.iterator();

        while (it.hasNext()) {
            int toAdd = it.next().getOneBased();
            listIndex.add(String.valueOf(toAdd));
        }

        String indexOut = listIndex.stream().collect(Collectors.joining(", "));


        if (!index.isEmpty()) {

            for (Index i : index) {
                if (i.getZeroBased() >= lastShownList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
            }
            successMessage = String.format(MESSAGE_REMOVE_SUCCESS + " from index " + indexOut + ".", tag);
            notFound = String.format(MESSAGE_TAG_NOT_FOUND + " index: " + indexOut + ".", tag);
        } else {
            successMessage = String.format(MESSAGE_REMOVE_SUCCESS + " from address book.", tag);
            notFound = String.format(MESSAGE_TAG_NOT_FOUND + " the address book.", tag);
        }


        try {
            model.removeTag(tag, index);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(
                    String.format
                            (MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(notFound);
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
        if (!(other instanceof RemoveTagCommand)) {
            return false;
        }
        // state check
        RemoveTagCommand e = (RemoveTagCommand) other;
        if ((index.size() == 0) && (e.index.size() == 0)) {
            return checkEqual(tag, e.tag);
        }

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


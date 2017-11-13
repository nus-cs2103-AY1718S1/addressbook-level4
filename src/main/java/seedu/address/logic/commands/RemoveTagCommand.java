package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Collections;
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

//@@author NabeelZaheer
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
            + "Parameters: INDEX... TAG...(INDEX must be positive integer)\n"
            + "[INDEX] can be set as a range.\n"
            + "Example: " + COMMAND_WORD + " 1 friends\n"
            + "Example: " + COMMAND_WORD + " 1-4 friends\n";

    public static final String MESSAGE_REMOVE_SUCCESS = "Removed Tag: %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag: %1$s does not exist in";

    private final Set<Tag> tag;
    private final Set<Index> index;
    private final List<String> indexList;

    /**
     *
     * @param tag to be removed from address book
     * @param index of the person in the filtered list to remove tag
     */
    public RemoveTagCommand(Set<Tag> tag, Set<Index> index, List<String> indexDisplay)  {
        this.tag = tag;
        this.index = index;
        this.indexList = indexDisplay;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        String successMessage;
        String notFound;
        Set<Tag> tagDisplaySet;
        List<Integer> numList = new ArrayList<>();


        Iterator<String> it = indexList.iterator();
        while (it.hasNext()) {
            int numToAdd = Integer.parseInt(it.next());
            numList.add(numToAdd);
        }
        Collections.sort(numList);
        indexList.clear();
        for (Integer i : numList) {
            String strToAdd = String.valueOf(i);
            indexList.add(strToAdd);
        }

        String indexDisplay = indexList.stream().collect(Collectors.joining(", "));

        if (!index.isEmpty()) {

            for (Index i : index) {
                if (i.getZeroBased() >= lastShownList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
            }
            successMessage = MESSAGE_REMOVE_SUCCESS + " from index " + indexDisplay + ".";
            notFound = String.format(MESSAGE_TAG_NOT_FOUND + " index: " + indexDisplay + ".", tag);
        } else {
            successMessage = MESSAGE_REMOVE_SUCCESS + " from address book.";
            notFound = String.format(MESSAGE_TAG_NOT_FOUND + " the address book.", tag);
        }

        String completeSuccess;

        try {
            tagDisplaySet = model.removeTag(tag, indexList);
            completeSuccess = String.format(successMessage, tagDisplaySet);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(
                    String.format
                            (MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(notFound);
        }
        return new CommandResult(completeSuccess);

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
        boolean check3 = indexList.equals(e.indexList);
        return check1 && check2 && check3;
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


package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.AccessCount;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author kenpaxtonlim
/**
 * Adds tags to an existing person in the address book.
 */
public class AddRemoveTagsCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "tags";
    public static final String COMMAND_ALIAS = "t";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add or remove tags to the person identified "
            + "by the index number used in the last person listing. \n"
            + "Parameters: TYPE (either add or remove) INDEX (must be a positive integer) "
            + "TAG [TAG] (can add 1 or more tags)... \n"
            + "Example: " + COMMAND_WORD + " add 1 "
            + "NUS CS2103 classmate";

    public static final String MESSAGE_ADD_TAGS_SUCCESS = "Added Tag/s to Person: %1$s";
    public static final String MESSAGE_REMOVE_TAGS_SUCCESS = "Removed Tag/s to Person: %1$s";
    public static final String MESSAGE_NO_TAG = "One or more tags must be entered.";
    public static final String MESSAGE_TAG_DONT_EXIST = "The following tag/s to be removed do not exist in person: ";

    private final boolean isAdd;
    private final Index index;
    private final Set<Tag> tags;

    public AddRemoveTagsCommand(boolean isAdd, Index index, Set<Tag> tags) {
        requireNonNull(isAdd);
        requireNonNull(index);
        requireNonNull(tags);

        this.isAdd = isAdd;
        this.index = index;
        this.tags = tags;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        checkParameterValidity(lastShownList);

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = prepareEditedPerson(personToEdit);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("The target person should not be duplicated");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return isAdd ? new CommandResult(String.format(MESSAGE_ADD_TAGS_SUCCESS, editedPerson))
                : new CommandResult(String.format(MESSAGE_REMOVE_TAGS_SUCCESS, editedPerson));
    }

    /**
     * Returns the edited Person based on whether is it an add or remove action.
     */
    private Person prepareEditedPerson(ReadOnlyPerson personToEdit) throws CommandException {
        Person editedPerson;
        if (isAdd) {
            editedPerson = addTagsToPerson(personToEdit, tags);
        } else {
            editedPerson = removeTagsFromPerson(personToEdit, tags);
        }
        return editedPerson;
    }

    /**
     * Checks that the index is valid and tags are present.
     */
    private void checkParameterValidity(List<ReadOnlyPerson> lastShownList) throws CommandException {
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (tags.isEmpty()) {
            throw new CommandException(MESSAGE_NO_TAG);
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with the {@code tags} to be added.
     */
    private static Person addTagsToPerson(ReadOnlyPerson personToEdit, Set<Tag> tags) {
        assert personToEdit != null;

        Set<Tag> personTags = personToEdit.getTags();
        HashSet<Tag> newTags = new HashSet<>(personTags);
        newTags.addAll(tags);

        AccessCount accessCount = new AccessCount((personToEdit.getAccessCount().numAccess() + 1));

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), newTags,
                personToEdit.getCreatedAt(), personToEdit.getSocialMedia(), accessCount);

    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with the {@code tags} to be removed.
     */
    private static Person removeTagsFromPerson(ReadOnlyPerson personToEdit, Set<Tag> tags) throws CommandException {
        assert personToEdit != null;

        Set<Tag> personTags = personToEdit.getTags();

        String[] dontExist = getTagsNamesThatDontExist(tags, personTags);

        if (dontExist.length > 0) {
            throw new CommandException(makeTagDontExistMessage(dontExist));
        }

        HashSet<Tag> newTags = new HashSet<>(personTags);
        newTags.removeAll(tags);

        AccessCount accessCount = new AccessCount(personToEdit.getAccessCount().numAccess() + 1);

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), newTags, personToEdit.getCreatedAt(),
                personToEdit.getSocialMedia(), accessCount);
    }

    /**
     * From a given set of {@code tags}, extract those that do not exist in {@code personTags}
     */
    public static String[] getTagsNamesThatDontExist(Set<Tag> tags, Set<Tag> personTags) {
        ArrayList<String> list = new ArrayList<>();
        for (Tag t: tags) {
            if (!personTags.contains(t)) {
                list.add(t.tagName);
            }
        }
        String[] arr = new String[list.size()];
        arr = list.toArray(arr);
        return arr;
    }

    /**
     * Make the error message when there are tags that don't exist
     */
    public static String makeTagDontExistMessage(String... tags) {
        return MESSAGE_TAG_DONT_EXIST + String.join(" ", tags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddRemoveTagsCommand)) {
            return false;
        }

        AddRemoveTagsCommand e = (AddRemoveTagsCommand) other;
        return isAdd == e.isAdd && index.equals(e.index) && tags.equals(e.tags);
    }
}

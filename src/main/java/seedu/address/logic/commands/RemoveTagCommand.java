package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_PERSON;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

//@@author liliwei25
/**
 * Removes the specified tag from all persons in addressbook
 */
public class RemoveTagCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "removetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the specified tag from all persons in the addressbook.\n"
            + "Parameters: [INDEX] (optional: must be positive integer) TAG (must be a valid tag)\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_TAG_NOT_FOUND = "Specified tag is not found";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed Tag: %s %s %3$s";
    public static final String ALL = "all";
    public static final String FROM = "from";
    private static final String MESSAGE_TAG_NOT_FOUND_IN_PERSON = "Tag not found in selected person";

    public final Tag target;
    public final String index;

    public RemoveTagCommand(String index, Tag target) {
        requireNonNull(index);
        requireNonNull(target);

        this.target = target;
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        String result;
        if (index.equals(ALL)) {
            removeTagFromAllPerson();
            removeTagFromModel();
            result = ALL;
        } else {
            result = removeTagFromPerson(Index.fromOneBased(Integer.parseInt(index)).getZeroBased());
        }
        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS, target, FROM, result));
    }

    /**
     * Removes selected {@code Tag} from selected {@code Person} in address book
     *
     * @throws CommandException When selected {@code Tag} is not found in address book
     */
    private String removeTagFromPerson(int index) throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        ReadOnlyPerson person = lastShownList.get(index);
        boolean result = removeAndUpdate(person);
        if (!result) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND_IN_PERSON);
        }
        return person.getName().fullName;
    }

    /**
     * Removes selected {@code Tag} from person and updates address book
     *
     * @param person person selected
     * @return true when tag is found in person
     * @throws CommandException when selected Tag is not found
     */
    private boolean removeAndUpdate(ReadOnlyPerson person) throws CommandException {
        if (containsTag(person)) {
            Set<Tag> updatedTags = new HashSet<>(person.getTags());

            updatedTags.remove(target);

            Person editedPerson = getEditedPerson(person, updatedTags);

            updateModel(person, editedPerson);
            return true;
        }
        return false;
    }

    /**
     * Checks if selected Person contains target Tag
     *
     * @param person Selected person
     * @return True when selected person contains target Tag
     */
    private boolean containsTag(ReadOnlyPerson person) {
        return person.getTags().contains(target);
    }

    /**
     * Removes selected {@code Tag} from all {@code Person} in address book
     *
     * @throws CommandException When selected {@code Tag} is not found in address book
     */
    private void removeTagFromAllPerson() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        for (ReadOnlyPerson person: lastShownList) {
            removeAndUpdate(person);
        }
    }

    /**
     * Removes selected {@code Tag} from address book
     *
     * @throws CommandException When selected {@code Tag} is not found in address book
     */
    private void removeTagFromModel() throws CommandException {
        try {
            model.removeTag(target);
        } catch (UniqueTagList.TagNotFoundException tnf) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }
    }

    /**
     * Updates model with new {@code Person} after removing selected {@code Tag}
     *
     * @param person {@code Person} with old data
     * @param editedPerson {@code Person} with new data
     * @throws CommandException When there is duplicate of the new person found in the address book
     */
    private void updateModel(ReadOnlyPerson person, Person editedPerson) throws CommandException {
        try {
            model.updatePerson(person, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError(MESSAGE_MISSING_PERSON);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateListToShowAll();
    }

    /**
     * Creates a new person with the new list of {@code tag}
     *
     * @param person Selected {@code Person}
     * @param updatedTags New list of {@code Tag}
     * @return Updated {@code Person} with new list
     */
    private Person getEditedPerson(ReadOnlyPerson person, Set<Tag> updatedTags) {
        return new Person(person.getName(), person.getPhone(), person.getEmail(),
                person.getAddress(), person.getRemark(), person.getBirthday(), updatedTags,
                person.getPicture(), person.getFavourite(), person.getNumTimesSearched());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && this.target.equals(((RemoveTagCommand) other).target)
                && this.index.equals(((RemoveTagCommand) other).index)); // state check
    }
}

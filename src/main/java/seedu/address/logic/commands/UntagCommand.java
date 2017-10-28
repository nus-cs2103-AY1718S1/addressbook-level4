package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Untags one or more persons identified using their last displayed targetIndexes from the address book.
 */
public class UntagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "untag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Untags one or more persons in the last person listing.\n"
            + "- Untag all tags of persons identified by the index numbers used\n"
            + "Parameters: INDEX,[MORE_INDEXES]... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1,2,3\n"
            + "- Untag one or more tags of persons identified by the index numbers used\n"
            + "Parameters: INDEX,[MORE_INDEXES]... (must be positive integers) + TAGNAME\n"
            + "Example: " + COMMAND_WORD + " 1,2,3 friends/colleagues\n"
            + "- Untag all tags of all persons in the last person listing\n"
            + "Parameters: -a\n"
            + "Example: " + COMMAND_WORD + " -a\n"
            + "- Untag one or more tags of all persons in the last person listing\n"
            + "Parameters: -a + TAGNAME\n"
            + "Example: " + COMMAND_WORD + " -a friends/colleagues";

    public static final String MESSAGE_SUCCESS = "%d person(s) successfully untagged from %s:";
    public static final String MESSAGE_SUCCESS_MULTIPLE_TAGS = "%s tag(s) successfully removed.";
    public static final String MESSAGE_SUCCESS_ALL_TAGS = "All tags successfully removed.";
    public static final String MESSAGE_PERSONS_DO_NOT_HAVE_TAGS = "%d person(s) do not have any tags specified:";

    public static final String MESSAGE_EMPTY_INDEX_LIST = "Please provide one or more indexes! \n%1$s";
    public static final String MESSAGE_EMPTY_TAG_LIST = "Please provide one or more tags! \n%1$s";
    public static final String MESSAGE_INVALID_INDEXES = "One or more person indexes provided are invalid.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private boolean toAllInFilteredList;
    private List<Index> targetIndexes;
    private List<Tag> tags;

    /**
     * @param targetIndexes of the persons in the filtered person list to untag
     * @param tags of the persons
     */
    public UntagCommand(boolean toAllInFilteredList, List<Index> targetIndexes, List<Tag> tags) {
        requireNonNull(targetIndexes);
        requireNonNull(tags);

        this.toAllInFilteredList = toAllInFilteredList;
        this.targetIndexes = targetIndexes;
        this.tags = tags;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (toAllInFilteredList) {
            for (ReadOnlyPerson personToUntag : lastShownList) {
                removeTagsFromPerson(personToUntag, tags);
            }

            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return (tags.isEmpty()) ? new CommandResult(MESSAGE_SUCCESS_ALL_TAGS)
                    : new CommandResult(String.format(MESSAGE_SUCCESS_MULTIPLE_TAGS, tags.toString()));
        }

        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_INDEXES);
            }
        }

        ArrayList<ReadOnlyPerson> alreadyUntaggedPersons = new ArrayList<>();
        ArrayList<ReadOnlyPerson> toBeUntaggedPersons = new ArrayList<>();
        for (Index targetIndex : targetIndexes) {
            ReadOnlyPerson personToUntag = lastShownList.get(targetIndex.getZeroBased());
            if (Collections.disjoint(personToUntag.getTags(), tags)) {
                alreadyUntaggedPersons.add(personToUntag);
                continue;
            }
            removeTagsFromPerson(personToUntag, tags);
            toBeUntaggedPersons.add(personToUntag);
        }

        for (Tag tag : tags) {
            model.deleteUnusedTag(tag);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        StringJoiner toBeUntaggedJoiner = new StringJoiner(", ");
        for (ReadOnlyPerson person : toBeUntaggedPersons) {
            toBeUntaggedJoiner.add(person.getName().toString());
        }
        if (alreadyUntaggedPersons.size() > 0) {
            StringJoiner alreadyUntaggedJoiner = new StringJoiner(", ");
            for (ReadOnlyPerson person : alreadyUntaggedPersons) {
                alreadyUntaggedJoiner.add(person.getName().toString());
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS,
                    targetIndexes.size() - alreadyUntaggedPersons.size(), tags.toString()) + " "
                    + toBeUntaggedJoiner.toString() + "\n"
                    + String.format(MESSAGE_PERSONS_DO_NOT_HAVE_TAGS, alreadyUntaggedPersons.size()) + " "
                    + alreadyUntaggedJoiner.toString());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                targetIndexes.size(), tags.toString()) + " " + toBeUntaggedJoiner.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UntagCommand // instanceof handles nulls
                && this.targetIndexes.equals(((UntagCommand) other).targetIndexes))
                && this.tags.equals(((UntagCommand) other).tags); // state check
    }

    /**
     * Removes a tag from the person
     * Removes all tags if tag is not specified
     * @param person to be untagged
     * @param tags to be removed
     */
    private void removeTagsFromPerson(ReadOnlyPerson person, List<Tag> tags) throws CommandException {
        Person untaggedPerson = new Person(person);
        UniqueTagList updatedTags = new UniqueTagList();
        if (!tags.isEmpty()) {
            updatedTags = new UniqueTagList(person.getTags());
            for (Tag t : tags) {
                updatedTags.remove(t);
            }
        }
        untaggedPerson.setTags(updatedTags.toSet());

        try {
            model.updatePerson(person, untaggedPerson);
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException e) {
            throw new AssertionError("The target person cannot be missing");
        }
    }

}

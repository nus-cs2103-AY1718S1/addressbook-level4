//@@author duyson98

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public static final String COMMAND_ALIAS = "ut";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Untags one or more persons in the last person listing.\n"
            + "- Untag all tags of persons identified by the index numbers used\n"
            + "Parameters: INDEX,[MORE_INDEXES]... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1,2,3\n"
            + "- Untag one or more tags of persons identified by the index numbers used\n"
            + "Parameters: INDEX,[MORE_INDEXES]... (must be positive integers) + TAGNAME\n"
            + "Example: " + COMMAND_WORD + " 1,2,3 friends/colleagues\n"
            + "- Untag all tags of all persons in the last person listing\n"
            + "Parameters: -all\n"
            + "Example: " + COMMAND_WORD + " -all\n"
            + "- Untag one or more tags of all persons in the last person listing\n"
            + "Parameters: -all + TAGNAME\n"
            + "Example: " + COMMAND_WORD + " -all friends/colleagues";

    public static final String MESSAGE_SUCCESS = "%d person(s) successfully untagged from %s:";
    public static final String MESSAGE_SUCCESS_ALL_TAGS = "%d person(s) sucessfully untagged:";
    public static final String MESSAGE_SUCCESS_MULTIPLE_TAGS_IN_LIST = "%s tag(s) successfully" + " "
            + "removed from person list.";
    public static final String MESSAGE_SUCCESS_ALL_TAGS_IN_LIST = "All tags in person list successfully removed.";

    public static final String MESSAGE_TAG_NOT_FOUND = "%s tag(s) not found in person list.";
    public static final String MESSAGE_PERSONS_DO_NOT_HAVE_TAGS = "%d person(s) do not have any of the specified tags:";
    public static final String MESSAGE_EMPTY_INDEX_LIST = "Please provide one or more indexes! \n%1$s";
    public static final String MESSAGE_INVALID_INDEXES = "One or more person indexes provided are invalid.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private boolean toAllPersonsInFilteredList;
    private List<Index> targetIndexes;
    private List<Tag> tags;

    /**
     * @param targetIndexes of the persons in the filtered person list to untag
     * @param tags of the persons
     */
    public UntagCommand(boolean toAllPersonsInFilteredList, List<Index> targetIndexes, List<Tag> tags) {
        requireNonNull(targetIndexes);
        requireNonNull(tags);

        this.toAllPersonsInFilteredList = toAllPersonsInFilteredList;
        this.targetIndexes = targetIndexes;
        this.tags = tags;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_INDEXES);
            }
        }

        if (!tags.isEmpty() && Collections.disjoint(model.getAddressBook().getTagList(), tags)) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, joinTagList(tags)));
        }

        if (toAllPersonsInFilteredList) {
            return new CommandResult(untagAllPersonsInFilteredList(lastShownList));
        }

        return new CommandResult(untagSpecifiedPersonsInFilteredList(lastShownList));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UntagCommand // instanceof handles nulls
                && this.targetIndexes.equals(((UntagCommand) other).targetIndexes))
                && this.tags.equals(((UntagCommand) other).tags); // state check
    }

    /**
     * @param lastShownList of person filtered list
     */
    private String untagSpecifiedPersonsInFilteredList(List<ReadOnlyPerson> lastShownList) throws CommandException {
        List<ReadOnlyPerson> toBeUntaggedPerson = new ArrayList<>();
        List<ReadOnlyPerson> alreadyUntaggedPerson = new ArrayList<>();
        Set<Tag> uniqueTags = new HashSet<>();

        for (Index targetIndex : targetIndexes) {
            ReadOnlyPerson person = lastShownList.get(targetIndex.getZeroBased());
            if (!tags.isEmpty() && Collections.disjoint(person.getTags(), tags)) {
                alreadyUntaggedPerson.add(person);
            } else {
                toBeUntaggedPerson.add(person);
            }
            uniqueTags.addAll(person.getTags());
        }

        List<Tag> tagsToRemove = new ArrayList<>(uniqueTags);
        removeTagsFromPersons(toBeUntaggedPerson, (tags.isEmpty()) ? tagsToRemove : tags);
        deleteUnusedTagsInTagList(tagsToRemove);

        if (tags.isEmpty()) {
            return String.format(MESSAGE_SUCCESS_ALL_TAGS, targetIndexes.size()) + " "
                    + joinPersonList(toBeUntaggedPerson);
        }
        return (alreadyUntaggedPerson.size() > 0)
                ? String.format(MESSAGE_SUCCESS, targetIndexes.size() - alreadyUntaggedPerson.size(), joinTagList(tags))
                + " " + joinPersonList(toBeUntaggedPerson) + "\n"
                + String.format(MESSAGE_PERSONS_DO_NOT_HAVE_TAGS, alreadyUntaggedPerson.size()) + " "
                + joinPersonList(alreadyUntaggedPerson)
                : String.format(MESSAGE_SUCCESS, targetIndexes.size(), joinTagList(tags))
                + " " + joinPersonList(toBeUntaggedPerson);
    }

    /**
     * @param lastShownList in filtered list
     */
    private String untagAllPersonsInFilteredList(List<ReadOnlyPerson> lastShownList) throws CommandException {
        List<ReadOnlyPerson> personsToUpdate = new ArrayList<>(lastShownList);

        removeTagsFromPersons(personsToUpdate, tags);
        deleteUnusedTagsInTagList(new ArrayList<>(tags.isEmpty()
                ? new ArrayList<>(model.getAddressBook().getTagList()) : tags));
        return (tags.isEmpty()) ? MESSAGE_SUCCESS_ALL_TAGS_IN_LIST
                : String.format(MESSAGE_SUCCESS_MULTIPLE_TAGS_IN_LIST, joinTagList(tags));
    }

    /**
     * Removes specified tags from the person list
     * Removes all tags if tag is not specified
     * @param persons to be untagged
     * @param tags to be removed
     */
    private void removeTagsFromPersons(List<ReadOnlyPerson> persons, List<Tag> tags) throws CommandException {
        assert persons != null;

        for (ReadOnlyPerson person : persons) {
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

    private void deleteUnusedTagsInTagList(List<Tag> tags) {
        for (Tag tag : tags) {
            model.deleteUnusedTag(tag);
        }
    }

    /**
     * Join person list elements by commas
     * @param personList to be joined
     */
    private String joinPersonList(List<ReadOnlyPerson> personList) {
        StringJoiner joiner = new StringJoiner(", ");
        for (ReadOnlyPerson person : personList) {
            joiner.add(person.getName().toString());
        }
        return joiner.toString();
    }

    /**
     * Join tag list elements by commas
     * @param tagList to be joined
     */
    private String joinTagList(List<Tag> tagList) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Tag tag : tagList) {
            joiner.add(tag.toString());
        }
        return joiner.toString();
    }
}

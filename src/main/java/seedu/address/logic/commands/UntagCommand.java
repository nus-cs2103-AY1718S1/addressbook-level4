package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
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
            + "- Untag by the index numbers used\n"
            + "Parameters: INDEX,[MORE_INDEXES]... (must be positive integers) + TAGNAME\n"
            + "Example: " + COMMAND_WORD + " 1,2,3 friends\n"
            + "- Untag a tag from persons in the list\n"
            + "Parameters: -all + TAGNAME\n"
            + "Example: " + COMMAND_WORD + " -all friends\n"
            + "- Untag all tags from persons in the list\n"
            + "Parameters: -all\n"
            + "Example: " + COMMAND_WORD + " -all";

    public static final String MESSAGE_SUCCESS = "%d persons successfully untagged from %s:";
    public static final String MESSAGE_SUCCESS_TAG = "%s tag successfully removed.";
    public static final String MESSAGE_SUCCESS_ALL_TAGS = "All tags successfully removed.";
    public static final String MESSAGE_PERSONS_DO_NOT_HAVE_TAG = "%d persons do not have this tag:";

    public static final String MESSAGE_EMPTY_INDEX_LIST = "Please provide one or more indexes! \n%1$s";
    public static final String MESSAGE_INVALID_INDEXES = "One or more person indexes provided are invalid.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private boolean toAllPersonsInFilteredList;
    private List<Index> targetIndexes;
    private Tag tag;

    public UntagCommand() {
        this.toAllPersonsInFilteredList = true;
        this.targetIndexes = null;
        this.tag = null;
    }

    /**
     * @param tag of the person
     */
    public UntagCommand(Tag tag) {
        requireNonNull(tag);

        this.toAllPersonsInFilteredList = true;
        this.targetIndexes = null;
        this.tag = tag;
    }

    /**
     * @param targetIndexes of the person in the filtered person list to untag
     * @param tag of the person
     */
    public UntagCommand(List<Index> targetIndexes, Tag tag) {
        requireNonNull(targetIndexes);
        requireNonNull(tag);

        this.toAllPersonsInFilteredList = false;
        this.targetIndexes = targetIndexes;
        this.tag = tag;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (toAllPersonsInFilteredList) {
            for (ReadOnlyPerson personToUntag : lastShownList) {
                removeTagFromPerson(personToUntag, tag);
            }

            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return (tag == null) ? new CommandResult(MESSAGE_SUCCESS_ALL_TAGS)
                    : new CommandResult(String.format(MESSAGE_SUCCESS_TAG, tag.toString()));
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
            if (!personToUntag.getTags().contains(tag)) {
                alreadyUntaggedPersons.add(personToUntag);
                continue;
            }
            removeTagFromPerson(personToUntag, tag);
            toBeUntaggedPersons.add(personToUntag);
        }

        //Todo: Delete the tag from unique tag list if no persons have it

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
                    targetIndexes.size() - alreadyUntaggedPersons.size(), tag.toString()) + " "
                    + toBeUntaggedJoiner.toString() + "\n"
                    + String.format(MESSAGE_PERSONS_DO_NOT_HAVE_TAG, alreadyUntaggedPersons.size()) + " "
                    + alreadyUntaggedJoiner.toString());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                targetIndexes.size(), tag.toString()) + " " + toBeUntaggedJoiner.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UntagCommand // instanceof handles nulls
                && ((targetIndexes == null) || this.targetIndexes.equals(((UntagCommand) other).targetIndexes)))
                && ((tag == null) || this.tag.equals(((UntagCommand) other).tag)); // state check
    }

    /**
     * Removes a tag from the person
     * Removes all tags if tag is not specified
     * @param person to be untagged
     * @param tag to be removed
     */
    private void removeTagFromPerson(ReadOnlyPerson person, Tag tag) throws CommandException {
        Person untaggedPerson = new Person(person);
        UniqueTagList updatedTags = new UniqueTagList();
        if (tag != null) {
            updatedTags = new UniqueTagList(person.getTags());
            updatedTags.remove(tag);
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

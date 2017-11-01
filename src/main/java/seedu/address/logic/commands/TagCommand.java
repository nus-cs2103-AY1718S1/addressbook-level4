//@@author duyson98

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
 * Tags one or more persons identified using their last displayed targetIndexes from the address book.
 */
public class TagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Tags one or more persons identified by the index numbers used in the last person listing.\n"
            + "Parameters: INDEX,[MORE_INDEXES]... (must be positive integers) + TAGNAME\n"
            + "Example: " + COMMAND_WORD + " 1,2,3 friends";

    public static final String MESSAGE_SUCCESS = "%d persons successfully tagged with %s:";
    public static final String MESSAGE_PERSONS_ALREADY_HAVE_TAG = "%d persons already have this tag:";

    public static final String MESSAGE_EMPTY_INDEX_LIST = "Please provide one or more indexes! \n%1$s";
    public static final String MESSAGE_INVALID_INDEXES = "One or more person indexes provided are invalid.";
    public static final String MESSAGE_DUPLICATE_TAG = "One or more persons already have this tag.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private List<Index> targetIndexes;
    private Tag tag;

    /**
     * @param targetIndexes of the person in the filtered person list to tag
     * @param tag of the person
     */
    public TagCommand(List<Index> targetIndexes, Tag tag) {
        requireNonNull(targetIndexes);
        requireNonNull(tag);

        this.targetIndexes = targetIndexes;
        this.tag = tag;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_INDEXES);
            }
        }

        ArrayList<ReadOnlyPerson> alreadyTaggedPersons = new ArrayList<>();
        ArrayList<ReadOnlyPerson> toBeTaggedPersons = new ArrayList<>();
        for (Index targetIndex : targetIndexes) {
            ReadOnlyPerson personToTag = lastShownList.get(targetIndex.getZeroBased());
            Person taggedPerson = new Person(personToTag);
            UniqueTagList updatedTags = new UniqueTagList(personToTag.getTags());
            if (updatedTags.contains(tag)) {
                alreadyTaggedPersons.add(personToTag);
                continue;
            }

            toBeTaggedPersons.add(personToTag);

            try {
                updatedTags.add(tag);
            } catch (UniqueTagList.DuplicateTagException e) {
                throw new CommandException(MESSAGE_DUPLICATE_TAG);
            }

            taggedPerson.setTags(updatedTags.toSet());

            try {
                model.updatePerson(personToTag, taggedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        StringJoiner toBeTaggedJoiner = new StringJoiner(", ");
        for (ReadOnlyPerson person : toBeTaggedPersons) {
            toBeTaggedJoiner.add(person.getName().toString());
        }
        if (alreadyTaggedPersons.size() > 0) {
            StringJoiner alreadyTaggedJoiner = new StringJoiner(", ");
            for (ReadOnlyPerson person : alreadyTaggedPersons) {
                alreadyTaggedJoiner.add(person.getName().toString());
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS,
                    targetIndexes.size() - alreadyTaggedPersons.size(), tag.toString()) + " "
                    + toBeTaggedJoiner.toString() + "\n"
                    + String.format(MESSAGE_PERSONS_ALREADY_HAVE_TAG, alreadyTaggedPersons.size()) + " "
                    + alreadyTaggedJoiner.toString());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                targetIndexes.size(), tag.toString()) + " " + toBeTaggedJoiner.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagCommand // instanceof handles nulls
                && this.targetIndexes.equals(((TagCommand) other).targetIndexes)) // state check
                && this.tag.equals(((TagCommand) other).tag); // state check
    }

}


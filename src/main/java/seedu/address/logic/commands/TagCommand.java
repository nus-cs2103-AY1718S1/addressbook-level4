package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

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
            + "Example: " + COMMAND_WORD + "1,2,3 friends";

    public static final String MESSAGE_SUCCESS = "Tagged successfully.";

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

        for (Index targetIndex : targetIndexes) {
            ReadOnlyPerson personToTag = lastShownList.get(targetIndex.getZeroBased());
            Person taggedPerson = new Person(personToTag);
            UniqueTagList updatedTags = new UniqueTagList(personToTag.getTags());
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
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagCommand // instanceof handles nulls
                && this.targetIndexes.equals(((TagCommand) other).targetIndexes)) // state check
                && this.tag.equals(((TagCommand) other).tag); // state check
    }

}


package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Unpins an existing pinned person from the address book
 */
public class UnpinCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unpin";

    /**
     * Shows message usage for unpin command
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unpins the selected person identified "
            + "by the index number used in the last person listing.\n "
            + "Parameters: INDEX (must be a positive integer)\n "
            + "Example: " + COMMAND_WORD + " 1 ";

    private static final String MESSAGE_UNPIN_PERSON_SUCCESS = "Unpinned Person: %1$s";
    private static final String MESSAGE_ALREADY_UNPINNED = "Person is not pinned!";
    private static final String MESSAGE_UNPIN_PERSON_FAILED = "Unpin was unsuccessful";

    private final Index index;

    public UnpinCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUnpin = lastShownList.get(index.getZeroBased());
        try {
            if (personToUnpin.isPinned()) {
                List<ReadOnlyPerson> newList = unpinAndSortContact(personToUnpin);
                model.pinOrUnpinPerson(newList);
                model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
                return new CommandResult(String.format(MESSAGE_UNPIN_PERSON_SUCCESS, personToUnpin));
            } else {
                return new CommandResult(MESSAGE_ALREADY_UNPINNED);
            }
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_UNPIN_PERSON_FAILED);
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(MESSAGE_UNPIN_PERSON_FAILED);
        }

    }

    /**
     * Set pinned contact on top of all persons
     * @return pinned list, where pinned contacts are on top
     */
    private List<ReadOnlyPerson> unpinAndSortContact(
            ReadOnlyPerson personToUnpin) throws CommandException, PersonNotFoundException, DuplicatePersonException {
        List<ReadOnlyPerson> newList = new ArrayList<>();
        Person unpinTag = removePinTag(personToUnpin);
        model.updatePerson(personToUnpin, unpinTag);
        newList.addAll(model.getFilteredPersonList().filtered(Model.PREDICATE_SHOW_PINNED_PERSONS).sorted());
        newList.addAll(model.getFilteredPersonList().filtered(Model.PREDICATE_SHOW_UNPINNED_PERSONS).sorted());
        return newList;
    }

    /**
     *
     * @param personToUnpin
     * @return updated Person with removed pin to be added to the address book
     * @throws CommandException
     */
    private Person removePinTag(ReadOnlyPerson personToUnpin) throws CommandException {
        try {
            UniqueTagList updatedTags = new UniqueTagList(personToUnpin.getTags());
            updatedTags.removePinTag();
            return new Person(personToUnpin.getName(), personToUnpin.getPhone(), personToUnpin.getEmail(),
                    personToUnpin.getAddress(), updatedTags.toSet());
        } catch (IllegalValueException ive) {
            throw new CommandException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
    }
}

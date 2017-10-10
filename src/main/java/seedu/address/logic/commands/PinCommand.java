package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Pins an existing person on top of the address book
 */
public class PinCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "pin";

    /**
     * Shows message usage for pin command
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Pins the selected person identified "
            + "by the index number used in the last person listing.\n "
            + "Parameters: INDEX (must be a positive integer)\n "
            + "Example: " + COMMAND_WORD + " 1 ";

    private static final String MESSAGE_PIN_PERSON_SUCCESS = "Pinned Person: %1$s";
    private static final String MESSAGE_ALREADY_PINNED = "Person is already pinned!";
    private static final String MESSAGE_PIN_PERSON_FAILED = "Pin was unsuccessful";

    private final Index index;

    public PinCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToPin = lastShownList.get(index.getZeroBased());
        try {
            if (personToPin.isPinned()) {
                return new CommandResult(MESSAGE_ALREADY_PINNED);
            } else {
                Person addPin = addPinTag(personToPin);
                model.updatePerson(personToPin, addPin);
                model.updateFilteredPersonList(p -> p.getTags().contains(getPinTag(addPin)));
                Collection<ReadOnlyPerson> listOfPersons = model.getFilteredPersonList();
                List<ReadOnlyPerson> newList = new ArrayList<>();
                newList.addAll(listOfPersons);
                model.updateFilteredPersonList(p -> !p.getTags().contains(getPinTag(addPin)));
                listOfPersons = model.getFilteredPersonList();
                newList.addAll(listOfPersons);

                return new CommandResult(String.format(MESSAGE_PIN_PERSON_SUCCESS, personToPin));
            }
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_PIN_PERSON_FAILED);
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(MESSAGE_PIN_PERSON_FAILED);
        }
    }

    /**
     * @param personToPin
     * @return updated Person with added pin to be added to the address book
     * @throws CommandException
     */
    private Person addPinTag(ReadOnlyPerson personToPin) throws CommandException {
        /**
         * Create a new UniqueTagList to add pin tag into the list.
         */
        UniqueTagList updatedTags = new UniqueTagList(personToPin.getTags());
        updatedTags.addPinTag();

        return new Person(personToPin.getName(), personToPin.getPhone(), personToPin.getEmail(),
                personToPin.getAddress(), updatedTags.toSet());
    }

    /**
     * Searches the tag list to find Pinned Tag. Can always be found as the person in pinned already
     *
     * @param pinnedPerson
     * @return tag that contains Pinned
     */
    private Tag getPinTag(Person pinnedPerson) {
        for (Tag tag : pinnedPerson.getTags()) {
            if ("Pinned".equals(tag.tagName)) {
                return tag;
            }
        }
        return null; //It will never reach here
    }

}

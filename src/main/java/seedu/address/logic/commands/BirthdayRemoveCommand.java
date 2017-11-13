package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author dalessr
/**
 * Remove a birthday from an existing person in the address book.
 */
public class BirthdayRemoveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "b-remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove birthday from the person selected "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REMOVE_BIRTHDAY_SUCCESS = "Removed Birthday from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;

    public BirthdayRemoveCommand (Index index) {
        this.targetIndex = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson currentPerson = lastShownList.get(targetIndex.getZeroBased());
        Person personToEdit = (Person) lastShownList.get(targetIndex.getZeroBased());
        Birthday birthdayToAdd = new Birthday();
        personToEdit.setBirthday(birthdayToAdd);

        try {
            model.updatePerson(currentPerson, personToEdit);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_REMOVE_BIRTHDAY_SUCCESS, personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BirthdayRemoveCommand // instanceof handles nulls
                && this.targetIndex.equals(((BirthdayRemoveCommand) other).targetIndex)); // state check
    }
}

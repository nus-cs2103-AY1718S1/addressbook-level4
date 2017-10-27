package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Add a birthday to an existing person in the address book.
 */
public class BirthdayAddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "b-add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add birthday to the person selected "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "BIRTHDAY[DD/MM/YYYY] (BIRTHDAY Should be in valid format).\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "18/10/1994";

    public static final String MESSAGE_ADD_BIRTHDAY_SUCCESS = "Added Birthday to Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;
    private final Birthday birthdayToAdd;

    public BirthdayAddCommand(Index index, Birthday birthday) {
        targetIndex = index;
        birthdayToAdd = birthday;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson currentPerson = lastShownList.get(targetIndex.getZeroBased());
        Person personToEdit = (Person) lastShownList.get(targetIndex.getZeroBased());
        personToEdit.setBirthday(birthdayToAdd);

        try {
            model.updatePerson(currentPerson, personToEdit);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_BIRTHDAY_SUCCESS, personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BirthdayAddCommand // instanceof handles nulls
                && this.targetIndex.equals(((BirthdayAddCommand) other).targetIndex))
                && (other instanceof BirthdayAddCommand
                && this.birthdayToAdd.equals(((BirthdayAddCommand) other).birthdayToAdd)); // state check
    }
}

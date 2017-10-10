package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class AddBirthdayCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addBirthday";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a birthday to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX MM/DD/YYYY(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 01/01/2001";

    public static final String MESSAGE_ADD_BIRTHDAY_SUCCESS = "Added Birthday for Person: %1$s";

    private final Index targetIndex;
    private final Birthday inputBirthday;

    public AddBirthdayCommand(Index targetIndex, Birthday inputBirthday) {
        this.targetIndex = targetIndex;
        this.inputBirthday = inputBirthday;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUpdate = lastShownList.get(targetIndex.getZeroBased());
        ReadOnlyPerson newPerson = null;

        try {
            newPerson = addBirthday(personToUpdate, inputBirthday);
            model.updatePerson(personToUpdate, newPerson);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        } catch (DuplicatePersonException e) {
            e.printStackTrace();
        }

        return new CommandResult(String.format(MESSAGE_ADD_BIRTHDAY_SUCCESS, newPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToAdd}
     */
    private Person addBirthday(ReadOnlyPerson personToAdd, Birthday inputBirthday) throws PersonNotFoundException,
                                                                                          DuplicatePersonException {
        Person newPerson = new Person(personToAdd);
        newPerson.setBirthday(inputBirthday);

        return newPerson;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddBirthdayCommand // instanceof handles nulls
                && this.targetIndex.equals(((AddBirthdayCommand) other).targetIndex)); // state check
    }
}

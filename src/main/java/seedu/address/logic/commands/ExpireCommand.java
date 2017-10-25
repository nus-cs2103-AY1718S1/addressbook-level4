package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
//import static seedu.address.model.person.ExpiryDate.MESSAGE_EXPIRY_DATE_CONSTRAINTS;

//import seedu.address.commons.exceptions.IllegalValueException;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ExpiryDate;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Sets expiry date of a person in the address book.
 */

public class ExpireCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "expire";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the expiry date "
            + "of the person identified by the index number used in the last person listing. "
            // overwrittable?
            + "Parameters: INDEX (must be positive integer) "
            + PREFIX_EXPIRE + "[DATE in YYYY-MM-DD format]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EXPIRE + "2017-09-09";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Date string: %2$s";
    public static final String MESSAGE_SET_EXPIRY_DATE_SUCCESS = "Expiry date of Person: %1$s set as %2s.";
    public static final String MESSAGE_DELETE_EXPIRY_DATE_SUCCESS = "Removed expiry date of Person: %1$s.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No such person in Address Book.";

    private final Index index;
    private final ExpiryDate date;

    public ExpireCommand(Index index, ExpiryDate date) {
        requireNonNull(index);
        requireNonNull(date);

        this.index = index;
        // create ExpiryDate object
        this.date = date;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), date, personToEdit.getRemark());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generate success message for:
     * Set expiry date: with person name and new expiry date
     * Remove expiry date: with person name
     */
    private String generateSuccessMessage(ReadOnlyPerson person) {
        if (!date.toString().isEmpty()) {
            return String.format(MESSAGE_SET_EXPIRY_DATE_SUCCESS, person, date);
        } else {
            return String.format(MESSAGE_DELETE_EXPIRY_DATE_SUCCESS, person);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ExpireCommand)) {
            return false;
        }

        ExpireCommand temp = (ExpireCommand) other;
        return (index.equals(temp.index) && date.equals(temp.date));
    }
}

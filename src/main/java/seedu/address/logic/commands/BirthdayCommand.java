package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_PERSON;
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

//@@author liliwei25
/**
 * Adds or Edits birthday field of selected person
 */
public class BirthdayCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "birthday";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds/Edits the birthday of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing birthday will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[BIRTHDAY (dd-mm-yyyy)]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "12-05-2016";

    public static final String MESSAGE_BIRTHDAY_PERSON_SUCCESS = "Birthday Updated success: %1$s";

    private final Index index;
    private final Birthday birthday;

    public BirthdayCommand (Index index, Birthday birthday) {
        requireNonNull(index);
        requireNonNull(birthday);

        this.index = index;
        this.birthday = birthday;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        ReadOnlyPerson editedPerson = getEditedPerson(personToEdit);
        updateModel(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_BIRTHDAY_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates a new {@code Person} with new person data
     *
     * @param personToEdit {@code Person} with old data
     * @return {@code Person} with new data
     */
    private Person getEditedPerson(ReadOnlyPerson personToEdit) {
        return new Person(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getRemark(), birthday,
                personToEdit.getTags(), personToEdit.getPicture(), personToEdit.getFavourite());
    }

    /**
     * Updates the model with the updated person
     *
     * @param personToEdit Old person data
     * @param editedPerson New person data
     * @throws CommandException when the new person already exists in the address book
     */
    private void updateModel(ReadOnlyPerson personToEdit, ReadOnlyPerson editedPerson) throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError(MESSAGE_MISSING_PERSON);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateListToShowAll();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BirthdayCommand // instanceof handles nulls
                && index.equals(((BirthdayCommand) other).index)
                && birthday.equals(((BirthdayCommand) other).birthday));
    }
}

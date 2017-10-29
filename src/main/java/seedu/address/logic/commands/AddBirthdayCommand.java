package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds the birthday to the identified persons.
 */
public class AddBirthdayCommand extends UndoableCommand {

    public static final String COMMAND_WORDVAR_1 = "birthday";
    public static final String COMMAND_WORDVAR_2 = "bd";

    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + " OR "
            + COMMAND_WORDVAR_2
            + ": Adds the given birthday to the person identified by the list of index numbers used in the "
            + "last person listing. The format of birthday is in DDMMYY. "
            + "Command is case-sensitive. \n"
            + "Parameters: "
            + "[INDEX] (index must be a positive integer) "
            + "[" + PREFIX_BIRTHDAY + "BIRTHDAY]... (birthday must be integers)\n"
            + "Example 1: "
            + COMMAND_WORDVAR_1
            + " 1 b/240594 \n"
            + "Example 2: "
            + COMMAND_WORDVAR_2.toUpperCase()
            + " 5 b/110696 \n";

    public static final String MESSAGE_ADD_BIRTHDAY_SUCCESS = "Added Birthday: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_BIRTHDAY = "A birthday already exists in the given person. "
            + "Please use edit command to make changes to it.";

    private final Index targetIndex;
    private final Birthday toAdd;

    /**
     * @param targetIndex of the person in the filtered person list to edit
     * @param toAdd birthday to add to given target index
     */
    public AddBirthdayCommand(Index targetIndex, Birthday toAdd) {

        requireNonNull(targetIndex);
        requireNonNull(toAdd);

        this.targetIndex = targetIndex;
        this.toAdd = toAdd;
    }

    /**
     * First checks if the target index is not out of bounds and then checks if a birthday exists in them
     * the given target index of person. If not, add the birthday to the target person.
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        boolean personsContainsBirthdayToAdd = true;

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson readOnlyPerson = lastShownList.get(targetIndex.getZeroBased());
        if (Objects.equals(readOnlyPerson.getBirthday().toString(), Birthday.DEFAULT_BIRTHDAY)) {
            personsContainsBirthdayToAdd = false;
        }

        if (personsContainsBirthdayToAdd) {
            throw new CommandException(MESSAGE_DUPLICATE_BIRTHDAY);
        }
        try {
            model.addBirthday(this.targetIndex, this.toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_ADD_BIRTHDAY_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddBirthdayCommand)) {
            return false;
        }

        // state check
        AddBirthdayCommand e = (AddBirthdayCommand) other;
        return targetIndex.equals(e.targetIndex)
                && toAdd.equals(e.toAdd);
    }
}

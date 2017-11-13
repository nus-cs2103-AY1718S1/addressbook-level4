package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_MISSING_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number(s) used in the last person listing.\n"
            + "Parameters: INDEX(ES) (must be a positive integer(s))\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person(s): %1$s";
    private static final String COMMA = ", ";
    private static final String FAILED = "\nFailed: ";

    public final Index[] targets;

    public DeleteCommand(Index[] targets) {
        Arrays.sort(targets);
        this.targets = targets;
    }

    //@@author liliwei25
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        String result = deleteAllSelectedPersonFromAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, result));
    }

    /**
     * Deletes all the selected person from address book and returns a StringJoiner containing their names
     *
     * @return A {@code StringJoiner} that includes all the names that were deleted
     * @throws CommandException when person selected is not found
     */
    private String deleteAllSelectedPersonFromAddressBook() throws CommandException {
        StringJoiner deletedNames = new StringJoiner(COMMA);
        StringJoiner failedIndexs = new StringJoiner(COMMA);

        deletePersons(deletedNames, failedIndexs);
        return getResult(deletedNames, failedIndexs);
    }

    /**
     * Deletes Person given by {@code targets}
     *
     * @param deletedNames Stores names of successfully deleted Persons
     * @param failedIndexs Stores invalid indexes
     */
    private void deletePersons(StringJoiner deletedNames, StringJoiner failedIndexs) {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        for (int i = targets.length - 1; i >= 0; i--) {
            if (targets[i].getZeroBased() >= lastShownList.size()) {
                failedIndexs.add(Integer.toString(targets[i].getOneBased()));
            } else {
                ReadOnlyPerson personToDelete = lastShownList.get(targets[i].getZeroBased());
                deletePersonFromAddressBook(deletedNames, personToDelete);
            }
        }
    }

    /**
     * Create result string
     *
     * @param deletedNames Names of successfully deleted Persons
     * @param failedIndexs Indexes of failed/invalid deletes
     * @return result string with names and indexes
     */
    private String getResult(StringJoiner deletedNames, StringJoiner failedIndexs) throws CommandException {
        if (isEmpty(deletedNames)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        String result = deletedNames.toString();
        if (!isEmpty(failedIndexs)) {
            result = result.concat(FAILED + failedIndexs.toString());
        }
        return result;
    }

    private boolean isEmpty(StringJoiner deletedNames) {
        return deletedNames.length() == 0;
    }

    /**
     * Delete selected person from address book
     *
     * @param joiner {@code StringJoiner} to join several names together if necessary
     * @param personToDelete Selected person
     */
    private void deletePersonFromAddressBook(StringJoiner joiner, ReadOnlyPerson personToDelete) {
        try {
            model.deletePerson(personToDelete);
            joiner.add(personToDelete.getName().toString());
        } catch (PersonNotFoundException pnfe) {
            assert false : MESSAGE_MISSING_PERSON;
        }
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && Arrays.equals(this.targets, ((DeleteCommand) other).targets)); // state check
    }
}

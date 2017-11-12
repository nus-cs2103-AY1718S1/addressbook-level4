package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.ListObserver;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Debt;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author jelneo
/**
 * Updates debt field when a person borrows more money
 */
public class BorrowCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "borrow";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": increase the debt of a person by "
            + "the amount of money entered.\n"
            + "Parameters: INDEX (optional, must be a positive integer if present)\n"
            + "AMOUNT (must have at least 1 digit and either a positive integer or a positive number with "
            + "two decimal places)\n"
            + "Example 1: " + COMMAND_WORD + " 1 120.50\n"
            + "Example 2: " + COMMAND_WORD + " 120.50";
    public static final String MESSAGE_BORROW_SUCCESS = "%1$s has borrowed $%2$s";
    public static final String MESSAGE_BORROW_BLACKLISTED_PERSON_FAILURE = "%1$s is prohibited from borrowing money!";

    private final ReadOnlyPerson personThatBorrowed;
    private final Debt amount;

    public BorrowCommand(Debt amount) throws CommandException {
        personThatBorrowed = selectPersonForCommand();
        this.amount = amount;
    }

    public BorrowCommand(Index targetIndex, Debt amount) throws CommandException {
        personThatBorrowed = selectPersonForCommand(targetIndex);
        this.amount = amount;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        ReadOnlyPerson targetPerson = personThatBorrowed;
        if (personThatBorrowed.isBlacklisted()) {
            throw new CommandException(String.format(MESSAGE_BORROW_BLACKLISTED_PERSON_FAILURE,
                    targetPerson.getName()));
        }

        try {
            if (personThatBorrowed.isWhitelisted()) {
                targetPerson = model.removeWhitelistedPerson(personThatBorrowed);
            }
            targetPerson = model.addDebtToPerson(targetPerson, amount);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        ListObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);
        reselectPerson(targetPerson);

        String currentList = ListObserver.getCurrentListName();

        return new CommandResult(currentList
                + String.format(MESSAGE_BORROW_SUCCESS, targetPerson.getName(), amount));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BorrowCommand // instanceof handles nulls
                && this.personThatBorrowed.equals(((BorrowCommand) other).personThatBorrowed)); // state check
    }
}

package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
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
            + "Parameters: INDEX (must be a positive integer) "
            + "AMOUNT (must have at least 1 digit and either a positive integer or a positive number with "
            + "two decimal places)\n"
            + "Example: " + COMMAND_WORD + " 1 120.50";
    public static final String MESSAGE_BORROW_SUCCESS = "%1$s has borrowed $%2$s";

    private final Index targetIndex;
    private final Debt amount;

    public BorrowCommand(Index targetIndex, Debt amount) {
        this.targetIndex = targetIndex;
        this.amount = amount;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personThatBorrowed = lastShownList.get(targetIndex.getZeroBased());

        try {
            if (personThatBorrowed.getIsWhitelisted()) {
                personThatBorrowed = model.removeWhitelistedPerson(personThatBorrowed);
            }
            model.addDebtToPerson(personThatBorrowed, amount);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_BORROW_SUCCESS, personThatBorrowed.getName(), amount));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BorrowCommand)) {
            return false;
        }

        // state check
        BorrowCommand e = (BorrowCommand) other;
        return targetIndex.equals(e.targetIndex)
                && amount.equals(e.amount);
    }
}

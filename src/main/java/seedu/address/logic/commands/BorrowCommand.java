package seedu.address.logic.commands;

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

    private final Index targetIndex;
    private final Debt amount;

    public BorrowCommand(Debt amount) {
        this.targetIndex = null;
        this.amount = amount;
    }

    public BorrowCommand(Index targetIndex, Debt amount) {
        this.targetIndex = targetIndex;
        this.amount = amount;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        ReadOnlyPerson personThatBorrowed = selectPerson(targetIndex);

        try {
            if (personThatBorrowed.isWhitelisted()) {
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
        return other == this // short circuit if same object
                || (other instanceof BorrowCommand // instanceof handles nulls
                && ((this.targetIndex == null && ((BorrowCommand) other).targetIndex == null) // both targetIndex null
                || this.targetIndex.equals(((BorrowCommand) other).targetIndex))); // state check
    }
}

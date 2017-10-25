package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Debt;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author jelneo
/**
 * Updates debt field when a person repays an amount
 */
public class PaybackCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "payback";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": decrease the debt of a person by "
            + "the amount of money entered.\n"
            + "Parameters: INDEX (optional, must be a positive integer if present) "
            + "AMOUNT (must have at least 1 digit and either a positive integer or a positive number with "
            + "two decimal places)\n"
            + "Example 1: " + COMMAND_WORD + " 1 100.50\n"
            + "Example 2: " + COMMAND_WORD + " 100.50";

    public static final String MESSAGE_PAYBACK_SUCCESS = "%1$s has paid $%2$s back";
    public static final String MESSAGE_PAYBACK_FAILURE = "Amount paid back cannot be more than the debt owed.";

    private final Index targetIndex;
    private final Debt amount;

    public PaybackCommand(Debt amount) {
        this.targetIndex = null;
        this.amount = amount;
    }

    public PaybackCommand(Index targetIndex, Debt amount) {
        this.targetIndex = targetIndex;
        this.amount = amount;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        ReadOnlyPerson personThatPaidBack = selectPerson(targetIndex);

        try {
            personThatPaidBack = model.deductDebtFromPerson(personThatPaidBack, amount);
            if (personThatPaidBack.getDebt().toNumber() == 0 && !personThatPaidBack.isBlacklisted()) {
                model.addWhitelistedPerson(personThatPaidBack);
            }
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_PAYBACK_SUCCESS, personThatPaidBack.getName(), amount));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PaybackCommand // instanceof handles nulls
                && ((this.targetIndex == null && ((PaybackCommand) other).targetIndex == null) // both targetIndex null
                || this.targetIndex.equals(((PaybackCommand) other).targetIndex)
                && this.amount.equals(((PaybackCommand) other).amount))); // state check
    }
}

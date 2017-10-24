package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
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
            + "Parameters: INDEX (must be a positive integer) "
            + "AMOUNT (must have at least 1 digit and either a positive integer or a positive number with "
            + "two decimal places)\n"
            + "Example: " + COMMAND_WORD + " 1 100.50";

    public static final String MESSAGE_PAYBACK_SUCCESS = "%1$s has paid $%2$s back";
    public static final String MESSAGE_PAYBACK_FAILURE = "Amount paid back cannot be more than the debt owed.";

    private final Index targetIndex;
    private final Debt amount;

    public PaybackCommand(Index targetIndex, Debt amount) {
        this.targetIndex = targetIndex;
        this.amount = amount;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personThatPaidBack = lastShownList.get(targetIndex.getZeroBased());

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
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PaybackCommand)) {
            return false;
        }

        // state check
        PaybackCommand e = (PaybackCommand) other;
        return targetIndex.equals(e.targetIndex)
                && amount.equals(e.amount);
    }
}

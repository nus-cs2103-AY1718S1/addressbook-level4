package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Pins an existing person on top of the address book
 */
public class PinCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "pin";

    /**
     * Shows message usage for pin command
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Pins the selected person identified "
            + "by the index number used in the last person listing.\n "
            + "Parameters: INDEX (must be a positive integer)\n "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_PIN_PERSON_SUCCESS = "Pinned Person: %1$s";
    public static final String MESSAGE_ALREADY_PINNED = "Person is already pinned!";
    public static final String MESSAGE_PIN_PERSON_FAILED = "Pin was unsuccessful";

    private final Index index;

    public PinCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToPin = lastShownList.get(index.getZeroBased());
        try {
            if (personToPin.isPinned()) {
                return new CommandResult(MESSAGE_ALREADY_PINNED);
            } else {
                model.pinPerson(personToPin);
                model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
                return new CommandResult(String.format(MESSAGE_PIN_PERSON_SUCCESS, personToPin));
            }
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(MESSAGE_PIN_PERSON_FAILED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PinCommand // instanceof handles nulls
                && this.index.equals(((PinCommand) other).index)); // state check
    }
}

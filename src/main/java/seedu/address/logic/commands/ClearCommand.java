package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    private boolean needToConfirm = true;

    public static final String COMMAND_WORDVAR_1 = "clear";
    public static final String COMMAND_WORDVAR_2 = "c";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_CONFIRMATION = "Type 'clear' again to confirm";

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);

        if (needToConfirm) {
            toggleConfirmationIndicator();
            return new CommandResult(MESSAGE_CONFIRMATION);
        } else {
            toggleConfirmationIndicator();
            model.resetData(new AddressBook());
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

    /**
     * Flip the boolean value of needToConfirm
     */
    public void toggleConfirmationIndicator() {
        needToConfirm = !needToConfirm;
    }
}

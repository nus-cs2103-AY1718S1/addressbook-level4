package seedu.address.logic.commands;
//@@author pjunwei95
import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.ui.ClearConfirmation;

/**
 * Pop-ups a clear confirmation window before clearing.
 * Confirming clears Weaver, otherwise cancels the clearing.
 */
public class ClearPopupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_CLEAR_SUCCESS = "Weaver has been cleared!";
    public static final String MESSAGE_NOT_CLEAR_SUCCESS = "Weaver has not been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        ClearConfirmation clearConfirmation = new ClearConfirmation();
        if (clearConfirmation.isClearCommand()) {
            requireNonNull(model);
            model.resetData(new AddressBook());
            model.clearBrowserPanel();
            return new CommandResult(MESSAGE_CLEAR_SUCCESS);

        } else {
            return new CommandResult(MESSAGE_NOT_CLEAR_SUCCESS);
        }
    }
}

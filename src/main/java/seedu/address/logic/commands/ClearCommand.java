package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Rolodex;

/**
 * Clears the rolodex.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_WORD_ABBREV = "c";
    public static final String MESSAGE_SUCCESS = "Rolodex has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new Rolodex());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

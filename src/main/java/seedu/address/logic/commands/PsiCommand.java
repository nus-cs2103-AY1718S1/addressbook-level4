package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowPsiRequestEvent;

//@@author aungmyin23
/**
 * Format full help instructions for every command for display.
 */
public class PsiCommand extends Command {

    public static final String COMMAND_WORD = "psi";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows PSI of Singapore.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_PSI_MESSAGE = "Latest PSI webpage loaded.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowPsiRequestEvent());
        return new CommandResult(SHOWING_PSI_MESSAGE);
    }
}

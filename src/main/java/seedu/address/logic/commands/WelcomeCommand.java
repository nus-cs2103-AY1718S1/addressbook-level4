package seedu.address.logic.commands;

import seedu.address.commons.core.enablingkeyword.EnablingKeyword;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Enables or disables the welcome screen.
 */
public class WelcomeCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "welcome";

    public static final String COMMAND_ALIAS = "w";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "(alias: " + COMMAND_ALIAS + ")"
            + ": Enables / disables the welcome screen upon running ConTag. "
            + "Paramters: "
            + "[ENABLING_KEYWORD] (enable / disable)";

    public static final String WELCOME_SCREEN_MESSAGE = "Welcome window is now %1$s.";

    private final EnablingKeyword enablingKeyword;

    public WelcomeCommand(EnablingKeyword enablingKeyword) {
        this.enablingKeyword = enablingKeyword;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        String keyword = enablingKeyword.toString();

        if (keyword.equals("enable")) {
            prefs.enableWelcomeScreen();
        } else if (keyword.equals("disable")) {
            prefs.disableWelcomeScreen();
        }
        return new CommandResult(String.format(WELCOME_SCREEN_MESSAGE, (keyword + "d")));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WelcomeCommand // instanceof handles nulls
                && this.enablingKeyword.equals(((WelcomeCommand) other).enablingKeyword)); // state check
    }
}

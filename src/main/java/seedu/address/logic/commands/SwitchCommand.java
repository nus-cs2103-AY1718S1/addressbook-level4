//@@author Hailinx
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SwitchDisplayEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Switches between TodoList and browser
 */
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";
    public static final String COMMAND_ALIAS = "sw";

    public static final int SWITCH_TO_TODOLIST = 1;
    public static final int SWITCH_TO_BROWSER = 2;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switch between Todo list and browser.\n"
            + "Parameters: NUMBER (1 for Todo list, 2 for browser)\n"
            + "Example: " + COMMAND_WORD + " " + SWITCH_TO_TODOLIST;

    public static final String MESSAGE_SUCCESS = "Switch to %1$s";

    public static final String PARSE_EXCEPTION_MESSAGE =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

    private final int mode;

    /**
     * Creates an SwitchCommand to switch according to the argument
     */
    public SwitchCommand(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
        try {
            mode = Integer.parseInt(trimmedArgs);
        } catch (Exception e) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new SwitchDisplayEvent(mode));
        switch (mode) {
        case SWITCH_TO_TODOLIST:
            return new CommandResult(String.format(MESSAGE_SUCCESS, "Todo list"));
        case SWITCH_TO_BROWSER:
            return new CommandResult(String.format(MESSAGE_SUCCESS, "browser"));
        default:
            throw new CommandException(PARSE_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchCommand // instanceof handles nulls
                && this.mode == ((SwitchCommand) other).mode); // state check
    }
}

package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeWindowSizeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.windowsize.WindowSize;

//@@author vivekscl
/**
 * Changes the window windowSize according to predefined sizes that the user can choose from
 */
public class ChangeWindowSizeCommand extends Command {


    public static final String COMMAND_WORD = "ws";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes window size. Command is case insensitive. \n"
            + "Parameters: WINDOWSIZE (Allowed sizes are small, med, big)\n"
            + "Example 1: " + COMMAND_WORD + " small\n"
            + "Example 2: " + COMMAND_WORD + " big";

    public static final String MESSAGE_SUCCESS = "Window sized has been changed to: ";

    private final String windowSize;

    public ChangeWindowSizeCommand(String windowSize) {
        this.windowSize = windowSize;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (WindowSize.isValidWindowSize(windowSize)) {
            double newWindowWidth = WindowSize.getUserDefinedWindowWidth(windowSize);
            double newWindowHeight = WindowSize.getUserDefinedWindowHeight(windowSize);
            EventsCenter.getInstance().post(new ChangeWindowSizeRequestEvent(newWindowWidth, newWindowHeight));
            return new CommandResult(MESSAGE_SUCCESS + newWindowWidth + " x " + newWindowHeight);
        } else {
            throw new CommandException(WindowSize.MESSAGE_WINDOW_SIZE_CONSTRAINTS);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeWindowSizeCommand // instanceof handles nulls
                && windowSize.equals(((ChangeWindowSizeCommand) other).windowSize));

    }
}

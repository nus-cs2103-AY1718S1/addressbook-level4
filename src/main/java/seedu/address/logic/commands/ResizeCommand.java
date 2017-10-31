package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_PARAMETERS;

import java.awt.Dimension;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ResizeMainWindowEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.ScreenDimension;

//@@author newalter
/**
 * Resize the main window.
 */
public class ResizeCommand extends Command {
    public static final Dimension DIMENSION = ScreenDimension.getDimension();
    public static final int MAX_WIDTH = DIMENSION.width;
    public static final int MAX_HEIGHT = DIMENSION.height;
    public static final String COMMAND_WORD = "resize";
    public static final String COMMAND_ALIAS = "rs";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Resize the MainWindows to "
            + String.format("the specified WIDTH(<=%d) and HEIGHT(<=%d) \n", MAX_WIDTH, MAX_HEIGHT)
            + "Parameters: WIDTH HEIGHT\n"
            + String.format("Example: " + COMMAND_WORD + " %d %d", MAX_WIDTH, MAX_HEIGHT);
    public static final String MESSAGE_SUCCESS = "Resize successfully to %d*%d";


    private int width;
    private int height;

    public ResizeCommand(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (width > MAX_WIDTH || height > MAX_HEIGHT) {
            throw new CommandException(MESSAGE_INVALID_COMMAND_PARAMETERS);
        }
        EventsCenter.getInstance().post(new ResizeMainWindowEvent(width, height));
        return new CommandResult(String.format(MESSAGE_SUCCESS, width, height));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ResizeCommand // instanceof handles nulls
                && this.width == ((ResizeCommand) other).width
                && this.height == ((ResizeCommand) other).height); // state check
    }
}

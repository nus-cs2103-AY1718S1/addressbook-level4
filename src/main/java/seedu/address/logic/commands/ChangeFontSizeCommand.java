//@@author ChenXiaoman
package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FONT_SIZE;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.font.FontSize;

/**
 * Customise the font size of the application.
 */
public class ChangeFontSizeCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "fs";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Customize the font size "
            + "Parameters: "
            + PREFIX_FONT_SIZE + "[FONT SIZE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FONT_SIZE + "xs\n";
    public static final String MESSAGE_SUCCESS = "Changed font size to ";

    public static final String INCREASE_FONT_SIZE_COMMAND = ChangeFontSizeCommand.COMMAND_WORD + " +";
    public static final String DECREASE_FONT_SIZE_COMMAND = ChangeFontSizeCommand.COMMAND_WORD + " -";

    private final String fontSize;

    public ChangeFontSizeCommand(String fontSize) {
        this.fontSize = fontSize;
    };

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        String newFontSize = fontSize;

        // Font size is valid
        if (FontSize.isValidFontSize(newFontSize)) {
            EventsCenter.getInstance().post(new ChangeFontSizeEvent(MESSAGE_SUCCESS + newFontSize + ".",
                    newFontSize));
            FontSize.setCurrentFontSizeLabel(newFontSize);
            return new CommandResult(MESSAGE_SUCCESS + newFontSize + ".");
        } else {
            throw new CommandException(FontSize.MESSAGE_FONT_SIZE_CONSTRAINTS);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeFontSizeCommand // instanceof handles nulls
                && fontSize.equals(((ChangeFontSizeCommand) other).fontSize));
    }
}

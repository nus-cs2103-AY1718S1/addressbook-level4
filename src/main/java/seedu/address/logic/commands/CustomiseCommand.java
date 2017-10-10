package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FONT_SIZE;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.model.font.FontSize;

/**
 * Customise the look of the Address Book application.
 */
public class CustomiseCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "customise";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Customise the look of the Address Book application. "
            + "Parameters: "
            + PREFIX_FONT_SIZE + "FONT-SIZE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FONT_SIZE + "xsmall\n";
    public static final String MESSAGE_SUCCESS = "Changed font size to ";
    public static final String FONT_SIZE_XSMALL = "xsmall";
    public static final String FONT_SIZE_SMALL = "small";
    public static final String FONT_SIZE_NORMAL = "normal";
    public static final String FONT_SIZE_LARGE = "large";
    public static final String FONT_SIZE_XLARGE = "xlarge";

    private final FontSize fontSize;

    public CustomiseCommand(FontSize fontSize) {
        this.fontSize = fontSize;
    };

    @Override
    public CommandResult executeUndoableCommand() {
        switch (fontSize.value) {
        case FONT_SIZE_XSMALL:
            EventsCenter.getInstance().post(new ChangeFontSizeEvent(MESSAGE_SUCCESS + FONT_SIZE_XSMALL + "."));
            return new CommandResult(MESSAGE_SUCCESS + FONT_SIZE_XSMALL + ".");

        case FONT_SIZE_SMALL:
            EventsCenter.getInstance().post(new ChangeFontSizeEvent(MESSAGE_SUCCESS + FONT_SIZE_SMALL + "."));
            return new CommandResult(MESSAGE_SUCCESS + FONT_SIZE_SMALL + ".");

        case FONT_SIZE_NORMAL:
            EventsCenter.getInstance().post(new ChangeFontSizeEvent(MESSAGE_SUCCESS + FONT_SIZE_NORMAL + "."));
            return new CommandResult(MESSAGE_SUCCESS + FONT_SIZE_NORMAL + ".");

        case FONT_SIZE_LARGE:
            EventsCenter.getInstance().post(new ChangeFontSizeEvent(MESSAGE_SUCCESS + FONT_SIZE_LARGE + "."));
            return new CommandResult(MESSAGE_SUCCESS + FONT_SIZE_LARGE + ".");

        case FONT_SIZE_XLARGE:
            EventsCenter.getInstance().post(new ChangeFontSizeEvent(MESSAGE_SUCCESS + FONT_SIZE_XLARGE + "."));
            return new CommandResult(MESSAGE_SUCCESS + FONT_SIZE_XLARGE + ".");

        default:
            break;
        }
        return new CommandResult(MESSAGE_SUCCESS + FONT_SIZE_NORMAL + ".");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomiseCommand // instanceof handles nulls
                && fontSize.equals(((CustomiseCommand) other).fontSize));
    }
}

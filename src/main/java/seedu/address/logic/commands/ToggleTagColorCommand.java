package seedu.address.logic.commands;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 *  Changes tag color mode in address book
 */
public class ToggleTagColorCommand extends Command {

    public static final String COMMAND_WORD = "tagcolor";
    public static final String MESSAGE_SUCCESS = "TagColor set to ";

    private final Logger logger = LogsCenter.getLogger(ToggleTagColorCommand.class);

    private boolean toSet;
    private String tag;
    private String color;
    private String message;

    public ToggleTagColorCommand(boolean toSet, String tag, String color) {
        this.toSet = toSet;
        this.tag = tag;
        this.color = color;
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.setTagColor(toSet, tag, color);
        model.resetData(model.getAddressBook());
        logger.fine("Tag color set to " + (toSet ? "on" : "off"));
        if (!tag.equals("") && !model.getAddressBook().getTagList().contains(tag)) {
            message = "No such tag";
        } else if (tagCustomized(tag, color)) {
            message = tag + " tag set to " + color;
        } else {
            message = String.format("%s%s", MESSAGE_SUCCESS, toSet ? "on" : "off");
        }
        return new CommandResult(message);
    }

    private boolean tagCustomized(String tag, String color) {
        return !tag.equals("") && !color.equals("");
    }
}

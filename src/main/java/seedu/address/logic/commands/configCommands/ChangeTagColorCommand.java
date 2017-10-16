package seedu.address.logic.commands.configCommands;

import static seedu.address.logic.commands.configCommands.ConfigCommand.ConfigType.TAG_COLOR;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;

/**
 * Changes the color of an existing tag.
 */
public class ChangeTagColorCommand extends ConfigCommand {
    private Tag tag;
    private String newColor;

    public ChangeTagColorCommand(String configValue, String tagName, String tagColor) {
        super(TAG_COLOR, configValue);
        this.newColor = tagColor;
    }

    @Override
    public CommandResult execute() throws CommandException {
        return null;
    }
}

package seedu.address.logic.commands.configs;

import static seedu.address.logic.commands.configs.ConfigCommand.ConfigType.TAG_COLOR;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

//@@author yunpengn
/**
 * Changes the color of an existing tag.
 */
public class ChangeTagColorCommand extends ConfigCommand {
    public static final String MESSAGE_SUCCESS = "The color of tag %1$s has been changed to %2$s.";
    public static final String MESSAGE_USAGE =  "Example: " + COMMAND_WORD + " --set-tag-color "
            + "friends blue";
    private static final String MESSAGE_NO_SUCH_TAG = "There is no such tag.";

    private Tag tag;
    private String newColor;

    public ChangeTagColorCommand(String configValue, String tagName, String tagColor) throws ParseException {
        super(TAG_COLOR, configValue);

        try {
            /* Two tags are equal as long as their tagNames are the same. */
            tag = new Tag(tagName);
        } catch (IllegalValueException e) {
            throw new ParseException(MESSAGE_TAG_CONSTRAINTS);
        }
        this.newColor = tagColor;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!model.hasTag(tag)) {
            throw new CommandException(MESSAGE_NO_SUCH_TAG);
        }

        model.setTagColor(tag, newColor);
        return new CommandResult(String.format(MESSAGE_SUCCESS, tag, newColor));
    }
}

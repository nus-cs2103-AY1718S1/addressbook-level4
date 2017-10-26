package seedu.address.logic.commands;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;

/**
 *  Changes tag color in address book
 */
public class ToggleTagColorCommand extends Command {

    public static final String COMMAND_WORD = "tagcolor";
    public static final String COMMAND_ALIAS = "tc";
    public static final String MESSAGE_SUCCESS = "tag color set to ";
    public static final String NO_SUCH_TAG_MESSAGE = "No such tag";

    private final Logger logger = LogsCenter.getLogger(ToggleTagColorCommand.class);

    private String tag;
    private String color;
    private String message;

    public ToggleTagColorCommand(String tag, String color) {
        this.tag = tag;
        this.color = color;
    }

    /**
     * Sets all tag color on/off, OR sets a specific tag string to a specific color
     * @throws CommandException if tagcolor command has the wrong number of parameters
     *
     */
    @Override
    public CommandResult execute() throws CommandException {

        model.setTagColor(tag, color);
        model.resetData(model.getAddressBook());

        if (color == null) {
            message = String.format("%s%s", MESSAGE_SUCCESS, tag.equals("random") ? "random" : "off");
        } else if (!(containsTag(model.getAddressBook().getTagList(), tag))) {
            message = NO_SUCH_TAG_MESSAGE;
        } else {
            message = tag + " " + MESSAGE_SUCCESS + color;
        }
        return new CommandResult(message);
    }

    /**
     * Check if
     * (a) Object is the same object
     * (b) Object is an instance of the object and that toSet, tag and color are the same
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ToggleTagColorCommand // instanceof handles nulls
                && this.tag.equals(((ToggleTagColorCommand) other).tag)); // state check
    }

    /**
     * Helper method to check if the tagList contains such a tag name
     * @param tagList - ObservableList type with elements of Tag type
     * @param tagString - Tag to check if it is within the list of tags (tagList)
     */
    private boolean containsTag(ObservableList<Tag> tagList, String tagString) {

        for (Tag tag : tagList) {
            if (tag.tagName.equals(tagString)) {
                return true;
            }
        }
        return false;
    }
}

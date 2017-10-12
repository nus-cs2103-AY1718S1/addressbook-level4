package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColor;

/**
 * Change color of tags
 */
public class ChangeTagColorCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "color";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the color of a tag or list of tags. "
            + "Existing color will be overwritten by the input.\n"
            + "Parameters: " + COMMAND_WORD + " "
            + PREFIX_TAG + "[TAG] "
            + PREFIX_COLOR + "[COLOR]\n"
            + "Example: " + COMMAND_WORD + " t/friend "
            + "c/red";

    public static final String MESSAGE_ARGUMENTS = "Tag: %1$s, Color: %2$s";

    public static final String MESSAGE_NOT_EXISTING_TAGS = "Cannot change color of not existing tags: %1$s";

    public static final String MESSAGE_INVALID_COLOR = "Color %1$s is invalid."
            + "\n" + TagColor.MESSAGE_TAG_COLOR_CONSTRAINTS;

    public static final String MESSAGE_FAILED = "Change tag color command failed";

    public static final String MESSAGE_CHANGE_TAG_COLOR_SUCCESS = "Change tag color of %1$s to %2$s";

    private final Set<Tag> tagList;
    private final TagColor color;

    /**
     * @param tagList to edit the color
     * @param color of the tagList
     */
    public ChangeTagColorCommand(Set<Tag> tagList, TagColor color) {
        requireNonNull(tagList);
        requireNonNull(color);

        this.tagList = tagList;
        this.color = color;
    }

    /**
     *Check whether a given tag exists in current database
     */
    private boolean isExistingTagName(Tag t) {
        for (Tag tag : model.getAddressBook().getTagList()) {
            if (tag.tagName.equals(t.tagName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        // Store all non existing tags
        Set<Tag> nonExistingTagList = new HashSet<>();

        // Check whether tags in the tagList are not existing tags
        for (Tag tag: tagList) {
            if (!isExistingTagName(tag)) {
                nonExistingTagList.add(tag);
            }
        }

        // There are tags that are not existing tags
        if (nonExistingTagList.size() != 0) {
            throw new CommandException(String.format(MESSAGE_NOT_EXISTING_TAGS, nonExistingTagList));
        }

        // Check whether the input tag color is a valid color name
        if (!TagColor.isValidTagColorName(color.tagColorName)) {
            throw new CommandException(String.format(MESSAGE_INVALID_COLOR, color));
        }

        try {
            model.updateTagColorPair(tagList, color);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_FAILED);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_CHANGE_TAG_COLOR_SUCCESS, tagList, color.tagColorName));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ChangeTagColorCommand)) {
            return false;
        }

        // state check
        ChangeTagColorCommand e = (ChangeTagColorCommand) other;
        return tagList.equals(e.tagList)
                && color.equals(e.color);
    }
}

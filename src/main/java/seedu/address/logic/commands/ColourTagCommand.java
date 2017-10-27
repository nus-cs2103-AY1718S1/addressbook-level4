package seedu.address.logic.commands;

import java.util.HashMap;
import java.util.Map;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.tag.Tag;

/**
 * Changes the colour of a tag to a given colour.
 */
public class ColourTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "colourtag";
    public static final String COMMAND_ALIAS = "ct";

    public static final String COMMAND_PARAMETERS = "TAG (must be alphanumeric) COLOUR";

    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_PARAMETERS;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the colour of the given tag to the given colour.\n"
            + "Parameters: " + COMMAND_PARAMETERS + "\n"
            + "Example: " + COMMAND_WORD + " friend red";
    public static final String MESSAGE_COLOUR_TAG_SUCCESS = "Colour of %1$s will be %2$s on next start.";

    private final Tag tag;
    private final String colour;

    public ColourTagCommand(Tag tag, String colour) {
        this.tag = tag;
        this.colour = colour;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        ModelManager m = ((ModelManager) model);
        Map<Tag, String> currentTagColours = new HashMap<>(m.getUserPrefs().getGuiSettings().getTagColours());
        currentTagColours.put(tag, colour);
        m.getUserPrefs().getGuiSettings().setTagColours(currentTagColours);

        return new CommandResult(String.format(MESSAGE_COLOUR_TAG_SUCCESS, tag, colour));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ColourTagCommand // instanceof handles nulls
                && this.tag.equals(((ColourTagCommand) other).tag)
                && this.colour.equals(((ColourTagCommand) other).colour)); // state check
    }
}

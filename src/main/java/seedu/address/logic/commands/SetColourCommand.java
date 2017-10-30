package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;

/**
 * Sets all iteration of the specified tag to the requested colour
 */
public class SetColourCommand extends Command {

    public static final String COMMAND_WORD = "setcolour";

    /**
     * Shows message usage for SetColour Command
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets colour to all iterations of the tag identified\n"
            + "Parameters: TAG_NAME (must be a valid tag name) COLOUR\n"
            + "Example: " + COMMAND_WORD + " friends blue";

    public static final String SETCOLOUR_SUCCESS = "All tags [%1s] are now coloured %2s";
    public static final String SETCOLOUR_INVALID_COLOUR = "Unfortunately, %1s is unavailable to be set in addressbook";
    private static final String[] colours = { "red", "yellow", "blue",
        "orange", "brown", "green", "pink", "black", "grey" };

    private String tag;
    private String newColour;

    public SetColourCommand(String tag, String colour) {
        this.tag = tag;
        newColour = colour;
    }

    @Override
    public CommandResult execute() {
        try {
            if (isColourValid()) {
                model.setTagColour(tag, newColour);
                model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
                return new CommandResult(String.format(SETCOLOUR_SUCCESS, tag, newColour));
            }
            return new CommandResult(String.format(SETCOLOUR_INVALID_COLOUR, newColour));
        } catch (IllegalValueException ive) {
            return new CommandResult(ive.getMessage());
        }
    }

    /**
     * Checks whether the requested colour is within String[]
     * @return true is colour is a colour inside the String[]
     */
    private boolean isColourValid() {
        for (String colour: colours) {
            if (colour.equals(newColour)) {
                return true;
            }
        }
        return false;
    }
}

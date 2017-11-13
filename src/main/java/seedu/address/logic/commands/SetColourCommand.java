package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

//@@author eldonng
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
    public static final String SETCOLOUR_INVALID_TAG = "No such tag.";
    private static final String[] colours = {"blue", "red", "brown", "green", "black", "purple", "indigo", "grey",
        "chocolate", "orange", "aquamarine"};

    private String tag;
    private String newColour;

    public SetColourCommand(String tag, String colour) {
        this.tag = tag;
        newColour = colour;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            if (isColourValid()) {
                model.setTagColour(tag, newColour);
                model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
                return new CommandResult(String.format(SETCOLOUR_SUCCESS, tag, newColour));
            }
            throw new CommandException(String.format(SETCOLOUR_INVALID_COLOUR, newColour));
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetColourCommand // instanceof handles nulls
                && this.tag.equals(((SetColourCommand) other).tag)
                && this.newColour.equals(((SetColourCommand) other).newColour)); // state check
    }
}

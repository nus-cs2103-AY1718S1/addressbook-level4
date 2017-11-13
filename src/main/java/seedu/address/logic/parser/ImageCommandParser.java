package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ImageCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author liliwei25
/**
 * Parses input arguments and creates a new ImageCommand object
 */
public class ImageCommandParser implements Parser<ImageCommand> {

    private static final String REMOVE = "remove";
    private static final boolean REMOVE_IMAGE = true;
    private static final String SPACE = " ";
    private static final int INDEX_POS = 0;
    private static final int SELECT_POS = 1;
    private static final String INVALID_POST_INDEX = "Wrong input after index";

    /**
     * Parses the given {@code String} of arguments in the context of the ImageCommand
     * and returns an ImageCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImageCommand parse(String args) throws ParseException {
        try {
            return getImageCommand(args);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImageCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Changes profile picture of Person depending on selected mode (remove/edit)
     *
     * @param args User input
     * @return New ImageCommand with correct Index and mode selection (remove/edit)
     * @throws IllegalValueException If input after index is invalid
     */
    private ImageCommand getImageCommand(String args) throws IllegalValueException {
        String[] splitArgs = args.trim().split(SPACE);
        Index index = ParserUtil.parseIndex(splitArgs[INDEX_POS]);
        if (toRemoveImage(splitArgs)) {
            return new ImageCommand(index, REMOVE_IMAGE);
        } else if (toEditImage(splitArgs)) {
            return new ImageCommand(index, !REMOVE_IMAGE);
        } else {
            throw new IllegalValueException(INVALID_POST_INDEX);
        }
    }

    /**
     * If given input is to edit profile picture of Person
     *
     * @param inputs User input
     * @return True if input does not contain "remove" keyword
     */
    private boolean toEditImage(String[] inputs) {
        return inputs.length <= 1;
    }

    /**
     * If given input is to remove profile picture of Person
     *
     * @param inputs User input
     * @return True if input contains "remove" keyword
     */
    private boolean toRemoveImage(String[] inputs) {
        return inputs.length > 1 && inputs[SELECT_POS].toLowerCase().equals(REMOVE);
    }
}

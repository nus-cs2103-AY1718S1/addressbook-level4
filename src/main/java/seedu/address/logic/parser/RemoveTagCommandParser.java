package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

//@@author liliwei25
/**
 * Parses input arguments and creates a new RemoveTagCommand object
 */
public class RemoveTagCommandParser implements Parser<RemoveTagCommand> {

    private static final String SPACE = " ";
    private static final int TAG_POS = 1;
    private static final int INDEX_POS = 0;

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagCommand
     * and returns an RemoveTagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String arg) throws ParseException {
        String[] inputs = arg.trim().split(SPACE);
        try {
            return getRemoveTagCommand(inputs);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Creates a new RemoveTagCommand depending on user input
     *
     * @param inputs User input
     * @return New RemoveTagCommand depending on user input
     * @throws IllegalValueException If Tag specified by user is invalid
     */
    private RemoveTagCommand getRemoveTagCommand(String[] inputs) throws IllegalValueException {
        if (toRemoveAll(inputs)) {
            Tag t = ParserUtil.parseTag(inputs[INDEX_POS]);
            return new RemoveTagCommand(RemoveTagCommand.ALL, t);
        } else {
            Tag t = ParserUtil.parseTag(inputs[TAG_POS]);
            return new RemoveTagCommand(inputs[INDEX_POS], t);
        }
    }

    /**
     * Determines if user chooses to remove target Tag from all Persons
     *
     * @param inputs User input
     * @return True if user does not include a Index in the input
     */
    private boolean toRemoveAll(String[] inputs) {
        return inputs.length < 2;
    }
}

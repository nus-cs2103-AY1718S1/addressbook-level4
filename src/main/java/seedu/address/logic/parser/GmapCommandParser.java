package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.GmapCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameConsistsKeywordsPredicate;

/**
 * Parses input arguments and creates a new GmaptCommand object
 */
public class GmapCommandParser implements Parser<GmapCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GmapCommand
     * and returns an GmapCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GmapCommand parse(String args) throws ParseException {
        //@@author Choony93
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GmapCommand.MESSAGE_USAGE));
        } else {
            if (Character.isDigit(trimmedArgs.charAt(0))) {
                try {
                    Index index = ParserUtil.parseIndex(args);
                    return new GmapCommand(index);
                } catch (IllegalValueException ive) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, GmapCommand.MESSAGE_USAGE));
                }
            } else {
                String[] nameKeywords = trimmedArgs.split("\\s+");
                return new GmapCommand(new NameConsistsKeywordsPredicate(Arrays.asList(nameKeywords)));
            }
        }
        //@@author
    }
}

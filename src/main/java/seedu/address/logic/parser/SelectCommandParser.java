package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.ui.BrowserSearchMode.GOOGLE_SEARCH_ADDRESS;
import static seedu.address.ui.BrowserSearchMode.GOOGLE_SEARCH_EMAIL;
import static seedu.address.ui.BrowserSearchMode.GOOGLE_SEARCH_NAME;
import static seedu.address.ui.BrowserSearchMode.GOOGLE_SEARCH_PHONE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.optionparser.CommandOptionUtil;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser implements Parser<SelectCommand> {

    public static final String PARSE_EXCEPTION_MESSAGE =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        String optionPrefix = CommandOptionUtil.getOptionPrefix(trimmedArgs);
        String optionArgs = CommandOptionUtil.getOptionArgs(optionPrefix, trimmedArgs);

        int searchMode;
        switch (optionPrefix) {
        case SelectCommand.PREFIX_SELECT_SEARCH_NAME:
            searchMode = GOOGLE_SEARCH_NAME;
            break;
        case SelectCommand.PREFIX_SELECT_SEARCH_PHONE:
            searchMode = GOOGLE_SEARCH_PHONE;
            break;
        case SelectCommand.PREFIX_SELECT_SEARCH_EMAIL:
            searchMode = GOOGLE_SEARCH_EMAIL;
            break;
        case SelectCommand.PREFIX_SELECT_SEARCH_ADDRESS:
            searchMode = GOOGLE_SEARCH_ADDRESS;
            break;
        default:
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        try {
            Index index = ParserUtil.parseIndex(optionArgs);
            return new SelectCommand(index, searchMode);
        } catch (IllegalValueException ive) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
    }
}

package seedu.address.logic.parser;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.optionparser.CommandOptionUtil;
import seedu.address.logic.parser.optionparser.FindOptionByName;
import seedu.address.logic.parser.optionparser.FindOptionFuzzy;
import seedu.address.logic.parser.optionparser.FindOptionInDetail;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    public static final String PARSE_EXCEPTION_MESSAGE =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

    /**
     * Parses the given {@code args} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        String optionPrefix = CommandOptionUtil.getOptionPrefix(trimmedArgs);
        String optionArgs = CommandOptionUtil.getOptionArgs(optionPrefix, trimmedArgs);

        switch (optionPrefix) {
            case FindCommand.PREFIX_FIND_IN_DETAIL:
                return new FindOptionInDetail(optionArgs).parse();
            case FindCommand.PREFIX_FIND_FUZZY_FIND:
                return new FindOptionFuzzy(optionArgs).parse();
            case FindCommand.PREFIX_FIND_BY_NAME:
                return new FindOptionByName(optionArgs).parse();
            default:
                throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
    }

}

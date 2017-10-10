package seedu.address.logic.parser;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.modeparser.CommandModeUtil;
import seedu.address.logic.parser.modeparser.FindModeByName;
import seedu.address.logic.parser.modeparser.FindModeFuzzy;
import seedu.address.logic.parser.modeparser.FindModeInDetail;

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

        String modePrefix = CommandModeUtil.getModePrefix(trimmedArgs);
        String modeArgs = CommandModeUtil.getModeArgs(modePrefix, trimmedArgs);

        switch (modePrefix) {
            case FindCommand.PREFIX_FIND_IN_DETAIL:
                return new FindModeInDetail(modeArgs).parse();
            case FindCommand.PREFIX_FIND_FUZZY_FIND:
                return new FindModeFuzzy(modeArgs).parse();
            case FindCommand.PREFIX_FIND_BY_NAME:
                return new FindModeByName(modeArgs).parse();
            default:
                throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
    }

}

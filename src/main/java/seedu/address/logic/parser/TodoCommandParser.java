//@@author Hailinx
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TodoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.optionparser.CommandOptionUtil;
import seedu.address.logic.parser.optionparser.TodoOptionAdd;
import seedu.address.logic.parser.optionparser.TodoOptionDeleteAll;
import seedu.address.logic.parser.optionparser.TodoOptionDeleteOne;
import seedu.address.logic.parser.optionparser.TodoOptionList;

/**
 * Parses input arguments and creates a new TodoCommand object
 */
public class TodoCommandParser implements Parser<TodoCommand> {

    public static final String PARSE_EXCEPTION_MESSAGE =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TodoCommand.MESSAGE_USAGE);

    private static final String REGEX = "^(\\d+)";
    private static final int FIRST_INDEX_GROUP = 0;

    /**
     * Parses the given {@code String} of arguments in the context of the TodoCommand
     * and returns an TodoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public TodoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new TodoCommand(TodoCommand.PREFIX_TODO_LIST_ALL, null, null, null);
        }

        String oneBasedIndex = parseIndexString(trimmedArgs);
        String option = parseStrAfterIndex(oneBasedIndex, trimmedArgs);

        String optionPrefix = CommandOptionUtil.getOptionPrefix(option);
        String optionArgs = CommandOptionUtil.getOptionArgs(optionPrefix, option);

        Index index;
        try {
            index = ParserUtil.parseIndex(oneBasedIndex);
        } catch (IllegalValueException e) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        // returns TodoCommand by parsing {@code optionPrefix}
        switch (optionPrefix) {
        case TodoCommand.PREFIX_TODO_ADD:
            return new TodoOptionAdd(optionArgs, index).parse();
        case TodoCommand.PREFIX_TODO_DELETE_ONE:
            return new TodoOptionDeleteOne(optionArgs, index).parse();
        case TodoCommand.PREFIX_TODO_DELETE_ALL:
            return new TodoOptionDeleteAll(optionArgs, index).parse();
        case TodoCommand.PREFIX_TODO_LIST:
            return new TodoOptionList(optionArgs, index).parse();
        default:
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
    }

    /**
     * Parses the {@code Index} from argument string.
     */
    public static String parseIndexString(String trimmedArgs) throws ParseException {
        Matcher matcher = Pattern.compile(REGEX).matcher(trimmedArgs);
        try {
            if (matcher.find()) {
                return matcher.group(FIRST_INDEX_GROUP);
            }
        } catch (Exception e) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
        throw new ParseException(PARSE_EXCEPTION_MESSAGE);
    }

    /**
     * Gets the rest of string after parsing {@code Index}
     * @see #parseIndexString(String)
     */
    public static String parseStrAfterIndex(String index, String args) {
        return args.substring(args.indexOf(index) + index.length()).trim();
    }
}

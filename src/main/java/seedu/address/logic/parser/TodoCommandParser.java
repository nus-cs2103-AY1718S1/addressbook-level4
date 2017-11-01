package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TodoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.optionparser.CommandOptionUtil;
import seedu.address.logic.parser.optionparser.TodoOptionAdd;
import seedu.address.logic.parser.optionparser.TodoOptionDeleteAll;
import seedu.address.logic.parser.optionparser.TodoOptionDeleteOne;
import seedu.address.logic.parser.optionparser.TodoOptionList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class TodoCommandParser implements Parser<TodoCommand> {

    public static final String PARSE_EXCEPTION_MESSAGE =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TodoCommand.MESSAGE_USAGE);

    private static final String REGEX = "^(\\d+)";

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

        System.out.println("oneBasedIndex:" + oneBasedIndex + "   optionPrefix:" + optionPrefix + "   optionArgs:" + optionArgs);
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

    public static String parseIndexString(String trimmedArgs) throws ParseException {
        int FirstIndexGroup = 0;
        Matcher matcher = Pattern.compile(REGEX).matcher(trimmedArgs);
        try {
            if (matcher.find()) {
                return matcher.group(FirstIndexGroup);
            }
        } catch (Exception e) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
        throw new ParseException(PARSE_EXCEPTION_MESSAGE);
    }

    public static String parseStrAfterIndex(String index, String args) {
        return args.substring(args.indexOf(index) + index.length()).trim();
    }
}

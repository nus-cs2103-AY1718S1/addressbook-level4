//@@author Hailinx
package seedu.address.logic.parser.optionparser;

import static seedu.address.logic.parser.TodoCommandParser.PARSE_EXCEPTION_MESSAGE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TodoCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Deletes a TodoItem from given person.
 */
public class TodoOptionDeleteOne extends CommandOption<TodoCommand> {

    private static final int MATCH_INDEX_GROUP = 1;

    private final Index index;

    public TodoOptionDeleteOne(String optionArgs, Index index) {
        super(optionArgs);
        this.index = index;
    }

    @Override
    public TodoCommand parse() throws ParseException {
        if (!isValidOptionArgs()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        Index todoItemIndex;
        try {
            Matcher matcher = Pattern.compile("^(\\d+)$").matcher(optionArgs);
            if (matcher.find()) {
                String oneBasedIndex = matcher.group(MATCH_INDEX_GROUP);
                todoItemIndex = ParserUtil.parseIndex(oneBasedIndex);
                return new TodoCommand(TodoCommand.PREFIX_TODO_DELETE_ONE, index, null, todoItemIndex);
            }
        } catch (Exception e) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
        throw new ParseException(PARSE_EXCEPTION_MESSAGE);
    }
}

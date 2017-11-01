//@@author Hailinx
package seedu.address.logic.parser.optionparser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.logic.parser.TodoCommandParser.PARSE_EXCEPTION_MESSAGE;
import static seedu.address.model.util.TimeConvertUtil.convertStringToTime;

import java.time.LocalDateTime;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TodoCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TodoItem;

/**
 * Adds a new TodoItem.
 */
public class TodoOptionAdd extends CommandOption<TodoCommand> {

    private final Index index;

    public TodoOptionAdd(String optionArgs, Index index) {
        super(optionArgs);
        this.index = index;
    }

    @Override
    public TodoCommand parse() throws ParseException {
        if (!isValidOptionArgs()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                " " + optionArgs, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_TASK);

        TodoItem todoItem;
        try {
            LocalDateTime startTime = convertStringToTime(argMultimap.getValue(PREFIX_START_TIME).get());

            LocalDateTime endTime = null;
            if (argMultimap.getValue(PREFIX_END_TIME).isPresent()) {
                endTime = convertStringToTime(argMultimap.getValue(PREFIX_END_TIME).get());
            }

            String task = argMultimap.getValue(PREFIX_TASK).get();

            todoItem = new TodoItem(startTime, endTime, task);
        } catch (Exception e) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }

        return new TodoCommand(TodoCommand.PREFIX_TODO_ADD, index, todoItem, null);
    }
}

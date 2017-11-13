package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.OrderEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author kaiyu92

/**
 * Parses input arguments and creates a new OrderEventCommand object
 */
public class OrderEventCommandParser implements Parser<OrderEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the OrderEventCommand
     * and returns an OrderEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public OrderEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, OrderEventCommand.MESSAGE_USAGE));
        }
        String upperCaseParameter = trimmedArgs.toUpperCase();
        return new OrderEventCommand(upperCaseParameter);
    }
}

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BorrowCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Debt;

//@@author jelneo
/**
 * Parses input arguments and creates a new BorrowCommand object
 */
public class BorrowCommandParser implements Parser<BorrowCommand> {

    public static final String ONE_OR_MORE_SPACES_REGEX = "\\s+";
    // arguments: index and debt amount borrowed
    private static final int MAXIMUM_ARGS_LENGTH = 2;
    private static final int ARGS_LENGTH_WITHOUT_INDEX = 1;


    @Override
    public BorrowCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index index;
        Debt debtAmount;
        String[] argsList = args.trim().split(ONE_OR_MORE_SPACES_REGEX);
        if (argsList.length > MAXIMUM_ARGS_LENGTH) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BorrowCommand.MESSAGE_USAGE));
        }

        if (argsList.length == ARGS_LENGTH_WITHOUT_INDEX) {
            try {
                debtAmount = new Debt(argsList[0]);
            } catch (IllegalValueException ive) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BorrowCommand.MESSAGE_USAGE));
            }

            return new BorrowCommand(debtAmount);
        } else {
            try {
                index = ParserUtil.parseIndex(argsList[0]);
                debtAmount = new Debt(argsList[1]);
            } catch (IllegalValueException ive) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BorrowCommand.MESSAGE_USAGE));
            }
        }

        return new BorrowCommand(index, debtAmount);
    }
}

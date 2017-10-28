package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteRemarkCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<Command> {

    static final Pattern FIRST_INT_PATTERN = Pattern.compile("^(\\d+)");
    static final Pattern DELETE_INDEX_PATTERN = Pattern.compile("-d\\s*(\\d+)");

    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public Command parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        Index index;
        String remarkContent;
        Matcher matcherFirstInt = FIRST_INT_PATTERN.matcher(trimmedArgs);
        Matcher matcherDeleteRmk = DELETE_INDEX_PATTERN.matcher(trimmedArgs);

        try {
            if (matcherFirstInt.find()) {
                index = ParserUtil.parseIndex(matcherFirstInt.group(0));
                remarkContent = trimmedArgs.substring(matcherFirstInt.group(0).length()).trim();
                return new RemarkCommand(index, remarkContent);
            } else if (matcherDeleteRmk.find()) {
                index = ParserUtil.parseIndex(matcherDeleteRmk.group(1));
                return new DeleteRemarkCommand(index);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
            }


        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

    }
}

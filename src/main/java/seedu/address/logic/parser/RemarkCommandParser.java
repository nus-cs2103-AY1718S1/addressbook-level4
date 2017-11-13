package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.EMPTY_STRING;
import static seedu.address.logic.parser.ParserUtil.INDEX_ZERO;
import static seedu.address.logic.parser.ParserUtil.SPACE_STRING;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

//@@author justintkj
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * Prefix is not needed for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String[] splitArgs = args.trim().split(SPACE_STRING);
        Remark remark;
        Index index;
        try {
            index = ParserUtil.parseIndex(splitArgs[INDEX_ZERO]);
            remark = new Remark(args.replaceFirst(splitArgs[INDEX_ZERO], EMPTY_STRING).trim());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }
        return new RemarkCommand(index, remark);
    }
}

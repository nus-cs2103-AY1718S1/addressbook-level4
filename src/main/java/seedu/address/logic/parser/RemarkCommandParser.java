package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String[] argus = trimmedArgs.split("r/");

        Integer index = Integer.parseInt(argus[0].trim());
        Remark remark = new Remark(argus[1].trim());

        return new RemarkCommand(index, remark);
    }

}

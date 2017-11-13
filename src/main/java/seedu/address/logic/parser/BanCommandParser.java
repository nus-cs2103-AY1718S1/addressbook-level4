package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BanCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author jaivigneshvenugopal
/**
 * Parses input arguments and creates a new {@code BanCommand} object
 */
public class BanCommandParser implements Parser<BanCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BanCommand
     * and returns an BanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BanCommand parse(String args) throws ParseException, CommandException {
        try {
            if (args.trim().equals("")) {
                return new BanCommand();
            } else {
                Index index = ParserUtil.parseIndex(args);
                return new BanCommand(index);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BanCommand.MESSAGE_USAGE));
        }
    }
}

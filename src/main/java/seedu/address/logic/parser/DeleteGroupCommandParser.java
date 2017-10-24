package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CreateGroupCommand;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.ReadOnlyGroup;

import java.util.stream.Stream;

/**
 * Parses input arguments and creates a new DeleteGroundCommand object
 */
public class DeleteGroupCommandParser implements Parser<DeleteGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteGroupCommand parse(String args) throws ParseException {
        try {
            if(args.trim().matches("\\d+")) {
                Index index = ParserUtil.parseIndex(args);
                return new DeleteGroupCommand(index);
            } else {
                ArgumentMultimap argMultimap =
                        ArgumentTokenizer.tokenize(args, PREFIX_NAME);

                if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateGroupCommand.MESSAGE_USAGE));
                }

                try {
                    GroupName name = ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_NAME)).get();
                    return new DeleteGroupCommand(name);
                } catch (IllegalValueException ive) {
                    throw new ParseException(ive.getMessage(), ive);
                }
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}



package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.ParserUtil.parseFirstInt;
import static seedu.address.logic.parser.ParserUtil.parseRemoveFirstInt;
import static seedu.address.logic.parser.ParserUtil.tryParseInt;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseArgsException;
import seedu.address.model.person.Remark;

/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     * @throws ParseArgsException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseArgsException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseArgsException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");

        return new RemarkCommand(index, new Remark(remark));
    }

    /**
     * Returns a formatted argument string given unformatted
     * {@code commandWord} and {@code rawArgs}
     * or a {@code null} {@code String} if not formattable.
     */
    public static String parseArguments(String commandWord, String rawArgs) {
        // Check if index (number) exists, removes Remark prefix (if it exists) and re-adds it before returning.
        if (tryParseInt(rawArgs)) {
            String indexString = Integer.toString(parseFirstInt(rawArgs));
            String remark = parseRemoveFirstInt(rawArgs).trim().replace(PREFIX_REMARK.toString(), "");
            return " " + indexString + " " + PREFIX_REMARK + remark;
        } else if (tryParseInt(commandWord)) {
            String indexString = Integer.toString(parseFirstInt(commandWord));
            String remark = rawArgs.trim().replace(PREFIX_REMARK.toString(), "");
            return " " + indexString + " " + PREFIX_REMARK + remark;
        }
        return null;
    }
}

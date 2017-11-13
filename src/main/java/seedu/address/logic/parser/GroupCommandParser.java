package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author tingtx

/**
 * Parses input arguments and creates a new GroupCommand object
 */
public class GroupCommandParser implements Parser<GroupCommand> {


    /**
     * Returns true the agrs contain only alphabets
     */
    private static boolean containsAlphabetOnly(String args) {
        return args.matches("[a-zA-Z]+");
    }

    /**
     * Returns true the prefixes contains no empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return Stream.of(prefix).allMatch(groupPrefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the GroupCommand
     * and returns an GroupCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        args = args.trim();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GROUP);

        List<Index> indexes = new ArrayList<>();
        if (containsAlphabetOnly(args)) {
            return new GroupCommand(indexes, args);
        }

        if (!isPrefixesPresent(argMultimap, PREFIX_GROUP) && !containsAlphabetOnly(args)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE));
        }

        String preamble;
        String[] indexStr;
        try {
            preamble = argMultimap.getPreamble();
            indexStr = preamble.split("\\s+");
            for (String index : indexStr) {
                indexes.add(ParserUtil.parseIndex(index));
            }

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupCommand.MESSAGE_USAGE));
        }

        String group = argMultimap.getValue(PREFIX_GROUP).get();
        return new GroupCommand(indexes, group);
    }
}

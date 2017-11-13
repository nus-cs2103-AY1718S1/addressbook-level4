package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.LinkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LinkCommand object
 */
public class LinkCommandParser implements Parser<LinkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LinkCommand
     * and returns an LinkCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public LinkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON);

        Index index;
        ArrayList<Index> personIndices = new ArrayList<>();

        if (!(argMultimap.getValue(PREFIX_PERSON).isPresent())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            for (String pIndex : argMultimap.getAllValues(PREFIX_PERSON)) {
                personIndices.add(ParserUtil.parseIndex(pIndex));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
        }

        return new LinkCommand(index, personIndices);
    }
}

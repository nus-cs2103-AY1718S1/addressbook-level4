package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MRT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_MRT, PREFIX_TAG);

        boolean isInclusive;

        try {
            isInclusive = ParserUtil.parseType(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        FindPersonDescriptor personDescriptor = new FindPersonDescriptor();
        argMultimap.getValue(PREFIX_NAME).ifPresent(personDescriptor::setName);
        argMultimap.getValue(PREFIX_PHONE).ifPresent(personDescriptor::setPhone);
        argMultimap.getValue(PREFIX_EMAIL).ifPresent(personDescriptor::setEmail);
        argMultimap.getValue(PREFIX_ADDRESS).ifPresent(personDescriptor::setAddress);
        argMultimap.getValue(PREFIX_MRT).ifPresent(personDescriptor::setMrt);
        argMultimap.getValue(PREFIX_TAG).ifPresent(personDescriptor::setTag);

        return new FindCommand(isInclusive, personDescriptor);
    }
}

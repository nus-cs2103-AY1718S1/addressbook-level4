package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBSITE;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommand.FindPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                    PREFIX_NAME,
                    PREFIX_PHONE,
                    PREFIX_EMAIL,
                    PREFIX_ADDRESS,
                    PREFIX_BIRTHDAY,
                    PREFIX_WEBSITE,
                    PREFIX_TAG);

        FindPersonDescriptor findPersonDescriptor = new FindPersonDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME))
                .ifPresent(findPersonDescriptor::setName);
            ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE))
                .ifPresent(findPersonDescriptor::setPhone);
            ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY))
                .ifPresent(findPersonDescriptor::setBirthday);
            ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL))
                .ifPresent(findPersonDescriptor::setEmail);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS))
                .ifPresent(findPersonDescriptor::setAddress);
            ParserUtil.parseWebsite(argMultimap.getValue(PREFIX_WEBSITE))
                .ifPresent(findPersonDescriptor::setWebsite);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (findPersonDescriptor.allNull()) {
            throw new ParseException(FindCommand.MESSAGE_NO_FIELD_PROVIDED);
        }


        return new FindCommand(findPersonDescriptor);
    }

}

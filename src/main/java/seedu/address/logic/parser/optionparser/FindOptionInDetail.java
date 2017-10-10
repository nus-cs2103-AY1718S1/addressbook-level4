package seedu.address.logic.parser.optionparser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.FindCommandParser.PARSE_EXCEPTION_MESSAGE;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DetailsContainsPredicate;

/**
 * Finds contacts by adding details.
 */
public class FindOptionInDetail extends CommandOption<FindCommand> {

    public FindOptionInDetail(String optionArgs) {
        super(optionArgs);
    }

    @Override
    public FindCommand parse() throws ParseException {
        if (!isValidOptionArgs()) {
            throw new ParseException(PARSE_EXCEPTION_MESSAGE);
        }
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(" " + optionArgs, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        FindCommand.FindDetailDescriptor descriptor = new FindCommand.FindDetailDescriptor();
        try {
            argMultimap.getValue(PREFIX_NAME).ifPresent(descriptor::setName);
            argMultimap.getValue(PREFIX_PHONE).ifPresent(descriptor::setPhone);
            argMultimap.getValue(PREFIX_EMAIL).ifPresent(descriptor::setEmail);
            argMultimap.getValue(PREFIX_ADDRESS).ifPresent(descriptor::setAddress);
            Optional.of(ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG))).ifPresent(descriptor::setTags);

            if (!descriptor.isValidDescriptor()) {
                throw new ParseException(PARSE_EXCEPTION_MESSAGE);
            }

            return new FindCommand(new DetailsContainsPredicate(descriptor));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

}

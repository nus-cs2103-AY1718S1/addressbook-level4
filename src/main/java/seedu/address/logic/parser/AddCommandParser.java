package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVATAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.NokName;
import seedu.address.model.person.NokPhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        Email email;
        Address address;
        NokName nokName;
        NokPhone nokPhone;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_AVATAR, PREFIX_NOK_NAME, PREFIX_NOK_PHONE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Avatar avatar = ParserUtil.parseAvatar(argMultimap.getAvatarValue(PREFIX_AVATAR));

            // Gets the email of the new person if possible. If not, it creates a null value
            Optional<Email> optionalEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL));
            if (optionalEmail.isPresent()) {
                email = optionalEmail.get();
            } else {
                email = new Email(null);
            }

            // Gets the address of the new person if possible. If not, it creates a null value
            Optional<Address> optionalAddress = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS));
            if (optionalAddress.isPresent()) {
                address = optionalAddress.get();
            } else {
                address = new Address(null);
            }

            // Gets the Nok Name of the new person if possible. If not, it creates a null value
            Optional<NokName> optionalNokName = ParserUtil.parseNokName(argMultimap.getValue(PREFIX_NOK_NAME));
            if (optionalNokName.isPresent()) {
                nokName = optionalNokName.get();
            } else {
                nokName = new NokName(null);
            }

            // Gets the Nok Name of the new person if possible. If not, it creates a null value
            Optional<NokPhone> optionalNokPhone = ParserUtil.parseNokPhone(argMultimap.getValue(PREFIX_NOK_PHONE));
            if (optionalNokPhone.isPresent()) {
                nokPhone = optionalNokPhone.get();
            } else {
                nokPhone = new NokPhone(null);
            }

            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            ReadOnlyPerson person = new Person(name, phone, email, address, avatar, nokName, nokPhone, tagList);

            return new AddCommand(person);
        } catch (IllegalValueException | IOException e) {
            throw new ParseException(e.getMessage(), e);
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

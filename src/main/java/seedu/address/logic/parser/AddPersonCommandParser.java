package seedu.address.logic.parser;

//@@author jeffreygohkw
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS_PRIVATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVATAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVATAR_PRIVATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_PRIVATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_PRIVATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_PRIVATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK_PRIVATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_PRIVATE;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new ReadOnlyPerson object in the context of AddPersonCommand.
 */
public class AddPersonCommandParser extends AddCommandParser {
    /**
     * Constructs a ReadOnlyPerson from the arguments provided.
     */
    public static ReadOnlyPerson constructPerson(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK,
                        PREFIX_AVATAR, PREFIX_TAG, PREFIX_NAME_PRIVATE, PREFIX_PHONE_PRIVATE, PREFIX_EMAIL_PRIVATE,
                        PREFIX_ADDRESS_PRIVATE, PREFIX_REMARK_PRIVATE, PREFIX_TAG_PRIVATE, PREFIX_AVATAR_PRIVATE);

        if (!(arePrefixesPresent(argMultimap, PREFIX_NAME)
                || (arePrefixesPresent(argMultimap, PREFIX_NAME_PRIVATE)))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE));
        }

        try {
            Name name;
            Phone phone;
            Email email;
            Address address;
            Remark remark;
            Avatar avatar;

            name = makeName(argMultimap);

            phone = makePhone(argMultimap);

            email = makeEmail(argMultimap);

            address = mmakeAddress(argMultimap);

            avatar = makeAvatar(argMultimap);

            remark = makeRemark(argMultimap);

            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            ReadOnlyPerson person = new Person(name, phone, email, address, false, remark, avatar, tagList);
            return person;
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Creates a Name based on the input arguments
     */
    private static Name makeName(ArgumentMultimap argMultimap) throws IllegalValueException {
        Name name;
        if ((arePrefixesPresent(argMultimap, PREFIX_NAME))) {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
        } else {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME_PRIVATE), true).get();
        }
        return name;
    }

    /**
     * Creates a Phone based on the input arguments
     */
    private static Phone makePhone(ArgumentMultimap argMultimap) throws IllegalValueException {
        Phone phone;
        if ((arePrefixesPresent(argMultimap, PREFIX_PHONE))) {
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
        } else if (arePrefixesPresent(argMultimap, PREFIX_PHONE_PRIVATE)) {
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE_PRIVATE), true).get();
        } else {
            phone = new Phone(null);
        }
        return phone;
    }

    /**
     * Creates an Email based on the input arguments
     */
    private static Email makeEmail(ArgumentMultimap argMultimap) throws IllegalValueException {
        Email email;
        if ((arePrefixesPresent(argMultimap, PREFIX_EMAIL))) {
            email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
        } else if (arePrefixesPresent(argMultimap, PREFIX_EMAIL_PRIVATE)) {
            email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL_PRIVATE), true).get();
        } else {
            email = new Email(null);
        }
        return email;
    }

    /**
     * Creates an Address based on the input arguments
     */
    private static Address mmakeAddress(ArgumentMultimap argMultimap) throws IllegalValueException {
        Address address;
        if ((arePrefixesPresent(argMultimap, PREFIX_ADDRESS))) {
            address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
        } else if (arePrefixesPresent(argMultimap, PREFIX_ADDRESS_PRIVATE)) {
            address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS_PRIVATE), true).get();
        } else {
            address = new Address(null);
        }
        return address;
    }

    /**
     * Creates an Avatar based on the input arguments
     */
    private static Avatar makeAvatar(ArgumentMultimap argMultimap) throws IllegalValueException {
        Avatar avatar;
        if ((arePrefixesPresent(argMultimap, PREFIX_AVATAR))) {
            avatar = ParserUtil.parseAvatar(argMultimap.getValue(PREFIX_AVATAR)).get();
        } else {
            avatar = new Avatar(null);
        }
        return avatar;
    }

    /**
     * Creates a Remark based on the input arguments
     */
    private static Remark makeRemark(ArgumentMultimap argMultimap) throws IllegalValueException {
        Remark remark;
        if ((arePrefixesPresent(argMultimap, PREFIX_REMARK))) {
            remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();
        } else if (arePrefixesPresent(argMultimap, PREFIX_REMARK_PRIVATE)) {
            remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK_PRIVATE), true).get();
        } else {
            remark = new Remark(null);
        }
        return remark;
    }
}

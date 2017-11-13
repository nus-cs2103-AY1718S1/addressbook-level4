package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEB_LINK;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.weblink.WebLink;
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
        Phone phone;
        ArrayList<Email> emailList;
        Address address;
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_WEB_LINK);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Optional<Phone> checkPhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE));
            if (!checkPhone.isPresent()) {
                phone = new Phone(null);
            } else {
                phone = checkPhone.get();
            }
            ArrayList<Email> checkEmail = ParserUtil.parseEmail(argMultimap.getAllValues(PREFIX_EMAIL));
            if (checkEmail.size() == 0) {
                emailList = createEmptyEmailList();
            } else {
                emailList = checkEmail;
            }
            Optional<Address> checkAddress = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS));
            if (!checkAddress.isPresent()) {
                address = new Address(null);
            } else {
                address = checkAddress.get();
            }
            Remark remark = new Remark(""); // Add command does not allow adding remarks right away
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            Set<WebLink> webLinkList = ParserUtil.parseWebLink(argMultimap.getAllValues(PREFIX_WEB_LINK));

            ReadOnlyPerson person = new Person(name, phone, emailList, address, remark, tagList, webLinkList);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns an empty {@code ArrayList} when no email is added in contact
     */
    private static ArrayList<Email> createEmptyEmailList() {
        ArrayList<Email> emptyEmailList = new ArrayList<>();
        Email emptyEmail = new Email();
        emptyEmailList.add(emptyEmail);
        return emptyEmailList;
    }

}

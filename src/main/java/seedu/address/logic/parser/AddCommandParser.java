package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEBT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HANDPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOME_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTEREST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFICE_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Date;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Debt;
import seedu.address.model.person.Email;
import seedu.address.model.person.Handphone;
import seedu.address.model.person.HomePhone;
import seedu.address.model.person.Interest;
import seedu.address.model.person.Name;
import seedu.address.model.person.OfficePhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new {@code AddCommand} object
 */
public class AddCommandParser implements Parser<AddCommand> {

    public static final String MESSAGE_INVALID_DEBT = "Unable to add a person with no debt";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_HANDPHONE, PREFIX_HOME_PHONE, PREFIX_OFFICE_PHONE,
                        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_POSTAL_CODE, PREFIX_DEBT, PREFIX_INTEREST, PREFIX_DEADLINE,
                        PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_HANDPHONE, PREFIX_HOME_PHONE,
                PREFIX_POSTAL_CODE, PREFIX_EMAIL, PREFIX_DEBT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Handphone handphone = ParserUtil.parseHandphone(argMultimap.getValue(PREFIX_HANDPHONE)).get();
            HomePhone homePhone = ParserUtil.parseHomePhone(argMultimap.getValue(PREFIX_HOME_PHONE)).get();
            OfficePhone officePhone = ParserUtil.parseOfficePhone(argMultimap.getValue(PREFIX_OFFICE_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            PostalCode postalCode = ParserUtil.parsePostalCode(argMultimap.getValue(PREFIX_POSTAL_CODE)).get();
            Interest interest = ParserUtil.parseInterest(argMultimap.getValue(PREFIX_INTEREST)).get();
            Deadline deadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE)).get();
            if (!deadline.value.equals(Deadline.NO_DEADLINE_SET)) {
                //check if deadline entered is before the date borrowed
                deadline.checkDateBorrow(new Date());
            }
            Debt debt = ParserUtil.parseDebt(argMultimap.getValue(PREFIX_DEBT)).get();
            if (debt.toNumber() == 0) {
                throw new ParseException(MESSAGE_INVALID_DEBT);
            }
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            //{@code DateBorrow}, {@code DateRepaid} and {@code totalDebt} fields are created
            // within {@code Person} class
            ReadOnlyPerson person = new Person(name, handphone, homePhone, officePhone, email, address, postalCode,
                    debt, interest, deadline, tagList);

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

}

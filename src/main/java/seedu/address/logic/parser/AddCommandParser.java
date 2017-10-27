package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.person.Bloodtype.NON_COMPULSORY_BLOODTYPE;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Bloodtype;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_BLOODTYPE, PREFIX_REMARK, PREFIX_DATE, PREFIX_TAG);


        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();

            //@@author Jeremy
            Phone phone = (!arePrefixesPresent(argMultimap, PREFIX_PHONE))
                    ? new Phone("000") : ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = (!arePrefixesPresent(argMultimap, PREFIX_EMAIL))
                    ? new Email("null@null.com") : ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Address address = (!arePrefixesPresent(argMultimap, PREFIX_ADDRESS))
                    ? new Address("???") : ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            Bloodtype bloodType = (!arePrefixesPresent(argMultimap, PREFIX_BLOODTYPE))
                    ? new Bloodtype(NON_COMPULSORY_BLOODTYPE)
                    : ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_BLOODTYPE)).get();
            Remark remark = (!arePrefixesPresent(argMultimap, PREFIX_REMARK))
                    ? new Remark("") : ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();
            //@@author

            Optional<Date> date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE));
            Appointment appointment;
            if (date.isPresent()) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date.get());
                appointment = new Appointment(name.toString(), calendar);
            } else {
                appointment = new Appointment(name.toString());
            }
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            ReadOnlyPerson person = new Person(name, phone, email, address, bloodType, tagList, remark, appointment);
            return new AddCommand(person);
        } catch (IllegalValueException | java.text.ParseException ive) {
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

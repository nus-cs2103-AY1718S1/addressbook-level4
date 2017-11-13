package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC_NO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMETABLE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.MatricNo;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.photo.PhotoPath;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    //@@author CindyTsai1
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENDER, PREFIX_MATRIC_NO,
                        PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG, PREFIX_BIRTHDAY, PREFIX_TIMETABLE);
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Gender gender = (arePrefixesPresent(argMultimap, PREFIX_GENDER))
                    ? ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER)).get() : new Gender("");
            MatricNo matricNo = (arePrefixesPresent(argMultimap, PREFIX_MATRIC_NO))
                    ? ParserUtil.parseMatricNo(argMultimap.getValue(PREFIX_MATRIC_NO)).get() : new MatricNo("");
            Phone phone = (arePrefixesPresent(argMultimap, PREFIX_PHONE))
                    ? ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get() : new Phone("");
            Email email = (arePrefixesPresent(argMultimap, PREFIX_EMAIL))
                    ? ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get() : new Email("");
            Address address = (arePrefixesPresent(argMultimap, PREFIX_ADDRESS))
                    ? ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get() : new Address("");
            Timetable timetable = (arePrefixesPresent(argMultimap, PREFIX_TIMETABLE))
                    ? ParserUtil.parseTimetable(argMultimap.getValue(PREFIX_TIMETABLE)).get() : new Timetable("");
            Remark remark = new Remark("");
            PhotoPath photoPath = new PhotoPath("");
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            Birthday birthday = (arePrefixesPresent(argMultimap, PREFIX_BIRTHDAY))
                    ? ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY)).get() : new Birthday("");

            ReadOnlyPerson person = new Person(name, gender, matricNo, phone, email, address, timetable,
                    remark, photoPath, tagList, birthday);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    //@@author
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddQuickCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddQuickCommand object
 */
public class AddQuickCommandParser implements Parser<AddQuickCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddQuickCommand
     * and returns an AddQuickCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddQuickCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_BIRTHDAY,
                        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK, PREFIX_TAG);

        //@@author aver0214
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddQuickCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Birthday birthday = ParserUtil.parseBirthday(
                    getDetails(argMultimap.getValue(PREFIX_BIRTHDAY), PREFIX_BIRTHDAY)).get();
            Email email = ParserUtil.parseEmail(
                    getDetails(argMultimap.getValue(PREFIX_EMAIL), PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(
                    getDetails(argMultimap.getValue(PREFIX_ADDRESS), PREFIX_ADDRESS)).get();
            Remark remark = ParserUtil.parseRemark(
                    getDetails(argMultimap.getValue(PREFIX_REMARK), PREFIX_REMARK)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            ReadOnlyPerson person = new Person(name, phone, birthday, email, address, remark, tagList);

            return new AddQuickCommand(person);
            //@@author
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

    //@@author aver0214
    /**
     * Returns a non-null {@code Optional <String>} object
     */
    public static Optional <String> getDetails(Optional <String> value, Prefix prefix) throws ParseException {
        if (!value.isPresent()) {
            if (prefix.equals(PREFIX_BIRTHDAY)) {
                return value.ofNullable("00/00/0000");
            } else if (prefix.equals(PREFIX_EMAIL)) {
                return value.ofNullable("No email");
            } else if (prefix.equals(PREFIX_ADDRESS)) {
                return value.ofNullable("No address");
            } else if (prefix.equals(PREFIX_REMARK)) {
                return value.ofNullable("No remark");
            }
        }

        return value;
    }
    //@@author

}

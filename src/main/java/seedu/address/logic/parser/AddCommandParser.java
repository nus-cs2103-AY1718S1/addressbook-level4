package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_ADDEDITCOMMANDREMARK_INVALID;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FORMCLASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTALCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.FormClass;
import seedu.address.model.person.Grades;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {
    //@@author Lenaldnwj
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {

        args = optionalInput(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_FORMCLASS, PREFIX_GRADES, PREFIX_POSTALCODE, PREFIX_REMARK, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_FORMCLASS,
                PREFIX_GRADES)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        if (arePrefixesPresent(argMultimap, PREFIX_REMARK)) {
            throw new ParseException(String.format(MESSAGE_ADDEDITCOMMANDREMARK_INVALID, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            Remark remark = new Remark(""); //add command does not allow adding remarks right away
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            FormClass formClass = ParserUtil.parseFormClass(argMultimap.getValue(PREFIX_FORMCLASS)).get();
            Grades grades = ParserUtil.parseGrades(argMultimap.getValue(PREFIX_GRADES)).get();
            PostalCode postalCode = ParserUtil.parsePostalCode(argMultimap.getValue(PREFIX_POSTALCODE)).get();
            ReadOnlyPerson person = new Person(name, phone, email, address, formClass,
                    grades, postalCode, remark, tagList);
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
     * Returns a appended string stating that specific optional inputs are not recorded if user decides to not enter
     * any of the optional inputs.
     */
    public static String optionalInput(String input) {
        if (!input.contains("a/")) {
            input = input + " a/ (Address not recorded)";
        }
        if (!input.contains("e/")) {
            input = input + " e/ (Email not recorded)";
        }
        if (!input.contains("c/")) {
            input = input + " c/ (Postal code not recorded)";
        }
        return input;
    }
}

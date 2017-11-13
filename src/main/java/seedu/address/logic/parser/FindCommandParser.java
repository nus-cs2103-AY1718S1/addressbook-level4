package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC_NO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMETABLE;

import java.util.ArrayList;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author CindyTsai1
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {

        ArgumentMultimap argsMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG, PREFIX_BIRTHDAY);

        if (args.isEmpty() || arePrefixesPresent(argsMultimap, PREFIX_GENDER, PREFIX_TIMETABLE, PREFIX_MATRIC_NO,
                PREFIX_NEW_TAG, PREFIX_OLD_TAG, PREFIX_PHOTO, PREFIX_REMARK) || !arePrefixesPresent(argsMultimap,
                PREFIX_TAG, PREFIX_BIRTHDAY, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_NAME)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArrayList<String> predicate = new ArrayList<String>();

        String nameList = new String();
        String phoneList = new String();
        String emailList = new String();
        String addressList = new String();
        String tagList = new String();
        String birthdayList = new String();

        if (argsMultimap.getValue(PREFIX_NAME).isPresent()) {
            nameList = argsMultimap.getValue(PREFIX_NAME).get();
            predicate.add(PREFIX_NAME.getPrefix());
            predicate.add(nameList);
        }

        if (argsMultimap.getValue(PREFIX_PHONE).isPresent()) {
            phoneList = argsMultimap.getValue(PREFIX_PHONE).get();
            predicate.add(PREFIX_PHONE.getPrefix());
            predicate.add(phoneList);
        }

        if (argsMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            emailList = argsMultimap.getValue(PREFIX_EMAIL).get();
            predicate.add(PREFIX_EMAIL.getPrefix());
            predicate.add(emailList);
        }

        if (argsMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            addressList = argsMultimap.getValue(PREFIX_ADDRESS).get();
            predicate.add(PREFIX_ADDRESS.getPrefix());
            predicate.add(addressList);
        }

        if (argsMultimap.getValue(PREFIX_TAG).isPresent()) {
            tagList = argsMultimap.getValue(PREFIX_TAG).get();
            predicate.add(PREFIX_TAG.getPrefix());
            predicate.add(tagList);
        }

        if (argsMultimap.getValue(PREFIX_BIRTHDAY).isPresent()) {
            birthdayList = argsMultimap.getValue(PREFIX_BIRTHDAY).get();

            /** validity of birthday keyword input check
             *  keyword input must have a value between 1 to 12
             *  keyword input must be 2 digits
             *  keyword input must be in Integers
             */
            if (!birthdayList.equals("")) {
                if (!birthdayList.matches("[0-9]+")) {
                    throw new ParseException(FindCommand.MESSAGE_BIRTHDAYKEYWORD_NONNUMBER);
                } else if (Integer.parseInt(birthdayList.trim()) > 12 || Integer.parseInt(birthdayList.trim()) < 1) {
                    throw new ParseException(String.format(FindCommand.MESSAGE_BIRTHDAYKEYWORD_NONEXIST,
                            birthdayList.trim()));
                } else if (birthdayList.trim().length() == 1) {
                    throw new ParseException(String.format(FindCommand.MESSAGE_BIRTHDAYKEYWORD_INVALID,
                            birthdayList.trim()));
                }
            }

            predicate.add(PREFIX_BIRTHDAY.getPrefix());
            predicate.add(birthdayList);
        }

        if (nameList.isEmpty() && phoneList.isEmpty() && emailList.isEmpty() && addressList.isEmpty()
                && tagList.isEmpty() && birthdayList.isEmpty()) {
            throw new ParseException(FindCommand.MESSAGE_NOT_FOUND);
        }

        return new FindCommand(predicate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

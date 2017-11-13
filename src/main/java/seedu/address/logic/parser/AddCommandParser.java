package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TWITTER;
import static seedu.address.ui.MainWindow.DEFAULT_DP;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.DisplayPic;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UserName;
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
                        PREFIX_BIRTHDAY, PREFIX_TWITTER, PREFIX_INSTAGRAM, PREFIX_DP, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        fillEmptyPrefixes(argMultimap);

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            Birthday birthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY)).get();
            UserName twitterUser = ParserUtil.parseTwitterName(argMultimap.getValue(PREFIX_TWITTER)).get();
            UserName instagramUser = ParserUtil.parseInstagramName(argMultimap.getValue(PREFIX_INSTAGRAM)).get();
            DisplayPic displayPic = ParserUtil.parseDisplayPic(argMultimap.getValue(PREFIX_DP)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            ReadOnlyPerson person = new Person(name, phone, email, address, birthday, twitterUser,
                    instagramUser, displayPic, tagList);

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

    //@@author danielbrzn
    /**
     * Fills the prefixes that have not been specified by the user with an empty string in the given
     * {@code ArgumentMultimap}.
     */
    private static void fillEmptyPrefixes(ArgumentMultimap argumentMultimap) {
        if (!argumentMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            argumentMultimap.put(PREFIX_EMAIL, "");
        }
        if (!argumentMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            argumentMultimap.put(PREFIX_ADDRESS, "");
        }
        if (!argumentMultimap.getValue(PREFIX_BIRTHDAY).isPresent()) {
            argumentMultimap.put(PREFIX_BIRTHDAY, "");
        }
        if (!argumentMultimap.getValue(PREFIX_TWITTER).isPresent()) {
            argumentMultimap.put(PREFIX_TWITTER, "");
        }
        if (!argumentMultimap.getValue(PREFIX_INSTAGRAM).isPresent()) {
            argumentMultimap.put(PREFIX_INSTAGRAM, "");
        }
        if (!argumentMultimap.getValue(PREFIX_DP).isPresent()) {
            argumentMultimap.put(PREFIX_DP, DEFAULT_DP);
        }
    }
}

//@@author hxy0229
package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.util.Set;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NO_NAME_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    //@@author yanji1221

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_BIRTHDAY,
                PREFIX_ADDRESS, PREFIX_PROFILEPAGE, PREFIX_TAG,PREFIX_GROUP);

        if ((!arePrefixesPresent(argMultimap, PREFIX_NAME))
                &&(!arePrefixesPresent(argMultimap, PREFIX_ADDRESS))
                &&(!arePrefixesPresent(argMultimap, PREFIX_PHONE))
                &&(!arePrefixesPresent(argMultimap, PREFIX_EMAIL))
                &&(!arePrefixesPresent(argMultimap, PREFIX_BIRTHDAY))
                &&(!arePrefixesPresent(argMultimap, PREFIX_PROFILEPAGE))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {

            Name name;
            Phone phone;
            Email email;
            Birthday birthday;
            Address address;
            ProfilePage profile;
            Set<Tag> tagList;
            Set<Group> groupList;

            if(arePrefixesPresent(argMultimap, PREFIX_NAME))
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            else {throw new IllegalValueException(MESSAGE_NO_NAME_FORMAT);}

            if(arePrefixesPresent(argMultimap, PREFIX_PHONE))
                phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            else {phone=new Phone();}

            if(arePrefixesPresent(argMultimap, PREFIX_EMAIL))
                email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            else {email=new Email();}

            if(arePrefixesPresent(argMultimap, PREFIX_BIRTHDAY))
                birthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY)).get();
            else {birthday=new Birthday();}

            if(arePrefixesPresent(argMultimap, PREFIX_ADDRESS))
                address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            else {address=new Address();}

            if(arePrefixesPresent(argMultimap, PREFIX_PROFILEPAGE))
              profile = ParserUtil.parseProfilePage(argMultimap.getValue(PREFIX_PROFILEPAGE)).get();
            else {profile=new ProfilePage();}

            tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            groupList = ParserUtil.parseGroups(argMultimap.getAllValues(PREFIX_GROUP));

            ReadOnlyPerson person = new Person(name, phone, email, birthday, address, profile, tagList,groupList);


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

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVATAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.AddMultipleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appoint;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Comment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

//@@author KhorSL
/**
 * Parses input arguments and creates a new AddMultipleCommand object
 */
public class AddMultipleCommandParser implements Parser<AddMultipleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMultipleCommand
     * then parse data from file name given arguments if it exists
     * and returns an AddMultipleCommand object for execution.
     *
     * @param args arguments
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMultipleCommand parse(String args) throws ParseException {
        String filePath = args.trim();
        if (filePath.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMultipleCommand.MESSAGE_USAGE));
        }

        File fileToRead = new File(filePath);
        if (!FileUtil.isFileExists(fileToRead)) {
            throw new ParseException(String.format(AddMultipleCommand.MESSAGE_INVALID_FILE, filePath));
        }

        String data;
        try {
            data = FileUtil.readFromFile(fileToRead);
        } catch (IOException ie) {
            throw new ParseException(String.format(AddMultipleCommand.MESSAGE_ERROR_FILE, filePath));
        }

        String[] lines = data.split(System.lineSeparator());
        ArrayList<ReadOnlyPerson> personsList = parseParticularsIntoPersonsList(lines);

        return new AddMultipleCommand(personsList);
    }

    /**
     * Parses the particulars from {@code argMultimap} into a person
     *
     * @param argMultimap should not be null.
     * @return a person with the parsed attributes
     * @throws ParseException if any of the person particulars contain illegal value
     */
    private ReadOnlyPerson parseParticularsIntoPerson(ArgumentMultimap argMultimap) throws ParseException {
        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            Avatar avatar = ParserUtil.parseAvatar(argMultimap.getValue(PREFIX_AVATAR)).get();
            Comment comment = new Comment(""); // add command does not allow adding comments straight away
            Appoint appoint = new Appoint("");

            return new Person(name, phone, email, address, comment, appoint, avatar, tagList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Parses persons' particulars from {@code lines} and returns {@code personsList}, an ArrayList of ReadOnlyPerson
     *
     * @param lines should not be null. The string array contains the person particulars
     * @return personsList that is being parsed from {@code lines}.
     * @throws ParseException if the compulsory person particulars are not present
     */
    private ArrayList<ReadOnlyPerson> parseParticularsIntoPersonsList(String[] lines) throws ParseException {
        ArrayList<ReadOnlyPerson> personsList = new ArrayList<>();
        for (String eachLine : lines) {
            String toAdd = " " + eachLine;
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(toAdd, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                            PREFIX_TAG, PREFIX_AVATAR);
            if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)) {
                throw new ParseException(String.format(MESSAGE_INVALID_PERSON_FORMAT,
                        AddMultipleCommand.MESSAGE_PERSON_FORMAT));
            }
            ReadOnlyPerson person = parseParticularsIntoPerson(argMultimap);
            personsList.add(person);
        }
        return personsList;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
//@@author

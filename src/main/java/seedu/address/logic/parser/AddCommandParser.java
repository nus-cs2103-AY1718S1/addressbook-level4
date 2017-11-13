package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.person.Position;
import seedu.address.model.person.Priority;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Status;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
    //@@author sebtsh
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_COMPANY, PREFIX_POSITION, PREFIX_STATUS,
                        PREFIX_PRIORITY, PREFIX_NOTE, PREFIX_PHOTO,
                        PREFIX_TAG, PREFIX_ADD_RELATIONSHIP, PREFIX_CLEAR_RELATIONSHIP, PREFIX_DELETE_RELATIONSHIP);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            //Initialize company, position, status, and note to NIL, priority to L
            Company company = new Company("NIL");
            Position position = new Position("NIL");
            Status status = new Status("NIL");
            Priority priority = new Priority("L");
            Note note = new Note("NIL");
            Set<Relationship> relationList = new HashSet<>();
            //Initialize photo to the default icon
            String s = File.separator;
            Photo photo = new Photo("src" + s + "main" + s + "resources" + s
                    + "images" + s + "default.jpg");

            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            //Since Company, Position, Status, Priority and Photo and Relationship are optional
            // parameters, set them if they are present
            if (arePrefixesPresent(argMultimap, PREFIX_COMPANY)) {
                company = ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY)).get();
            }

            if (arePrefixesPresent(argMultimap, PREFIX_POSITION)) {
                position = ParserUtil.parsePosition(argMultimap.getValue(PREFIX_POSITION)).get();
            }

            if (arePrefixesPresent(argMultimap, PREFIX_STATUS)) {
                status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS)).get();
            }

            if (arePrefixesPresent(argMultimap, PREFIX_PRIORITY)) {
                priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY)).get();
            }

            if (arePrefixesPresent(argMultimap, PREFIX_NOTE)) {
                note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE)).get();
            }

            if (arePrefixesPresent(argMultimap, PREFIX_PHOTO)) {
                photo = ParserUtil.parsePhoto(argMultimap.getValue
                        (PREFIX_PHOTO)).get();
            }
            if (arePrefixesPresent(argMultimap, PREFIX_ADD_RELATIONSHIP) || arePrefixesPresent(argMultimap,
                PREFIX_DELETE_RELATIONSHIP) || arePrefixesPresent(argMultimap, PREFIX_CLEAR_RELATIONSHIP)) {
                throw new ParseException(Relationship.MESSAGE_REL_PREFIX_NOT_ALLOWED);
            }

            ReadOnlyPerson person = new Person(name, phone, email, address, company, position, status, priority,
                    note, photo, tagList, relationList);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}

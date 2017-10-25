package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.room.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.room.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.room.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.room.logic.parser.CliSyntax.PREFIX_ROOM;
import static seedu.room.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.room.logic.parser.CliSyntax.PREFIX_TEMPORARY;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.commands.AddCommand;
import seedu.room.logic.parser.exceptions.ParseException;
import seedu.room.model.person.Email;
import seedu.room.model.person.Name;
import seedu.room.model.person.Person;
import seedu.room.model.person.Phone;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.Room;
import seedu.room.model.person.Timestamp;
import seedu.room.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {

        Phone phone;
        Email email;
        Room room;
        Timestamp timestamp;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ROOM,
                        PREFIX_TEMPORARY, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            //Gets the name of the person being added
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();

            //Gets the phone of the person being added. If no phone is provided, it creates a phone with null as value
            Optional<Phone> optionalPhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE));
            if (optionalPhone.isPresent()) {
                phone = optionalPhone.get();
            } else {
                phone = new Phone(null);
            }

            //Gets the email of the person being added. If no email is provided, it creates an email with null as value
            Optional<Email> optionalEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL));
            if (optionalEmail.isPresent()) {
                email = optionalEmail.get();
            } else {
                email = new Email(null);
            }

            //Gets the room of the person being added. If no room is provided, it creates an room with null
            //as value
            Optional<Room> optionalRoom = ParserUtil.parseRoom(argMultimap.getValue(PREFIX_ROOM));
            if (optionalRoom.isPresent()) {
                room = optionalRoom.get();
            } else {
                room = new Room(null);
            }

            //Gets the temporary duration of the person being added. If no temporary duration is provided,
            // it creates a person with permanent duration
            Optional<Timestamp> optionalTimestamp = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_TEMPORARY));
            if (optionalTimestamp.isPresent()) {
                timestamp = optionalTimestamp.get();
            } else {
                timestamp = new Timestamp(0);
            }


            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            ReadOnlyPerson person = new Person(name, phone, email, room, timestamp, tagList);

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

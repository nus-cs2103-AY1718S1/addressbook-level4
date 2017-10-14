package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lecturer.Lecturer;
import seedu.address.model.module.*;

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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLASS_TYPE, PREFIX_VENUE, PREFIX_GROUP, PREFIX_TIME_SLOT,
                        PREFIX_MODULE_CODE, PREFIX_LECTURER);

        if (!arePrefixesPresent(argMultimap, PREFIX_CLASS_TYPE, PREFIX_VENUE, PREFIX_GROUP, PREFIX_TIME_SLOT,
                PREFIX_MODULE_CODE, PREFIX_LECTURER)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            ClassType classType = ParserUtil.parseClassType(argMultimap.getValue(PREFIX_CLASS_TYPE)).get();
            Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_VENUE)).get();
            Group group = ParserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP)).get();
            TimeSlot timeSlot = ParserUtil.parseTimeSlot(argMultimap.getValue(PREFIX_TIME_SLOT)).get();
            Code code = ParserUtil.parseCode(argMultimap.getValue(PREFIX_MODULE_CODE)).get();
            Set<Lecturer> lecturerList = ParserUtil.parseLecturer(argMultimap.getAllValues(PREFIX_LECTURER));

            ReadOnlyLesson lesson = new Lesson(classType, location, group, timeSlot, code, lecturerList);

            return new AddCommand(lesson);
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

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

import com.joestelmach.natty.DateGroup;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author Eric
/**
 * Parse input arguments and creates a new AddAppointmentCommand Object
 */
public class AddAppointmentParser implements Parser<AddAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointmentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddAppointmentCommand parse(String userInput) throws ParseException {

        if (userInput.split(" ").length == 1) {
            return new AddAppointmentCommand();
        }

        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_DATE);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        String[] args = userInput.split(" ");
        try {
            Index index = Index.fromOneBased(Integer.parseInt(args[1]));
            if ("d/off".equals(args[2])) {
                return new AddAppointmentCommand(index);
            }
            List<DateGroup> groups = getDatesFromString(argumentMultimap.getValue(PREFIX_DATE).get());
            Calendar calendar = Calendar.getInstance();
            if (groups.size() == 0) {
                throw new ParseException("Please be more specific with your appointment time");
            }

            //If there is a start and end time that is parsed
            if (groups.get(0).getDates().size() == 2) {
                calendar.setTime(groups.get(0).getDates().get(0));
                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.setTime(groups.get(0).getDates().get(1));
                return new AddAppointmentCommand(index, calendar, calendarEnd);
            }
            calendar.setTime(groups.get(0).getDates().get(0));
            return new AddAppointmentCommand(index, calendar);
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage(), e);
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
     * Natty parser that takes in a string and returns a list of Groupdates
     */
    public static List<DateGroup> getDatesFromString(String str) {
        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
        return parser.parse(str);
    }
}

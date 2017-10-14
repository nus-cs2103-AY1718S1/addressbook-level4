package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Name;

import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

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
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_DATE);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_NAME, PREFIX_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argumentMultimap.getValue(PREFIX_NAME)).get();
            Date date = Appointment.dateFormatter.parse(argumentMultimap.getValue(PREFIX_DATE).get());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Appointment appointment = new Appointment(name.toString(), calendar);
            return new AddAppointmentCommand(appointment);
        } catch (IllegalValueException e) {
            throw new ParseException(e.getMessage(), e);
        } catch (java.text.ParseException e) {
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
}

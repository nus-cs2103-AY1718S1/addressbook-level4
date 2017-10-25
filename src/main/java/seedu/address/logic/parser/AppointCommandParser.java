package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AppointCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Appointment;

/**
 * Parses input arguments and creates a new AppointCommand object
 */
public class AppointCommandParser implements Parser<AppointCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AppointCommand
     * and returns an AppointCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AppointCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_APPOINT);

        Index index;
        String appointmentDetails = argumentMultimap.getValue(PREFIX_APPOINT).orElse("");

        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointCommand.MESSAGE_USAGE));
        }
        return new AppointCommand(index, new Appointment(appointmentDetails));
    }
}

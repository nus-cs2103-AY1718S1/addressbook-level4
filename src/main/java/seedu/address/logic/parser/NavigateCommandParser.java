package seedu.address.logic.parser;

//@@author jeffreygohkw
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_FROM_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_FROM_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_FROM_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_TO_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_TO_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_TO_TASK;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.NavigateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Location;

/**
 * Parses input arguments and creates a new NavigateCommand object
 */
public class NavigateCommandParser implements Parser<NavigateCommand> {

    private Location from = null;
    private Location to = null;
    private Index fromIndex = null;
    private Index toIndex = null;
    /**
     * Parses the given {@code String} of arguments in the context of the NavigateCommand
     * and returns an NavigateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NavigateCommand parse(String args) throws ParseException {
        resetValues();
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAVIGATE_FROM_PERSON,
                PREFIX_NAVIGATE_FROM_TASK, PREFIX_NAVIGATE_FROM_ADDRESS, PREFIX_NAVIGATE_TO_PERSON,
                PREFIX_NAVIGATE_TO_TASK, PREFIX_NAVIGATE_TO_ADDRESS);

        boolean fromAddress = arePrefixesPresent(argumentMultimap, PREFIX_NAVIGATE_FROM_ADDRESS);
        boolean fromPerson = arePrefixesPresent(argumentMultimap, PREFIX_NAVIGATE_FROM_PERSON);
        boolean fromTask = arePrefixesPresent(argumentMultimap, PREFIX_NAVIGATE_FROM_TASK);

        boolean toAddress = arePrefixesPresent(argumentMultimap, PREFIX_NAVIGATE_TO_ADDRESS);
        boolean toPerson = arePrefixesPresent(argumentMultimap, PREFIX_NAVIGATE_TO_PERSON);
        boolean toTask = arePrefixesPresent(argumentMultimap, PREFIX_NAVIGATE_TO_TASK);

        checkFrom(argumentMultimap, fromAddress, fromPerson, fromTask);

        checkTo(argumentMultimap, toAddress, toPerson, toTask);

        try {
            return new NavigateCommand(from, to, fromIndex, toIndex, fromTask, toTask);
        } catch (IllegalValueException e) {
            throw new ParseException(e.getMessage(), e);
        }
    }

    /**
     * Resets the values stored in NavigateCommandParser object to null
     */
    private void resetValues() {
        from = null;
        to = null;
        fromIndex = null;
        toIndex = null;
    }

    /**
     * Checksif only 1 To argument is provided
     * @throws ParseException if there are no To arguments or there are more than 1 To arguements
     */
    private void checkTo(ArgumentMultimap argumentMultimap, boolean toAddress, boolean toPerson, boolean toTask)
            throws ParseException {
        if (!(toAddress || toPerson || toTask)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NavigateCommand.MESSAGE_USAGE));
        } else if ((toAddress && (toPerson || toTask)) || (toPerson && toTask)) {
            // If 2 or more to prefixes are present
            throw new ParseException(NavigateCommand.MESSAGE_MULTIPLE_TO_ERROR);
        } else {
            try {
                setArgsForNavigateCommand(argumentMultimap, toAddress, toPerson, false);
            } catch (IllegalValueException e) {
                throw new ParseException(e.getMessage(), e);
            }
        }
    }
    /**
     * Checks if only 1 From argument is provided
     * @throws ParseException if there are no From arguments or there are more than 1 From arguments
     */
    private void checkFrom(ArgumentMultimap argumentMultimap, boolean fromAddress, boolean fromPerson, boolean fromTask)
            throws ParseException {
        if (!(fromAddress || fromPerson || fromTask)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NavigateCommand.MESSAGE_USAGE));
        } else if ((fromAddress && (fromPerson || fromTask)) || (fromPerson && fromTask)) {
            // If 2 or more from prefixes are present
            throw new ParseException(NavigateCommand.MESSAGE_MULTIPLE_FROM_ERROR);
        } else {
            try {
                setArgsForNavigateCommand(argumentMultimap, fromAddress, fromPerson, true);
            } catch (IllegalValueException e) {
                throw new ParseException(e.getMessage(), e);
            }
        }
    }

    private void setArgsForNavigateCommand(ArgumentMultimap argumentMultimap, boolean address, boolean person,
                                           boolean forFrom) throws IllegalValueException {
        if (address) {
            if (forFrom) {
                from = new Location(ParserUtil.parseLocationFromAddress(
                        argumentMultimap.getValue(PREFIX_NAVIGATE_FROM_ADDRESS)).get().toString());
            } else {
                to = new Location(ParserUtil.parseLocationFromAddress(
                        argumentMultimap.getValue(PREFIX_NAVIGATE_TO_ADDRESS)).get().toString());
            }
        } else if (person) {
            if (forFrom) {
                fromIndex = ParserUtil.parseIndex(argumentMultimap
                        .getValue(PREFIX_NAVIGATE_FROM_PERSON).get());
            } else {
                toIndex = ParserUtil.parseIndex(argumentMultimap
                        .getValue(PREFIX_NAVIGATE_TO_PERSON).get());
            }
        } else {
            if (forFrom) {
                fromIndex = ParserUtil.parseIndex(argumentMultimap
                        .getValue(PREFIX_NAVIGATE_FROM_TASK).get());
            } else {
                toIndex = ParserUtil.parseIndex(argumentMultimap
                        .getValue(PREFIX_NAVIGATE_TO_TASK).get());
            }
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

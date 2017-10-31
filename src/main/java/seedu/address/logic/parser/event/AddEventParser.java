package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.property.Address;
import seedu.address.model.property.DateTime;
import seedu.address.model.property.Name;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyManager;
import seedu.address.model.property.exceptions.DuplicatePropertyException;
import seedu.address.model.property.exceptions.PropertyNotFoundException;
import seedu.address.model.reminder.Reminder;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddEventParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        Set<Prefix> prefixes = PropertyManager.getAllPrefixes();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixes);

        // TODO: Keep this checking for now. These pre-loaded properties are compulsory.
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_DATE_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        try {
            Set<Property> propertyList = ParserUtil.parseProperties(argMultimap.getAllValues());
            ArrayList<Reminder> reminderList = new ArrayList<>();
            return new AddEventCommand(new Event(propertyList, reminderList));
        } catch (IllegalValueException | PropertyNotFoundException | DuplicatePropertyException e) {
            throw new ParseException(e.getMessage(), e);
        }
    }
}


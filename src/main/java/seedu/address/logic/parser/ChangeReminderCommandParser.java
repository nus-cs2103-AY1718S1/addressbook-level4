package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_PRIORITY;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeReminderCommand;
import seedu.address.logic.commands.ChangeReminderCommand.ChangeReminderDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
//@@author RonakLakhotia

/**
 * Parses input arguments and creates a new ChangeReminderCommand object.
 */
public class ChangeReminderCommandParser implements Parser<ChangeReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangeReminderCommand
     * and returns an ChangeReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER_DETAILS, PREFIX_REMINDER_DUE_DATE,
                        PREFIX_REMINDER_PRIORITY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeReminderCommand.MESSAGE_USAGE));
        }

        ChangeReminderDescriptor changeReminderDescriptor = new ChangeReminderDescriptor();
        try {
            ParserUtil.parseDetails(argMultimap.getValue(PREFIX_REMINDER_DETAILS))
                    .ifPresent(changeReminderDescriptor::setDetails);
            ParserUtil.parsePriority(argMultimap.getValue(PREFIX_REMINDER_PRIORITY))
                    .ifPresent(changeReminderDescriptor::setPriority);
            ParserUtil.parseDueDate(argMultimap.getValue(PREFIX_REMINDER_DUE_DATE))
                    .ifPresent(changeReminderDescriptor::setDueDate);

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!changeReminderDescriptor.isAnyFieldChanged()) {
            throw new ParseException(ChangeReminderCommand.MESSAGE_NOT_CHANGED);
        }

        return new ChangeReminderCommand(index, changeReminderDescriptor);
    }


}

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.UpdateUserCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new UpdateUserCommand object
 */
public class UpdateUserCommandParser implements Parser<UpdateUserCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateUserCommand
     * and returns an UpdateUserCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateUserCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        String preamble = argMultimap.getPreamble();
        if (!preamble.isEmpty()){
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateUserCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).ifPresent(editPersonDescriptor::setName);
            ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).ifPresent(editPersonDescriptor::setPhone);
            ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).ifPresent(editPersonDescriptor::setEmail);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).ifPresent(editPersonDescriptor::setAddress);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
            throw new ParseException(UpdateUserCommand.MESSAGE_TAGS_NOT_ALLOWED);
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateUserCommand.MESSAGE_NOT_UPDATED);
        }

        return new UpdateUserCommand(editPersonDescriptor);
    }

}

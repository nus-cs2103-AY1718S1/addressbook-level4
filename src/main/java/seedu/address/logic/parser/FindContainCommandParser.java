package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindContainCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsLettersPredicate;

import java.util.HashMap;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class FindContainCommandParser implements Parser<FindContainCommand> {

    public FindContainCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindContainCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                                                                                PREFIX_ADDRESS, PREFIX_TAG);

        HashMap<String, String> mapKeywords = new HashMap<>();

        try {
            if (argumentMultimap.getValue(PREFIX_NAME).isPresent()) {
                String trimmedArgsName = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_NAME)).get().trim();
                if (trimmedArgsName.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindContainCommand.MESSAGE_USAGE));
                }
                mapKeywords.put(PREFIX_NAME.toString(), trimmedArgsName);
            }

            if (argumentMultimap.getValue(PREFIX_PHONE).isPresent()) {
                String trimmedArgsPhone = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_PHONE)).get().trim();
                if (trimmedArgsPhone.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindContainCommand.MESSAGE_USAGE));
                }
                mapKeywords.put(PREFIX_PHONE.toString(), trimmedArgsPhone);
            }

            if (argumentMultimap.getValue(PREFIX_EMAIL).isPresent()) {
                String trimmedArgsEmail = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_EMAIL)).get().trim();
                if (trimmedArgsEmail.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindContainCommand.MESSAGE_USAGE));
                }
                mapKeywords.put(PREFIX_EMAIL.toString(), trimmedArgsEmail);
            }

            if (argumentMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
                String trimmedArgsAddress = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_ADDRESS)).get().trim();
                if (trimmedArgsAddress.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindContainCommand.MESSAGE_USAGE));
                }
                mapKeywords.put(PREFIX_ADDRESS.toString(), trimmedArgsAddress);
            }

            if (argumentMultimap.getValue(PREFIX_TAG).isPresent()) {
                String trimmedArgsTag = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_TAG)).get().trim();
                if (trimmedArgsTag.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindContainCommand.MESSAGE_USAGE));
                }
                mapKeywords.put(PREFIX_TAG.toString(), trimmedArgsTag);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (mapKeywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindContainCommand.MESSAGE_USAGE));
        }

        return new FindContainCommand(new PersonContainsLettersPredicate(mapKeywords));
    }
}

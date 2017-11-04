package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

//@@author KhorSL
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL,
                PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_COMMENT, PREFIX_APPOINT);

        String trimmedArgsName;
        String trimmedArgsTag;
        String trimmedArgsEmail;
        String trimmedArgsPhone;
        String trimmedArgsAddress;
        String trimmedArgsComment;
        String trimmedArgsAppoint;

        String[] keywordNameList;
        String[] keywordTagList;
        String[] keywordEmailList;
        String[] keywordPhoneList;
        String[] keywordAddressList;
        String[] keywordCommentList;
        String[] keywordAppointList;

        HashMap<String, List<String>> mapKeywords = new HashMap<>();

        try {
            if (argumentMultimap.getValue(PREFIX_NAME).isPresent()) {
                trimmedArgsName = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_NAME)).get().trim();
                if (trimmedArgsName.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
                keywordNameList = trimmedArgsName.split("\\s+");
                mapKeywords.put(PREFIX_NAME.toString(), Arrays.asList(keywordNameList));
            }

            if (argumentMultimap.getValue(PREFIX_TAG).isPresent()) {
                trimmedArgsTag = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_TAG)).get().trim();
                if (trimmedArgsTag.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
                keywordTagList = trimmedArgsTag.split("\\s+");
                mapKeywords.put(PREFIX_TAG.toString(), Arrays.asList(keywordTagList));
            }

            if (argumentMultimap.getValue(PREFIX_EMAIL).isPresent()) {
                trimmedArgsEmail = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_EMAIL)).get().trim();
                if (trimmedArgsEmail.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
                keywordEmailList = trimmedArgsEmail.split("\\s+");
                mapKeywords.put(PREFIX_EMAIL.toString(), Arrays.asList(keywordEmailList));
            }

            if (argumentMultimap.getValue(PREFIX_PHONE).isPresent()) {
                trimmedArgsPhone = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_PHONE)).get().trim();
                if (trimmedArgsPhone.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
                keywordPhoneList = trimmedArgsPhone.split("\\s+");
                mapKeywords.put(PREFIX_PHONE.toString(), Arrays.asList(keywordPhoneList));
            }

            if (argumentMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
                trimmedArgsAddress = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_ADDRESS)).get().trim();
                if (trimmedArgsAddress.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
                keywordAddressList = trimmedArgsAddress.split("\\s+");
                mapKeywords.put(PREFIX_ADDRESS.toString(), Arrays.asList(keywordAddressList));
            }

            if (argumentMultimap.getValue(PREFIX_COMMENT).isPresent()) {
                trimmedArgsComment = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_COMMENT)).get().trim();
                if (trimmedArgsComment.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
                keywordCommentList = trimmedArgsComment.split("\\s+");
                mapKeywords.put(PREFIX_COMMENT.toString(), Arrays.asList(keywordCommentList));
            }

            if (argumentMultimap.getValue(PREFIX_APPOINT).isPresent()) {
                trimmedArgsAppoint = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_APPOINT)).get().trim();
                if (trimmedArgsAppoint.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
                keywordAppointList = trimmedArgsAppoint.split("\\s+");
                mapKeywords.put(PREFIX_APPOINT.toString(), Arrays.asList(keywordAppointList));
            }

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (mapKeywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new PersonContainsKeywordsPredicate(mapKeywords));
    }

}
//@@author

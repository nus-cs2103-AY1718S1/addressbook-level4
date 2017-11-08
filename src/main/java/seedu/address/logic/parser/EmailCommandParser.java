package seedu.address.logic.parser;
//@@author RonakLakhotia
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.EmailSubject;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EmailCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {


    public static final String MULTIPLE_TAGS_FALIURE = "Multiple tags cannot be entered";

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String args) throws ParseException {


        EmailSubject subject;
        Set<Tag> tagList;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_SUBJECT, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
        try {
            subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT)).get();
            tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        if (tagList.size() > 1) {
            throw new ParseException(String.format(MULTIPLE_TAGS_FALIURE, EmailCommand.MESSAGE_USAGE));
        }

        Tag[] dummyArrayToGetTagName = tagList.toArray(new Tag[1]);
        String tagName = dummyArrayToGetTagName[0].tagName.toString();
        return new EmailCommand(tagName, subject.toString());
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}

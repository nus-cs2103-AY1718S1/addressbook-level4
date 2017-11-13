package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.TagFindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.TagMatchingKeywordPredicate;

//@@author ZhangH795

/**
 * Parses input arguments and creates a new TagFindCommand object
 */
public class TagFindCommandParser implements Parser<TagFindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the TagFindCommand
     * and returns an TagFindCommand object for execution.
     * @throws ParseException if the user does not provide any input
     */
    public TagFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        boolean looseFind = true;
        //Throw an error if there is no argument followed by the command word
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagFindCommand.MESSAGE_USAGE));
        }
        TagMatchingKeywordPredicate predicate = new TagMatchingKeywordPredicate(trimmedArgs, looseFind);
        return new TagFindCommand(predicate);
    }
}

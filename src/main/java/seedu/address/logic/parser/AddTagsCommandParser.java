package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddTagsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddTagsCommand object
 */
public class AddTagsCommandParser implements Parser<AddTagsCommand> {

    private static final int TAG_ARGUMENT_INDEX = 3;
    /**
     * Parses the given {@code String} of arguments in the context of the AddTagsCommand
     * and returns an AddTagsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddTagsCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (args.isEmpty() || args.length() < TAG_ARGUMENT_INDEX + 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagsCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(args.substring(0, TAG_ARGUMENT_INDEX));
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagsCommand.MESSAGE_USAGE));
        }

        List<String> tagsList = Arrays.asList(args.substring(TAG_ARGUMENT_INDEX).split(" "));
        Set<Tag> tagsSet;
        try {
            tagsSet = ParserUtil.parseTags(tagsList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        return new AddTagsCommand(index, tagsSet);
    }
}

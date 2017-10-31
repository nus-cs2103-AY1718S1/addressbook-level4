package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddRemoveTagsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

//@@author kenpaxtonlim
/**
 * Parses input arguments and creates a new AddRemoveTagsCommand object
 */
public class AddRemoveTagsCommandParser implements Parser<AddRemoveTagsCommand> {

    private static final int ARGUMENT_START_INDEX = 1;
    private static final int TYPE_ARGUMENT_INDEX = 0;
    private static final int INDEX_ARGUMENT_INDEX = 1;
    private static final int TAG_ARGUMENT_INDEX = 2;

    private static final String TYPE_ADD = "add";
    private static final String TYPE_REMOVE = "remove";
    /**
     * Parses the given {@code String} of arguments in the context of the AddRemoveTagsCommand
     * and returns an AddRemoveTagsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddRemoveTagsCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (args.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemoveTagsCommand.MESSAGE_USAGE));
        }

        List<String> argsList = Arrays.asList(args.substring(ARGUMENT_START_INDEX).split(" "));

        if (argsList.size() < TAG_ARGUMENT_INDEX + 1 || argsList.contains("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemoveTagsCommand.MESSAGE_USAGE));
        }

        boolean isAdd;
        if (argsList.get(TYPE_ARGUMENT_INDEX).equals(TYPE_ADD)) {
            isAdd = true;
        } else if (argsList.get(TYPE_ARGUMENT_INDEX).equals(TYPE_REMOVE)) {
            isAdd = false;
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemoveTagsCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argsList.get(INDEX_ARGUMENT_INDEX));
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemoveTagsCommand.MESSAGE_USAGE));
        }

        List<String> tagsList = argsList.subList(TAG_ARGUMENT_INDEX, argsList.size());
        Set<Tag> tagsSet;
        try {
            tagsSet = ParserUtil.parseTags(tagsList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        return new AddRemoveTagsCommand(isAdd, index, tagsSet);
    }
}

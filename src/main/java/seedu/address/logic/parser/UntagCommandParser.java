package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.UntagCommand.MESSAGE_EMPTY_INDEX_LIST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UntagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new UntagCommand object
 */
public class UntagCommandParser implements Parser<UntagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UntagCommand
     * and returns a UntagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UntagCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        if (trimmedArgs.equals("-a")) {
            return new UntagCommand(true, Collections.emptyList(), Collections.emptyList());
        }
        String[] splittedArgs = trimmedArgs.split("\\s+");
        if (trimmedArgs.isEmpty() || splittedArgs.length != 1 && splittedArgs.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }

        List<Tag> tagList = new ArrayList<>();
        if (splittedArgs.length == 2) {
            Set<String> uniqueTags = new HashSet<>(Arrays.asList(splittedArgs[1].split("/")));
            try {
                for (String tagArg : uniqueTags) {
                    tagList.add(new Tag(tagArg));
                }
            } catch (IllegalValueException ive) {
                throw new ParseException(ive.getMessage(), ive);
            }
        }

        List<Index> indexList = new ArrayList<>();
        if (splittedArgs[0].equals("-a")) {
            return new UntagCommand(true, indexList, tagList);
        }
        Set<String> uniqueIndexes = new HashSet<>(Arrays.asList(splittedArgs[0].split(",")));
        if (uniqueIndexes.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_EMPTY_INDEX_LIST, UntagCommand.MESSAGE_USAGE));
        }
        try {
            for (String indexArg : uniqueIndexes) {
                indexList.add(ParserUtil.parseIndex(indexArg));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }

        return new UntagCommand(false, indexList, tagList);
    }

}

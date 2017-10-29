//@@author duyson98

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.UntagCommand.MESSAGE_EMPTY_INDEX_LIST;

import java.util.ArrayList;
import java.util.Arrays;
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
public class UntagCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the UntagCommand
     * and returns a UntagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UntagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.equals("-all")) {
            return new UntagCommand();
        }

        String[] splittedArgs = trimmedArgs.split("\\s+");
        if (trimmedArgs.isEmpty() || splittedArgs.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }

        if (splittedArgs[0].equals("-all")) {
            try {
                return new UntagCommand(new Tag(splittedArgs[1]));
            } catch (IllegalValueException ive) {
                throw new ParseException(ive.getMessage(), ive);
            }
        }

        Set<String> uniqueIndexes = new HashSet<>(Arrays.asList(splittedArgs[0].split(",")));
        if (uniqueIndexes.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_EMPTY_INDEX_LIST, UntagCommand.MESSAGE_USAGE));
        }
        List<Index> indexList = new ArrayList<>();
        try {
            for (String indexArg : uniqueIndexes) {
                indexList.add(ParserUtil.parseIndex(indexArg));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }

        try {
            Tag tag = new Tag(splittedArgs[1]);
            return new UntagCommand(indexList, tag);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

}

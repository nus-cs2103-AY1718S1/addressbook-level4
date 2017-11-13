package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BatchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

//@@author rushan-khor
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class BatchCommandParser implements Parser<BatchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BatchCommand parse(String args) throws ParseException {
        final Set<String> tagNames = new HashSet<>();
        Scanner tagNameScanner = new Scanner(args);

        while (tagNameScanner.hasNext()) {
            String nextTagName = tagNameScanner.next();
            tagNames.add(nextTagName);
        }

        try {
            Set<Tag> tags = ParserUtil.parseTags(tagNames);
            return new BatchCommand(tags);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BatchCommand.MESSAGE_USAGE));
        }
    }
}

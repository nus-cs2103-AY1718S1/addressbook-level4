package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddTagCommand object
 */
public class AddTagCommandParser implements Parser<AddTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTagCommand
     * and returns a AddTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        Set<Tag> toAddSet = new HashSet<>();
        Set<Index> index = new HashSet<>();

        if (args.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        StringTokenizer st = new StringTokenizer(args.trim(), " ");

        try {
            Tag toAdd = new Tag(st.nextToken());
            toAddSet.add(toAdd);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        Boolean indexAdded = false;
        while (st.hasMoreTokens()) {
            String newToken = st.nextToken();
            Boolean isIndex = true;

            // check is newToken holds an non-integer value
            char[] charArray = newToken.toCharArray();
            for (char c : charArray) {
                if (!Character.isDigit(c)) {
                    isIndex = false;
                }
            }


            if (isIndex) {
                try {
                    Index indexToAdd = ParserUtil.parseIndex(newToken);
                    index.add(indexToAdd);
                    indexAdded = true;
                } catch (IllegalValueException ive) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
                }
            } else {
                if (indexAdded) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
                } else {
                    try {
                        Tag toAdd = new Tag(newToken);
                        toAddSet.add(toAdd);
                    } catch (IllegalValueException ive) {
                        throw new ParseException(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
                    }
                }
            }
        }

        if (index.isEmpty()) {
            throw new ParseException("Please provide at least one index.\n"
            + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }
        return new AddTagCommand(toAddSet, index);
    }

}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new RemoveTagCommand object
 */
public class RemoveCommandParser implements Parser<RemoveTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagCommand
     * and returns a RemoveTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        Set<Tag> toRemoveSet = new HashSet<>();
        Set<Index> index = new HashSet<>();

        if (args.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }

        StringTokenizer st = new StringTokenizer(args, " ");

        try {
            Tag toRemove = new Tag(st.nextToken());
            toRemoveSet.add(toRemove);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }

        Boolean indexAdded = false;
        String indexInput;
        List<String> indexSet = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String newToken = st.nextToken();
            Boolean isIndex = true;

            // check is newToken holds an non-integer value
            char[] charArray = newToken.toCharArray();
            for (char c : charArray) {
                if (!Character.isDigit(c)) {
                    isIndex = false;
                    break;
                }
            }


            if (isIndex) {
                indexSet.add(newToken);
                try {
                    Index indexToAdd = ParserUtil.parseIndex(newToken);
                    index.add(indexToAdd);
                    indexAdded = true;
                } catch (IllegalValueException ive) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                }
            } else {
                if (indexAdded) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                } else {
                    try {
                        Tag toRemove = new Tag(newToken);
                        toRemoveSet.add(toRemove);
                    } catch (IllegalValueException ive) {
                        throw new ParseException(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                    }
                }
            }
        }
        indexInput = indexSet.stream().collect(Collectors.joining(", "));
        return new RemoveTagCommand(toRemoveSet, index, indexInput);
    }

}

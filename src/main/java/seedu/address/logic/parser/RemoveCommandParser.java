package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

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
        List<String> indexSet = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String newToken = st.nextToken();
            Boolean isIndex = true;


            // check is newToken holds an non-integer value
            char[] charArray = newToken.toCharArray();
            String lowerLimit = "";
            String upperLimit = "";
            boolean isRange = false;
            if (newToken.contains("-")) {
                isRange = true;
            }
            boolean reach2nd = false;
            for (char c : charArray) {
                if (!Character.isDigit(c)) {
                    if (c == '-') {
                        reach2nd = true;
                        continue;
                    }
                    isIndex = false;
                    break;
                } else {
                    if (isRange) {
                        if (!reach2nd) {
                            lowerLimit += c;
                        } else {
                            upperLimit += c;
                        }
                    }
                }
            }

            if (isRange) {
                boolean isLowerValid = lowerLimit.isEmpty();
                boolean isUpperValid = upperLimit.isEmpty();
                if (isLowerValid || isUpperValid) {
                    throw new ParseException("Invalid index range provided.\n"
                            + String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                }
                int lower = Integer.parseInt(lowerLimit);
                int upper = Integer.parseInt(upperLimit);
                if (lower > upper) {
                    throw new ParseException("Invalid index range provided.\n"
                            + String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                }

                for (int i = lower; i <= upper; i++) {
                    String toAdd = String.valueOf(i);
                    indexSet.add(toAdd);
                    try {
                        Index indexFromRangeToAdd = ParserUtil.parseIndex(toAdd);
                        index.add(indexFromRangeToAdd);
                        indexAdded = true;
                    } catch (IllegalValueException ive) {
                        throw new ParseException(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                    }
                }
            } else {
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
        }
        return new RemoveTagCommand(toRemoveSet, index, indexSet);
    }

}

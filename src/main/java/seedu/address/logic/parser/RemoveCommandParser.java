package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

//@@author NabeelZaheer
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
        final int indexLowerLimit = 0;
        final int indexUpperLimit = 1;
        final int indexIsTag = 2;
        final int indexIsRange = 3;
        Set<Tag> toRemoveSet = new HashSet<>();
        Set<Index> index = new HashSet<>();
        List<String> indexSet = new ArrayList<>();

        if (args.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }

        StringTokenizer st = new StringTokenizer(args, " ");

        List<String> firstItemArray;
        String firstItem = st.nextToken();

        firstItemArray = fillCheckArray(firstItem);

        boolean removeAll;
        boolean isRange;
        String lowerLimit = firstItemArray.get(indexLowerLimit);
        String upperLimit = firstItemArray.get(indexUpperLimit);
        String checkFirstTag = firstItemArray.get(indexIsTag);
        String checkIsRange = firstItemArray.get(indexIsRange);

        if (checkIsRange.equals("true")) {
            isRange = true;
        } else {
            isRange = false;
        }

        if (checkFirstTag.equals("true")) {
            removeAll = true;
        } else {
            removeAll = false;
        }



        // Check if remove tag from index or whole contact list
        if (removeAll) {
            try {
                Tag toRemove = new Tag(firstItem);
                toRemoveSet.add(toRemove);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
            }
        } else {
            if (isRange) {
                // Check if any of the limit is empty
                boolean isLowerValid = lowerLimit.isEmpty();
                boolean isUpperValid = upperLimit.isEmpty();
                if (isLowerValid || isUpperValid) {
                    throw new ParseException("Invalid index range provided.\n"
                            + String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                }

                // Check if range is appropriate
                int lower = Integer.parseInt(lowerLimit);
                int upper = Integer.parseInt(upperLimit);
                if (lower > upper) {
                    throw new ParseException("Invalid index range provided.\n"
                            + String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                }

                // Adding of range of indexes
                for (int i = lower; i <= upper; i++) {
                    String toAdd = String.valueOf(i);
                    indexSet.add(toAdd);
                    try {
                        Index indexFromRangeToAdd = ParserUtil.parseIndex(toAdd);
                        index.add(indexFromRangeToAdd);
                    } catch (IllegalValueException ive) {
                        throw new ParseException(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                    }
                }
            } else {
                // Adding of an index
                indexSet.add(firstItem);
                try {
                    Index indexToAdd = ParserUtil.parseIndex(firstItem);
                    index.add(indexToAdd);
                } catch (IllegalValueException ive) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                }
            }
        }


        // Check remaining input
        boolean tagAdded = false;
        while (st.hasMoreTokens()) {

            String newToken = st.nextToken();

            firstItemArray = fillCheckArray(newToken);

            boolean isTag;
            boolean isRangeAgain;
            String lowerLimit2 = firstItemArray.get(indexLowerLimit);
            String upperLimit2 = firstItemArray.get(indexUpperLimit);
            String checkIsTag = firstItemArray.get(indexIsTag);
            String checkIsRangeAgain = firstItemArray.get(indexIsRange);

            if (checkIsRangeAgain.equals("true")) {
                isRangeAgain = true;
            } else {
                isRangeAgain = false;
            }

            if (checkIsTag.equals("true")) {
                isTag = true;
            } else {
                isTag = false;
            }

            // Adding of tag with no overlap
            if (isTag) {
                Tag toRemoveAgain;
                try {
                    toRemoveAgain = new Tag(newToken);
                } catch (IllegalValueException ive) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                }

                if (!toRemoveSet.contains(toRemoveAgain)) {
                    toRemoveSet.add(toRemoveAgain);
                    tagAdded = true;
                }
            } else {
                if (tagAdded || removeAll) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                } else {
                    if (isRangeAgain) {
                        // Check if any of the limit is empty
                        boolean isLowerValidAgain = lowerLimit2.isEmpty();
                        boolean isUpperValidAgain = upperLimit2.isEmpty();
                        if (isLowerValidAgain || isUpperValidAgain) {
                            throw new ParseException("Invalid index range provided.\n"
                                    + String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                        }

                        // Check if range is appropriate
                        int lower = Integer.parseInt(lowerLimit2);
                        int upper = Integer.parseInt(upperLimit2);
                        if (lower > upper) {
                            throw new ParseException("Invalid index range provided.\n"
                                    + String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                        }


                        // Adding of range of indexes
                        for (int i = lower; i <= upper; i++) {
                            String toAdd = String.valueOf(i);
                            if (!indexSet.contains(toAdd)) {
                                indexSet.add(toAdd);
                                try {
                                    Index indexFromRangeToAdd = ParserUtil.parseIndex(toAdd);
                                    index.add(indexFromRangeToAdd);
                                } catch (IllegalValueException ive) {
                                    throw new ParseException(
                                            String.format(
                                                    MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                                }
                            }
                        }
                    } else {
                        // Adding of an index with no overlap
                        if (!indexSet.contains(newToken)) {
                            indexSet.add(newToken);
                            try {
                                Index indexToAdd = ParserUtil.parseIndex(newToken);
                                index.add(indexToAdd);
                            } catch (IllegalValueException ive) {
                                throw new ParseException(
                                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                            }
                        }
                    }
                }
            }
        }
        Collections.sort(indexSet);
        return new RemoveTagCommand(toRemoveSet, index, indexSet);
    }

    /**
     *
     * @param token
     * @return checkArray containing values for checking
     */
    private List<String> fillCheckArray(String token) {
        List<String> checkArray = new ArrayList<>();

        boolean startUpper = false;
        String isRange = "false";
        String isTag = "false";
        String lowerLimit = "";
        String upperLimit = "";

        char[] itemArray = token.toCharArray();

        if (token.contains("-")) {
            isRange = "true";
        }

        // Check character of first keyword of input
        for (char c : itemArray) {
            if (!Character.isDigit(c)) {
                if (c == '-') {
                    startUpper = true;
                } else {
                    if (isRange.equals("false")) {
                        isTag = "true";
                        break;
                    } else {
                        if (startUpper) {
                            upperLimit = "";
                            break;
                        } else {
                            lowerLimit = "";
                            break;
                        }
                    }
                }
            } else {
                if (startUpper) {
                    upperLimit += c;
                } else {
                    lowerLimit += c;
                }
            }
        }
        checkArray.add(0, lowerLimit);
        checkArray.add(1, upperLimit);
        checkArray.add(2, isTag);
        checkArray.add(3, isRange);
        return checkArray;
    }

}

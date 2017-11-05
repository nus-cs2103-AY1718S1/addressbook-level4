package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

//@@author NabeelZaheer
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
        String indexInput;
        List<String> indexSet = new ArrayList<>();

        if (args.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        StringTokenizer st = new StringTokenizer(args.trim(), " ");

        String firstItem = st.nextToken();
        boolean isRange = false;
        boolean startUpper = false;
        boolean firstTag = false;
        String lowerLimit = "";
        String upperLimit = "";

        char[] firstItemArray = firstItem.toCharArray();

        if (firstItem.contains("-")) {
            isRange = true;
        }

        // Check character of first keyword of input
        for (char c : firstItemArray) {
            if (!Character.isDigit(c)) {
                if (c == '-') {
                    startUpper = true;
                } else {
                    if (!isRange && Character.isLetter(c)) {
                        firstTag = true;
                        break;
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


        // Check if first keyword is tag or index
        if (firstTag) {
            throw new ParseException("Please provide an input for index.\n"
                    + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        } else {
            if (isRange) {
                // Check if any of the limit is empty
                boolean isLowerValid = lowerLimit.isEmpty();
                boolean isUpperValid = upperLimit.isEmpty();
                if (isLowerValid || isUpperValid) {
                    throw new ParseException("Invalid index range provided.\n"
                            + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
                }

                // Check if range is appropriate
                int lower = Integer.parseInt(lowerLimit);
                int upper = Integer.parseInt(upperLimit);
                if (lower > upper) {
                    throw new ParseException("Invalid index range provided.\n"
                            + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
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
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
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
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
                }
            }
        }

        boolean tagAdded = false;
        while (st.hasMoreTokens()) {

            String newToken = st.nextToken();

            boolean isRangeAgain = false;
            boolean startUpperAgain = false;
            boolean isTag = false;
            String lowerLimit2 = "";
            String upperLimit2 = "";

            if (newToken.contains("-")) {
                isRangeAgain = true;
            }

            char[] nextItemArray = newToken.toCharArray();


            // Check characters of remaining keywords
            for (char c : nextItemArray) {
                if (!Character.isDigit(c)) {
                    if (c == '-') {
                        startUpperAgain = true;
                    } else {
                        if (!isRangeAgain) {
                            isTag = true;
                            break;
                        }
                    }
                } else {
                    if (startUpperAgain) {
                        upperLimit2 += c;
                    } else {
                        lowerLimit2 += c;
                    }
                }
            }

            // Adding of tag with no overlap
            if (isTag) {
                Tag toAdd;
                try {
                    toAdd = new Tag(newToken);
                } catch (IllegalValueException ive) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
                }

                if (!toAddSet.contains(toAdd)) {
                    toAddSet.add(toAdd);
                    tagAdded = true;
                }
            } else {
                if (tagAdded) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
                } else {
                    if (isRangeAgain) {
                        // Check if range is appropriate
                        int lower = Integer.parseInt(lowerLimit2);
                        int upper = Integer.parseInt(upperLimit2);
                        if (lower > upper) {
                            throw new ParseException("Invalid index range provided.\n"
                                    + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
                        }

                        // Check if any of the limit is empty
                        boolean isLowerValidAgain = lowerLimit2.isEmpty();
                        boolean isUpperValidAgain = upperLimit2.isEmpty();
                        if (isLowerValidAgain || isUpperValidAgain) {
                            throw new ParseException("Invalid index range provided.\n"
                                    + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
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
                                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
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
                                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
                            }
                        }
                    }
                }
            }





        }












//        try {
//            Tag toAdd = new Tag(st.nextToken());
//            toAddSet.add(toAdd);
//        } catch (IllegalValueException ive) {
//            throw new ParseException(
//                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
//        }
//
//        Boolean indexAdded = false;
//        String indexInput;
//        List<String> indexSet = new ArrayList<>();
//        while (st.hasMoreTokens()) {
//            String newToken = st.nextToken();
//            Boolean isIndex = true;
//
//            // check is newToken holds an non-integer value
//            char[] charArray = newToken.toCharArray();
//            String lowerLimit = "";
//            String upperLimit = "";
//            boolean isRange = false;
//            if (newToken.contains("-")) {
//                isRange = true;
//            }
//            boolean reach2nd = false;
//            for (char c : charArray) {
//                if (!Character.isDigit(c)) {
//                    if (c == '-') {
//                        reach2nd = true;
//                        continue;
//                    }
//                    isIndex = false;
//                    break;
//                } else {
//                    if (isRange) {
//                        if (!reach2nd) {
//                            lowerLimit += c;
//                        } else {
//                            upperLimit += c;
//                        }
//                    }
//                }
//            }
//
//            if (isRange) {
//                boolean isLowerValid = lowerLimit.isEmpty();
//                boolean isUpperValid = upperLimit.isEmpty();
//                if (isLowerValid || isUpperValid) {
//                    throw new ParseException("Invalid index range provided.\n"
//                            + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
//                }
//                int lower = Integer.parseInt(lowerLimit);
//                int upper = Integer.parseInt(upperLimit);
//                if (lower > upper) {
//                    throw new ParseException("Invalid index range provided.\n"
//                            + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
//                }
//
//                for (int i = lower; i <= upper; i++) {
//                    String toAdd = String.valueOf(i);
//                    indexSet.add(toAdd);
//                    try {
//                        Index indexFromRangeToAdd = ParserUtil.parseIndex(toAdd);
//                        index.add(indexFromRangeToAdd);
//                        indexAdded = true;
//                    } catch (IllegalValueException ive) {
//                        throw new ParseException(
//                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
//                    }
//                }
//            } else {
//                if (isIndex) {
//                    indexSet.add(newToken);
//                    try {
//                        Index indexToAdd = ParserUtil.parseIndex(newToken);
//                        index.add(indexToAdd);
//                        indexAdded = true;
//                    } catch (IllegalValueException ive) {
//                        throw new ParseException(
//                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
//                    }
//                } else {
//                    if (indexAdded) {
//                        throw new ParseException(
//                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
//                    } else {
//                        try {
//                            Tag toAdd = new Tag(newToken);
//                            toAddSet.add(toAdd);
//                        } catch (IllegalValueException ive) {
//                            throw new ParseException(
//                                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
//                        }
//                    }
//                }
//            }
//        }

        Collections.sort(indexSet);
        indexInput = indexSet.stream().collect(Collectors.joining(", "));
        return new AddTagCommand(toAddSet, index, indexInput);
    }

}

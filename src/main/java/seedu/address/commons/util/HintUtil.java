package seedu.address.commons.util;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPTY;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;

/**
 * Util Methods for parsing of userInput to generate Hints
 */

public class HintUtil {

    /**
     * Given a list of {@code prefixes} we return a list of prefixes that were
     * not completed in the {@code arguments} provided
     */
    public static List<Prefix> getUncompletedPrefixes(String arguments, List<Prefix> prefixes) {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(arguments, prefixes.toArray(new Prefix[0]));

        List<Prefix> uncompletedPrefixList = new ArrayList<>();
        for (Prefix p: prefixes) {
            if (!argumentMultimap.getValue(p).isPresent()) {
                uncompletedPrefixList.add(p);
            }
        }
        return uncompletedPrefixList;
    }

    /**
     * returns the "/" of prefix, if the given {@code arguments},
     * ends with part of prefix that's inside the list of {@code prefixes} provided.
     *
     * If no prefix is present, we return an empty optional.
     */
    public static Optional<String> findPrefixCompletionHint(String arguments, List<Prefix> prefixes) {
        for (Prefix p : prefixes) {
            String prefixLetter = " " + (p.getPrefix().toCharArray()[0]); // " n"
            String identifier = "" + (p.getPrefix().toCharArray()[1]); // "/"

            if (arguments.endsWith(prefixLetter)) {
                return Optional.of(identifier);
            }
        }

        return Optional.empty();
    }

    /**
     * returns prefix if the given {@code arguments}
     * ends with part of prefix that's inside the list of {@code prefixes} provided.
     *
     * If no prefix is present, we return an empty prefix.
     */
    public static Prefix findPrefixOfCompletionHint(String arguments, List<Prefix> prefixes) {
        for (Prefix prefix : prefixes) {
            String prefixLetter = " " + (prefix.getPrefix().toCharArray()[0]);
            if (arguments.endsWith(prefixLetter)) {
                return prefix;
            }
        }

        return PREFIX_EMPTY;
    }

    /**
     * returns prefix if the given {@code userInput}
     * ends with the full prefix that's inside the list of {@code prefixes} provided.
     *
     * If no prefix is present, we return an empty optional.
     */
    public static Optional<Prefix> findEndPrefix(String userInput, List<Prefix> prefixes) {

        for (Prefix p : prefixes) {
            if (userInput.endsWith(p.toString())) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }


    /**
     * returns prefix that is not present in {@code arguments}
     * but listed in {@code prefixes} provided.
     *
     * If all prefixes are present, we return an empty prefix.
     */
    public static Prefix offerHint(String arguments, List<Prefix> prefixes) {

        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(arguments, prefixes.toArray(new Prefix[0]));

        for (Prefix p : prefixes) {
            Optional<String> parameterOptional = argumentMultimap.getValue(p);
            if (!parameterOptional.isPresent()) {
                return p;
            }
        }

        return PREFIX_EMPTY;
    }

    /**
     * returns true if argument starts with a number
     */
    public static boolean hasPreambleIndex(String arguments) {

        String trimmed = arguments.trim();
        boolean hasDigit = false;

        for (int i = 0; i < trimmed.length(); i++) {

            if (Character.isDigit(trimmed.charAt(i))) {
                hasDigit = true;
            } else if (Character.isSpaceChar(trimmed.charAt(i))) {
                return true;
            } else {
                return false;
            }
        }

        return hasDigit;
    }

    /**
     * returns the index provided in the {@code arguments}
     * requires {@code prefixes} to use the {@code ArgumentMultiMap}
     * returns -1 if no index is present.
     */
    public static int getPreambleIndex(String arguments, List<Prefix> prefixes) {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(arguments, prefixes.toArray(new Prefix[0]));
        try {
            Index i = ParserUtil.parseIndex(argMultimap.getPreamble());
            return i.getOneBased();
        } catch (IllegalValueException ive) {
            return -1;
        }
    }

    /**
     * returns true if argument has an index
     */
    public static boolean hasIndex(String arguments) {
        try {
            ParserUtil.parseIndex(arguments);
            return true;
        } catch (IllegalValueException ive) {
            return false;
        }
    }

    /**
     * returns index in the {@code arguments}
     * returns -1 if no index is present;
     */
    public static int getIndex(String arguments) {
        try {
            Index i = ParserUtil.parseIndex(arguments);
            return i.getOneBased();
        } catch (IllegalValueException ive) {
            return -1;
        }
    }


}

package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.List;

//@@author newalter
/**
 * Convert a list of string of unprocessed keywords with wildcard symbol "*" and "?"
 * into a list of lowercase regular expression matching the keywords.
 */
public class ArgumentWildcardMatcher {

    /**
     * Convert arguments string of keywords with wildcard symbol "*" and "?"
     * into a list of lowercase regular expression matching the keywords.
     * @param keywords List of String containing unprocessed keywords
     * @return A lists of string regular expression in lowercase matching the keywords.
     */
    public static List<String> processKeywords(List<String> keywords) {
        ArrayList<String> processedKeywords = new ArrayList<>();
        for (String keyword : keywords) {
            processedKeywords.add(keyword.toLowerCase().replace("*", "\\S*").replace("?", "\\S"));
        }
        return processedKeywords;
    }
}

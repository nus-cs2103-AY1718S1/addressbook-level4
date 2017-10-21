package seedu.address.logic.parser;

/**
 * Convert arguments string of keywords with wildcard symbol "*"
 * into a list of lowercase regular expression matching the keywords.
 */
public class ArgumentWildcardMatcher {

    /**
     * Convert arguments string of keywords with wildcard symbol "*"
     * into a list of lowercase regular expression matching the keywords.
     * @param args Arguments string separated by spaces without leading or trailing space.
     * @return A lists of string regular expression in lowercase matching the keywords.
     */
    public static String[] createKeywords(String args) {
        String[] keywords = args.split("\\s+");
        for (int i = 0; i < keywords.length; i++) {
            keywords[i] = keywords[i].toLowerCase().replace("*", "\\S*");
        }
        return keywords;
    }
}

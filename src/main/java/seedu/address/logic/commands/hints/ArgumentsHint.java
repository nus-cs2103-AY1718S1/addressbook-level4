package seedu.address.logic.commands.hints;

import java.util.List;

import seedu.address.commons.util.HintUtil;
import seedu.address.logic.parser.Prefix;

/**
 * Template class for hints that have arguments in their command format
 * These arguments include prefixes and indices.
 * Specifies autocomplete to return {@code onTab}
 */
public abstract class ArgumentsHint extends Hint {

    protected String onTab = "";

    protected String getDescription(Prefix p) {
        return "description";
    }

    /**
     * Should be used when prefix is half completed
     * {@code prefixCompletion} which is auto completed portion of the prefix (ie "/")
     * {@code possbilePrefixesToComplete} required to parse for prefix to get description TODO: rethink this
     * updates {@code onTab} to return {@code userInput} auto completed with {@code prefixCompletion}
     */
    protected void handlePrefixCompletion(String prefixCompletion, List<Prefix> possiblePrefixesToComplete) {
        argumentHint = prefixCompletion;
        Prefix p = HintUtil.findPrefixOfCompletionHint(arguments, possiblePrefixesToComplete);
        description = getDescription(p);
        onTab = userInput + argumentHint;
        LOGGER.info("ArgumentsHint - Handled Prefix Completion");
    }

    /**
     * Should be used when prefix is completed and caret is next to prefix (ie: n/|)
     * {@code currentPrefix} that is completed
     * {@code possiblePrefixesToComplete, prefixList} to generate the next prefix option on pressing tab
     * updates {@code onTab} to return {@code userInput} auto completed with the next prefix option
     */
    protected void handlePrefixTabbing(Prefix currentPrefix,
                                       List<Prefix> prefixList,
                                       List<Prefix> possiblePrefixesToComplete) {
        argumentHint = "";
        description = getDescription(currentPrefix);
        Prefix nextPrefix = generateNextPrefix(currentPrefix, prefixList, possiblePrefixesToComplete);
        onTab = userInput.substring(0, userInput.length() - 2) + nextPrefix.toString();
        LOGGER.info("ArgumentsHint - Handled Prefix Tabbing");
    }

    /**
     * generates next prefix given {@code currentPrefix} and {@code possiblePrefixesToComplete}\
     * uses {@code prefixList} as an ordering so that we can cycle through all prefixes in the same order
     */
    private Prefix generateNextPrefix(Prefix currentPrefix,
                                      List<Prefix> prefixList,
                                      List<Prefix> possiblePrefixesToComplete) {
        int startIndex = prefixList.indexOf(currentPrefix);
        for (int i = 0; i < prefixList.size(); i++) {
            int index = (i + startIndex) % prefixList.size();
            if (possiblePrefixesToComplete.contains(prefixList.get(index))) {
                return prefixList.get(index);
            }
        }
        return currentPrefix;
    }

    /**
     * Should be used when you want the addressbook to offer the next argument (ie n/nicholas |)
     * {@code prefixList} to generate the next prefix hint
     * updates {@code onTab} to return {@code userInput} auto completed with the next prefix hint
     */
    protected void handleOfferHint(List<Prefix> prefixList) {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        Prefix offeredPrefix = HintUtil.offerHint(arguments, prefixList);
        argumentHint = whitespace + offeredPrefix.toString();
        description = getDescription(offeredPrefix);
        onTab = userInput + argumentHint;
        LOGGER.info("ArgumentsHint - Handled Prefix Offer");
    }

    /**
     * Should be used when you want the addressbook to offer an index hint (ie delete|)
     * updates {@code onTab} to return {@code userInput} auto completed with a default index of 1
     * TODO: default index based on current model
     */
    protected void handleOfferIndex(String userInput) {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        argumentHint = whitespace + "1";
        description = " index";
        onTab = userInput + argumentHint;
        LOGGER.info("ArgumentsHint - Handled Index Offer");
    }

    /**
     * Should be used when index is written and caret is directly next to index (ie edit 12|)
     * updates {@code onTab} to return {@code userInput} with an increment to the index
     * TODO: index update based on current model
     */
    protected void handleIndexTabbing(int currentIndex) {
        argumentHint = "";
        description = " index";
        int length = String.valueOf(currentIndex).length();

        int newIndex = currentIndex + 1;
        onTab = userInput.substring(0, userInput.length() - length) + newIndex;
        LOGGER.info("ArgumentsHint - Handled Index Tabbing");
    }

    public String autocomplete() {
        return onTab;
    }

}

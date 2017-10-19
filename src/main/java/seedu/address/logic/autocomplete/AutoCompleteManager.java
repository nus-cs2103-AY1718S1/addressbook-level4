package seedu.address.logic.autocomplete;

import java.util.LinkedList;

import seedu.address.logic.autocomplete.parser.AutoCompleteCommandParser;
import seedu.address.logic.autocomplete.parser.AutoCompleteParser;
import seedu.address.logic.autocomplete.parser.IdentityParser;

/**
 * Manages cache and memoization for autocomplete possibilities.
 * Not implemented yet and is consider area for future enhancement.
 */
public class AutoCompleteManager {

    private final IdentityParser identity = new IdentityParser();
    private final AutoCompleteCommandParser commandParser = new AutoCompleteCommandParser();
    private final LinkedList<AutoCompletePossibilities> cache = new LinkedList<AutoCompletePossibilities>();
    private final int maxSize;

    public AutoCompleteManager(int size) {
        maxSize = size;
    }

    /**
     * Searches the cache for old AutoCompletePossibilities that has already been evaluated and stored,
     * based on the command stub specified.
     * @param stub incomplete user input given
     * @return AutoCompletePossibilities object that contains all autocomplete options,
     * new object will be generated if not found in cache
     */
    public AutoCompletePossibilities search(String stub) {
        for (AutoCompletePossibilities entryInCache : cache) {
            if (stub.equals(entryInCache.getStub())) {
                return entryInCache;
            }
        }
        return insert(new AutoCompletePossibilities(stub, new AutoCompleteCommandParser()));
    }

    /**
     * Inserts new entry into the cache, remove oldest entry if max size is hit
     * @param entry newly generated AutoCompletePossibilities
     * @return entry that has been added
     */
    private AutoCompletePossibilities insert(AutoCompletePossibilities entry) {
        cache.addFirst(entry);
        if (cache.size() > maxSize) {
            cache.removeLast();
        }
        return entry;
    }

    /**
     * Chooses an AutoCompleteParser based on the user input stub,
     * more specifically the parser used is determined by:
     *  > number words in the user input
     *  > closest prefix on the left of the input
     * @param user input stub
     * @return AutoCompleteParser that should be used to complete the user input
     */
    private AutoCompleteParser chooseParser(String stub) {
        if ("".equals(stub)) {
            return identity;
        }

        int numberOfWordsInStub = stub.split(" ").length;

        if (numberOfWordsInStub == 1) {
            return commandParser;
        }

        // if the right parser can't be found, return an identity function parser
        return identity;
    }

}

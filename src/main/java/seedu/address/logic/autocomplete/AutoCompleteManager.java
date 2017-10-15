package seedu.address.logic.autocomplete;

import java.util.LinkedList;

/**
 * Manages cache and memoization for autocomplete possibilities.
 * Not implemented yet and is consider area for future enhancement.
 */
public class AutoCompleteManager {
    private final LinkedList<AutoCompletePossibilities> cache = new LinkedList<AutoCompletePossibilities>();
    private final int maxSize;

    public AutoCompleteManager(int size) {
        maxSize = size;
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
        return insert(new AutoCompletePossibilities(stub));
    }

}

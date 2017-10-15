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

    private void insert(AutoCompletePossibilities entry) {
        cache.addFirst(entry);
        if (cache.size > maxSize) {
            cache.removeLast();
        }
    }

    public AutoCompletePossibilities search(String stub) {
        for (AutoCompletePossibilties entryInCache : cache) {
            if (stub.equals(entryInCache.getStub())) {
                return entryInCache;
            }
        }
        insert(new AutoCompletePossibilties(stub));
    }

}

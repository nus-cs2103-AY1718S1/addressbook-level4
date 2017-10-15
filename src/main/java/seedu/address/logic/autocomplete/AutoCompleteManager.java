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

    private AutoCompletePossibilities insert(AutoCompletePossibilities entry) {
        cache.addFirst(entry);
        if (cache.size() > maxSize) {
            cache.removeLast();
        }
        return entry;
    }

    public AutoCompletePossibilities search(String stub) {
        for (AutoCompletePossibilities entryInCache : cache) {
            if (stub.equals(entryInCache.getStub())) {
                return entryInCache;
            }
        }
        return insert(new AutoCompletePossibilities(stub));
    }

}

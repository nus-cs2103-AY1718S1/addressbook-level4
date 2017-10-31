package seedu.address.logic.parser;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
* Represents an ascending index range with a start and end (inclusive).
* Start and end are both zero-based index.
*/
public class IndexRange {
    private final int start;
    private final int end;

    public IndexRange(int start, int end) {
        requireAllNonNull(start, end);

        // Forcing ascending order.
        this.start = Math.min(start, end);
        this.end = Math.max(start, end);
    }

    /**
    * Returns all intermediate values of this index range's start and end (inclusive) in a {@code List}.
    */
    public List<Integer> getAllValues() {
        return IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

    /**
    * Returns all intermediate values of every {@code IndexRange}'s start and end (inclusive) of {@code ranges}
    * in a {@code List}. The original order of indices is maintained from {@code ranges}. <br>
    * Essentially, this method calls {@link #getAllValues()} of each {@code IndexRange} in {@code ranges}
    * and flattens the resulting lists into a single list.
    */
    public static List<Integer> getAllValues(List<IndexRange> ranges) {
        return ranges.stream().flatMap(range -> range.getAllValues().stream()).collect(Collectors.toList());
    }

    /** Returns true if every index in {@code zeroBasedIndices} is within bounds of {@code bounds}. */
    public static boolean areIndicesWithinBounds(HashSet<Integer> zeroBasedIndices, List<?> bounds) {
        return areIndicesWithinBounds(zeroBasedIndices, 0, bounds.size());
    }

    /** Returns true if every index in {@code zeroBasedIndices} is between {@code start} and {@code endExclusive}. */
    public static boolean areIndicesWithinBounds(HashSet<Integer> zeroBasedIndices, int start, int endExclusive) {
        assert start >= 0;
        assert start <= endExclusive;
        return zeroBasedIndices.stream().allMatch(i -> i >= start && i < endExclusive);
    }

    /**
    * Returns a sublist of items from {@code list} specified by {@code indices}.
    * Original list order is maintained in the extracted list.
    */
    public static <T> List<T> subList(HashSet<Integer> indices, List<T> list) {
        assert areIndicesWithinBounds(indices, list);
        return indices.stream()
                .sorted()
                .map(list::get)
                .collect(Collectors.toList());
    }
}

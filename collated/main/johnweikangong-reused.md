# johnweikangong-reused
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (!IndexRange.areIndicesWithinBounds(zeroBasedTargetIndexes, lastShownList)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        List<ReadOnlyPerson> personToDelete = IndexRange.subList(zeroBasedTargetIndexes, lastShownList);

        try {
            model.deletePersons(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.size(),
                StringUtil.toIndexedListString(zeroBasedTargetIndexes, personToDelete)));
    }
```
###### \java\seedu\address\logic\parser\DeleteCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            final List<String> rangedIndices = Arrays.asList(args.trim().split("\\s+"));
            final List<IndexRange> indexRanges = ParserUtil.parseRangedIndices(rangedIndices);

            return new DeleteCommand(new HashSet<>(IndexRange.getAllValues(indexRanges)));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\IndexRange.java
``` java
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
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code List<String> allRangedIndices} into a {@code List<IndexRange> indexRanges}
     * and returns it, providing a list of index ranges using the IndexRange class
     * @throws IllegalValueException if index range is invalid
     */
    public static List<IndexRange> parseRangedIndices(List<String> allRangedIndices)
            throws IllegalValueException {
        final List<IndexRange> indexRanges = new ArrayList<>();

        for (String rangedIndices : allRangedIndices) {
            final IndexRange rangedIndexes = parseRangedIndices(rangedIndices.trim());

            indexRanges.add(rangedIndexes);
        }

        return indexRanges;
    }
```

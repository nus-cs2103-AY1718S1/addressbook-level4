package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.commons.core.index.Index;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    public static final ArrayList<Index> INDEX_FIRST_PERSON = new ArrayList<Index>(
            Arrays.asList(Index.fromOneBased(1)));
    public static final ArrayList<Index> INDEX_SECOND_PERSON = new ArrayList<Index>(
            Arrays.asList(Index.fromOneBased(2)));
    public static final ArrayList<Index> INDEX_THIRD_PERSON = new ArrayList<Index>(
            Arrays.asList(Index.fromOneBased(3)));
    public static final ArrayList<Index> INDEX_MULTIPLE_PERSON = new ArrayList<Index>(
            Arrays.asList(Index.fromOneBased(1), Index.fromOneBased(2)));

    public static final ArrayList<Index> INDEX_FIRST_TASK = new ArrayList<Index>(
            Arrays.asList(Index.fromOneBased(1)));
    public static final ArrayList<Index> INDEX_SECOND_TASK = new ArrayList<Index>(
            Arrays.asList(Index.fromOneBased(2)));
    public static final ArrayList<Index> INDEX_THIRD_TASK = new ArrayList<Index>(
            Arrays.asList(Index.fromOneBased(3)));
    public static final ArrayList<Index> INDEX_MULTIPLE_TASK = new ArrayList<Index>(
            Arrays.asList(Index.fromOneBased(1), Index.fromOneBased(2)));


    public static final Index INDEX_FIRST_THEME = Index.fromOneBased(1);
}

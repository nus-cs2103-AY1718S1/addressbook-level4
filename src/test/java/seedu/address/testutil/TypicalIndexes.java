package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    public static final Index INDEX_FIRST_PERSON = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_PERSON = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_PERSON = Index.fromOneBased(3);
    public static final Index INDEX_FIRST_EVENT = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_EVENT = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_EVENT = Index.fromOneBased(3);

    /**
     * Returns an {@code list} with all the typical person indexes.
     */
    public static List<Index> getTypicalPersonIndexList() {
        List<Index> idx = new ArrayList<Index>();
        for (Index index : getTypicalPersonIndexes()) {
            try {
                idx.add(index);
            } catch (ArrayStoreException e) {
                assert false : "not possible";
            }
        }
        return idx;
    }

    /**
     * Returns an {@code list} with all the typical event indexes.
     */
    public static List<Index> getTypicalEventIndexList() {
        List<Index> idx = new ArrayList<>();
        for (Index index : getTypicalEventIndexes()) {
            try {
                idx.add(index);
            } catch (ArrayStoreException e) {
                assert false : "not possible";
            }
        }
        return idx;
    }

    public static List<Index> getTypicalPersonIndexes() {
        return new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON));
    }

    public static List<Index> getTypicalEventIndexes() {
        return new ArrayList<>(Arrays.asList(INDEX_FIRST_EVENT, INDEX_SECOND_EVENT, INDEX_THIRD_EVENT));
    }
}

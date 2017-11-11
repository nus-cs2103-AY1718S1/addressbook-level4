package seedu.address.testutil;

import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

//@@author marvinchin
/**
 * Utility class for tests involving {@code Storage}.
 */
public class StorageUtil {

    /**
     * Returns a dummy storage for tests where a real storage does not need to be used
     */
    public static Storage getDummyStorage() {
        return new StorageManager(null, null);
    }
}

package seedu.address.testutil;

/**
 * A utility class containing a list of TsvFile objects to be used in tests.
 */
public class TypicalTsvFiles {
    public static final String PERFECT_TSV_FILE_PATH = "src/test/resources/docs/perfectTsvFile.txt";
    public static final String PERFECT_TSV_FILE_MESSAGE_SUCCESS = "2 new person (people) added, "
            + "0 new person (people) duplicated, 0 entry (entries) failed: ";
    public static final String PERFECT_TSV_FILE_MESSAGE_ALL_DUPLICATED = "0 new person (people) added, "
            + "2 new person (people) duplicated, 0 entry (entries) failed: ";
}

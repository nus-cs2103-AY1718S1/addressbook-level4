package seedu.address.storage;

import java.io.FileNotFoundException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.util.FileUtil;

//@@author kaiyu92
/**
 * Checking the test of the exportation for both addressbook and eventbook
 */
public class XmlFileStorageTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final String MISSING_FILE = TEST_DATA_FOLDER + "missing.xml";

    private static final String VALID_ADDRESSBOOK_PATH = TEST_DATA_FOLDER + "validAddressBook.xml";
    private static final String ADDRESSBOOK_DESTINATION_PATH = TEST_DATA_FOLDER + "validAddressBook.csv";
    private static final String ADDRESSBOOK_HEADER = "Name,Phone,Address,Birthday,Email,Group,Remark,Tagged";

    private static final String VALID_EVENTBOOK_FILE = TEST_DATA_FOLDER + "validEventBook.xml";
    private static final String EVENTBOOK_DESTINATION_PATH = TEST_DATA_FOLDER + "validEventBook.csv";
    private static final String EVENTBOOK_HEADER = "Title,Description,Location,Datetime";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void exportAddressbook_nullSource_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlFileStorage.exportAddressbook(null, ADDRESSBOOK_DESTINATION_PATH, ADDRESSBOOK_HEADER);
    }

    @Test
    public void exportAddressbook_nullDestination_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlFileStorage.exportAddressbook(VALID_ADDRESSBOOK_PATH, null, ADDRESSBOOK_HEADER);
    }

    @Test
    public void exportAddressbook_nullHeader_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlFileStorage.exportAddressbook(VALID_ADDRESSBOOK_PATH, ADDRESSBOOK_DESTINATION_PATH, null);
    }

    @Test
    public void exportAddressbook_missingSourceFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlFileStorage.exportAddressbook(MISSING_FILE, ADDRESSBOOK_DESTINATION_PATH, ADDRESSBOOK_HEADER);
    }

    @Test
    public void exportEventbook_nullSource_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlFileStorage.exportEventbook(null, EVENTBOOK_DESTINATION_PATH, EVENTBOOK_HEADER);
    }

    @Test
    public void exportEventbook_nullDestination_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlFileStorage.exportEventbook(VALID_EVENTBOOK_FILE, null, EVENTBOOK_HEADER);
    }

    @Test
    public void exportEventbook_nullHeader_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlFileStorage.exportEventbook(VALID_EVENTBOOK_FILE, EVENTBOOK_DESTINATION_PATH, null);
    }

    @Test
    public void exportEventbook_missingSourceFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlFileStorage.exportEventbook(MISSING_FILE, EVENTBOOK_DESTINATION_PATH, EVENTBOOK_HEADER);
    }
}

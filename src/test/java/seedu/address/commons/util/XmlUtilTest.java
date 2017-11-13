package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.Rolodex;
import seedu.address.storage.XmlSerializableRolodex;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.RolodexBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.rldx");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validRolodex.rldx");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempRolodex.rldx"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFileNullFileThrowsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, Rolodex.class);
    }

    @Test
    public void getDataFromFileNullClassThrowsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFileMissingFileFileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, Rolodex.class);
    }

    @Test
    public void getDataFromFileEmptyFileDataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, Rolodex.class);
    }

    @Test
    public void getDataFromFileValidFileValidResult() throws Exception {
        XmlSerializableRolodex dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableRolodex.class);
        assertEquals(9, dataFromFile.getPersonList().size());
        assertEquals(0, dataFromFile.getTagList().size());
    }

    @Test
    public void saveDataToFileNullFileThrowsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new Rolodex());
    }

    @Test
    public void saveDataToFileNullClassThrowsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFileMissingFileFileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new Rolodex());
    }

    @Test
    public void saveDataToFileValidFileDataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableRolodex dataToWrite = new XmlSerializableRolodex(new Rolodex());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableRolodex dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableRolodex.class);
        assertEquals((new Rolodex(dataToWrite)).toString(), (new Rolodex(dataFromFile)).toString());

        RolodexBuilder builder = new RolodexBuilder(new Rolodex());
        dataToWrite = new XmlSerializableRolodex(
                builder.withPerson(new PersonBuilder().build()).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableRolodex.class);
        assertEquals((new Rolodex(dataToWrite)).toString(), (new Rolodex(dataFromFile)).toString());
    }
}

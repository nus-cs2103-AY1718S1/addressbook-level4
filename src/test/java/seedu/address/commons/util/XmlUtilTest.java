package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.storage.XmlSerializableAddressBook;
import seedu.address.storage.XmlSerializableEventBook;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.EventBookBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");

    private static final File VALID_ADDRESSBOOK_FILE = new File(TEST_DATA_FOLDER + "validAddressBook.xml");
    private static final File TEMP_ADDRESSBOOK_FILE = new File(TestUtil
            .getFilePathInSandboxFolder("tempAddressBook.xml"));
    private static final String ADDRESSBOOK_DESTINATION_PATH = TEST_DATA_FOLDER + "validAddressBook.csv";
    private static final String ADDRESSBOOK_HEADER = "Name,Phone,Address,Birthday,Email,Group,Remark,Tagged";
    private static Element singlePersonElement;

    private static final File VALID_EVENTBOOK_FILE = new File(TEST_DATA_FOLDER + "validEventBook.xml");
    private static final File TEMP_EVENTBOOK_FILE = new File(TestUtil
            .getFilePathInSandboxFolder("tempEventBook.xml"));
    private static final String EVENTBOOK_DESTINATION_PATH = TEST_DATA_FOLDER + "validEventBook.csv";
    private static final String EVENTBOOK_HEADER = "Title,Description,Location,Datetime";
    private static Element singleEventElement;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //@@author kaiyu92
    @Before
    public void setup() throws Exception {
        NodeList personList = XmlUtil.getNodeListFromFile(VALID_ADDRESSBOOK_FILE, "persons");
        singlePersonElement = (Element) personList.item(0);

        NodeList eventList = XmlUtil.getNodeListFromFile(VALID_EVENTBOOK_FILE, "events");
        singleEventElement = (Element) eventList.item(0);
    }

    //@@author kaiyu92
    @Test
    public void exportGeneralBookDataToFile_nullDestination_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.exportDataToFile(null, new StringBuilder());
    }
    //@@author kaiyu92
    @Test
    public void appendGeneralBookHeader_nullBuilder_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.appendHeader(null, "testing");
    }

    //@@author kaiyu92
    @Test
    public void appendGeneralBookHeader_nullHeader_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.appendHeader(new StringBuilder(), null);
    }

    //==============================Addressbook==========================================

    @Test
    public void getAddressBookDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, AddressBook.class);
    }

    @Test
    public void getAddressBookDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_ADDRESSBOOK_FILE, null);
    }

    @Test
    public void getAddressBookDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, AddressBook.class);
    }

    @Test
    public void getAddressBookDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, AddressBook.class);
    }

    @Test
    public void getAddressBookDataFromFile_validFile_validResult() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(VALID_ADDRESSBOOK_FILE,
                XmlSerializableAddressBook.class);
        assertEquals(9, dataFromFile.getPersonList().size());
        assertEquals(0, dataFromFile.getTagList().size());
    }

    @Test
    public void saveAddressBookDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new AddressBook());
    }

    @Test
    public void saveAddressBookDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_ADDRESSBOOK_FILE, null);
    }

    @Test
    public void saveAddressBookDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new AddressBook());
    }

    @Test
    public void saveAddressBookDataToFile_validFile_dataSaved() throws Exception {
        TEMP_ADDRESSBOOK_FILE.createNewFile();
        XmlSerializableAddressBook dataToWrite = new XmlSerializableAddressBook(new AddressBook());
        XmlUtil.saveDataToFile(TEMP_ADDRESSBOOK_FILE, dataToWrite);
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TEMP_ADDRESSBOOK_FILE,
                XmlSerializableAddressBook.class);
        assertEquals((new AddressBook(dataToWrite)).toString(), (new AddressBook(dataFromFile)).toString());
        //TODO: use equality instead of string comparisons

        AddressBookBuilder builder = new AddressBookBuilder(new AddressBook());
        dataToWrite = new XmlSerializableAddressBook(
                builder.withPerson(new PersonBuilder().build()).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_ADDRESSBOOK_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_ADDRESSBOOK_FILE, XmlSerializableAddressBook.class);
        assertEquals((new AddressBook(dataToWrite)).toString(), (new AddressBook(dataFromFile)).toString());
    }

    //@@author kaiyu92
    @Test
    public void exportAddressBookDataToFile_nullContent_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.exportDataToFile(ADDRESSBOOK_DESTINATION_PATH, null);
    }

    //@@author kaiyu92
    @Test
    public void getAddressBookNodeList_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getNodeListFromFile(MISSING_FILE, "persons");
    }

    //@@author kaiyu92
    @Test
    public void getAddressBookNodeList_nullNodeName_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getNodeListFromFile(VALID_ADDRESSBOOK_FILE, null);
    }

    //@@author kaiyu92
    @Test
    public void getAddressBookNodeList_validFile_validResult() throws Exception {
        NodeList personList = XmlUtil.getNodeListFromFile(VALID_ADDRESSBOOK_FILE, "persons");
        assertEquals(9, personList.getLength());
    }

    //@@author kaiyu92
    @Test
    public void appendAddressBookHeader_validHeader_validResult() throws Exception {
        StringBuilder sb = new StringBuilder();
        XmlUtil.appendHeader(sb, ADDRESSBOOK_HEADER);
        int headerLength = (sb.toString().split(XmlUtil.NEW_LINE_SEPARATOR))[0].split(XmlUtil.COMMA_DELIMITER).length;
        assertEquals(8, headerLength);
    }

    //@@author kaiyu92
    @Test
    public void appendAddressBookContent_nullBuilder_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.appendContent(null, singlePersonElement, "name", "phone", "address", "birthday",
                "email", "group", "remark");
    }

    //@@author kaiyu92
    @Test
    public void appendAddressBookContent_nullElement_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.appendContent(new StringBuilder(), null, "name", "phone", "address", "birthday",
                "email", "group", "remark");
    }

    //@@author kaiyu92
    @Test
    public void appendAddressBookContent_nullFields_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.appendContent(new StringBuilder(), singlePersonElement, (String) null);
    }

    //=======================================================================================================

    //============================EventBook===============================================================
    //@@author kaiyu92
    @Test
    public void getEventBookDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, EventBook.class);
    }

    //@@author kaiyu92
    @Test
    public void getEventBookDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_EVENTBOOK_FILE, null);
    }

    //@@author kaiyu92
    @Test
    public void getEventBookDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, EventBook.class);
    }

    //@@author kaiyu92
    @Test
    public void getEventBookDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, EventBook.class);
    }

    //@@author kaiyu92
    @Test
    public void getEventBookDataFromFile_validFile_validResult() throws Exception {
        XmlSerializableEventBook dataFromFile = XmlUtil.getDataFromFile(VALID_EVENTBOOK_FILE,
                XmlSerializableEventBook.class);
        assertEquals(6, dataFromFile.getEventList().size());
    }

    //@@author kaiyu92
    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new EventBook());
    }

    //@@author kaiyu92
    @Test
    public void saveEventBookDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_EVENTBOOK_FILE, null);
    }

    //@@author kaiyu92
    @Test
    public void saveEventBookDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new EventBook());
    }

    //@@author kaiyu92
    @Test
    public void saveEventBookDataToFile_validFile_dataSaved() throws Exception {
        TEMP_EVENTBOOK_FILE.createNewFile();
        XmlSerializableEventBook dataToWrite = new XmlSerializableEventBook(new EventBook());
        XmlUtil.saveDataToFile(TEMP_EVENTBOOK_FILE, dataToWrite);
        XmlSerializableEventBook dataFromFile = XmlUtil.getDataFromFile(TEMP_EVENTBOOK_FILE,
                XmlSerializableEventBook.class);
        assertEquals((new EventBook(dataToWrite)).toString(), (new EventBook(dataFromFile)).toString());
        //TODO: use equality instead of string comparisons

        EventBookBuilder builder = new EventBookBuilder(new EventBook());
        dataToWrite = new XmlSerializableEventBook(builder.withEvent(new EventBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_EVENTBOOK_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_EVENTBOOK_FILE, XmlSerializableEventBook.class);
        assertEquals((new EventBook(dataToWrite)).toString(), (new EventBook(dataFromFile)).toString());
    }

    //@@author kaiyu92
    @Test
    public void exportEventBookDataToFile_nullContent_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.exportDataToFile(EVENTBOOK_DESTINATION_PATH, null);
    }

    //@@author kaiyu92
    @Test
    public void getEventBookNodeList_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getNodeListFromFile(MISSING_FILE, "events");
    }

    //@@author kaiyu92
    @Test
    public void getEventBookNodeList_nullNodeName_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getNodeListFromFile(VALID_EVENTBOOK_FILE, null);
    }

    //@@author kaiyu92
    @Test
    public void getEventBookNodeList_validFile_validResult() throws Exception {
        NodeList eventList = XmlUtil.getNodeListFromFile(VALID_EVENTBOOK_FILE, "events");
        assertEquals(6, eventList.getLength());
    }

    //@@author kaiyu92
    @Test
    public void appendEventBookHeader_validHeader_validResult() throws Exception {
        StringBuilder sb = new StringBuilder();
        XmlUtil.appendHeader(sb, EVENTBOOK_HEADER);
        int headerLength = (sb.toString().split(XmlUtil.NEW_LINE_SEPARATOR))[0].split(XmlUtil.COMMA_DELIMITER).length;
        assertEquals(4, headerLength);
    }

    //@@author kaiyu92
    @Test
    public void appendEventBookContent_nullBuilder_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.appendContent(null, singleEventElement, "title", "description", "location", "datetime");
    }

    //@@author kaiyu92
    @Test
    public void appendEventBookContent_nullElement_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.appendContent(new StringBuilder(), null, "title",
                "description", "location", "datetime");
    }

    //@@author kaiyu92
    @Test
    public void appendEventBookContent_nullFields_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.appendContent(new StringBuilder(), singleEventElement, (String) null);
    }
    //=======================================================================================================
}

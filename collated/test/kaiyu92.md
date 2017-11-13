# kaiyu92
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Before
    public void setup() throws Exception {
        NodeList personList = XmlUtil.getNodeListFromFile(VALID_ADDRESSBOOK_FILE, "persons");
        singlePersonElement = (Element) personList.item(0);

        NodeList eventList = XmlUtil.getNodeListFromFile(VALID_EVENTBOOK_FILE, "events");
        singleEventElement = (Element) eventList.item(0);
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void exportGeneralBookDataToFile_nullDestination_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.exportDataToFile(null, new StringBuilder());
    }
```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void appendGeneralBookHeader_nullBuilder_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.appendHeader(null, "testing");
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
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

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void exportAddressBookDataToFile_nullContent_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.exportDataToFile(ADDRESSBOOK_DESTINATION_PATH, null);
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void getAddressBookNodeList_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getNodeListFromFile(MISSING_FILE, "persons");
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void getAddressBookNodeList_nullNodeName_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getNodeListFromFile(VALID_ADDRESSBOOK_FILE, null);
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void getAddressBookNodeList_validFile_validResult() throws Exception {
        NodeList personList = XmlUtil.getNodeListFromFile(VALID_ADDRESSBOOK_FILE, "persons");
        assertEquals(9, personList.getLength());
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void appendAddressBookHeader_validHeader_validResult() throws Exception {
        StringBuilder sb = new StringBuilder();
        XmlUtil.appendHeader(sb, ADDRESSBOOK_HEADER);
        int headerLength = (sb.toString().split(XmlUtil.NEW_LINE_SEPARATOR))[0].split(XmlUtil.COMMA_DELIMITER).length;
        assertEquals(8, headerLength);
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void appendAddressBookContent_nullBuilder_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.appendContent(null, singlePersonElement, "name", "phone", "address", "birthday",
                "email", "group", "remark");
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void appendAddressBookContent_nullElement_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.appendContent(new StringBuilder(), null, "name", "phone", "address", "birthday",
                "email", "group", "remark");
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void appendAddressBookContent_nullFields_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.appendContent(new StringBuilder(), singlePersonElement, (String) null);
    }

    //=======================================================================================================

    //============================EventBook===============================================================
```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void getEventBookDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, EventBook.class);
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void getEventBookDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_EVENTBOOK_FILE, null);
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void getEventBookDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, EventBook.class);
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void getEventBookDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, EventBook.class);
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void getEventBookDataFromFile_validFile_validResult() throws Exception {
        XmlSerializableEventBook dataFromFile = XmlUtil.getDataFromFile(VALID_EVENTBOOK_FILE,
                XmlSerializableEventBook.class);
        assertEquals(6, dataFromFile.getEventList().size());
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new EventBook());
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void saveEventBookDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_EVENTBOOK_FILE, null);
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void saveEventBookDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new EventBook());
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
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

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void exportEventBookDataToFile_nullContent_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.exportDataToFile(EVENTBOOK_DESTINATION_PATH, null);
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void getEventBookNodeList_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getNodeListFromFile(MISSING_FILE, "events");
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void getEventBookNodeList_nullNodeName_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getNodeListFromFile(VALID_EVENTBOOK_FILE, null);
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void getEventBookNodeList_validFile_validResult() throws Exception {
        NodeList eventList = XmlUtil.getNodeListFromFile(VALID_EVENTBOOK_FILE, "events");
        assertEquals(6, eventList.getLength());
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void appendEventBookHeader_validHeader_validResult() throws Exception {
        StringBuilder sb = new StringBuilder();
        XmlUtil.appendHeader(sb, EVENTBOOK_HEADER);
        int headerLength = (sb.toString().split(XmlUtil.NEW_LINE_SEPARATOR))[0].split(XmlUtil.COMMA_DELIMITER).length;
        assertEquals(4, headerLength);
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void appendEventBookContent_nullBuilder_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.appendContent(null, singleEventElement, "title", "description", "location", "datetime");
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void appendEventBookContent_nullElement_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.appendContent(new StringBuilder(), null, "title",
                "description", "location", "datetime");
    }

```
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void appendEventBookContent_nullFields_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.appendContent(new StringBuilder(), singleEventElement, (String) null);
    }
    //=======================================================================================================
}
```
###### /java/seedu/address/logic/parser/FindEventCommandParserTest.java
``` java
public class FindEventCommandParserTest {

    private FindEventCommandParser parser = new FindEventCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindEventCommand expectedFindCommand =
                new FindEventCommand(new TitleContainsKeywordsPredicate(Arrays.asList("Spectra", "Deepavali")));
        assertParseSuccess(parser, "et/Spectra Deepavali", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "et/ \n Spectra \n \t Deepavali  \t", expectedFindCommand);
    }
}
```
###### /java/seedu/address/logic/parser/SelectEventCommandParserTest.java
``` java

/**
 * Test scope: similar to {@code DeleteSelectCommandParserTest}.
 *
 * @see DeleteEventCommandParserTest
 */
public class SelectEventCommandParserTest {

    private SelectEventCommandParser parser = new SelectEventCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectEventCommand(INDEX_FIRST_EVENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectEventCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/EditEventCommandParserTest.java
``` java
public class EditEventCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

    private EditEventCommandParser parser = new EditEventCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TITLE_SPECTRA, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditEventCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TITLE_DESC_SPECTRA, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TITLE_DESC_SPECTRA, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC,
                Title.MESSAGE_TITLE_CONSTRAINTS); // invalid title
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_DESCRIPTION_CONSTRAINTS); // invalid description
        assertParseFailure(parser, "1" + INVALID_LOCATION_DESC,
                Location.MESSAGE_LOCATION_CONSTRAINTS); // invalid location
        assertParseFailure(parser, "1" + INVALID_DATETIME_DESC,
                Datetime.MESSAGE_DATETIME_CONSTRAINTS); // invalid datetime

        // invalid description followed by valid location
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC
                + LOCATION_DESC_SPECTRA, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // valid description followed by invalid location. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DESCRIPTION_DESC_DEEPAVALI
                + INVALID_DESCRIPTION_DESC, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC + INVALID_DESCRIPTION_DESC
                + VALID_LOCATION_SPECTRA + VALID_DATETIME_SPECTRA, Title.MESSAGE_TITLE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EVENT;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_DEEPAVALI
                + LOCATION_DESC_SPECTRA + DATETIME_DESC_SPECTRA + TITLE_DESC_SPECTRA;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withTitle(VALID_TITLE_SPECTRA)
                .withDescription(VALID_DESCRIPTION_DEEPAVALI).withLocation(VALID_LOCATION_SPECTRA)
                .withDatetime(VALID_DATETIME_SPECTRA).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_DEEPAVALI + LOCATION_DESC_SPECTRA;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_DEEPAVALI)
                .withLocation(VALID_LOCATION_SPECTRA).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // title
        Index targetIndex = INDEX_THIRD_EVENT;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_SPECTRA;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withTitle(VALID_TITLE_SPECTRA).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_SPECTRA;
        descriptor = new EditEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_SPECTRA).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // location
        userInput = targetIndex.getOneBased() + LOCATION_DESC_SPECTRA;
        descriptor = new EditEventDescriptorBuilder().withLocation(VALID_LOCATION_SPECTRA).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // datetime
        userInput = targetIndex.getOneBased() + DATETIME_DESC_SPECTRA;
        descriptor = new EditEventDescriptorBuilder().withDatetime(VALID_DATETIME_SPECTRA).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_SPECTRA + DATETIME_DESC_SPECTRA
                + LOCATION_DESC_SPECTRA + DESCRIPTION_DESC_SPECTRA + DATETIME_DESC_SPECTRA + LOCATION_DESC_SPECTRA
                + DESCRIPTION_DESC_DEEPAVALI + DATETIME_DESC_DEEPAVALI + LOCATION_DESC_DEEPAVALI;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_DEEPAVALI)
                .withLocation(VALID_LOCATION_DEEPAVALI).withDatetime(VALID_DATETIME_DEEPAVALI)
                .build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + INVALID_DESCRIPTION_DESC + DESCRIPTION_DESC_DEEPAVALI;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_DEEPAVALI).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + LOCATION_DESC_DEEPAVALI + INVALID_DESCRIPTION_DESC
                + DATETIME_DESC_DEEPAVALI + DESCRIPTION_DESC_DEEPAVALI;
        descriptor = new EditEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_DEEPAVALI)
                .withLocation(VALID_LOCATION_DEEPAVALI).withDatetime(VALID_DATETIME_DEEPAVALI).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### /java/seedu/address/logic/parser/AddEventCommandParserTest.java
``` java
public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder().withTitle(VALID_TITLE_DEEPAVALI)
                .withDescription(VALID_DESCRIPTION_DEEPAVALI)
                .withLocation(VALID_LOCATION_DEEPAVALI).withDatetime(VALID_DATETIME_DEEPAVALI).build();

        // multiple titles - last title accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_SPECTRA + TITLE_DESC_DEEPAVALI
                + DESCRIPTION_DESC_DEEPAVALI
                + LOCATION_DESC_DEEPAVALI + DATETIME_DESC_DEEPAVALI, new AddEventCommand(expectedEvent));

        // multiple description - last description accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_DEEPAVALI + DESCRIPTION_DESC_SPECTRA
                + DESCRIPTION_DESC_DEEPAVALI
                + LOCATION_DESC_DEEPAVALI + DATETIME_DESC_DEEPAVALI, new AddEventCommand(expectedEvent));

        // multiple locations - last location accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_DEEPAVALI + DESCRIPTION_DESC_DEEPAVALI
                + LOCATION_DESC_SPECTRA
                + LOCATION_DESC_DEEPAVALI + DATETIME_DESC_DEEPAVALI, new AddEventCommand(expectedEvent));

        // multiple datetime - last datetime accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_DEEPAVALI + DESCRIPTION_DESC_DEEPAVALI
                + LOCATION_DESC_DEEPAVALI
                + DATETIME_DESC_SPECTRA + DATETIME_DESC_DEEPAVALI, new AddEventCommand(expectedEvent));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Event expectedEvent = new EventBuilder().withTitle(VALID_TITLE_SPECTRA)
                .withDescription(VALID_DESCRIPTION_SPECTRA)
                .withLocation(VALID_LOCATION_SPECTRA).withDatetime(VALID_DATETIME_SPECTRA).build();
        // command word
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_SPECTRA + DESCRIPTION_DESC_SPECTRA
                + LOCATION_DESC_SPECTRA + DATETIME_DESC_SPECTRA, new AddEventCommand(expectedEvent));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_TITLE_DEEPAVALI + DESCRIPTION_DESC_DEEPAVALI
                + LOCATION_DESC_DEEPAVALI + DATETIME_DESC_DEEPAVALI, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_DEEPAVALI + VALID_DESCRIPTION_DEEPAVALI
                + LOCATION_DESC_DEEPAVALI + DATETIME_DESC_DEEPAVALI, expectedMessage);

        // missing location prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_DEEPAVALI + DESCRIPTION_DESC_DEEPAVALI
                + VALID_LOCATION_DEEPAVALI + DATETIME_DESC_DEEPAVALI, expectedMessage);

        // missing datetime prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_DEEPAVALI + DESCRIPTION_DESC_DEEPAVALI
                + LOCATION_DESC_DEEPAVALI + VALID_DATETIME_DEEPAVALI, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid title
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_TITLE_DESC
                + DESCRIPTION_DESC_DEEPAVALI + LOCATION_DESC_DEEPAVALI
                + DATETIME_DESC_DEEPAVALI, Title.MESSAGE_TITLE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_DEEPAVALI
                + INVALID_DESCRIPTION_DESC + LOCATION_DESC_DEEPAVALI
                + DATETIME_DESC_DEEPAVALI, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_DEEPAVALI
                + DESCRIPTION_DESC_DEEPAVALI + INVALID_LOCATION_DESC
                + DATETIME_DESC_DEEPAVALI, Location.MESSAGE_LOCATION_CONSTRAINTS);

        // invalid datetime
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_DEEPAVALI
                + DESCRIPTION_DESC_DEEPAVALI + LOCATION_DESC_DEEPAVALI
                + INVALID_DATETIME_DESC, Datetime.MESSAGE_DATETIME_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_TITLE_DESC
                + DESCRIPTION_DESC_DEEPAVALI + LOCATION_DESC_DEEPAVALI
                + INVALID_DATETIME_DESC, Title.MESSAGE_TITLE_CONSTRAINTS);
    }
}
```
###### /java/seedu/address/logic/parser/OrderEventCommandParserTest.java
``` java
public class OrderEventCommandParserTest {

    private OrderEventCommandParser parser = new OrderEventCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OrderEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {

        OrderEventCommand expectedOrderCommand = new OrderEventCommand("TITLE");

        //same value
        assertParseSuccess(parser, "TITLE", expectedOrderCommand);

        //case insensitive
        assertParseSuccess(parser, "tItLe", expectedOrderCommand);
    }
}
```
###### /java/seedu/address/logic/parser/CalendarViewStateParserTest.java
``` java
public class CalendarViewStateParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private UserPrefs userPrefs;
    private Model model;

    @Before
    public void setup() {
        userPrefs = new UserPrefs();
        model = new ModelManager();
    }

    @Test
    public void updateViewState_nullCalendar_throwsNullPointerException() throws Exception {
        CalendarViewStateParser parser = new CalendarViewStateParser(userPrefs, model, null);
        thrown.expect(NullPointerException.class);
        parser.updateViewState("Test");
    }
}
```
###### /java/seedu/address/logic/parser/ExportCommandParserTest.java
``` java
public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_BOOK_PARAMS,
                ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsExportCommand() {
        // no leading and trailing whitespaces
        ExportCommand expectedExportCommand =
                new ExportCommand("addressbook");
        assertParseSuccess(parser, "addressbook", expectedExportCommand);
    }
}
```
###### /java/seedu/address/logic/parser/RemarkCommandParserTest.java
``` java
public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final Remark remark = new Remark("Some remark.");

        // have remarks
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString() + " " + remark;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, remark);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no remarks
        userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString();
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### /java/seedu/address/logic/parser/DeleteEventCommandParserTest.java
``` java

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteEventCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteEventCommandParserTest {

    private DeleteEventCommandParser parser = new DeleteEventCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteEventCommand() {
        assertParseSuccess(parser, "1", new DeleteEventCommand(INDEX_FIRST_EVENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/commands/AddEventCommandIntegrationTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) for {@code AddEventCommand}.
 */
public class AddEventCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(), new Account(),
                new Config());
    }

    @Test
    public void execute_newEvent_success() throws Exception {
        Event validEvent = new EventBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getEventBook(), new UserPrefs(), new
                Account(), new Config());
        expectedModel.addEvent(validEvent);

        assertCommandSuccess(prepareCommand(validEvent, model), model,
                String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), expectedModel);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event eventInList = new Event(model.getEventBook().getEventList().get(0));
        assertCommandFailure(prepareCommand(eventInList, model), model, AddEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    /**
     * Generates a new {@code AddEventCommand} which upon execution, adds {@code event} into the {@code model}.
     */
    private AddEventCommand prepareCommand(Event event, Model model) {
        AddEventCommand command = new AddEventCommand(event);
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return command;
    }
}
```
###### /java/seedu/address/logic/commands/AddEventCommandTest.java
``` java
public class AddEventCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null);
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = getAddEventCommandForEvent(validEvent, modelStub).execute();

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() throws Exception {
        AddEventCommandTest.ModelStub modelStub = new AddEventCommandTest.ModelStubThrowingDuplicateEventException();
        Event validEvent = new EventBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddEventCommand.MESSAGE_DUPLICATE_EVENT);

        getAddEventCommandForEvent(validEvent, modelStub).execute();
    }

    @Test
    public void equals() {
        Event test1 = new EventBuilder().withTitle("Test1").build();
        Event test2 = new EventBuilder().withTitle("Test2").build();
        AddEventCommand addTest1Command = new AddEventCommand(test1);
        AddEventCommand addTest2Command = new AddEventCommand(test2);

        // same object -> returns true
        assertTrue(addTest1Command.equals(addTest1Command));

        // same values -> returns true
        AddEventCommand addTest1CommandCopy = new AddEventCommand(test1);
        assertTrue(addTest1Command.equals(addTest1CommandCopy));

        // different types -> returns false
        assertFalse(addTest1Command.equals(1));

        // null -> returns false
        assertFalse(addTest1Command.equals(null));

        // different event -> returns false
        assertFalse(addTest1Command.equals(addTest2Command));
    }

    /**
     * Generates a new AddEventCommand with the details of the given event.
     */
    private AddEventCommand getAddEventCommandForEvent(Event event, Model model) {
        AddEventCommand command = new AddEventCommand(event);
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void exportAddressBook() throws FileNotFoundException, ParserConfigurationException,
                IOException, SAXException, TransformerException {

        }

        @Override
        public ReadOnlyAccount getAccount() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void transferData() {
            fail("This method should not be called.");
        }

        @Override
        public void transferDataWithDefault() {
            fail("This method should not be called.");
        }

        @Override
        public void deleteEncryptedContacts(String substring) {
            fail("This method should not be called.");
        }

        @Override
        public void releaseEncryptedContacts(String fileName) throws DataConversionException, IOException {
            fail("This method should not be called.");
        }

        @Override
        public UserPrefs getUserPrefs() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void refreshAddressBook() throws IOException, DataConversionException {
            fail("This method should not be called.");
        }

        @Override
        public void emptyPersonList(ObservableList<ReadOnlyPerson> list) throws PersonNotFoundException, IOException,
                DataConversionException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getListLength() throws IOException, DataConversionException {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void encrypt(String userId, String pass, boolean emptyFile) throws Exception {

        }

        @Override
        public void decrypt(String fileName, String pass) throws Exception {

        }

        @Override
        public void encryptPublic(boolean isLockCommand) throws CommandException {

        }

        @Override
        public void saveToEncryptedFile() {

        }

        @Override
        public void orderList(String parameter) throws UnrecognisedParameterException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredListToShowAll() {
            fail("This method should not be called");
        }

        @Override
        public ObservableList<Group> getGroupList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ArrayList<ArrayList<String>> getCommands() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public String getAliasForCommand(String commandName) {
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void resetEventData(ReadOnlyEventBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyEventBook getEventBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void exportEventBook() throws FileNotFoundException, ParserConfigurationException,
                IOException, SAXException, TransformerException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
            fail("This method should not be called.");
        }

        @Override
        public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent) throws DuplicateEventException,
                EventNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyEvent> getFilteredEventList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate) {
            fail("This method should not be called");
        }

        @Override
        public void orderEventList(String parameter)
                throws seedu.address.model.event.exceptions.UnrecognisedParameterException {
            fail("This method should not be called.");
        }

        @Override
        public void setAlias(String command, String alias) throws UnknownCommandException, DuplicateAliasException {
            fail("This method should not be called.");
        }

        @Override
        public void persistUserAccount(ReadOnlyUser user) throws DuplicateUserException {
            fail("This method should not be called.");
        }

        @Override
        public User getUserFromIdAndPassword(String userName, String password) throws UserNotFoundException {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteUser(String userName, String saltedPasswordHex) throws UserNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public String retrieveSaltFromStorage(String userId) {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void setUserStorage(Storage userStorage) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateEventException when trying to add a event.
     */
    private class ModelStubThrowingDuplicateEventException extends AddEventCommandTest.ModelStub {
        @Override
        public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
            throw new DuplicateEventException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ReadOnlyEventBook getEventBook() {
            return new EventBook();
        }
    }

    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingEventAdded extends AddEventCommandTest.ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
            eventsAdded.add(new Event(event));
        }

        @Override
        public ReadOnlyEventBook getEventBook() {
            return new EventBook();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### /java/seedu/address/logic/commands/EditEventDescriptorTest.java
``` java
public class EditEventDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditEventCommand.EditEventDescriptor descriptorWithSameValues =
                new EditEventCommand.EditEventDescriptor(DESC_SPECTRA);
        assertTrue(DESC_SPECTRA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_SPECTRA.equals(DESC_SPECTRA));

        // null -> returns false
        assertFalse(DESC_SPECTRA.equals(null));

        // different types -> returns false
        assertFalse(DESC_SPECTRA.equals(5));

        // different values -> returns false
        assertFalse(DESC_SPECTRA.equals(DESC_DEEPAVALI));

        // different title -> returns false
        EditEventCommand.EditEventDescriptor editedSpectra =
                new EditEventDescriptorBuilder(DESC_SPECTRA).withTitle(VALID_TITLE_DEEPAVALI).build();
        assertFalse(DESC_SPECTRA.equals(editedSpectra));

        // different description -> returns false
        editedSpectra =
                new EditEventDescriptorBuilder(DESC_SPECTRA).withDescription(VALID_DESCRIPTION_DEEPAVALI).build();
        assertFalse(DESC_SPECTRA.equals(editedSpectra));

        // different email -> returns false
        editedSpectra = new EditEventDescriptorBuilder(DESC_SPECTRA).withLocation(VALID_LOCATION_DEEPAVALI).build();
        assertFalse(DESC_SPECTRA.equals(editedSpectra));

        // different address -> returns false
        editedSpectra = new EditEventDescriptorBuilder(DESC_SPECTRA).withDatetime(VALID_DATETIME_DEEPAVALI).build();
        assertFalse(DESC_SPECTRA.equals(editedSpectra));
    }
}
```
###### /java/seedu/address/logic/commands/SelectEventCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) for {@code SelectEventCommand}.
 */
public class SelectEventCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(), new Account(),
                new Config());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastEventIndex = Index.fromOneBased(model.getFilteredEventList().size());

        assertExecutionSuccess(INDEX_FIRST_EVENT);
        assertExecutionSuccess(INDEX_THIRD_EVENT);
        assertExecutionSuccess(lastEventIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstEventOnly(model);

        assertExecutionSuccess(INDEX_FIRST_EVENT);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstEventOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getEventBook().getEventList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectEventCommand selectFirstCommand = new SelectEventCommand(INDEX_FIRST_EVENT);
        SelectEventCommand selectSecondCommand = new SelectEventCommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectEventCommand selectFirstCommandCopy = new SelectEventCommand(INDEX_FIRST_EVENT);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectEventCommand} with the given {@code index},
     * and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectEventCommand selectEventCommand = prepareCommand(index);

        try {
            CommandResult commandResult = selectEventCommand.execute();
            assertEquals(String.format(SelectEventCommand.MESSAGE_SELECT_EVENT_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        //JumpToListRequestEvent lastEvent =
        // (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(index.getZeroBased()));
    }

    /**
     * Executes a {@code SelectEventCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectEventCommand selectEventCommand = prepareCommand(index);

        try {
            selectEventCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SelectEventCommand} with parameters {@code index}.
     */
    private SelectEventCommand prepareCommand(Index index) {
        SelectEventCommand selectEventCommand = new SelectEventCommand(index);
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        selectEventCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return selectEventCommand;
    }
}
```
###### /java/seedu/address/logic/commands/FindEventCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) for {@code FindEventCommand}.
 */
public class FindEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(), new
            Account(), new Config());

    @Test
    public void equals() {
        TitleContainsKeywordsPredicate firstPredicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("first"));
        TitleContainsKeywordsPredicate secondPredicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("second"));

        FindEventCommand findFirstCommand = new FindEventCommand(firstPredicate);
        FindEventCommand findSecondCommand = new FindEventCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindEventCommand findFirstCommandCopy = new FindEventCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        FindEventCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 3);
        FindEventCommand command = prepareCommand("Spectra Deepavali Henna");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(SPECTRA, DEEPAVALI, HENNA));
    }

    /**
     * Parses {@code userInput} into a {@code FindEventCommand}.
     */
    private FindEventCommand prepareCommand(String userInput) {
        FindEventCommand command =
                new FindEventCommand(new TitleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyEvent>} is equal to {@code expectedList}<br>
     * - the {@code EventBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindEventCommand command, String expectedMessage,
                                      List<ReadOnlyEvent> expectedList) {
        EventBook expectedEventBook = new EventBook(model.getEventBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredEventList());
        assertEquals(expectedEventBook.toString(), model.getEventBook().toString());
    }
}
```
###### /java/seedu/address/logic/commands/EditEventCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditEventCommand.
 */
public class EditEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(), new
            Account(), new Config());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Event editedEvent = new EventBuilder().build();
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EditEventCommand editEventCommand = prepareCommand(INDEX_FIRST_EVENT, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs(), new Account(), new Config());

        expectedModel.updateEvent(expectedModel.getFilteredEventList().get(0), editedEvent);

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastEvent = Index.fromOneBased(model.getFilteredEventList().size());
        ReadOnlyEvent lastEvent = model.getFilteredEventList().get(indexLastEvent.getZeroBased());

        EventBuilder eventInList = new EventBuilder(lastEvent);
        Event editedEvent = eventInList.withTitle(VALID_TITLE_DEEPAVALI).withDescription(VALID_DESCRIPTION_DEEPAVALI)
                .withLocation(VALID_LOCATION_DEEPAVALI).build();

        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withTitle(VALID_TITLE_DEEPAVALI)
                .withDescription(VALID_DESCRIPTION_DEEPAVALI).withLocation(VALID_LOCATION_DEEPAVALI).build();
        EditEventCommand editEventCommand = prepareCommand(indexLastEvent, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs(), new Account(), new Config());
        expectedModel.updateEvent(expectedModel.getFilteredEventList().get(indexLastEvent.getZeroBased()), editedEvent);

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditEventCommand editEventCommand = prepareCommand(INDEX_FIRST_EVENT,
                new EditEventCommand.EditEventDescriptor());
        ReadOnlyEvent editedEvent = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs(), new Account(), new Config());

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstEventOnly(model);

        ReadOnlyEvent eventInFilteredList = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event editedEvent = new EventBuilder(eventInFilteredList).withTitle(VALID_TITLE_DEEPAVALI).build();
        EditEventCommand editEventCommand = prepareCommand(INDEX_FIRST_EVENT,
                new EditEventDescriptorBuilder().withTitle(VALID_TITLE_DEEPAVALI).build());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs(), new Account(), new Config());
        expectedModel.updateEvent(expectedModel.getFilteredEventList().get(0), editedEvent);

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateEventUnfilteredList_failure() {
        Event firstEvent = new Event(model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()));
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(firstEvent).build();
        EditEventCommand editEventCommand = prepareCommand(INDEX_SECOND_EVENT, descriptor);

        assertCommandFailure(editEventCommand, model, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_duplicateEventFilteredList_failure() {
        showFirstEventOnly(model);

        // edit event in filtered list into a duplicate in event book
        ReadOnlyEvent eventInList = model.getEventBook().getEventList().get(INDEX_SECOND_EVENT.getZeroBased());
        EditEventCommand editEventCommand = prepareCommand(INDEX_FIRST_EVENT,
                new EditEventDescriptorBuilder(eventInList).build());

        assertCommandFailure(editEventCommand, model, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withTitle(VALID_TITLE_DEEPAVALI).build();
        EditEventCommand editEventCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of event book
     */
    @Test
    public void execute_invalidEventIndexFilteredList_failure() {
        showFirstEventOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of event book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEventBook().getEventList().size());

        EditEventCommand editEventCommand = prepareCommand(outOfBoundIndex,
                new EditEventDescriptorBuilder().withTitle(VALID_TITLE_DEEPAVALI).build());

        assertCommandFailure(editEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditEventCommand standardCommand = new EditEventCommand(INDEX_FIRST_EVENT, DESC_SPECTRA);

        // same values -> returns true
        EditEventCommand.EditEventDescriptor copyDescriptor = new EditEventCommand.EditEventDescriptor(DESC_SPECTRA);
        EditEventCommand commandWithSameValues = new EditEventCommand(INDEX_FIRST_EVENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_SECOND_EVENT, DESC_SPECTRA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_FIRST_EVENT, DESC_DEEPAVALI)));
    }

    /**
     * Returns an {@code EditEventCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditEventCommand prepareCommand(Index index, EditEventCommand.EditEventDescriptor descriptor) {
        EditEventCommand editEventCommand = new EditEventCommand(index, descriptor);
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        editEventCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return editEventCommand;
    }
}
```
###### /java/seedu/address/logic/commands/OrderEventCommandTest.java
``` java
public class OrderEventCommandTest {
    private Model model;
    private Model expectedModel;

    private String firstParameter;
    private String secondParameter;
    private String thirdParameter;

    @Before
    public void setUp() {
        firstParameter = "TITLE";
        secondParameter = "LOCATION";
        thirdParameter = "DATETIME";

        model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(), new Account(),
                new Config());
        expectedModel = new ModelManager(model.getAddressBook(), model.getEventBook(), new UserPrefs(), new Account(),
                new Config());

    }

    //Interaction with model
    @Test
    public void execute_listIsOrdered_showsEverything() {
        OrderEventCommand command = prepareCommand(firstParameter);
        assertCommandSuccess(command, model, OrderEventCommand.MESSAGE_SORT_SUCCESS
                + firstParameter, expectedModel);
    }

    @Test
    public void execute_zeroParameter_listNotSorted() throws CommandException {
        OrderEventCommand command = prepareCommand(" ");
        assertOrderSuccess(command, OrderEventCommand.MESSAGE_SORT_WRONG_PARAMETER,
                Arrays.asList(SPECTRA, DEEPAVALI, HENNA, WINE));
    }

    @Test
    public void execute_titleParameter_listSorted() throws CommandException {
        OrderEventCommand command = prepareCommand(firstParameter);
        assertOrderSuccess(command, OrderEventCommand.MESSAGE_SORT_SUCCESS + firstParameter,
                Arrays.asList(DEEPAVALI, HENNA, SPECTRA, WINE));
    }


    @Test
    public void execute_locationParameter_listSorted() throws CommandException {
        OrderEventCommand command = prepareCommand(secondParameter);
        assertOrderSuccess(command, OrderEventCommand.MESSAGE_SORT_SUCCESS + secondParameter,
                Arrays.asList(WINE, DEEPAVALI, SPECTRA, HENNA));
    }

    @Test
    public void execute_datetimeParameter_listSorted() throws CommandException {
        OrderEventCommand command = prepareCommand(thirdParameter);
        assertOrderSuccess(command, OrderEventCommand.MESSAGE_SORT_SUCCESS + thirdParameter,
                Arrays.asList(DEEPAVALI, WINE, HENNA, SPECTRA));
    }

    @Test
    public void equals() {
        final OrderEventCommand standardCommand = new OrderEventCommand(firstParameter);

        // save values -> returns true
        OrderEventCommand commandWithSameValues = new OrderEventCommand("TITLE");
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        //null -> returns false
        assertFalse(standardCommand.equals(null));

        // different parameter -> returns false
        assertFalse(standardCommand.equals(new OrderEventCommand(thirdParameter)));

        //different order of parameter -> return false
        assertFalse(standardCommand.equals(new OrderEventCommand(secondParameter)));

    }

    /**
     * Generates a new OrderCommand
     */
    private OrderEventCommand prepareCommand(String parameter) {
        OrderEventCommand orderEventCommand = new OrderEventCommand(parameter);
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        orderEventCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return orderEventCommand;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyEvent>} is equal to {@code expectedList}<br>
     */
    private void assertOrderSuccess(OrderEventCommand command, String expectedMessage,
                                    List<ReadOnlyEvent> expectedList) throws CommandException {
        CommandResult commandResult = command.executeUndoableCommand();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredEventList());
    }
}
```
###### /java/seedu/address/logic/commands/DeleteEventCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteEventCommand}.
 */
public class DeleteEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(),
            new Account(), new Config());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyEvent eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteEventCommand deleteEventCommand = prepareCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getEventBook(), new UserPrefs(),
                new Account(), new Config());
        ReadOnlyEvent expectedEventToDelete = expectedModel.getFilteredEventList()
                .get(INDEX_FIRST_EVENT.getZeroBased());
        expectedModel.deleteEvent(expectedEventToDelete);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteEventCommand deleteEventCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstEventOnly(model);

        ReadOnlyEvent eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteEventCommand deleteEventCommand = prepareCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getEventBook(), new UserPrefs(),
                new Account(), new Config());
        ReadOnlyEvent expectedEventToDelete = expectedModel.getFilteredEventList()
                .get(INDEX_FIRST_EVENT.getZeroBased());
        expectedModel.deleteEvent(expectedEventToDelete);
        showNoEvent(expectedModel);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstEventOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of event book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEventBook().getEventList().size());

        DeleteEventCommand deleteEventCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteEventCommand deleteFirstEventCommand = new DeleteEventCommand(INDEX_FIRST_EVENT);
        DeleteEventCommand deleteSecondEventCommand = new DeleteEventCommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(deleteFirstEventCommand.equals(deleteFirstEventCommand));

        // same values -> returns true
        DeleteEventCommand deleteFirstEventCommandCopy = new DeleteEventCommand(INDEX_FIRST_EVENT);
        assertTrue(deleteFirstEventCommand.equals(deleteFirstEventCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstEventCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstEventCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstEventCommand.equals(deleteSecondEventCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteEventCommand prepareCommand(Index index) {
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(index);
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        deleteEventCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return deleteEventCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEvent(Model model) {
        model.updateFilteredEventList(p -> false);

        assert model.getFilteredEventList().isEmpty();
    }
}
```
###### /java/seedu/address/logic/commands/RemarkCommandTest.java
``` java
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(), new
            Account(), new Config());

    //
    @Test
    public void execute_addRemark_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("Some remark").build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().value);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs(), new Account(), new Config());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemark_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setRemark(new Remark(""));

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs(), new Account(), new Config());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withRemark("Some remark").build();
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().value);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs(), new Account(), new Config());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        //ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));

        // save values -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        //null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> return false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
    private RemarkCommand prepareCommand(Index index, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, new Remark(remark));
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return remarkCommand;
    }
}
```
###### /java/seedu/address/logic/commands/ListEventCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListEventCommand.
 */
public class ListEventCommandTest {

    private Model model;
    private Model expectedModel;
    private ListEventCommand listEventCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(), new Account(),
                new Config());
        expectedModel = new ModelManager(model.getAddressBook(), model.getEventBook(), new UserPrefs(), new Account(),
                new Config());

        listEventCommand = new ListEventCommand();
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        listEventCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listEventCommand, model, ListEventCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstEventOnly(model);
        assertCommandSuccess(listEventCommand, model, ListEventCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### /java/seedu/address/logic/commands/ExportCommandTest.java
``` java
public class ExportCommandTest {

    private ExportCommand exportCommandForAddressBook;
    private ExportCommand exportCommandForEventBook;
    private CommandHistory history;

    @Before
    public void setUp() {

        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Storage userStorage = new StorageManager(
                new XmlAddressBookStorage(userPrefs.getAddressBookFilePath(), userPrefs.getAddressbookExportedPath(),
                        userPrefs.getAddressbookHeader()),
                new XmlEventBookStorage(userPrefs.getEventBookFilePath(), userPrefs.getEventbookExportedPath(),
                        userPrefs.getEventbookHeader()),
                new JsonUserPrefsStorage(config.getUserPrefsFilePath()),
                new XmlAccountStorage(userPrefs.getAccountFilePath()));
        Model model = new ModelManager();
        model.setUserStorage(userStorage);
        history = new CommandHistory();
        exportCommandForAddressBook = new ExportCommand("addressbook");
        exportCommandForEventBook = new ExportCommand("eventbook");


        Logic logic = null;
        exportCommandForAddressBook.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        exportCommandForEventBook.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
    }

    @Test
    public void execute_exportAddressBook_success() throws CommandException, DuplicateUserException {
        CommandResult result = exportCommandForAddressBook.execute();
        assertEquals(MESSAGE_EXPORT_BOOK_SUCCESS, result.feedbackToUser);
    }

    @Test
    public void execute_exportEventBook_success() throws CommandException, DuplicateUserException {
        CommandResult result = exportCommandForEventBook.execute();
        assertEquals(MESSAGE_EXPORT_BOOK_SUCCESS, result.feedbackToUser);
    }
}
```
###### /java/seedu/address/storage/XmlFileStorageTest.java
``` java
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
```
###### /java/seedu/address/storage/StorageManagerTest.java
``` java
    @Test
    public void eventBookReadSave() throws Exception {

        EventBook original = getTypicalEventBook();
        storageManager.saveEventBook(original);
        ReadOnlyEventBook retrieved = storageManager.readEventBook().get();
        assertEquals(original, new EventBook(retrieved));
    }

```
###### /java/seedu/address/storage/StorageManagerTest.java
``` java
    @Test
    public void getEventBookFilePath() {
        assertNotNull(storageManager.getEventBookFilePath());
    }

```
###### /java/seedu/address/storage/StorageManagerTest.java
``` java
    @Test
    public void handleEventBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy",
                "dummy", "dummy"),
                new XmlEventBookStorageExceptionThrowingStub("dummy", "dummy", "dummy"),
                new JsonUserPrefsStorage("dummy"), new XmlAccountStorage("dummy"));
        storage.handleEventBookChangedEvent(new EventBookChangedEvent(new EventBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAddressBookStorageExceptionThrowingStub extends XmlAddressBookStorage {

        public XmlAddressBookStorageExceptionThrowingStub(String filePath, String exportPath, String header) {
            super(filePath, exportPath, header);
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

```
###### /java/seedu/address/storage/StorageManagerTest.java
``` java
    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlEventBookStorageExceptionThrowingStub extends XmlEventBookStorage {

        public XmlEventBookStorageExceptionThrowingStub(String filePath, String exportPath, String header) {
            super(filePath, exportPath, header);
        }

        @Override
        public void saveEventBook(ReadOnlyEventBook eventBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }
}
```
###### /java/seedu/address/storage/XmlEventBookStorageTest.java
``` java
public class XmlEventBookStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil
            .getPath("./src/test/data/XmlEventBookStorageTest/");

    private static final String HEADER = "Title,Description,Location,Datetime";
    private static final String EXPORT_DATA = "TempEventBook.csv";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readEventBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readEventBook(null);
    }

    private java.util.Optional<ReadOnlyEventBook> readEventBook(String filePath) throws Exception {
        return new XmlEventBookStorage(filePath, TEST_DATA_FOLDER + EXPORT_DATA, HEADER)
                .readEventBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEventBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readEventBook("NotXmlFormatEventBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveEventBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempEventBook.xml";
        String exportPath = testFolder.getRoot().getPath() + "TempEventBook.csv";
        EventBook original = getTypicalEventBook();
        XmlEventBookStorage xmlEventBookStorage = new XmlEventBookStorage(filePath, exportPath, HEADER);

        //Save in new file and read back
        xmlEventBookStorage.saveEventBook(original, filePath);
        ReadOnlyEventBook readBack = xmlEventBookStorage.readEventBook(filePath).get();
        assertEquals(original, new EventBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addEvent(new Event(NETWORK));
        ReadOnlyEvent eventToRemoved = original.getEventList().get(0);
        original.removeEvent(eventToRemoved);
        xmlEventBookStorage.saveEventBook(original, filePath);
        readBack = xmlEventBookStorage.readEventBook(filePath).get();
        assertEquals(original, new EventBook(readBack));

        //Save and read without specifying file path
        original.addEvent(new Event(SECURITY));
        xmlEventBookStorage.saveEventBook(original); //file path not specified
        readBack = xmlEventBookStorage.readEventBook().get(); //file path not specified
        assertEquals(original, new EventBook(readBack));

    }

    @Test
    public void saveEventBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveEventBook(null, "SomeFile.xml");
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        XmlSerializableEventBook eventBook = new XmlSerializableEventBook();
        thrown.expect(UnsupportedOperationException.class);
        eventBook.getEventList().remove(0);
    }

    /**
     * Saves {@code eventBook} at the specified {@code filePath}.
     */
    private void saveEventBook(ReadOnlyEventBook eventBook, String filePath) {
        try {
            new XmlEventBookStorage(filePath, TEST_DATA_FOLDER + EXPORT_DATA, HEADER)
                    .saveEventBook(eventBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveEventBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveEventBook(new EventBook(), null);
    }
}
```
###### /java/seedu/address/model/EventBookTest.java
``` java
public class EventBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EventBook eventBook = new EventBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), eventBook.getEventList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyEventBook_replacesData() {
        EventBook newData = getTypicalEventBook();
        eventBook.resetData(newData);
        assertEquals(newData, eventBook);
    }

    @Test
    public void resetData_withDuplicateEvents_throwsAssertionError() {
        // Repeat SPECTRA twice
        List<Event> newEvents = Arrays.asList(new Event(SPECTRA), new Event(SPECTRA));
        EventBookStub newData = new EventBookStub(newEvents);

        thrown.expect(AssertionError.class);
        eventBook.resetData(newData);
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        eventBook.getEventList().remove(0);
    }

    /**
     * A stub ReadOnlyEventBook whose events lists can violate interface constraints.
     */
    private static class EventBookStub implements ReadOnlyEventBook {
        private final ObservableList<ReadOnlyEvent> events = FXCollections.observableArrayList();

        EventBookStub(Collection<? extends ReadOnlyEvent> events) {
            this.events.setAll(events);
        }

        @Override
        public ObservableList<ReadOnlyEvent> getEventList() {
            return events;
        }
    }
}
```
###### /java/seedu/address/model/UniqueEventListTest.java
``` java
public class UniqueEventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueEventList.asObservableList().remove(0);
    }
}
```
###### /java/seedu/address/model/ModelManagerTest.java
``` java
    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();

        EventBook eventBook = new EventBookBuilder().withEvent(SPECTRA).withEvent(DEEPAVALI).build();
        EventBook differentEventBook = new EventBook();

        UserPrefs userPrefs = new UserPrefs();

        Account account = new Account();

        Config config = new Config();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(addressBook, eventBook, userPrefs, account, config);
        ModelManager modelManagerCopy = new ModelManager(addressBook, eventBook, userPrefs, account, config);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, eventBook, userPrefs,
                account, config)));

        // different Persons filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new ContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, eventBook, userPrefs, account, config)));

        // different events filteredList -> returns false
        String keywordForEvents = SPECTRA.getTitle().value;
        modelManager.updateFilteredEventList(new TitleContainsKeywordsPredicate(Arrays.asList(keywordForEvents)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, eventBook, userPrefs, account, config)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManager.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookName("differentName");
        differentUserPrefs.setEventBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, eventBook, differentUserPrefs, account, config)));
    }
}
```
###### /java/seedu/address/model/event/LocationTest.java
``` java
public class LocationTest {

    @Test
    public void isValidLocation() {
        // invalid location
        assertFalse(Location.isValidLocation("")); // empty string
        assertFalse(Location.isValidLocation(" ")); // spaces only
        assertFalse(Location.isValidLocation("~!?>")); //Symbols cannot be at the start

        // valid location
        assertTrue(Location.isValidLocation("sentosa")); // alphabets only
        assertTrue(Location.isValidLocation("123456789")); // numbers only
        assertTrue(Location.isValidLocation(
                "This is a testing event to test to accept long location")); // long description
        assertTrue(Location.isValidLocation("Hello World with Test")); // with capital letters
        assertTrue(Location.isValidLocation("5top Test")); // alphanumeric characters
    }
}
```
###### /java/seedu/address/model/event/DescriptionTest.java
``` java
public class DescriptionTest {

    @Test
    public void isValidDescription() {
        // invalid description
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only
        assertFalse(Description.isValidDescription("~!?>")); //Symbols cannot be at the start

        // valid description
        assertTrue(Description.isValidDescription("IT Fair 2017 with many offers")); // alphabets only
        assertTrue(Description.isValidDescription("123456789")); // numbers only
        assertTrue(Description.isValidDescription(
                "This is a testing event to test to accept long description")); // long description
        assertTrue(Description.isValidDescription("Hello World with Test")); // with capital letters
        assertTrue(Description.isValidDescription("5top Test")); // alphanumeric characters
    }
}
```
###### /java/seedu/address/model/event/DatetimeTest.java
``` java
public class DatetimeTest {

    @Test
    public void isValidDate() {
        // invalid datetime
        assertFalse(Datetime.isValidDatetime("")); // empty string
        assertFalse(Datetime.isValidDatetime(" ")); // spaces only
        assertFalse(Datetime.isValidDatetime("123456789")); // numbers only
        assertFalse(Datetime.isValidDatetime("shakjhsa")); // characters only
        assertFalse(Datetime.isValidDatetime("test123")); // numbers and characters
        assertFalse(Datetime.isValidDatetime("1-09-2017 2010")); // invalid date format
        assertFalse(Datetime.isValidDatetime("90-09-2017 2010")); // invalid day
        assertFalse(Datetime.isValidDatetime("02-13-2017 2010")); // invalid month
        assertFalse(Datetime.isValidDatetime("02-09-17 2010")); // invalid year
        assertFalse(Datetime.isValidDatetime("02-09-2017 2510")); // invalid hour
        assertFalse(Datetime.isValidDatetime("02-09-2017 2065")); // invalid minute

        // valid datetime
        assertTrue(Datetime.isValidDatetime("02-09-2017 2015"));
    }
}
```
###### /java/seedu/address/model/event/TitleTest.java
``` java
public class TitleTest {

    @Test
    public void isValidTitle() {
        // invalid title
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only
        assertFalse(Title.isValidTitle("~!?>")); //Symbols cannot be at the start

        // valid title
        assertTrue(Title.isValidTitle("Computing fair")); // alphabets only
        assertTrue(Title.isValidTitle("13580")); // numbers only
        assertTrue(Title.isValidTitle("Chinese New Year Hari Raya Deepavali")); // long title
        assertTrue(Title.isValidTitle("Sentosa Event")); // with capital letters
        assertTrue(Title.isValidTitle("Jay Chou 10th World Tour")); // alphanumeric characters
    }
}
```
###### /java/seedu/address/testutil/EventBuilder.java
``` java

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_TITLE = "Halloween Horror Night";
    public static final String DEFAULT_DESCRIPTION = "Terrifying Night";
    public static final String DEFAULT_LOCATION = "Univsersal Studio";
    public static final String DEFAULT_DATETIME = "13-10-2017 2359";

    private Event event;

    public EventBuilder() {
        try {
            Title defaultTitle = new Title(DEFAULT_TITLE);
            Description defaultDescription = new Description(DEFAULT_DESCRIPTION);
            Location defaultLocation = new Location(DEFAULT_LOCATION);
            Datetime defaultDatetime = new Datetime(DEFAULT_DATETIME);
            this.event = new Event(defaultTitle, defaultDescription, defaultLocation, defaultDatetime);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default event's values are invalid.");
        }
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(ReadOnlyEvent eventToCopy) {
        this.event = new Event(eventToCopy);
    }

    /**
     * Sets the {@code title} of the {@code Event} that we are building.
     */
    public EventBuilder withTitle(String title) {
        try {
            this.event.setTitle(new Title(title));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("title is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        try {
            this.event.setDescription(new Description(description));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("description is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code location} of the {@code Event} that we are building.
     */
    public EventBuilder withLocation(String location) {
        try {
            this.event.setLocation(new Location(location));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("location is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code datetime} of the {@code Event} that we are building.
     */
    public EventBuilder withDatetime(String datetime) {
        try {
            this.event.setDatetime(new Datetime(datetime));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("datetime is expected to be unique.");
        }
        return this;
    }

    public Event build() {
        return this.event;
    }
}
```
###### /java/seedu/address/testutil/EventBookBuilder.java
``` java

/**
 * A utility class to help with building Eventbook objects.
 * Example usage: <br>
 * {@code EventBook ab = new EventBookBuilder().withEvent(Sentosa).build();}
 */
public class EventBookBuilder {

    private EventBook eventBook;

    public EventBookBuilder() {
        eventBook = new EventBook();
    }

    public EventBookBuilder(EventBook eventBook) {
        this.eventBook = eventBook;
    }

    /**
     * Adds a new {@code Event} to the {@code EventBook} that we are building.
     */
    public EventBookBuilder withEvent(ReadOnlyEvent event) {
        try {
            eventBook.addEvent(event);
        } catch (DuplicateEventException dpe) {
            throw new IllegalArgumentException("event is expected to be unique.");
        }
        return this;
    }


    public EventBook build() {
        return eventBook;
    }
}
```
###### /java/seedu/address/testutil/TypicalEvents.java
``` java

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final ReadOnlyEvent SPECTRA = new EventBuilder().withTitle("Spectra")
            .withDescription("Light and water show").withLocation("Marina Bay Sands")
            .withDatetime("01-09-2017 1900").build();
    public static final ReadOnlyEvent DEEPAVALI = new EventBuilder().withTitle("Deepavali")
            .withDescription("Deepavali Celebrations 2017").withLocation("Little India")
            .withDatetime("12-11-2017 1900").build();
    public static final ReadOnlyEvent HENNA = new EventBuilder().withTitle("Henna")
            .withDescription("Henna Workshop").withLocation("Orchard Gateway")
            .withDatetime("18-10-2017 1500").build();
    public static final ReadOnlyEvent WINE = new EventBuilder().withTitle("Wine Fest")
            .withDescription("Singapore Wine Fiesta 2017").withLocation("Clifford Square")
            .withDatetime("26-10-2017 1500").build();

    // Manually added
    public static final ReadOnlyEvent NETWORK = new EventBuilder().withTitle("Network Talk")
            .withDescription("Networking meeting session").withLocation("IMDA").withDatetime("13-05-2017 1300").build();
    public static final ReadOnlyEvent SECURITY = new EventBuilder().withTitle("Security Talk")
            .withDescription("Security meeting session").withLocation("CSIT").withDatetime("26-10-2017 1300").build();

    private TypicalEvents() {
    } // prevents instantiation

    /**
     * Returns an {@code EventBook} with all the typical events.
     */
    public static EventBook getTypicalEventBook() {
        EventBook ab = new EventBook();
        for (ReadOnlyEvent event : getTypicalEvents()) {
            try {
                ab.addEvent(event);
            } catch (DuplicateEventException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyEvent> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(SPECTRA, DEEPAVALI, HENNA, WINE));
    }
}
```

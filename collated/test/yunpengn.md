# yunpengn
###### \java\seedu\address\commons\events\model\TagColorChangedEventTest.java
``` java
public class TagColorChangedEventTest {
    @Test
    public void createEvent_success() throws Exception {
        BaseEvent event = new TagColorChangedEvent(new Tag(VALID_TAG_HUSBAND), "7db9a1");
        assertEquals("The color of tag [husband] has been changed to 7db9a1.", event.toString());
    }
}
```
###### \java\seedu\address\commons\events\ui\SwitchToContactsListEventTest.java
``` java
public class SwitchToContactsListEventTest {
    @Test
    public void createEvent_success() throws Exception {
        BaseEvent event = new SwitchToContactsListEvent();
        assertEquals("SwitchToContactsListEvent", event.toString());
    }
}
```
###### \java\seedu\address\commons\events\ui\SwitchToEventsListEventTest.java
``` java
public class SwitchToEventsListEventTest {
    @Test
    public void createEvent_success() throws Exception {
        BaseEvent event = new SwitchToEventsListEvent();
        assertEquals("SwitchToEventsListEvent", event.toString());
    }
}
```
###### \java\seedu\address\commons\exceptions\InvalidFileExtensionExceptionTest.java
``` java
public class InvalidFileExtensionExceptionTest {
    @Test
    public void createException_getMessage_checkCorrectness() throws Exception {
        Exception exception = new InvalidFileExtensionException(Avatar.INVALID_PATH_MESSAGE);
        assertEquals(Avatar.INVALID_PATH_MESSAGE, exception.getMessage());
    }

    @Test
    public void createException_emptyMessage_checkCorrectness() throws Exception {
        Exception exception = new InvalidFileExtensionException();
        assertEquals(null, exception.getMessage());
    }
}
```
###### \java\seedu\address\commons\exceptions\InvalidFilePathExceptionTest.java
``` java
public class InvalidFilePathExceptionTest {
    @Test
    public void createException_getMessage_checkCorrectness() throws Exception {
        Exception exception = new InvalidFilePathException(Avatar.INVALID_PATH_MESSAGE);
        assertEquals(Avatar.INVALID_PATH_MESSAGE, exception.getMessage());
    }

    @Test
    public void createException_emptyMessage_checkCorrectness() throws Exception {
        Exception exception = new InvalidFilePathException();
        assertEquals(null, exception.getMessage());
    }
}
```
###### \java\seedu\address\commons\util\UrlUtilTest.java
``` java
public class UrlUtilTest {
    private ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseUrlString_success_checkCorrectness() throws Exception {
        URL url = UrlUtil.parseUrlString(VALID_URL);

        assertEquals("https", url.getProtocol());
        assertEquals("www.google.com.sg", url.getAuthority());
        assertEquals("/contacts", url.getPath());
        assertEquals("day=monday", url.getQuery());
    }

    @Test
    public void parseUrlString_fail_expectException() throws Exception {
        thrown.expect(MalformedURLException.class);
        UrlUtil.parseUrlString(INVALID_URL_COMMA);
    }

    @Test
    public void fetchUrlParameters_success_checkCorrectness() throws Exception {
        Map<String, String> parameters = UrlUtil.fetchUrlParameters(new URL(VALID_URL));

        assertEquals(1, parameters.size());
        assertTrue(parameters.containsKey("day"));
        assertTrue(parameters.containsValue("monday"));
    }

    @Test
    public void fetchUrlParameterKeys_success_checkCorrectness() throws Exception {
        Set<String> keys = UrlUtil.fetchUrlParameterKeys(new URL(VALID_URL));

        assertEquals(1, keys.size());
        assertTrue(keys.contains("day"));
    }

    @Test
    public void urlDecode_success_checkCorrectness() throws Exception {
        assertEquals(VALID_URL, UrlUtil.urlDecode(VALID_URL_ENCODED));
    }
}
```
###### \java\seedu\address\logic\commands\configs\AddPropertyCommandTest.java
``` java
public class AddPropertyCommandTest {
    private ConfigCommand successCommand;

    private final String shortName = "b";
    private final String shortNameAlter = "b1";
    private final String fullName = "birthday";
    private final String message = "something";
    private final String validRegex = "[^\\s].*";
    private final String invalidRegex = "*asf";

    @Before
    public void setUp() {
        successCommand = new AddPropertyCommand(VALID_NEW_PROPERTY,
                shortName, fullName, message, validRegex);
        successCommand.setData(new AddPropertyModelStub(), new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_addNewProperty_success() throws Exception {
        int propertyCountBefore = PropertyManager.getAllShortNames().size();
        successCommand.execute();
        int propertyCountAfter = PropertyManager.getAllShortNames().size();

        assertEquals(1, propertyCountAfter - propertyCountBefore);
    }

    @Test
    public void execute_addSamePropertyAgain_expectDuplicateException() {
        try {
            // Execute the command (add the same property) again, will get DuplicatePropertyException.
            successCommand.execute();
        } catch (CommandException e) {
            assertEquals(MESSAGE_DUPLICATE_PROPERTY, e.getMessage());
        }
    }

    @Test
    public void execute_invalidRegex_expectRegexException() {
        ConfigCommand invalidRegexCommand = new AddPropertyCommand(VALID_NEW_PROPERTY,
                shortNameAlter, fullName, message, invalidRegex);
        invalidRegexCommand.setData(new AddPropertyModelStub(), new CommandHistory(), new UndoRedoStack());

        try {
            // Execute the command (add the same property) again, will get DuplicatePropertyException.
            invalidRegexCommand.execute();
        } catch (CommandException e) {
            assertEquals(MESSAGE_INVALID_REGEX, e.getMessage());
        }
    }

    @Test
    public void equal_twoSameCommands_returnTrue() {
        ConfigCommand command1 = new AddPropertyCommand(VALID_NEW_PROPERTY,
                shortName, fullName, message, validRegex);
        ConfigCommand command2 = new AddPropertyCommand(VALID_NEW_PROPERTY,
                shortName, fullName, message, validRegex);

        assertEquals(command1, command2);
    }

    private class AddPropertyModelStub extends ModelStub {
        @Override
        public void addProperty(String shortName, String fullName, String message, String regex)
                throws DuplicatePropertyException, PatternSyntaxException {
            PropertyManager.addNewProperty(shortName, fullName, message, regex);
        }
    }
}
```
###### \java\seedu\address\logic\commands\configs\ChangeTagColorCommandTest.java
``` java
public class ChangeTagColorCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createCommand_success() throws Exception {
        ConfigCommand command = new ChangeTagColorCommand(VALID_TAG_FRIEND + VALID_TAG_COLOR,
                VALID_TAG_FRIEND, VALID_TAG_COLOR);

        assertNotNull(command);
    }

    @Test
    public void createCommand_illegalTagName_throwException() throws Exception {
        thrown.expect(ParseException.class);

        ConfigCommand command = new ChangeTagColorCommand(INVALID_TAG + VALID_TAG_COLOR,
                INVALID_TAG, VALID_TAG_COLOR);

        assertNotNull(command);
    }

    @Test
    public void execute_noSuchTag_throwException() throws Exception {
        thrown.expect(CommandException.class);

        ConfigCommand command = new ChangeTagColorCommand(VALID_TAG_FRIEND + VALID_TAG_COLOR,
                VALID_TAG_FRIEND, VALID_TAG_COLOR);
        command.setData(new NoSuchTagModelStub(), new CommandHistory(), new UndoRedoStack());
        command.execute();
    }

    @Test
    public void execute_hasTag_success() throws Exception {
        ConfigCommand command = new ChangeTagColorCommand(VALID_TAG_FRIEND + VALID_TAG_COLOR,
                VALID_TAG_FRIEND, VALID_TAG_COLOR);
        command.setData(new HasTagModelStub(), new CommandHistory(), new UndoRedoStack());
        CommandResult result = command.execute();

        assertEquals(String.format(ChangeTagColorCommand.MESSAGE_SUCCESS, "[friend]", VALID_TAG_COLOR),
                result.feedbackToUser);
    }

    @Test
    public void equal_twoSameCommands_returnTrue() throws Exception {
        ConfigCommand command1 = new ChangeTagColorCommand(VALID_TAG_FRIEND + VALID_TAG_COLOR,
                VALID_TAG_FRIEND, VALID_TAG_COLOR);
        ConfigCommand command2 = new ChangeTagColorCommand(VALID_TAG_FRIEND + VALID_TAG_COLOR,
                VALID_TAG_FRIEND, VALID_TAG_COLOR);

        assertEquals(command1, command2);
    }

    private class NoSuchTagModelStub extends ModelStub {
        @Override
        public boolean hasTag(Tag tag) {
            return false;
        }
    }

    private class HasTagModelStub extends ModelStub {
        @Override
        public boolean hasTag(Tag tag) {
            return true;
        }

        @Override
        public void setTagColor(Tag tag, String color) {

        }
    }
}
```
###### \java\seedu\address\logic\commands\configs\ConfigCommandTest.java
``` java
public class ConfigCommandTest {
    @Test
    public void configTypes_checkCount() {
        assertEquals(ConfigCommand.ConfigType.values().length, ConfigCommand.TO_ENUM_CONFIG_TYPE.size());
    }
}
```
###### \java\seedu\address\logic\commands\imports\ImportCommandTest.java
``` java
public class ImportCommandTest {
    @Test
    public void configTypes_checkCount() {
        assertEquals(ImportCommand.ImportType.values().length, ImportCommand.TO_ENUM_IMPORT_TYPE.size());
    }
}
```
###### \java\seedu\address\logic\commands\imports\ImportNusmodsCommandTest.java
``` java
public class ImportNusmodsCommandTest {
    private static ImportCommand validCommand;

    @BeforeClass
    public static void setUp() throws Exception {
        validCommand = new ImportNusmodsCommand(new URL(NUSMODS_VALID_URL));
    }

    @Test
    public void createCommand_succeed_checkCorrectness() throws Exception {
        String expected = "AY2017-2018 Semester 1";
        assertEquals(expected, validCommand.toString());
    }

    @Test
    public void createCommand_wrongSemesterInformation_expectException() throws Exception {
        assertConstructorFailure(NUSMODS_INVALID_URL_YEAR_START, String.format(INVALID_URL, ""));
        assertConstructorFailure(NUSMODS_INVALID_URL_YEAR_OFFSET, String.format(INVALID_URL, YEAR_OFFSET_BY_ONE));
    }

    @Test
    public void execute_succeed_checkCorrectness() throws Exception {
        Model modelStub = new ImportNusmodsCommandModelStub();
        validCommand.setData(modelStub, new CommandHistory(), new UndoRedoStack());
        CommandResult result = validCommand.execute();
        ImportNusmodsCommandModelStub stubCount = (ImportNusmodsCommandModelStub) modelStub;
        assertEquals(1, stubCount.getEventCount());
        assertEquals(String.format(MESSAGE_SUCCESS, 1), result.feedbackToUser);
    }

    /**
     * Constructs an {@link ImportNusmodsCommand} and checks its failure and corresponding error message.
     */
    private void assertConstructorFailure(String input, String expectedMessage) throws Exception {
        ImportCommand command = null;
        try {
            command = new ImportNusmodsCommand(new URL(input));
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
        assertNull(command);
    }

    private class ImportNusmodsCommandModelStub extends ModelStub {
        private int countEvent = 0;

        ImportNusmodsCommandModelStub() {
            PropertyManager.initializePropertyManager();
        }

        @Override
        public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
            countEvent++;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        int getEventCount() {
            return countEvent;
        }
    }
}
```
###### \java\seedu\address\logic\commands\imports\ImportScriptCommandTest.java
``` java
public class ImportScriptCommandTest {
    @Test
    public void createCommand_allPresent_checkCorrectness() throws Exception {
        ImportCommand command = new ImportScriptCommand("some script file");
        assertNotNull(command);
    }

    @Test
    public void execute_allPresent_checkCorrectness() throws Exception {
        ImportCommand command = new ImportScriptCommand("some script file");
        command.setData(new ImportScriptModelStub(), new CommandHistory(), new UndoRedoStack());
        assertEquals("The script has been imported.", command.execute().feedbackToUser);
    }

    private class ImportScriptModelStub extends ModelStub {
        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### \java\seedu\address\logic\commands\imports\ModuleInfoTest.java
``` java
public class ModuleInfoTest {
    private static ModuleInfo info;
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/ConfigUtilTest/");
    private static final String SAMPLE_MODULE_JSON = "SampleModuleInfoJson.json";

    @BeforeClass
    public static void setUp() throws Exception {
        info = getSampleModule();
        assertNotNull(info);
    }

    @Test
    @Ignore
    public void createModuleInfo_fromJsonUrl_checkCorrectness() throws Exception {
        assertEquals("CS1101S", info.getModuleCode());

        Date expectedDate = DateTime.parseDateTime("29112017 17:00");
        assertEquals(expectedDate, info.getExamDate());
    }

    @Test
    public void equals_returnTrue_checkCorrectness() throws Exception {
        ModuleInfo another = getSampleModule();
        assertEquals(info, info);
        assertEquals(info, another);
        assertNotEquals(info, "");
    }

    @Test
    @Ignore
    public void toString_checkCorrectness() throws Exception {
        String expected = "Module Code: CS1101S\n"
                + "Module Title: Programming Methodology\n"
                + "Module Credit: 5\n"
                + "Examination Date: Wed Nov 29 17:00:00 SGT 2017";
        assertEquals(expected, info.toString());
    }

    @Test
    public void hashCode_checkCorrectness() {
        assertEquals("CS1101S".hashCode(), info.hashCode());
    }

    private static ModuleInfo getSampleModule() throws Exception {
        // Although this method returns an Optional, we do not want to make use of such wrapper here.
        return JsonUtil.readJsonFile(TEST_DATA_FOLDER + SAMPLE_MODULE_JSON, ModuleInfo.class).orElse(null);
    }
}
```
###### \java\seedu\address\logic\commands\person\AddAvatarCommandTest.java
``` java
public class AddAvatarCommandTest {
    private static final String VALID_PATH = FileUtil.getPath("./src/test/resources/SampleAvatar.jpg");
    private static Avatar validAvatar;
    private static ModelStub model;

    @BeforeClass
    public static void setUp() throws Exception {
        validAvatar = new Avatar(VALID_PATH);
        model = new AddAvatarModelStub();
    }

    @Test
    public void equal_twoCommands_checkCorrectness() {
        Command command1 = new AddAvatarCommand(Index.fromOneBased(1), validAvatar);
        Command command2 = new AddAvatarCommand(Index.fromOneBased(1), validAvatar);
        assertEquals(command1, command2);

        command1 = new AddAvatarCommand(Index.fromZeroBased(1), validAvatar);
        command2 = new AddAvatarCommand(Index.fromOneBased(1), validAvatar);
        assertNotEquals(command1, command2);
    }

    @Test
    public void execute_changeAvatar_checkCorrectness() throws Exception {
        Command command = new AddAvatarCommand(Index.fromOneBased(1), validAvatar);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult result = command.execute();

        ReadOnlyPerson person = model.getFilteredPersonList().get(0);
        assertEquals(String.format(MESSAGE_ADD_AVATAR_SUCCESS, person), result.feedbackToUser);
        assertEquals(validAvatar, person.getAvatar());
    }

    @Test
    public void execute_invalidIndex_expectException() {
        Command command = new AddAvatarCommand(Index.fromOneBased(5), validAvatar);
        command.setData(model, new CommandHistory(), new UndoRedoStack());

        CommandResult result = null;
        try {
            result = command.execute();
        } catch (CommandException ce) {
            assertEquals(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, ce.getMessage());
            assertNull(result);
        }

    }

    private static class AddAvatarModelStub extends ModelStub {
        private List<ReadOnlyPerson> list = new ArrayList<>();

        public AddAvatarModelStub() {
            list.add(new PersonBuilder().build());
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return FXCollections.observableList(list);
        }

        @Override
        public void setPersonAvatar(ReadOnlyPerson target, Avatar avatar) {
            target.setAvatar(avatar);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### \java\seedu\address\logic\commands\person\EmailCommandTest.java
``` java
    @Test
    public void execute_allPresent_checkCorrectness() throws Exception {
        Command command = prepareCommand(INDEX_FIRST_PERSON);
        CommandResult result = command.execute();
        assertEquals(String.format(MESSAGE_SUCCESS, model.getFilteredPersonList().get(0)), result.feedbackToUser);
    }
```
###### \java\seedu\address\logic\parser\ConfigCommandParserTest.java
``` java
public class ConfigCommandParserTest {
    private ConfigCommandParser parser = new ConfigCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        // Test for ChangeTagColorCommand.
        ConfigCommand expected = new ChangeTagColorCommand("husband #7db9a1", "husband", "#7db9a1");
        assertParseSuccess(parser, VALID_CONFIG_TAG_COLOR + VALID_TAG_HUSBAND + VALID_TAG_COLOR, expected);

        // Test for AddPropertyCommand.
        expected = new AddPropertyCommand(VALID_NEW_PROPERTY.trim(), "b",
                "birthday", "something", "[^\\s].*");
        assertParseSuccess(parser, VALID_CONFIG_ADD_PROPERTY + VALID_NEW_PROPERTY, expected);

        // Test for AddPropertyCommand (without using customize constraintMessage and regex).
        expected = new AddPropertyCommand(VALID_NEW_PROPERTY_NO_REGEX.trim(), "m",
                "major", String.format(DEFAULT_MESSAGE, "major"), DEFAULT_REGEX);
        assertParseSuccess(parser, VALID_CONFIG_ADD_PROPERTY + VALID_NEW_PROPERTY_NO_REGEX, expected);
    }

    @Test
    public void parse_usePreDefinedColor_success() throws Exception {
        ConfigCommand expected = new ChangeTagColorCommand("husband red", "husband", VALID_PREDEFINED_COLOR.trim());
        assertParseSuccess(parser, VALID_CONFIG_TAG_COLOR + VALID_TAG_HUSBAND + VALID_PREDEFINED_COLOR, expected);
    }

    @Test
    public void parse_invalidConfigType_expectException() {
        assertParseFailure(parser, INVALID_CONFIG_TYPE + INVALID_CONFIG_VALUE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CONFIG_TYPE_NOT_FOUND));
    }

    @Test
    public void parse_invalidTagColor_expectException() {
        assertParseFailure(parser, VALID_CONFIG_TAG_COLOR + VALID_TAG_HUSBAND + INVALID_TAG_COLOR,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, COLOR_CODE_WRONG));
    }

    @Test
    public void parse_invalidNewProperty_expectException() {
        assertParseFailure(parser, VALID_CONFIG_ADD_PROPERTY + INVALID_NEW_PROPERTY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ImportCommandParserTest.java
``` java
public class ImportCommandParserTest {
    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        ImportCommand expected = new ImportNusmodsCommand(new URL(NUSMODS_VALID_URL));
        assertParseSuccess(parser, NUSMODS_VALID_IMPORT, expected);

        expected = new ImportXmlCommand(VALID_IMPORT_XML_PATH.trim());
        assertParseSuccess(parser, XML_VALID_IMPORT, expected);

        // This is because ImportScriptCommand has not been implemented, coming in v2.0
        assertParseSuccess(parser, SCRIPT_VALID_IMPORT, null);
    }

    @Test
    public void parse_noDoubleHyphen_expectXmlCommand() {
        ImportCommand expected = new ImportXmlCommand(VALID_IMPORT_XML_PATH.trim());
        assertParseSuccess(parser, VALID_IMPORT_XML_PATH, expected);
    }

    @Test
    public void parse_invalidConfigType_expectException() {
        assertParseFailure(parser, INVALID_IMPORT_TYPE + INVALID_IMPORT_PATH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, IMPORT_TYPE_NOT_FOUND));
    }

    @Test
    public void parse_noPathSpecified_expectException() {
        assertParseFailure(parser, IMPORT_NO_PATH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }

    @Test
    public void  checkNusmodsImport_invalidUrlForNusMods_expectException() {
        assertParseFailure(parser, NOT_FROM_NUSMODS_IMPORT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportNusmodsCommand.MESSAGE_USAGE));

        assertParseFailure(parser, NUSMODS_INVALID_IMPORT, String.format(INVALID_URL, ""));

        assertParseFailure(parser, NUSMODS_MALFORMED_URL, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ImportNusmodsCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\person\AddAvatarCommandParserTest.java
``` java
public class AddAvatarCommandParserTest {
    private static final String VALID_PATH = FileUtil.getPath("./src/test/resources/SampleAvatar.jpg");
    private static final String NOT_IMAGE_PATH = FileUtil.getPath("./src/test/resources/SampleNotImage.txt");
    private final AddAvatarCommandParser parser = new AddAvatarCommandParser();

    @Test
    public void parse_allFieldsPresent_checkCorrectness() throws Exception {
        String input = "1 " + VALID_PATH;
        Command expected = new AddAvatarCommand(Index.fromOneBased(1), new Avatar(VALID_PATH));
        assertParseSuccess(parser, input, expected);
    }

    @Test
    public void parse_fieldsMissing_expectException() throws Exception {
        String input = "1 ";
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAvatarCommand.MESSAGE_USAGE);
        assertParseFailure(parser, input, message);
    }

    @Test
    public void parse_invalidIndex_expectException() throws Exception {
        String input = "-1 " + VALID_PATH;
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, input, message);
    }

    @Test
    public void parse_invalidFilePath_expectException() throws Exception {
        String input = "1 " + "invalid*.jpg";
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, INVALID_PATH_MESSAGE);
        assertParseFailure(parser, input, message);
    }

    @Test
    public void parse_fileNotExist_expectException() throws Exception {
        String input = "1 " + "noSuchFile.jpg";
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, IMAGE_NOT_EXISTS);
        assertParseFailure(parser, input, message);
    }

    @Test
    public void parse_fileNotImage_expectException() throws Exception {
        String input = "1 " + NOT_IMAGE_PATH;
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FILE_NOT_IMAGE);
        assertParseFailure(parser, input, message);
    }
}
```
###### \java\seedu\address\logic\parser\person\EmailCommandParserTest.java
``` java
public class EmailCommandParserTest {
    private final EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parse_validIndex_checkCorrectness() {
        Command expected = new EmailCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, " 1 ", expected);
    }

    @Test
    public void parse_invalidIndex_expectException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertParseFailure(parser, " -1 ", expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\person\FindTagCommandParserTest.java
``` java
public class FindTagCommandParserTest {
    private final FindTagCommandParser parser = new FindTagCommandParser();

    @Test
    public void parse_tagNamePresent_checkCorrectness() {
        List<String> names = new ArrayList<>();
        names.add("friends");
        names.add("classmates");
        Command expected = new FindTagCommand(new TagContainsKeywordsPredicate(names));
        assertParseSuccess(parser, "friends classmates", expected);
    }

    @Test
    public void parse_tagNameEmpty_expectException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " ", expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\util\ArgumentMultimapTest.java
``` java
public class ArgumentMultimapTest {
    private static final String NOT_EXISTS = "not exists";

    @Test
    public void put_singleEntry_checkCorrectness() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, VALID_NAME_AMY);
        assertEquals(VALID_NAME_AMY, map.getValue(PREFIX_NAME).orElse(NOT_EXISTS));
    }

    @Test
    public void put_singleEmptyEntry_checkCorrectness() {
        ArgumentMultimap map = new ArgumentMultimap();
        assertEquals(NOT_EXISTS, map.getValue(PREFIX_NAME).orElse(NOT_EXISTS));
    }

    @Test
    public void put_sameNameMultipleTimes_checkCorrectness() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, VALID_NAME_AMY);
        map.put(PREFIX_NAME, VALID_NAME_BOB);
        assertEquals(2, map.getAllValues(PREFIX_NAME).size());
    }

    @Test
    public void put_multipleEntries_checkCorrectness() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, VALID_NAME_AMY);
        map.put(PREFIX_PHONE, VALID_PHONE_BOB);
        assertEquals(VALID_NAME_AMY, map.getValue(PREFIX_NAME).orElse(NOT_EXISTS));
        assertEquals(VALID_PHONE_BOB, map.getValue(PREFIX_PHONE).orElse(NOT_EXISTS));
    }

    @Test
    public void getAllValues_multipleEntries_checkCorrectness() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, VALID_NAME_AMY);
        map.put(PREFIX_NAME, VALID_NAME_BOB);
        map.put(PREFIX_PHONE, VALID_PHONE_BOB);

        HashMap<Prefix, String> values = map.getAllValues();
        assertEquals(2, values.size());
        assertEquals(VALID_NAME_BOB, values.get(PREFIX_NAME));
        assertEquals(VALID_PHONE_BOB, values.get(PREFIX_PHONE));
    }

    @Test
    public void getPreamble_checkCorrectness() {
        String preamble = "Some things here";
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(new Prefix(""), preamble);
        assertEquals(preamble, map.getPreamble());
    }
}
```
###### \java\seedu\address\model\event\EventTest.java
``` java
    @Test
    public void createEvent_viaSetProperties_checkCorrectness() throws Exception {
        Event event = new Event(properties, new ArrayList<>());

        assertEquals(name, event.getName());
        assertEquals(dateTime, event.getTime());
        assertEquals(address, event.getAddress());
        assertEquals(0, event.getReminders().size());
        assertEquals(3, event.getProperties().size());
    }
```
###### \java\seedu\address\model\event\EventTest.java
``` java
    @Test
    public void toString_checkCorrectness() throws Exception {
        Event event = new Event(name, dateTime, address, new ArrayList<>());
        String expected =
                " Event: Mel Birthday |  Date/Time: 25 Dec, 2017 08:30 |  Address: Block 312, Amy Street 1";

        assertEquals(expected, event.toString());
        assertEquals(expected, event.getAsText());
    }
    @Test
    public void hashCode_checkCorrectness() {
        Event event = new Event(name, dateTime, address, reminders);
        assertNotNull(event);

        assertEquals(Objects.hash(event.nameProperty(), event.timeProperty(), event.addressProperty(),
                event.reminderProperty()), event.hashCode());
    }
}
```
###### \java\seedu\address\model\event\UniqueEventListTest.java
``` java
    @Test
    public void add_allPresent_checkCorrectness() throws Exception {
        UniqueEventList list = new UniqueEventList();
        assertEquals(0, list.asObservableList().size());

        list.add(EVENT1);
        assertEquals(1, list.asObservableList().size());

        list.add(EVENT2);
        assertEquals(2, list.asObservableList().size());
    }

    @Test
    public void add_haveDuplicate_expectException() throws Exception {
        UniqueEventList list = new UniqueEventList();
        assertEquals(0, list.asObservableList().size());

        list.add(EVENT1);
        assertEquals(1, list.asObservableList().size());

        thrown.expect(DuplicateEventException.class);
        list.add(EVENT1);
        assertEquals(1, list.asObservableList().size());
    }

    @Test
    public void addEvents_success_checkCorrectness() throws Exception {
        UniqueEventList list1 = new UniqueEventList();
        list1.add(EVENT1);
        list1.add(EVENT2);

        UniqueEventList list2 = new UniqueEventList();
        list2.addEvents(Arrays.asList(EVENT1, EVENT2));

        assertEquals(list1, list2);
    }

    @Test
    public void sort_basedOnTime_checkCorrectness() throws Exception {
        UniqueEventList list = new UniqueEventList();
        list.add(EVENT1);
        list.add(EVENT2);
        assertEquals(2, list.asObservableList().size());

        list.sortEvents();
        assertEquals(EVENT1, list.asObservableList().get(0));
        assertEquals(EVENT2, list.asObservableList().get(1));
    }

    @Test
    public void setEvent_changeSingleEvent_checkCorrectness() throws Exception {
        UniqueEventList list = new UniqueEventList();
        list.add(EVENT1);
        assertEquals(1, list.asObservableList().size());

        list.setEvent(EVENT1, EVENT2);
        assertEquals(EVENT2, list.asObservableList().get(0));
    }

    @Test
    public void setEvent_newEventAlreadyExist_expectException() throws Exception {
        UniqueEventList list = new UniqueEventList();
        list.add(EVENT1);
        list.add(EVENT2);
        assertEquals(2, list.asObservableList().size());

        thrown.expect(DuplicateEventException.class);
        list.setEvent(EVENT1, EVENT2);
        assertEquals(2, list.asObservableList().size());
        assertEquals(EVENT1, list.asObservableList().get(0));
    }

    @Test
    public void setEvent_eventNotFound_expectException() throws Exception {
        UniqueEventList list = new UniqueEventList();
        list.add(EVENT1);
        assertEquals(1, list.asObservableList().size());

        thrown.expect(EventNotFoundException.class);
        list.setEvent(EVENT2, EVENT1);
        assertEquals(1, list.asObservableList().size());
        assertEquals(EVENT1, list.asObservableList().get(0));
    }

    @Test
    public void remove_success_checkCorrectness() throws Exception {
        UniqueEventList list = new UniqueEventList();
        list.add(EVENT1);
        list.add(EVENT2);
        assertEquals(2, list.asObservableList().size());

        list.remove(EVENT1);
        assertEquals(1, list.asObservableList().size());
        assertEquals(EVENT2, list.asObservableList().get(0));
    }

    @Test
    public void equal_checkCorrectness() {
        UniqueEventList list1 = new UniqueEventList();
        UniqueEventList list2 = new UniqueEventList();

        assertEquals(list1, list1);
        assertEquals(list1, list2);
        assertNotEquals(list1, null);
        assertNotEquals(list1, 1);
    }
}
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    @Test
    public void setTagColor_validFields_success() throws Exception {
        ModelManager modelManager = new ModelManager();
        Tag myTag = new Tag(VALID_TAG_FRIEND);
        modelManager.setTagColor(myTag, VALID_TAG_COLOR);
        assertEquals(VALID_TAG_COLOR, TagColorManager.getColor(myTag));
    }
```
###### \java\seedu\address\model\person\AvatarTest.java
``` java
public class AvatarTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void create_validFile_checkCorrectness() throws Exception {
        String path = FileUtil.getPath("./src/test/resources/SampleAvatar.jpg");
        File file = new File(path);

        Avatar avatar = new Avatar(path);
        assertEquals(path, avatar.getPath());
        assertEquals(file.toURI().toString(), avatar.getUri());
    }

    @Test
    public void create_invalidName_expectException() throws Exception {
        thrown.expect(IllegalValueException.class);
        Avatar avatar = new Avatar("invalid*.png");
        assertNull(avatar);
    }

    @Test
    public void create_invalidNameSeparator_expectException() throws Exception {
        thrown.expect(IllegalValueException.class);
        Avatar avatar = new Avatar("folder\\folder/invalid*.png");
        assertNull(avatar);
    }

    @Test
    public void create_fileNotExist_expectException() throws Exception {
        String path = FileUtil.getPath("./src/test/resources/SampleAvatar2.jpg");
        thrown.expect(IllegalValueException.class);
        Avatar avatar = new Avatar(path);
        assertNull(avatar);
    }

    @Test
    public void create_fileNotImage_expectException() throws Exception {
        String path = FileUtil.getPath("./src/test/resources/SampleNotImage.txt");
        thrown.expect(IllegalValueException.class);
        Avatar avatar = new Avatar(path);
        assertNull(avatar);
    }

    @Test
    public void equals_twoSameAvatar_checkCorrectness() throws Exception {
        String path = FileUtil.getPath("./src/test/resources/SampleAvatar.jpg");
        Avatar avatar1 = new Avatar(path);
        Avatar avatar2 = new Avatar(path);
        assertEquals(avatar1, avatar2);
    }

    @Test
    public void hashCode_checkCorrectness() throws Exception {
        String path = FileUtil.getPath("./src/test/resources/SampleAvatar.jpg");
        File file = new File(path);

        Avatar avatar = new Avatar(path);
        assertEquals(file.toURI().toString().hashCode(), avatar.hashCode());
    }

    @Test
    public void toString_checkCorrectness() throws Exception {
        String path = FileUtil.getPath("./src/test/resources/SampleAvatar.jpg");
        File file = new File(path);

        Avatar avatar = new Avatar(path);
        assertEquals("Avatar from " + file.toURI().toString(), avatar.toString());
    }
}
```
###### \java\seedu\address\model\person\PersonTest.java
``` java
public class PersonTest {
    private static Name name;
    private static Phone phone;
    private static Email email;
    private static Address address;

    @BeforeClass
    public static void setUp() throws Exception {
        PropertyManager.initializePropertyManager();

        name = new Name(VALID_NAME_AMY);
        phone = new Phone(VALID_PHONE_AMY);
        email = new Email(VALID_EMAIL_AMY);
        address = new Address(VALID_ADDRESS_AMY);
    }

    @Test
    public void createPerson_preDefinedFieldsPresent_checkCorrectness() throws Exception {
        Person person = new Person(name, phone, email, address, Collections.emptySet());
        assertNotNull(person);

        assertEquals(name, person.getName());
        assertEquals(phone, person.getPhone());
        assertEquals(email, person.getEmail());
        assertEquals(address, person.getAddress());
        assertEquals(0, person.getTags().size());
        assertEquals(4, person.getProperties().size());
        assertEquals(4, person.getSortedProperties().size());
    }

    @Test
    public void equal_twoSameStatePerson_checkCorrectness() throws Exception {
        Person person = new Person(name, phone, email, address, Collections.emptySet());
        Person another = new Person(name, phone, email, address, Collections.emptySet());
        assertEquals(person, another);

        Person copied = new Person(person);
        assertEquals(person, copied);
    }

```
###### \java\seedu\address\model\property\DateTimeTest.java
``` java
    @Test
    public void create_viaString_checkCorrectness() throws Exception {
        DateTime dateTime = new DateTime(VALID_DATE_EVENT1);
        assertEquals(VALID_DISPLAY_DATE_EVENT1, dateTime.getValue());
    }

    @Test
    public void create_viaDateObject_checkCorrectness() throws Exception {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy HH:mm");
        Date date = dateFormatter.parse(VALID_DATE_EVENT1);

        // Create a Datetime property via alternative constructor.
        DateTime dateTime = new DateTime(date);
        assertEquals(VALID_DISPLAY_DATE_EVENT1, dateTime.getValue());
    }

    @Test
    public void create_viaNaturalLanguage_checkCorrectness() throws Exception {
        assertEquals(new DateTime(VALID_DATE_EVENT1), new DateTime(VALID_NATURAL_DATE_EVENT1));
        assertEquals(new DateTime(VALID_DATE_EVENT2), new DateTime(VALID_NATURAL_DATE_EVENT2));
    }
}
```
###### \java\seedu\address\model\property\NameContainsKeywordsPredicateTest.java
``` java
    @Test
    public void testForEvent_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = generatePredicate("CS2103T");
        assertTrue(predicate.test(new EventBuilder().withName("CS2103T Tutorial").build()));

        // Multiple keywords
        predicate = generatePredicate("CS2103T", "Tutorial");
        assertTrue(predicate.test(new EventBuilder().withName("CS2103T Tutorial").build()));

        // Only one matching keyword
        predicate = generatePredicate("CS2103T", "Examination");
        assertTrue(predicate.test(new EventBuilder().withName("CS2103T Tutorial").build()));

        // Mixed-case keywords
        predicate = generatePredicate("cs2103T", "tutorial");
        assertTrue(predicate.test(new EventBuilder().withName("CS2103T Tutorial").build()));
    }

    private NameContainsKeywordsPredicate generatePredicate(String... names) {
        return new NameContainsKeywordsPredicate(Arrays.asList(names));
    }
}
```
###### \java\seedu\address\model\property\PropertyManagerTest.java
``` java
public class PropertyManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        PropertyManager.initializePropertyManager();
    }

    @Test
    public void preLoadedProperty_checkInitializationSuccessful() {
        assertEquals(5, PropertyManager.getAllShortNames().size());

        assertEquals("Name", PropertyManager.getPropertyFullName("n"));
        assertEquals("Email", PropertyManager.getPropertyFullName("e"));
        assertEquals("Phone", PropertyManager.getPropertyFullName("p"));
        assertEquals("Address", PropertyManager.getPropertyFullName("a"));
        assertEquals("DateTime", PropertyManager.getPropertyFullName("dt"));

        assertEquals(String.format(DEFAULT_MESSAGE, "Address"), PropertyManager.getPropertyConstraintMessage("a"));
        assertEquals(DEFAULT_REGEX, PropertyManager.getPropertyValidationRegex("a"));

        assertTrue(PropertyManager.containsShortName("n"));
        assertTrue(PropertyManager.containsShortName("e"));
        assertTrue(PropertyManager.containsShortName("p"));
        assertTrue(PropertyManager.containsShortName("a"));
        assertTrue(PropertyManager.containsShortName("dt"));
    }

    @Test
    public void preLoadedProperty_checkCount() throws Exception {
        int numPreLoadedProperties = testPrivateFieldsCount("propertyFullNames");

        assertEquals(numPreLoadedProperties, testPrivateFieldsCount("propertyConstraintMessages"));

        assertEquals(numPreLoadedProperties, testPrivateFieldsCount("propertyValidationRegex"));
    }

    @Test
    public void addProperty_successfullyAdd() throws Exception {
        String shortName = "b";
        String fullName = "birthday";
        String message = "Birthday must be a valid date format in dd/mm/yyyy, dd-mm-yyyy or dd.mm.yyyy";
        String regex = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)"
                + "(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\"
                + "3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|"
                + "(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)"
                + "(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

        PropertyManager.addNewProperty(shortName, fullName, message, regex);

        assertEquals(fullName, PropertyManager.getPropertyFullName(shortName));
        assertEquals(message, PropertyManager.getPropertyConstraintMessage(shortName));
        assertEquals(regex, PropertyManager.getPropertyValidationRegex(shortName));
    }

    @Test
    public void addProperty_invalidRegex_error() throws Exception {
        String shortName = "d";
        String fullName = "description";
        String message = "Description can be any string, but cannot be blank";
        String regex = "*asf";

        thrown.expect(PatternSyntaxException.class);

        PropertyManager.addNewProperty(shortName, fullName, message, regex);
    }

    @Test
    public void addProperty_duplicateShortName_error() throws Exception {
        // Duplicate because the shortName is the same as the pre-loaded property "address".
        String shortName = "a";
        String fullName = "mailing address";
        String message = "Description can be any string, but cannot be blank";
        String regex = "[^\\s].*";

        thrown.expect(DuplicatePropertyException.class);

        PropertyManager.addNewProperty(shortName, fullName, message, regex);
    }

    @Test
    public void clearAllProperties_success_checkCorrectness() {
        PropertyManager.clearAllProperties();
        assertEquals(0, PropertyManager.getAllShortNames().size());
        PropertyManager.initializePropertyManager();
    }

    /**
     * Uses reflection to get the size of private static {@link HashMap}s.
     *
     * @param fieldName is the name of the private field in String format.
     * @return the number of items in the {@link HashMap}
     */
    @SuppressWarnings(value = "unchecked")
    private int testPrivateFieldsCount(String fieldName) {
        HashMap<String, String> variable = null;
        try {
            Field field = PropertyManager.class.getDeclaredField(fieldName);
            field.setAccessible(true);

            // We use an SuppressWarning annotation to avoid warning here, because this is
            // in the test environment anyway.
            variable = (HashMap<String, String>) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            assert(false);
        }

        return variable.size();
    }
}
```
###### \java\seedu\address\model\property\PropertyTest.java
``` java
public class PropertyTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        PropertyManager.initializePropertyManager();
    }

    @Test
    public void createProperty_preLoadedProperty_successfulCreation() throws Exception {
        String value = "12345678";
        Property newProperty = new Property("p", value);

        assertEquals("p", newProperty.getShortName());
        assertEquals("Phone", newProperty.getFullName());
        assertEquals(value, newProperty.getValue());
    }

    @Test
    public void createProperty_preLoadedProperty_invalidValue() {
        Property newProperty = null;
        String value = "12";
        String expectedMessage = "Phone numbers can only contain numbers, and should be at least 3 digits long.";

        try {
            newProperty = new Property("p", value);
        } catch (IllegalValueException | PropertyNotFoundException ive) {
            assertEquals(expectedMessage, ive.getMessage());
        }

        assertNull(newProperty);
    }

    @Test
    public void createProperty_customProperty_noSuchProperty() throws Exception {
        thrown.expect(PropertyNotFoundException.class);

        Property newProperty = new Property("w", "some random value");
        assertNull(newProperty);
    }

    @Test
    public void equalProperty_sameKeyAndValue_successfulCompare() throws Exception {
        Property propertyA = new Property("a", "This is my address");
        Property propertyB = new Property("a", "This is my address");

        assertEquals(propertyA, propertyB);
    }
}
```
###### \java\seedu\address\model\property\UniquePropertyMapTest.java
``` java
public class UniquePropertyMapTest {
    private static Set<Property> mySet;
    private static Property newProperty;
    private static Property existingProperty;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUp() throws Exception {
        PropertyManager.initializePropertyManager();
        mySet = new HashSet<>();
        mySet.add(new Property("a", "some address"));
        mySet.add(new Property("p", "12345678"));

        newProperty = new Property("e", "google@microsoft.com");
        existingProperty = new Property("a", "another address");
    }

    @Test
    public void createPropertyMap_nullInput_throwNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);

        UniquePropertyMap propertyMap = null;
        Set<Property> myMap = null;
        propertyMap = new UniquePropertyMap(myMap);
        assertNull(propertyMap);
    }

    @Test
    public void createPropertyMap_validButEmptyInput_successfullyCreated() throws Exception {
        Set<Property> mySet = new HashSet<>();
        UniquePropertyMap propertyMap = new UniquePropertyMap(mySet);

        assertEquals(0, propertyMap.toSet().size());
    }

    @Test
    public void createPropertyMap_validNonEmptyInput_successfullyCreated() throws Exception {
        UniquePropertyMap propertyMap = createSampleMap();

        assertEquals(mySet.size(), propertyMap.size());
    }

    @Test
    public void addProperty_checkCorrectness() throws Exception {
        UniquePropertyMap propertyMap = createSampleMap();
        int countBefore = propertyMap.size();
        propertyMap.add(newProperty);
        int countAfter = propertyMap.size();

        assertEquals(1, countAfter - countBefore);
    }

    @Test
    public void addProperty_existingProperty_throwException() throws Exception {
        thrown.expect(DuplicatePropertyException.class);

        UniquePropertyMap propertyMap = createSampleMap();
        propertyMap.add(existingProperty);
        assertEquals(mySet.size(), propertyMap.size());
    }

    @Test
    public void updateProperty_checkCorrectness() throws Exception {
        UniquePropertyMap propertyMap = createSampleMap();
        int countBefore = propertyMap.size();
        propertyMap.update(existingProperty);
        int countAfter = propertyMap.size();

        assertEquals(countBefore, countAfter);
    }

    @Test
    public void updateProperty_notFoundProperty_throwException() throws Exception {
        thrown.expect(PropertyNotFoundException.class);

        UniquePropertyMap propertyMap = createSampleMap();
        propertyMap.update(newProperty);
    }

    @Test
    public void addOrUpdateProperty_checkCorrectness() throws Exception {
        UniquePropertyMap propertyMap = createSampleMap();
        int count1 = propertyMap.size();
        propertyMap.addOrUpdate(existingProperty);
        int count2 = propertyMap.size();
        propertyMap.addOrUpdate(newProperty);
        int count3 = propertyMap.size();

        assertEquals(0, count2 - count1);
        assertEquals(1, count3 - count2);
    }

    @Test
    public void containsProperty_checkCorrectness() throws Exception {
        UniquePropertyMap propertyMap = createSampleMap();
        propertyMap.add(newProperty);

        assertTrue(propertyMap.containsProperty("e"));
        assertTrue(propertyMap.containsProperty(newProperty));
    }

    @Test
    public void toSet_checkCorrectness() throws Exception {
        UniquePropertyMap propertyMap = createSampleMap();
        assertEquals(mySet, propertyMap.toSet());
    }

    @Test
    public void toSortedList_checkCorrectness() throws Exception {
        UniquePropertyMap propertyMap = createSampleMap();
        List list = propertyMap.toSortedList();
        assertEquals(new Property("a", "some address"), list.get(0));
        assertEquals(new Property("p", "12345678"), list.get(1));
    }

    @Test
    public void setProperties_validButEmptyInput_successfullySet() throws Exception {
        Set<Property> myNewSet = new HashSet<>();
        UniquePropertyMap propertyMap = createSampleMap();
        propertyMap.setProperties(myNewSet);

        assertEquals(0, propertyMap.toSet().size());
    }

    @Test
    public void mergeFrom_samePropertyMap_notChanged() throws Exception {
        UniquePropertyMap propertyMap1 = createSampleMap();
        UniquePropertyMap propertyMap2 = createSampleMap();
        propertyMap1.mergeFrom(propertyMap2);

        assertEquals(mySet.size(), propertyMap1.toSet().size());
    }

    @Test
    public void equal_samePropertyMap_returnTrue() throws Exception {
        UniquePropertyMap propertyMap1 = createSampleMap();
        UniquePropertyMap propertyMap2 = createSampleMap();

        assertEquals(propertyMap1, propertyMap2);
        assertTrue(propertyMap1.equalsOrderInsensitive(propertyMap2));
    }

    /**
     * Util for creating a sample {@link UniquePropertyMap}.
     */
    private UniquePropertyMap createSampleMap() throws Exception {
        return new UniquePropertyMap(mySet);
    }
}
```
###### \java\seedu\address\model\reminder\exceptions\ReminderNotFoundExceptionTest.java
``` java
public class ReminderNotFoundExceptionTest {
    @Test
    public void createException_toString_checkCorrectness() throws Exception {
        Exception exception = new ReminderNotFoundException("Some message here");
        assertEquals("Some message here", exception.toString());
    }
}
```
###### \java\seedu\address\model\reminder\ReminderTest.java
``` java
    @Test
    public void createUsingName_alternativeConstructor_checkCorrectness() {
        Reminder reminder = new Reminder("some name here", message);
        assertEquals("some name here", reminder.getName());
    }

    @Test
    public void copyReminder_alternativeConstructor_checkCorrectness() {
        Reminder reminder1 = new Reminder(event, message);
        Reminder reminder2 = new Reminder(reminder1);

        assertEquals(reminder1, reminder2);
    }

    @Test
    public void toString_checkCorrectness() {
        Reminder reminder = new Reminder(event, message);
        assertNotNull(reminder);

        assertEquals("Message: You have an event", reminder.getAsText());
        assertEquals("Message: You have an event", reminder.toString());
    }

    @Test
    public void hashCode_checkCorrectness() {
        Reminder reminder = new Reminder(event, message);
        assertNotNull(reminder);

        assertEquals(Objects.hash(reminder.eventProperty(), reminder.messageProperty()), reminder.hashCode());
    }
}
```
###### \java\seedu\address\model\reminder\UniqueReminderListTest.java
``` java
public class UniqueReminderListTest {
    private static final Reminder reminder = new Reminder(EVENT1, "Some message");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void create_viaList_checkCorrectness() throws Exception {
        List<ReadOnlyReminder> list = new ArrayList<>();
        list.add(reminder);
        UniqueReminderList uniqueList = new UniqueReminderList(list);
        assertEquals(list.size(), uniqueList.toList().size());
    }

    @Test
    public void add_checkSize_checkCorrectness() throws Exception {
        UniqueReminderList list = new UniqueReminderList();
        list.add(reminder);
        assertEquals(1, list.toList().size());
    }

    @Test
    public void add_hasDuplicate_checkCorrectness() throws Exception {
        thrown.expect(DuplicateReminderException.class);

        UniqueReminderList list = new UniqueReminderList();
        list.add(reminder);
        list.add(reminder);
    }

    @Test
    public void toList_checkSize_checkCorrectness() {
        UniqueReminderList list = new UniqueReminderList();
        assertEquals(0, list.toList().size());
    }

    @Test
    public void equals_checkCorrectness() {
        UniqueReminderList list1 = new UniqueReminderList();
        UniqueReminderList list2 = new UniqueReminderList();

        assertEquals(list1, list1);
        assertEquals(list1, list2);
        assertNotEquals(list1, null);
        assertNotEquals(list1, 1);
    }
}
```
###### \java\seedu\address\model\tag\TagColorManagerTest.java
``` java
public class TagColorManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getTagColor_createColorWhenCreateTag_noException() throws Exception {
        Tag tag = new Tag(VALID_TAG_HUSBAND);

        if (!TagColorManager.contains(tag)) {
            thrown.expect(TagNotFoundException.class);
        }
        TagColorManager.getColor(tag);
    }

    @Test
    public void setTagColor_success_checkCorrectness() throws  Exception {
        Tag tag = new Tag(VALID_TAG_HUSBAND);
        TagColorManager.setColor(tag, VALID_TAG_COLOR);
        assertEquals(VALID_TAG_COLOR, TagColorManager.getColor(tag));
    }

    @Test
    public void setTagColor_randomColor_checkCorrectness() throws Exception {
        Tag tag = new Tag(VALID_TAG_FRIEND);
        TagColorManager.setColor(tag);
        assertTrue(TagColorManager.getColor(tag).matches("#[A-Fa-f0-9]{6}"));
    }
}
```
###### \java\systemtests\AddAvatarCommandSystemTest.java
``` java
public class AddAvatarCommandSystemTest extends AddressBookSystemTest {
    private static final String VALID_PATH = FileUtil.getPath("./src/test/resources/SampleAvatar.jpg");

    @Test
    public void addAvatar() throws Exception {
        Index index = INDEX_FIRST_PERSON;
        String command = AddAvatarCommand.COMMAND_WORD + " " + index.getOneBased() + " " + VALID_PATH;
        assertCommandSuccess(command, index, VALID_PATH);
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * Also verifies that the selected card and status bar remain unchanged, and the command box has the error style.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Index toAdd, String path) throws Exception {
        Model model = getModel();
        ReadOnlyPerson personToAdd = model.getFilteredPersonList().get(toAdd.getZeroBased());
        Avatar avatar = new Avatar(path);
        String expectedResultMessage = String.format(MESSAGE_ADD_AVATAR_SUCCESS, personToAdd);
        model.setPersonAvatar(personToAdd, avatar);

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, model);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }
}
```

# JunQuann
###### /java/guitests/guihandles/PersonCardHandle.java
``` java
    public Image getDisplayPic() {
        ImagePattern displayPicImage = (ImagePattern) displayPicPanel.getFill();
        return displayPicImage.getImage();
    }
```
###### /java/guitests/guihandles/PersonDescriptionHandle.java
``` java
/**
 * Provides a handle to the person description panel.
 */
public class PersonDescriptionHandle extends NodeHandle<Node> {
    private static final String DP_FIELD_ID = "#displayPic";
    private static final String NAME_FIELD_ID = "#name";
    private static final String MOBILE_FIELD_ID = "#mobile";
    private static final String BIRTHDAY_FIELD_ID = "#birthday";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String INSTAGRAM_FIELD_ID = "#instagram";
    private static final String TWITTER_FIELD_ID = "#twitter";

    private final Circle displayPicPanel;
    private final Label nameLabel;
    private final Label mobileLabel;
    private final Label birthdayLabel;
    private final Label emailLabel;
    private final Label addressLabel;
    private final Label instagramLabel;
    private final Label twitterLabel;

    public PersonDescriptionHandle(Node personDescriptionPanelNode) {
        super(personDescriptionPanelNode);

        this.displayPicPanel = getChildNode(DP_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.mobileLabel = getChildNode(MOBILE_FIELD_ID);
        this.birthdayLabel = getChildNode(BIRTHDAY_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.instagramLabel = getChildNode(INSTAGRAM_FIELD_ID);
        this.twitterLabel = getChildNode(TWITTER_FIELD_ID);
    }

    public Image getDisplayPic() {
        ImagePattern displayPicImage = (ImagePattern) displayPicPanel.getFill();
        return displayPicImage.getImage();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getMobile() {
        return mobileLabel.getText();
    }

    public String getBirthday() {
        return birthdayLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getInstagram() {
        return instagramLabel.getText();
    }

    public String getTwitter() {
        return twitterLabel.getText();
    }


}
```
###### /java/seedu/address/logic/parser/ArgumentTokenizerTest.java
``` java
    private static String getFinalImgPathName = "getFinalImgPath";

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private final Prefix unknownPrefix = new Prefix("--u");
    private final Prefix pSlash = new Prefix("p/");
    private final Prefix dashT = new Prefix("-t");
    private final Prefix hatQ = new Prefix("^Q");

    private final Prefix dpSlash = new Prefix("dp/");
    private final String hashedDisplayPicName = "HashedName";
    private final String testImgPath = TestUtil.getTestImgPath();


    private ArgumentTokenizer argumentTokenizer;
    private Method getFinalImgPath;
    private Class[] parameterTypes;
    private Object[] parameters;


    @Before
    public void setUp() throws Exception {
        argumentTokenizer = new ArgumentTokenizer();
        parameterTypes = new Class[2];
        parameterTypes[0] = java.lang.String.class;
        parameterTypes[1] = java.lang.String.class;
        getFinalImgPath = argumentTokenizer.getClass().getDeclaredMethod(getFinalImgPathName, parameterTypes);
        getFinalImgPath.setAccessible(true);
        parameters = new Object[2];
    }

    /**
     * Test that {@raise NewImageEvent} is successfully raised
     */
    @Test
    public void testGetFinalImgPath() throws Exception {
        parameters[0] = hashedDisplayPicName;
        parameters[1] = testImgPath;
        String actualResult = (String) getFinalImgPath.invoke(argumentTokenizer, parameters);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof NewImageEvent);
    }
```
###### /java/seedu/address/logic/parser/ArgumentTokenizerTest.java
``` java
    @Test
    public void tokenize_multipleArgumentsJoined() {
        String argsString = "SomePreambleStringp/ pSlash joined-tjoined -t not joined^Qjoined dp/";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ, dpSlash);
        assertPreamblePresent(argMultimap, "SomePreambleStringp/ pSlash joined-tjoined");
        assertArgumentAbsent(argMultimap, pSlash);
        assertArgumentPresent(argMultimap, dashT, "not joined^Qjoined");
        assertArgumentAbsent(argMultimap, hatQ);
        assertArgumentPresent(argMultimap, dpSlash, "", "/images/defaultperson.png");
    }

    @Test
    public void handlePresentDisplayPicPrefix_eventRaised() {
        EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
        String argString = "SomePreambleString dp/";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argString, dpSlash);
        assertArgumentPresent(argMultimap, dpSlash, "", "/images/defaultperson.png");
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof FileChooserEvent);
    }
```
###### /java/seedu/address/model/person/DisplayPicTest.java
``` java
public class DisplayPicTest {

    @Test
    public void isValidDisplayPic() throws IllegalValueException {
        //invalid file path
        assertFalse(DisplayPic.isValidPicPath(" "));
        assertFalse(DisplayPic.isValidPicPath("invalid file path"));
        assertFalse(DisplayPic.isValidPicPath("/filePath/does/not/exists"));

        //valid file path but not picture file
        assertFalse(DisplayPic.isValidPicPath("./src/test/"));
        assertFalse(DisplayPic.isValidPicPath("./src/test/data/"));
        assertFalse(DisplayPic.isValidPicPath("./src/test/data/ImageFileStorageTest"));

        //valid picture extension but invalid file path
        assertFalse(DisplayPic.isValidPicPath("./invalid.png"));
        assertFalse(DisplayPic.isValidPicPath("./invalid.jpeg"));

        //valid file and is picture
        assertTrue(DisplayPic.isValidPicPath("./src/test/data/ImageFileStorageTest/testImage.png"));
    }
}
```
###### /java/seedu/address/storage/ImageFileStorageTest.java
``` java
public class ImageFileStorageTest {
    private static final String TEST_DATA_FOLDER = "./src/test/data/ImageFileStorageTest/";
    private static final String TEST_FILENAME = "test";
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private final String testImage = TestUtil.getTestImgPath();

    @Test
    public void createImageFolder_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        createImageDir(null);
    }

    public void createImageDir(String filePath) throws IOException {
        new ImageFileStorage(filePath).createImageDir();
    }

    @Test
    public void assertImageFile() {
        String expectedFile = TEST_DATA_FOLDER + TEST_FILENAME + ".png";
        String actualFile = new ImageFileStorage(TEST_DATA_FOLDER).getImageFilePath(TEST_FILENAME);
        assertEquals(expectedFile, actualFile);
    }

    @Test
    public void copiedImage_success() throws IOException {
        String filePath = testFolder.getRoot().getPath();
        ImageFileStorage imageFileStorage = new ImageFileStorage(filePath);
        imageFileStorage.copyImage(testImage, TEST_FILENAME);
        File expectedFile = new File(testImage);
        File actualFile = new File(imageFileStorage.getImageFilePath(TEST_FILENAME));
        assertFileContentEqual(expectedFile, actualFile);
    }

    /**
     * Asserts that the actual file {@code actualFile} has the same binary data as {@code expectedFile}.
     */
    public void assertFileContentEqual(File expectedFile, File actualFile) throws IOException {
        byte[] expectedContent = Files.readAllBytes(expectedFile.toPath());
        byte[] actualContent = Files.readAllBytes(actualFile.toPath());
        boolean equal = Arrays.equals(expectedContent, actualContent);
        assertTrue(equal);
    }
}
```
###### /java/seedu/address/storage/StorageManagerTest.java
``` java
    private static final String INVALID_IMG_PATH = "/invalid/test.png";
    private static final String IMG_NAME = "testImage";
    private static final NewImageEvent EVENT_STUB = new NewImageEvent(IMG_NAME, INVALID_IMG_PATH);
```
###### /java/seedu/address/storage/StorageManagerTest.java
``` java
    @Test
    public void handleNewImageEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that throws an exception when the copyImage method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"),
                                             new ImageFileStorage("dummy"));
        storage.handleNewImageEvent(EVENT_STUB);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleNewImageEvent_success() throws IOException {
        String testFolderPath = testFolder.getRoot().getPath();
        StorageManager storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"),
                                             new ImageFileStorage(testFolderPath));
        NewImageEvent newImageEvent = new NewImageEvent(IMG_NAME, TestUtil.getTestImgPath());
        storage.handleNewImageEvent(newImageEvent);
        File expectedFile = new File(TestUtil.getTestImgPath());
        File actualFile = new File(storage.getImageFilePath(IMG_NAME));
        assertFileContentEqual(expectedFile, actualFile);
    }

    /**
     * Asserts that the actual file {@code actualFile} has the same binary data as {@code expectedFile}.
     */
    public void assertFileContentEqual(File expectedFile, File actualFile) throws IOException {
        byte[] expectedContent = Files.readAllBytes(expectedFile.toPath());
        byte[] actualContent = Files.readAllBytes(actualFile.toPath());
        boolean equal = Arrays.equals(expectedContent, actualContent);
        assertTrue(equal);
    }
```
###### /java/seedu/address/testutil/EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code DisplayPic} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withDisplayPic(String displayPic) {
        try {
            ParserUtil.parseDisplayPic(Optional.of(displayPic)).ifPresent(descriptor::setDisplayPic);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withDisplayPic(String displayPic) {
        try {
            this.person.setDisplayPic(new DisplayPic(displayPic));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }
```
###### /java/seedu/address/ui/PersonCardTest.java
``` java
        // changes made to Person reflects on card
        guiRobot.interact(() -> {
            personWithTags.setName(ZEPHYR.getName());
            personWithTags.setPhone(ZEPHYR.getPhone());
            personWithTags.setTags(ZEPHYR.getTags());
            personWithTags.setDisplayPic(ZEPHYR.getDisplayPic());
        });
        assertCardDisplay(personCard, personWithTags, 2);
```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertImageEquals(expectedCard.getDisplayPic(), actualCard.getDisplayPic());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson} with default Display Picture.
     */
    public static void assertCardDisplaysPerson(ReadOnlyPerson expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        Image expectedDisplayPic = getFileImage(expectedPerson);
        Image actualDisplayPic = actualCard.getDisplayPic();
        assertImageEquals(expectedDisplayPic, actualDisplayPic);
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    private static Image getFileImage(ReadOnlyPerson expectedPerson) {
        Image expectedDisplayPic;
        if (expectedPerson.getDisplayPic().getNewDisplayPicPath().equals(DEFAULT_DP)) {
            expectedDisplayPic = AppUtil.getImage(expectedPerson.getDisplayPic().getNewDisplayPicPath());
        } else {
            File expectedImgFile = new File(expectedPerson.getDisplayPic().getNewDisplayPicPath());
            expectedDisplayPic = new Image(expectedImgFile.toURI().toString());
        }
        return expectedDisplayPic;
    }

    /**
     * Asserts that {@code personDescription} displays the details of {@code expectedPerson}.
     */
    public static void assertDescriptionDisplaysPerson(ReadOnlyPerson expectedPerson,
                                                       PersonDescriptionHandle actualDescription) {

        Image expectedDisplayPic = getFileImage(expectedPerson);
        Image actualDisplayPic = actualDescription.getDisplayPic();
        assertImageEquals(expectedDisplayPic, actualDisplayPic);
        assertEquals(expectedPerson.getName().fullName, actualDescription.getName());
        assertEquals(expectedPerson.getPhone().value, actualDescription.getMobile());
        assertEquals(expectedPerson.getBirthday().value, actualDescription.getBirthday());
        assertEquals(expectedPerson.getEmail().value, actualDescription.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualDescription.getAddress());
        assertEquals(expectedPerson.getInstagramName().value, actualDescription.getInstagram());
        assertEquals(expectedPerson.getTwitterName().value, actualDescription.getTwitter());
    }
```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
    /**
     * Asserts that the image {@code expected} equals to {@code actual}
     */
    public static void assertImageEquals(Image expected, Image displayed) {
        boolean equal = true;
        for (int i = 0; i < expected.getWidth(); i++) {
            for (int j = 0; j < expected.getHeight(); j++) {
                if (expected.getPixelReader().getArgb(i, j) != displayed.getPixelReader().getArgb(i, j)) {
                    equal = false;
                }
            }
        }
        assertTrue(equal);
    }
}
```
###### /java/systemtests/AddressBookSystemTest.java
``` java
    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field",
            "jfx-text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", "jfx-text-field", CommandBox.ERROR_STYLE_CLASS);
```

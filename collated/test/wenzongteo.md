# wenzongteo
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @BeforeClass
    public static void setup() {
        String imageFilePath = "data/images/";
        File imageFolder = new File(imageFilePath);

        if (!imageFolder.exists()) {
            imageFolder.mkdirs();
        } else {

        }

        try {
            Files.copy(Paths.get("default.jpeg"), Paths.get("data/images/amy@example.com.jpg"),
                    StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get("default.jpeg"), Paths.get("data/images/bob@example.com.jpg"),
                    StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get("default.jpeg"), Paths.get("data/images/alice@example.com.jpg"),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new AssertionError("Nope");
        }
    }

```
###### \java\seedu\address\model\person\PhotoTest.java
``` java
public class PhotoTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        ImageInit.checkDirectories();
        ImageInit.initPictures();
    }

    @AfterClass
    public static void recovery() {
        ImageInit.deleteEditedFiles();
        ImageInit.deleteImagesFiles();
    }

    @Test
    public void isValidPhoto() {
        // invalid photos
        assertFalse(Photo.isValidPhoto("")); // empty string
        assertFalse(Photo.isValidPhoto(" ")); // spaces only
        assertFalse(Photo.isValidPhoto("91")); // digits only
        assertFalse(Photo.isValidPhoto("Photo")); // alphabet only
        assertFalse(Photo.isValidPhoto("9011p041")); // alphabets within digits
        assertFalse(Photo.isValidPhoto("9312 1534")); // spaces between digits
        assertFalse(Photo.isValidPhoto("abc abc")); // spaces between words
        assertFalse(Photo.isValidPhoto("data/default")); //No file type.
        assertFalse(Photo.isValidPhoto("data/default.mp3")); //Invalid file type
        assertFalse(Photo.isValidPhoto(".jpeg")); //No file name

        // valid Photo
        assertTrue(Photo.isValidPhoto("docs/images/default.jpeg")); // end with .jpeg file extension
        assertTrue(Photo.isValidPhoto("docs/images/ritchie.jpg")); // end with .jpg file extension
        assertTrue(Photo.isValidPhoto("docs/images/yl_coder.jpg")); // file name with special character _
        assertTrue(Photo.isValidPhoto("docs/images/m133225.jpg")); // file name with digits
        assertTrue(Photo.isValidPhoto("docs/images/prof damith.jpg")); //file name with space in between
    }

    @Test
    public void photoDoesNotExist() {
        try {
            new Photo("doesnotexist.jpg");
        } catch (IllegalValueException ioe) {
            assertEquals(Photo.MESSAGE_PHOTO_NOT_FOUND , ioe.getMessage());
        }
        try {
            new Photo("doesnotexist.jpg", 0);
        } catch (AssertionError ae) {
            assertEquals("Photo should already exist!", ae.getMessage());
        }
    }

    @Test
    public void photoFailCheck() {
        try {
            new Photo("wrong format");
        } catch (IllegalValueException ive) {
            assertEquals(Photo.MESSAGE_PHOTO_CONSTRAINTS, ive.getMessage());
        }
    }

    @Test
    public void photoHashCode() {
        try {
            Photo photo1 = new Photo("default.jpeg", 0);
            Photo photo2 = new Photo("data/images/alice@example.com.jpg");

            //hashcode matches for same email --> return true
            assertTrue(photo1.hashCode() == photo1.hashCode());

            //hashcode don't match for different email --> return false
            assertFalse(photo1.hashCode() == photo2.hashCode());

        } catch (IllegalValueException e) {
            assert false : "shouldn't hit this case at all";
        }
    }

    @Test
    public void compareHash() {
        try {
            Photo photo1 = new Photo("default.jpeg");
            Photo photo2 = new Photo("docs/images/wz.jpg", 0);

            //Compare hash of the same photo --> return true
            assertTrue(photo1.getHash().equals(photo1.getHash()));

            //Compare hash of different photos --> return false
            assertFalse(photo1.getHash().equals(photo2.getHash()));

        } catch (IllegalValueException ive) {
            assert false : "Not possible";
        }
    }

    @Test
    public void compareString() {
        try {
            Photo photo1 = new Photo("default.jpeg");
            Photo photo2 = new Photo("docs/images/wz.jpg", 0);

            //Compare String value of the same photo --> return true
            assertTrue(photo1.toString().equals(photo1.toString()));

            //Compare String value of different photos --> return false
            assertFalse(photo1.toString().equals(photo2.toString()));
        } catch (IllegalValueException ive) {
            assert false : "Not possible";
        }
    }

    @Test
    public void equals() {
        try {
            Photo photo1 = new Photo("default.jpeg");
            Photo photo2 = new Photo("docs/images/wz.jpg", 0);

            //Compare String value of the same photo --> return true
            assertTrue(photo1.equals(photo1));

            //Compare String value of different photos --> return false
            assertFalse(photo1.equals(photo2));
        } catch (IllegalValueException ive) {
            assert false : "Not possible";
        }
    }
}
```
###### \java\seedu\address\testutil\ImageInit.java
``` java

/**
 * Class that provides methods to be executed before or after tests to ensure a smooth execution of the tests.
 */
public class ImageInit {
    private static final String EDITED_FOLDER_PATH = "data/edited";
    private static final String IMAGES_FOLDER_PATH = "data/images";
    private static final String DEFAULT_ORIGINAL_PATH = "default.jpeg";
    private static final String ALICE_PHOTO_PATH = "data/images/alice@example.com.jpg";
    private static final String DEFAULT_PHOTO_PATH = "data/images/default.jpeg";
    private static final String JOHN_PHOTO_PATH = "data/images/johnd@example.com.jpg";
    private static final String HEINZ_PHOTO_PATH = "data/images/heinz@example.com.jpg";
    private static final String ANNA_PHOTO_PATH = "data/images/anna@example.com.jpg";
    private static final String STEFAN_PHOTO_PATH = "data/images/stefan@example.com.jpg";
    private static final String HANS_PHOTO_PATH = "data/images/hans@example.com.jpg";
    private static final String AMY_PHOTO_PATH = "data/images/amy@example.com.jpg";
    private static final String CORNELIA_PHOTO_PATH = "data/images/cornelia@example.com.jpg";
    private static final String WERNER_PHOTO_PATH = "data/images/werner@example.com.jpg";
    private static final String LYDIA_PHOTO_PATH = "data/images/lydia@example.com.jpg";
    private static final String BOB_PHOTO_PATH = "data/images/bob@example.com.jpg";

    ImageInit() {
    }

    /**
     * Check if data/images and data/edited folder exist. If they do not exist, create them
     *
     * @throws IOException if encounter error in creating the folders.
     */
    public static void checkDirectories() {
        try {
            FileUtil.createDirs(new File(EDITED_FOLDER_PATH));
            FileUtil.createDirs(new File(IMAGES_FOLDER_PATH));
        } catch (IOException ioe) {
            throw new AssertionError("Error Creating File!");
        } catch (Exception e) {
            throw new AssertionError("No such error should exist!");
        }
    }

    /**
     *  Initialize photos of all possible contacts in PersonBuilder to ensure the photo is available before the start
     *  of a test.
     *
     *  @throws IOException if file does not exist.
     */
    public static void initPictures() {
        try {
            Files.copy(Paths.get(DEFAULT_ORIGINAL_PATH), Paths.get(ALICE_PHOTO_PATH),
                    StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get(DEFAULT_ORIGINAL_PATH), Paths.get(JOHN_PHOTO_PATH),
                    StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get(DEFAULT_ORIGINAL_PATH), Paths.get(HEINZ_PHOTO_PATH),
                    StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get(DEFAULT_ORIGINAL_PATH), Paths.get(ANNA_PHOTO_PATH),
                    StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get(DEFAULT_ORIGINAL_PATH), Paths.get(STEFAN_PHOTO_PATH),
                    StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get(DEFAULT_ORIGINAL_PATH), Paths.get(HANS_PHOTO_PATH),
                    StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get(DEFAULT_ORIGINAL_PATH), Paths.get(AMY_PHOTO_PATH),
                    StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get(DEFAULT_ORIGINAL_PATH), Paths.get(CORNELIA_PHOTO_PATH),
                    StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get(DEFAULT_ORIGINAL_PATH), Paths.get(WERNER_PHOTO_PATH),
                    StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get(DEFAULT_ORIGINAL_PATH), Paths.get(LYDIA_PHOTO_PATH),
                    StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get(DEFAULT_ORIGINAL_PATH), Paths.get(BOB_PHOTO_PATH),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new AssertionError("Initialization of images failed!");
        } catch (Exception e) {
            throw new AssertionError("No such error possible");
        }
    }

    /**
     * Initialize alice's photo during the test.
     */
    public static void initAlice() {
        try {
            Files.copy(Paths.get(DEFAULT_ORIGINAL_PATH), Paths.get(ALICE_PHOTO_PATH),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new AssertionError("Initialization of Alice's image failed");
        } catch (Exception e) {
            throw new AssertionError("No such error possible");
        }
    }

    /**
     * Initialize john's photo during the test.
     */
    public static void initJohn() {
        try {
            Files.copy(Paths.get(DEFAULT_ORIGINAL_PATH), Paths.get(JOHN_PHOTO_PATH),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new AssertionError("Initialization of John's image failed");
        } catch (Exception e) {
            throw new AssertionError("No such error possible");
        }
    }

    /**
     * Initialize default photo during the test.
     */
    public static void initDefault() {
        try {
            Files.copy(Paths.get(DEFAULT_ORIGINAL_PATH), Paths.get(DEFAULT_PHOTO_PATH),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new AssertionError("Initialization of default image failed");
        } catch (Exception e) {
            throw new AssertionError("No such error possible");
        }
    }

    /**
     * Delete all files inside data/edited folder before deleting the data/edited folder.
     */
    public static void deleteEditedFiles() {
        File toBeDeletedFolder = new File("data/edited");
        File[] toBeDeletedImages = toBeDeletedFolder.listFiles();
        if (toBeDeletedImages != null) {
            for (File f : toBeDeletedImages) {
                f.delete();
            }
        }
        toBeDeletedFolder.delete();
    }

    /**
     * Delete all files inside data/images folder before deleting the data/images folder.
     */
    public static void deleteImagesFiles() {
        File toBeDeletedFolder = new File("data/images");
        File[] toBeDeletedImages = toBeDeletedFolder.listFiles();
        if (toBeDeletedImages != null) {
            for (File f : toBeDeletedImages) {
                f.delete();
            }
        }
        toBeDeletedFolder.delete();
    }

    /**
     * Check if Photo for Alice exists.
     */
    public static boolean checkAlicePhoto() {
        File alicePhoto = new File(ALICE_PHOTO_PATH);
        return FileUtil.isFileExists(alicePhoto);
    }

    /**
     * Check if Photo for John exists.
     */
    public static boolean checkJohnPhoto() {
        File johnPhoto = new File(JOHN_PHOTO_PATH);
        return FileUtil.isFileExists(johnPhoto);
    }

    /**
     * Check if Photo for Anna exists.
     */
    public static boolean checkAnnaPhoto() {
        File annaPhoto = new File(ANNA_PHOTO_PATH);
        return FileUtil.isFileExists(annaPhoto);
    }

    /**
     * Check if Photo for Heinz exists.
     */
    public static boolean checkHeinzPhoto() {
        File heinzPhoto = new File(HEINZ_PHOTO_PATH);
        return FileUtil.isFileExists(heinzPhoto);
    }

    /**
     * Check if Photo for Lydia exists.
     */
    public static boolean checkLydiaPhoto() {
        File lydiaPhoto = new File(LYDIA_PHOTO_PATH);
        return FileUtil.isFileExists(lydiaPhoto);
    }
}
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
    @BeforeClass
    public static void setup() throws Exception {
        ImageInit.checkDirectories();
        ImageInit.initPictures();
    }

    @AfterClass
    public static void recovery() throws Exception {
        ImageInit.deleteEditedFiles();
        ImageInit.deleteImagesFiles();
    }

```

package seedu.address.testutil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import seedu.address.commons.util.FileUtil;

//@@author wenzongteo

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

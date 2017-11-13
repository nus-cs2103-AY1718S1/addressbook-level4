package seedu.address.testutil;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.PhotoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting string.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static String getFilePathInSandboxFolder(String fileName) {
        try {
            FileUtil.createDirs(new File(SANDBOX_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER + fileName;
    }

    /**
     * Returns the middle index of the person in the {@code model}'s person list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getAddressBook().getPersonList().size() / 2);
    }

    /**
     * Returns the last index of the person in the {@code model}'s person list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getAddressBook().getPersonList().size());
    }

    /**
     * Returns the person in the {@code model}'s person list at {@code index}.
     */
    public static ReadOnlyPerson getPerson(Model model, Index index) {
        return model.getAddressBook().getPersonList().get(index.getZeroBased());
    }

    //@@author April0616-unused
    /**
     * Removes the temporary test files and parents if they exist till the root of the path.
     * @param pathString of the file
     * @throws IOException
     */
    public static void removeFileAndItsParentsTillRoot(String pathString) throws IOException {
        Path path = Paths.get(pathString);
        if (path.toString().matches("[a-zA-Z]+[:]+[\\\\]") || path == null) {
            return;
        } else if (Files.isRegularFile(path)) {
            Files.deleteIfExists(path);

        }

        if (Files.isDirectory(path)) {
            try {
                Files.delete(path);
            } catch (DirectoryNotEmptyException e) {
                return;
            }
        }
        String parentPathString = path.getParent().toString();
        //use recursion here
        removeFileAndItsParentsTillRoot(parentPathString);
    }

    /**
     * If the file in the specific path in the app directory exists, delete the file in the path
     * @param path
     */
    public static void removeAppFile(String path) {
        File fileToDelete = new File(path);
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

    /**
     * Creates temporary folders and a photo file for parseCommand_photo() test, i.e.,
     * "C:\\family\\photo.jpg"
     */
    public static void createTempFile(String path) {
        if (!PhotoCommand.isValidLocalPhotoPath(path)) {
            assert false : "Invalid photo path!";
            return;
        }
        final String photoPath = path;
        File testFile = new File(photoPath);

        //create new test file
        testFile.getParentFile().mkdirs();
        try {
            testFile.createNewFile();
        } catch (IOException e) {
            assert false : "Cannot open or write file!";
        }

    }
}

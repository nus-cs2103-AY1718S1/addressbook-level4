package seedu.address.commons.util;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Writes and reads files
 */
public class FileUtil {
    //@@author April0616
    public static final String REGEX_VALID_IMAGE = "([^\\s]+(\\.(?i)(jpg|jpeg|png|gif|bmp))$)";
    private static final String CHARSET = "UTF-8";
    /**
     * Checks whether the file is a valid image file.
     * A valid image file should have extension "jpg", "jpeg", "png", "gif" or "bmp".
     * @param photoPath of the image
     * @return true if it has specified extension
     */
    public static Boolean isValidImageFile(String photoPath) {
        return photoPath.matches(REGEX_VALID_IMAGE);
    }

    /**
     * Gets the extension of the file path by split the path string by regex "."
     * @param filePath
     * @return extension string
     */
    public static String getFileExtension(String filePath) {
        return "." + filePath.split("\\.")[1];
    }

    /**
     * Checks whether the specified file is in the specified folder.
     * @param filePath of the file to be checked
     * @param folderPath of the folder
     * @return true if the file is in the folder
     */
    public static Boolean isInFolder(String filePath, String folderPath) {
        return filePath.startsWith(folderPath);
    }
    /**
     * Copies all the contents from the file in original path to the one in destination path.
     * @param oriPath of the file to be copied
     * @param destPath of the file to be pasted
     * @return true if the file is successfully copied to the specified place.
     */
    public static boolean copyFile(String oriPath, String destPath) throws IOException {

        //create a buffer to store content
        byte[] buffer = new byte[1024];

        //bufferedInputStream
        FileInputStream fis = new FileInputStream(oriPath);
        BufferedInputStream bis = new BufferedInputStream(fis);

        //bufferedOutputStream
        FileOutputStream fos = new FileOutputStream(destPath);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        int numBytes = bis.read(buffer);
        while (numBytes > 0) {
            bos.write(buffer, 0, numBytes);
            numBytes = bis.read(buffer);
        }

        //close input,output stream
        bis.close();
        bos.close();

        return true;
    }

    /**
     * Removes the file in the app if it exists.
     * @param path of the file to be deleted
     */
    public static void removeAppFile(String path) {
        File fileToDelete = new File(path);
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

    /**
     * Checks whether two files have the same content.
     * @param firstPath path of one file
     * @param secondPath path of another file
     * @return true if they have the same content, false otherwise
     * @throws IOException if an I/O error occurs reading from the stream
     */
    public static boolean haveSameContent(String firstPath, String secondPath) {
        Path p1 = Paths.get(firstPath);
        Path p2 = Paths.get(secondPath);

        byte[] firstFileBytes = new byte[0];
        try {
            firstFileBytes = Files.readAllBytes(p1);
        } catch (IOException e) {
            assert false : "An I/O error occurs reading from the stream.";
        }

        byte[] secondFileBytes = new byte[0];
        try {
            secondFileBytes = Files.readAllBytes(p2);
        } catch (IOException e) {
            assert false : "An I/O error occurs reading from the stream.";
        }
        return Arrays.equals(firstFileBytes, secondFileBytes);
    }
    //@@author

    public static boolean isFileExists(File file) {
        return file.exists() && file.isFile();
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     * @throws IOException if the file or directory cannot be created.
     */
    public static void createIfMissing(File file) throws IOException {
        if (!isFileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories
     *
     * @return true if file is created, false if file already exists
     */
    public static boolean createFile(File file) throws IOException {
        if (file.exists()) {
            return false;
        }

        createParentDirsOfFile(file);

        return file.createNewFile();
    }

    /**
     * Creates the given directory along with its parent directories
     *
     * @param dir the directory to be created; assumed not null
     * @throws IOException if the directory or a parent directory cannot be created
     */
    public static void createDirs(File dir) throws IOException {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to make directories of " + dir.getName());
        }
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(File file) throws IOException {
        File parentDir = file.getParentFile();

        if (parentDir != null) {
            createDirs(parentDir);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()), CHARSET);
    }

    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(File file, String content) throws IOException {
        Files.write(file.toPath(), content.getBytes(CHARSET));
    }

    /**
     * Converts a string to a platform-specific file path
     * @param pathWithForwardSlash A String representing a file path but using '/' as the separator
     * @return {@code pathWithForwardSlash} but '/' replaced with {@code File.separator}
     */
    public static String getPath(String pathWithForwardSlash) {
        checkArgument(pathWithForwardSlash.contains("/"));
        return pathWithForwardSlash.replace("/", File.separator);
    }

}

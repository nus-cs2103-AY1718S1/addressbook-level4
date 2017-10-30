package seedu.address.commons.util;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Writes and reads files
 */
public class FileUtil {

    private static final String CHARSET = "UTF-8";
    private static final Pattern XML_FILE_FORMAT = Pattern.compile(".*\\.xml$");
    private static final Pattern UNIX_NAME_SEPARATOR_FORMAT = Pattern.compile(".*/.*");
    private static final Pattern WINDOWS_NAME_SEPARATOR_FORMAT = Pattern.compile(".*\\\\.*");
    private static final Pattern INVALID_NAME_CHARACTERS_FORMAT = Pattern.compile(".*[?!%*+:|\"<>].*");
    private static final Pattern CONSECUTIVE_NAME_SEPARATOR_FORMAT = Pattern.compile("(.*//.*)|(.*\\\\\\\\.*)");
    private static final Pattern CONSECUTIVE_EXTENSION_SEPARATOR_FORMAT = Pattern.compile(".*\\.\\..*");

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

    /**
     * Checks whether the file specified in the {@code filePath} is a valid XML file
     */
    public static boolean isValidXmlFile(String filePath) {
        return XML_FILE_FORMAT.matcher(filePath.toLowerCase()).matches();
    }

    /**
     * Checks whether the {@code filePath} contain any invalid name separators (OS-dependent)
     */
    public static boolean hasInvalidNameSeparators(String filePath) {
        Matcher unixMatcher = UNIX_NAME_SEPARATOR_FORMAT.matcher(filePath);
        Matcher windowsMatcher = WINDOWS_NAME_SEPARATOR_FORMAT.matcher(filePath);

        if (unixMatcher.matches() && File.separator.equals("\\")
            || windowsMatcher.matches() && File.separator.equals("/")) {
            return true;
        } else {
            return false;
        }
        return unixMatcher.matches() && File.separator.equals("\\")
                || windowsMatcher.matches() && File.separator.equals("/");
    }

    /**
     * Checks whether the non-existent file name and folder names in {@filePath} are valid
     */
    public static boolean hasInvalidNonExistentNames(String filePath) {
        File file = new File(filePath);
        // taking account into relative paths with non-existent parent folders
        if (!file.isAbsolute()) {
            String userDir = System.getProperty("user.dir");
            file = new File(userDir + File.separator + filePath);
        }

        File parentFile = file.getParentFile();
        while (!parentFile.exists()) {
            parentFile = parentFile.getParentFile();
        }
        String nonExistentNames = file.getAbsolutePath().substring(parentFile.getAbsolutePath().length());

        return INVALID_NAME_CHARACTERS_FORMAT.matcher(nonExistentNames).matches();
    }

    /**
     * Checks whether the {@filePath} contain any consecutive name separators (OS-dependent)
     *
     * {@link #hasInvalidNameSeparators(String)} should be checked prior this method
     */
    public static boolean hasConsecutiveNameSeparators(String filePath) {
        return CONSECUTIVE_NAME_SEPARATOR_FORMAT.matcher(filePath).matches();
    }

    /**
     * Checks whether the {@filePath} contain any consecutive extension separators (.)
     *
     */
    public static boolean hasConsecutiveExtensionSeparators(String filePath) {
        return CONSECUTIVE_EXTENSION_SEPARATOR_FORMAT.matcher(filePath).matches();
    }
}

# aali195
###### /java/seedu/address/commons/util/CompressUtil.java
``` java
package seedu.address.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Handles the compression of directories
 */
public class CompressUtil {
    private static List<String> fileList = new ArrayList<>();

    /**
     * Run the compression of the directory
     * @param source
     * @param path
     */
    public static void run(String source, String path) throws IOException {
        String dir = source;
        String zipFile = "Data.zip";

        compressDirectory(dir, zipFile);
    }

    /**
     * Compresses the directory
     * @param dir is the directory to compress
     * @param zipFile is the final zipped file
     */
    private static void compressDirectory(String dir, String zipFile) throws IOException {
        File directory = new File(dir);
        getFileList(directory);


        FileOutputStream fos  = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(fos);

        for (String filePath : fileList) {
            String name = filePath.substring(directory.getAbsolutePath().length() + 1, filePath.length());
            ZipEntry zipEntry = new ZipEntry(name);
            zos.putNextEntry(zipEntry);

            FileInputStream fis = new FileInputStream(filePath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
            fis.close();
        }

        zos.close();
        fos.close();
    }

    /**
     * Get files list from the directory recursive to the sub directory.
     * @param directory to go through
     */
    private static void getFileList(File directory) {
        File[] files = directory.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    fileList.add(file.getAbsolutePath());
                } else {
                    getFileList(file);
                }
            }
        }
    }
}
```
###### /java/seedu/address/commons/util/ImageUtil.java
``` java
package seedu.address.commons.util;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_IMAGE;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import seedu.address.logic.commands.ImageCommand;
import seedu.address.commons.exceptions.ImageException;

/**
 * Handles the IO for editing persons images
 */
public class ImageUtil {

    /**
     * Reads the image using the provided path if correct and stores it locally
     * @param path of the file
     * @return uniquePath of the new local image
     * @throws IOException if the path is missing
     */
    public String run(String path, int newPath) throws IOException, ImageException {
        File fileToRead;
        BufferedImage image;

        File fileToWrite;

        String uniquePath;

        try {
            fileToRead = new File(path);
            image = ImageIO.read(fileToRead);

            uniquePath = Integer.toString(newPath);

            fileToWrite = new File("data/" + uniquePath + ".png");
            ImageIO.write(image, "png", fileToWrite);

        } catch (IOException e) {
            throw  new ImageException(String.format(MESSAGE_INVALID_IMAGE, ImageCommand.MESSAGE_IMAGE_PATH_FAIL));
        }

        return uniquePath;
    }

}
```
###### /java/seedu/address/logic/commands/ExportCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.CompressUtil;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * This command is used to export a compressed version of the working addressbook and the images saved
 */

public class ExportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports a compressed copy of the working addressbook  "
            + "Existing files will be replaced.\n"
            + "Parameters: [FILEPATH] \n"
            + "Example: " + COMMAND_WORD + " C:\\Users\\Admin\\Desktop\\.";

    public static final String MESSAGE_EXPORT_PATH_FAIL =
            "This specified path cannot be read.";

    public static final String MESSAGE_EXPORT_SUCCESS = "Addressbook has been exported.";

    private String path;
    private String source = "data";

    public ExportCommand(String path) {
        requireNonNull(path);

        this.path = path;
    }

    /**
     * Executes the command
     * @return a success message
     * @throws CommandException
     */
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            CompressUtil.run(source, path);
        } catch (Exception e) {
            return new CommandResult(generateFailureMessage());
        }
        return new CommandResult(generateSuccessMessage());
    }

    /**
     * Generates success messages
     * @return Message
     */
    private String generateSuccessMessage() {
        return MESSAGE_EXPORT_SUCCESS;
    }

    /**
     * Generates failure messages
     * @return Message
     */
    private String generateFailureMessage() {
        return MESSAGE_EXPORT_PATH_FAIL;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExportCommand)) {
            return false;
        }

        // state check
        ExportCommand e = (ExportCommand) other;
        return path.equals(e.path);
    }
}

```
###### /java/seedu/address/logic/parser/exceptions/ImageException.java
``` java
package seedu.address.logic.parser.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents an image error encountered by a parser.
 */
public class ImageException extends IllegalValueException {

    public ImageException(String message) {
        super(message);
    }

    public ImageException(String message, Throwable cause) {
        super(message, cause);
    }
}
```
###### /java/seedu/address/logic/parser/ExportCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser for the export command
 */
public class ExportCommandParser implements Parser<ExportCommand> {


    @Override
    public ExportCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        String path = userInput;
        return new ExportCommand(path);
    }
}

```
###### /java/seedu/address/logic/parser/ImageCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ImageCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Image;

/**
 * Parser for the image command
 */
public class ImageCommandParser implements Parser<ImageCommand> {


    @Override
    public ImageCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        String regex = "[\\s]+";
        String[] splitArgs = userInput.trim().split(regex, 2);
        Index index;

        try {
            index = ParserUtil.parseIndex(splitArgs[0]);
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImageCommand.MESSAGE_USAGE));
        }

        String path;
        if (splitArgs.length > 1) {
            path = splitArgs[1];
        } else {
            path = "";
        }

        return new ImageCommand(index, new Image(path));
    }
}
```
###### /java/seedu/address/model/person/Image.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents the path of an image for a Person in the address book
 */
public class Image {

    private String path;

    public Image(String path) {
        requireNonNull(path);
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Image // instanceof handles nulls
                && this.path.equals(((Image) other).path)); // state check
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }
}
```
###### /java/seedu/address/model/UserPrefs.java
``` java
    public String getAddressBookImagesPath() {
        return addressBookImagesPath;
    }

    public void setAddressBookImagesPath(String addressBookImagesPath) {
        this.addressBookImagesPath = addressBookImagesPath;
    }
```
###### /java/seedu/address/storage/ImageStorage.java
``` java
package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.util.FileUtil;

/**
 * To create a display picture resource folder
 */
public class ImageStorage {

    private String filePath;

    public ImageStorage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns the file path of the image folder.
     */
    public String getAddressBookImagePath() {
        return filePath;
    }

    /**
     * Creates a new folder for image storage
     */
    public void createImageStorageFolder() throws IOException {
        requireNonNull(filePath);

        File file  = new File(filePath);
        FileUtil.createIfMissing(file);

    }
}
```

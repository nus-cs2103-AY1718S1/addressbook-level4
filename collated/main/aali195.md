# aali195

###### \java\seedu\address\commons\exceptions\ImageException.java
``` java
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

###### \java\seedu\address\commons\util\CompressUtil.java
``` java
/**
 * Handles the compression of directories
 */
public class CompressUtil {

    /**
     * Run the compression of the directory
     * @param source of directory to compress
     * @param destPath to save to
     * @throws Exception if problems with IO are encountered
     */
    public static void run(String source, String destPath) throws Exception {
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;
        String destZipFile;

        if (destPath.equals("")) {
            destZipFile = "AddressbookData.zip";
        } else {
            destZipFile = destPath + ".zip";
        }

        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);

        addFolderToZip("", source, zip);
        zip.flush();
        zip.close();
    }

    /**
     * Add files to the zip file and write it out
     * @param path is the current directory path
     * @param source is the directory to zip
     * @param zip is the final zipped file
     * @throws Exception if problems with IO are encountered
     */
    private static void addFileToZip(String path, String source, ZipOutputStream zip) throws Exception {
        File folder = new File(source);
        if (folder.isDirectory()) {
            addFolderToZip(path, source, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(source);
            zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
        }
    }

    /**
     * Add directories to the zip file
     * @param path is the current directory path
     * @param source is the directory to zip
     * @param zip is the final zipped file
     * @throws Exception if problems with IO are encountered
     */
    private static void addFolderToZip(String path, String source, ZipOutputStream zip)
            throws Exception {
        File folder = new File(source);

        for (String fileName : folder.list()) {
            if (path.equals("")) {
                addFileToZip(folder.getName(), source + "/" + fileName, zip);
            } else {
                addFileToZip(path + "/" + folder.getName(), source + "/" + fileName, zip);
            }
        }
    }
}
```
###### \java\seedu\address\commons\util\ImageUtil.java
``` java
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
        String imageDirectory = "data/";
        checkDirectory(imageDirectory);

        File fileToRead;
        BufferedImage image;

        File fileToWrite;

        String uniquePath;

        try {
            fileToRead = new File(path);
            image = ImageIO.read(fileToRead);

            uniquePath = Integer.toString(newPath);

            fileToWrite = new File(imageDirectory + uniquePath + ".png");
            ImageIO.write(image, "png", fileToWrite);

        } catch (IOException e) {
            throw  new ImageException(String.format(MESSAGE_INVALID_IMAGE, ImageCommand.MESSAGE_IMAGE_PATH_FAIL));
        }

        return uniquePath;
    }

    /**
     * Creates a directory if it does not exist
     * @param imageDirectory to be checked and created
     */
    private void checkDirectory(String imageDirectory) throws IOException {
        Path path = Paths.get(imageDirectory);
        if (!Files.exists(path)) {
            Files.createDirectories(Paths.get(imageDirectory));
        }
    }

}
```
###### \java\seedu\address\logic\commands\ExportCommand.java
``` java
/**
 * This command is used to export a compressed version of the working addressbook and the images saved
 */

public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports a compressed copy of the working addressbook. "
            + "Existing files will be replaced.\n"
            + "Parameters: [FILEPATH] \n"
            + "Example: " + COMMAND_WORD + " C:\\Users\\Admin\\Desktop\\NameOfFile\n"
            + "[FILEPATH] can be omitted to export to the application directory as \"AddressbookData\".";

    public static final String MESSAGE_EXPORT_PATH_FAIL =
            "This specified path cannot be read.";

    public static final String MESSAGE_EXPORT_SUCCESS = "Addressbook has been exported.";

    private String path;
    private String source = "data/";

    public ExportCommand(String path) {
        requireNonNull(path);

        this.path = path;
    }

    /**
     * Executes the command
     * @return a success message
     * @throws CommandException
     */
    public CommandResult execute() throws CommandException {
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

###### \java\seedu\address\logic\commands\ImageCommand.java
``` java
    public static final String COMMAND_WORD = "image";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds/updates the image of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing image will be updated by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_IMAGE + "[FILEPATH]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "C:\\Users\\Admin\\Desktop\\pic.jpg.";

    public static final String MESSAGE_IMAGE_PATH_FAIL =
            "This specified path cannot be read.";

    public static final String MESSAGE_ADD_IMAGE_SUCCESS = "Added image for person: %1$s";
    public static final String MESSAGE_DELETE_IMAGE = "Deleted image from person:  %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists";

    private Index index;
    private Image image;

    public ImageCommand(Index index, Image image) {
        requireNonNull(index);
        requireNonNull(image);

        this.image = image;
        this.index = index;
    }
```
###### \java\seedu\address\logic\commands\ImageCommand.java
``` java
    /**
     * Generates success messages
     * @param personToEdit
     * @return Message and the person
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!image.getPath().isEmpty()) {
            return String.format(MESSAGE_ADD_IMAGE_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_IMAGE, personToEdit);
        }
    }

    /**
     * Generates failure messages
     * @return Message
     */
    private String generateFailureMessage() {
        return MESSAGE_IMAGE_PATH_FAIL;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof ImageCommand)) {
            return false;
        }

        // state check
        ImageCommand e = (ImageCommand) other;
        return index.equals(e.index)
                && image.equals(e.image);
    }
```
###### /java/seedu/address/logic/parser/ExportCommandParser.java
``` java
/**
 * Parser for the export command
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    @Override
    public ExportCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        String regex = "[\\s]+";
        String[] splitArgs = userInput.trim().split(regex, 2);

        String path;

        if (splitArgs.length > 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        } else {
            path = splitArgs[0];
        }
        return new ExportCommand(path);
    }
}
```
###### \java\seedu\address\logic\parser\ImageCommandParser.java
``` java
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
###### \java\seedu\address\model\person\Image.java
``` java
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
